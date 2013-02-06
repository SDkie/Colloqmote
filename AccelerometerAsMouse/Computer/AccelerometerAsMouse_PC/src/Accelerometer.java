import java.io.InputStream;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Robot;
public class Accelerometer extends javax.swing.JFrame implements ChangeListener{
   float slider;
    Robot key;
    Point b ;
    int flag=0;
    int x,y;
    PointerInfo a;
    public boolean start=true;
    Bluetooth pcclient;
    boolean connect=false;
    public Accelerometer(Bluetooth pcclient) {
         try
            {
            key=new Robot();
            }
            catch(Exception e)
            {
                pcclient.log(e);
            }
        this.pcclient=pcclient;
        initComponents();
        this.setVisible(true);
       }

   
  
   //creating GUI
    private void initComponents() {
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
   void readxyz(InputStream in)
    {
       byte[] sen=new byte[4];

       while(connect)
       {

           try{
                        in.read(sen);//reading accelerometer values
                                       
           }
           catch(Exception e){
            pcclient.log(e);
                  }
  if(start)
           {           
               //logic to generate mouse move and mouse click events
                                                    if(sen[0]>70)
                                                    sen[0]=(byte)(sen[0]-256);
                                                    if(sen[1]>70)
                                                        sen[1]=(byte)(sen[1]-256);
                                                    if(sen[2]>70)
                                                        sen[2]=(byte)(sen[2]-256);
						
                                                     jTextField2.setText(" "+sen[0]+" ");
                                        jTextField3.setText(" "+sen[1]+" ");
                                        jTextField4.setText(" "+sen[2]+" ");


                                             
                                             a = MouseInfo.getPointerInfo();
                                             b  = a.getLocation();
                                             x = (int)b.getX();
                                             y = (int)b.getY();
                                     
                                             if(sen[3]==1)
                                                 flag++;

                                             

                                           if(sen[0]<10&&sen[0]>-10)
                                               sen[0]=0;
                                          if(sen[1]<10&&sen[1]>-10)
                                             sen[1]=0;

                                            
                                                slider=jSlider1.getValue();

                                                if(slider==0)
                                                    slider=1;

                                                slider/=1000;

                                                x=x+(int)(sen[1]*slider)+sen[1]*(sen[1]/70);
                                                y=y+(int)(sen[0]*slider)+sen[1]*(sen[1]/70);

                                                key.mouseMove(x, y);

                                                    if(sen[3]==1)
                                                    {
                                                         key.mousePress(InputEvent.BUTTON1_MASK);
                                                        key.mouseRelease(InputEvent.BUTTON1_MASK);
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
