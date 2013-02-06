
import java.io.*;
import java.net.*;


public class ScratchIO
{
  private static final int SCRATCH_PORT = 42001;
  private static final int NUM_BYTES_SIZE = 4;


  private Socket scratchSocket;
  private InputStream in = null;
  private OutputStream out = null;


  public ScratchIO()
  {
    try {
      scratchSocket = new Socket("localhost", SCRATCH_PORT);
      in = scratchSocket.getInputStream();
      out = scratchSocket.getOutputStream();
    }
    catch (UnknownHostException e) {
      System.err.println("Scratch port (" + SCRATCH_PORT + ") not found");
      System.exit(1);
    }
    catch (IOException e) {
      System.err.println("Scratch IO link could not be created");
      System.exit(1);
    }
  }  // end of ScratchIO()


  public void closeDown()
  {
    InputStream in = null;
    OutputStream out = null;
    try {
      scratchSocket.close();
    }
    catch (IOException e) {}
  }  // end of closeDown()



  public boolean broadcastMsg(String msg)

  {
    String scratchMsg = "broadcast \"" + msg + "\"";
    return sendMsg(scratchMsg);
  }  


  public boolean updateMsg(String name, String value)
  // send a sensor-update message
  {
    String scratchMsg = "sensor-update " + name + " " + value;
    return sendMsg(scratchMsg);
  }  // end of updateMsg()



  private boolean sendMsg(String msg)
 
  {
    if (out == null) {
      System.err.println("Output stream error");
      return false;
    }

    // System.out.println("Sending: " + msg);
    try {
      byte[] sizeBytes = intToByteArray( msg.length() );
      for (int i = 0; i < NUM_BYTES_SIZE; i++)                 
        out.write(sizeBytes[i]);
      out.write(msg.getBytes());
    }
    catch (IOException e) {
      System.err.println("Couldn't send: " + msg);
      return false;
    }
    return true;
  }  // end of sendMsg()



  private byte[] intToByteArray(int value)
  // convert an integer into a 4-element byte array
  { return new byte[] { 
            (byte)(value >>> 24), (byte)(value >> 16 & 0xff), 
            (byte)(value >> 8 & 0xff), (byte)(value & 0xff) };
  }


  // ------------- message reading methods --------------------

  public ScratchMessage readMsg()
 
  {
    if (in == null) {
      System.err.println("Input stream error");
      return null;
    }

    ScratchMessage scratchMsg = null;
    int msgSize = readMsgSize();
    if (msgSize > 0) {
	  try {
        byte[] buf = new byte[msgSize];
        in.read(buf, 0, msgSize);
        String msg = new String(buf); 
        // System.out.println("string: <" + msg + ">");
        scratchMsg = new ScratchMessage(msg);
      } 
      catch (IOException e) {
        System.err.println("Message read error: " + e);
        System.exit(0);
      }
    }
    return scratchMsg;
  }  // end of readMsg()


  private int readMsgSize()
 
  {
    int msgSize = -1;
	try {
      byte[] buf = new byte[NUM_BYTES_SIZE];
      in.read(buf, 0, NUM_BYTES_SIZE);
      msgSize = byteArrayToInt(buf);
      // System.out.println("message size: " + msgSize);
    } 
    catch (IOException e) {
      System.err.println("Header read error: " + e);
      System.exit(0);
    }
    return msgSize;
  }  // end of readMsgSize()



  private static int byteArrayToInt(byte [] b) 
  { 
    return (b[0] << 24) + ((b[1] & 0xFF) << 16) +
            ((b[2] & 0xFF) << 8) + (b[3] & 0xFF);
  }

}  

