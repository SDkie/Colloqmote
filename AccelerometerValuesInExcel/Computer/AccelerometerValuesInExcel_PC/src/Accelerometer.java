import java.io.InputStream;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;

public class Accelerometer extends javax.swing.JFrame implements ChangeListener{
    boolean writetofile=true;
    Robot key;
    public boolean start=true;
    Bluetooth pcclient;
    boolean connect=false;
    int pointer;
WritableWorkbook workbook ;
    WritableSheet sheet;
    public Accelerometer(Bluetooth pcclient) {
         try
            {
            key=new Robot();
            }
            catch(Exception e)
            {
               // pcclient.log(e);
            }
        this.pcclient=pcclient;
        try
        {
            initComponents();// throws IOException, WriteException;
        }
        catch(Exception e)
        {
           // System.err.print(e);
        }
        this.setVisible(true);
       }

    
    @SuppressWarnings("unchecked")
   
    private void initComponents() throws IOException, WriteException {
        pointer=1;
        workbook = Workbook.createWorkbook(new File("F:/test.xls"));
        sheet = workbook.createSheet("First Sheet",0); 
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider(0,1000,1000);
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

    

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("   X                                         Y                                        Z   ");

        jTextField2.setEditable(false);
        jTextField2.setText(" 000");

        jTextField3.setEditable(false);
        jTextField3.setText("  000");

        jTextField4.setEditable(false);
        jTextField4.setText(" 000");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); 
        jLabel2.setText("Sensitivity:");

        jLabel3.setText("0%");

        jLabel4.setText("100%");

        jTextField5.setEditable(false);
        jTextField5.setText("100");

        jLabel5.setText("%");

        jButton1.setText("Pause");

        jSlider1.addChangeListener(this);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(98, 98, 98)
                                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel4))
                                    .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jButton1)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
         jButton1.addActionListener(new ActionListener()
        {
           public void actionPerformed(ActionEvent ae)
            {
                    start=!start;
                    if(start==false)
                    {
                        jButton1.setText("Start");
                       
                    }
                    else
                    {
                       
                        jButton1.setText("Pause");
                    }
           }
         });
    }

   public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        if (source.getValueIsAdjusting()) {
                 this.jTextField5.setText(source.getValue()/10+"");
        
        }
        }
   void readxyz(InputStream in) throws WriteException,IOException
    {
       byte[] sen=new byte[3];

       while(connect)
       {

           try{
                                        in.read(sen);
                                       
           }
           catch(Exception e){
          //  pcclient.log(e);
                  }
  if(start)
        {
             if(sen[0]>70)
                                                    sen[0]=(byte)(sen[0]-256);
                                                    if(sen[1]>70)
                                                        sen[1]=(byte)(sen[1]-256);
                                                    if(sen[2]>70)
                                                        sen[2]=(byte)(sen[2]-256);
						//log("Read " + sen[0]+"  "+sen[1]);
                                                     jTextField2.setText(" "+sen[0]+" ");
                                        jTextField3.setText(" "+sen[1]+" ");
                                        jTextField4.setText(" "+sen[2]+" ");
            
         System.out.println("pointer:"+pointer);
         if(writetofile)
         {
        jxl.write.Label label = new jxl.write.Label(0,pointer,sen[0]+"");
        jxl.write.Label label1 = new jxl.write.Label(1,pointer,sen[1]+"");
        jxl.write.Label label2 = new jxl.write.Label(2,pointer++,sen[2]+"");
       // System.out.println("pointer:"+pointer);
        sheet.addCell(label);
        sheet.addCell(label1);
        sheet.addCell(label2);
         }
       if(pointer>200)
       {
           
           start=false;
           writetofile=false;
       }

        }
  else
  {try
  {
      workbook.write();
  }
  catch(Exception e)
  {
      
  }
    workbook.close();
    if ((new File("F:\\test.xls")).exists()) {
                    try {
                        Process p = Runtime
                           .getRuntime()
                           .exec("rundll32 url.dll,FileProtocolHandler F:\\test.xls");
                        p.waitFor();
                    } catch (InterruptedException ex) {
                        System.err.print(ex);
                    }
 
		} else {
 
			System.out.println("File is not exists");
 
		}
 
  }
       }   
    }
   
    private javax.swing.JButton jButton1;
   private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSlider jSlider1;

    public javax.swing.JTextField jTextField2;
    public javax.swing.JTextField jTextField3;
    public javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    
}
