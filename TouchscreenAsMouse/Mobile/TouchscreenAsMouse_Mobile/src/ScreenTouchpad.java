

import java.io.IOException;
import java.io.OutputStream;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;
public class ScreenTouchpad extends MIDlet implements CommandListener{
        OutputStream out;
        Keysend m;
	protected String UUID = new UUID("1101", true).toString();
        Display display;
	protected int discoveryMode = DiscoveryAgent.GIAC; // no paring needed
        int button;
	protected int endToken = 255;
        Command start;
        Command exit;
	protected Form infoArea = new Form("Bluetooth Server");
	protected void startApp() throws MIDletStateChangeException {
            m=new Keysend(this);
            display=Display.getDisplay(this);
            this.initSensor();
            start=new Command("start",Command.SCREEN,1);
            exit=new Command("exit",Command.EXIT,1);
            infoArea.addCommand(exit);
            infoArea.addCommand(start);
            
	}

	protected void pauseApp() {
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
	}

	private void serverLoop(StreamConnectionNotifier notifier) {
		try {
			while (true) { // infinite loop to accept connections.
				log("Waiting for connection...");
				handleConnection(notifier.acceptAndOpen());
			}
		} catch (Exception e) {
			log(e);
		}
	}

	synchronized private void handleConnection(StreamConnection conn) throws IOException {
		out = conn.openOutputStream();
                log("connection open ready to write....");
                display.setCurrent(m);
                try
                {
                    while(true)
                        wait();
                }
                catch(Exception e)
                {

                }


		
	}
	synchronized private void log(String msg) {
		infoArea.append(msg);
		infoArea.append("\n\n");
	}

	private void log(Throwable e) {
		log(e.getMessage());
	}
        void send(int i)
    {
            try
                {
                log("keypressed");
                    out.write(i);
                    out.flush();
                }
                catch(Exception e)
                {

                }

        }
        public void commandAction(Command c,Displayable d)
        {
                    if(c==start)
                    {
                            this.initSensor();
                    }
                    else
                    {
                        try
                        {
                                this.destroyApp(true);
                                this.notifyDestroyed();
                        }
                        catch(Exception e){}
                    }
        }
        void initSensor()
         {

		infoArea.deleteAll();
		display.setCurrent(infoArea);
		try {
			LocalDevice device = LocalDevice.getLocalDevice();
			device.setDiscoverable(DiscoveryAgent.GIAC);

			String url = "btspp://localhost:" + UUID + ";name=DeviceServerCOMM";

			log("Create server by uri: " + url);
			StreamConnectionNotifier notifier = (StreamConnectionNotifier) Connector
					.open(url);

			serverLoop(notifier);

		} catch (Throwable e) {
			log(e);
		}
        }

     


}
