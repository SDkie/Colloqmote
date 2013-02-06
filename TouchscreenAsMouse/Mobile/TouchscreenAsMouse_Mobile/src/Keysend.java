
import javax.microedition.lcdui.*;

 public class Keysend extends Canvas implements CommandListener{
     Command LeftClick=new Command("Left Click",Command.SCREEN,1);
   Command RightClick=new Command("Right Click",Command.EXIT,1);
    String msg;
    int width=getWidth(),height=getHeight();
    int k;
    ScreenTouchpad m;
   int click2=0;
    public Keysend(ScreenTouchpad m)
    {
        this.m=m;
        this.setFullScreenMode(true);
        addCommand(LeftClick);
     //   addCommand(RightClick);
        setCommandListener(this);
        
    }

int flag=0;

    int x,y;
    int dx,dy;
    byte[] sen=new byte[3];
   public void pointerPressed(int x,int y)
     {
            this.x=x;
            this.y=y;
            click2=1;

    }
   public void pointerDragged(int x,int y)
     {
       click2=0;
      if(flag==1)
      {
          flag=-1;
        
            dx=this.x-x;
            dy=this.y-y;
            this.x=x;
            this.y=y;
            sen[0]=(byte)dx;
            sen[1]=(byte)dy;
            sen[2]=(byte)click2;
            try
            {
           m.out.write(sen);
           m.out.flush();
         }catch(Exception e)
            {

         }
        


         }
      flag++;
   }
   public void pointerReleased(int x,int y)
     {
        dx=this.x-x;
            dy=this.y-y;
             sen[0]=(byte)dx;
            sen[1]=(byte)dy;
            sen[2]=(byte)click2;
            try
            {
           m.out.write(sen);
           m.out.flush();
         }catch(Exception e)
            {

         }
            
   }
   public void commandAction(Command c,Displayable d)
     {
        sen[0]=0;
    sen[1]=0;
if(c==LeftClick)
{
   sen[2]=1;
}
if(c==RightClick)
{
    sen[2]=2;
}
    try
            {
           m.out.write(sen);
           m.out.flush();
         }catch(Exception e)
            {

         }
   }
    public void paint(Graphics g)
    {

    }

}
