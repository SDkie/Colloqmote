import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.Robot;

public class Bluetooth implements DiscoveryListener {
    

	static final boolean DEBUG = false;

	static final String DEBUG_address = "80501B580DD1"; // N6630

	protected UUID uuid = new UUID(0x1101); // serial port profile

	protected int inquiryMode = DiscoveryAgent.GIAC;

	protected int connectionOptions = ServiceRecord.NOAUTHENTICATE_NOENCRYPT;

	

	protected int stopToken = -1;



	protected JTextArea infoArea = null;

	protected Vector deviceList = new Vector();

	protected JComboBox combo;

	protected ItemListener comboSelectionListener = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			startServiceSearch((RemoteDevice) combo.getSelectedItem());
			combo.removeItemListener(this);
			combo.setEnabled(false);
		}
	};

	public Bluetooth() {
           
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				createGUI();

				if (DEBUG) // skip inquiry in debug mode
					startServiceSearch(new RemoteDevice(DEBUG_address) {
					});
				else
					startDeviceInquiry();
			}
		});
	}

	

	private void startDeviceInquiry() {
		try {
			deviceList.removeAllElements();
			log("Start inquiry method - this will take few seconds...");
			getAgent().startInquiry(inquiryMode, this);
		} catch (Exception e) {
			log(e);
		}
	}

	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
		log("A device discovered (" + getDeviceStr(btDevice) + ")");
		deviceList.addElement(btDevice);
		if (!combo.isEnabled()) {
			combo.setEnabled(true);
			combo.addItemListener(comboSelectionListener);
		}
	}

	public void inquiryCompleted(int discType) {
		log("Inquiry compeleted. Please select device from combo box.");
	}

	
	private void startServiceSearch(RemoteDevice device) {
		try {
			log("Start search for Serial Port Profile service from "
					+ getDeviceStr(device));
			UUID uuids[] = new UUID[] { uuid };
			getAgent().searchServices(null, uuids, device, this);
		} catch (Exception e) {
			log(e);
		}
	}

	
	public void servicesDiscovered(int transId, ServiceRecord[] records) {
		log("Service discovered.");
                String url=null;
		for (int i = 0; i < records.length; i++) {
			ServiceRecord rec = records[i];
			 url = rec.getConnectionURL(connectionOptions, false);
			
		}
                handleConnection(url);
	}

	public void serviceSearchCompleted(int transID, int respCode) {
		String msg = null;
		switch (respCode) {
		case SERVICE_SEARCH_COMPLETED:
			msg = "the service search completed normally";
			break;
		case SERVICE_SEARCH_TERMINATED:
			msg = "the service search request was cancelled by a call to DiscoveryAgent.cancelServiceSearch()";
			break;
		case SERVICE_SEARCH_ERROR:
			msg = "an error occurred while processing the request";
			break;
		case SERVICE_SEARCH_NO_RECORDS:
			msg = "no records were found during the service search";
			break;
		case SERVICE_SEARCH_DEVICE_NOT_REACHABLE:
			msg = "the device specified in the search request could not be reached or the local device could not establish a connection to the remote device";
			break;
		}
		log("Service search completed - " + msg);

		if (respCode == SERVICE_SEARCH_ERROR)
			startDeviceInquiry();
	}

	private void handleConnection(final String url) {
            	final Accelerometer framexyz = new Accelerometer(this);
                framexyz.connect=true;
	
                {
				StreamConnection stream = null;
				try {
					log("Connecting to server by url: " + url);
					stream = (StreamConnection) Connector.open(url);
                                        int flag=10000;
                                        log("Bluetooth stream open.");
					InputStream in = stream.openInputStream();
					OutputStream out = stream.openOutputStream();
                                        byte[] sen=new byte[2];
					log("Start echo loop.");
                                        framexyz.readxyz(in);

					
				} catch (IOException e) {
					log(e);
				} finally {
					log("Bluetooth stream closed.");
					if (stream != null) {
						try {
							stream.close();
						} catch (IOException e) {
							log(e);
						}
					}
				}
			}
		
		//echo.start();
	}

	

	private void createGUI() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Bluetooth Client");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.setSize(new Dimension(400, 400));
		JPanel margin = new JPanel(new BorderLayout());
		margin.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		frame.getContentPane().add(margin);
		infoArea = new JTextArea();
		infoArea.setLineWrap(true);
		JScrollPane pane = new JScrollPane(infoArea,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		margin.add(pane);

		combo = new JComboBox(this.deviceList);
		combo.setEnabled(false);
		combo.setEditable(false);
		combo.setSelectedItem(null);
		combo.setRenderer(new DefaultListCellRenderer() {
			public Component getListCellRendererComponent(JList list,
					Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				RemoteDevice remote = (RemoteDevice) value;
				if (remote == null)
					setText("No remote devices available.");
				else
					setText(getDeviceStr(remote));
				return this;
			}
		});
		margin.add(combo, BorderLayout.SOUTH);

		frame.setVisible(true);
	}

	 void log(String msg) {
		infoArea.insert(msg + "\n", infoArea.getDocument().getLength());
	}

	void log(Exception e) {
		log(e.getMessage());
		e.printStackTrace();
	}

	

	private DiscoveryAgent getAgent() {
		try {
			return LocalDevice.getLocalDevice().getDiscoveryAgent();
		} catch (BluetoothStateException e) {
			log(e);
			log("ERROR detected and all operations stopped.");
			throw new Error("No discovery agent available.");
		}
	}

	private String getDeviceStr(RemoteDevice btDevice) {
		return getFriendlyName(btDevice) + " - 0x"
				+ btDevice.getBluetoothAddress();
	}

	private String getFriendlyName(RemoteDevice btDevice) {
		try {
			return btDevice.getFriendlyName(false);
		} catch (IOException e) {
			return "no name available";
		}
	}

	public static void main(String[] args) {
		new Bluetooth();
	}

}