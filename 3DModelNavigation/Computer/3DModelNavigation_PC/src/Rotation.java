
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Rotation extends Applet {
    Bluetooth bluetooth;
    private SimpleUniverse universe;
    private Canvas3D canvas3D;

  public Rotation(Bluetooth bluetooth) {
      this.bluetooth=bluetooth;
  }

  public void init() {  //initialize gui
    setLayout(new BorderLayout());
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    canvas3D = new Canvas3D(config);  //create canvas
    add("Center", canvas3D);
    universe = new SimpleUniverse(canvas3D);  //create universe
    universe.getViewingPlatform().setNominalViewingTransform();
    BranchGroup szene = new BranchGroup();
    Transform3D ourTrans = new Transform3D();
    Transform3D drehung = new Transform3D();
    Transform3D drehung2 = new Transform3D();
    Transform3D drehung3 = new Transform3D();
    TransformGroup spin = new TransformGroup(ourTrans);   //create transfromation group
    spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    spin.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
    spin.addChild(new ColorCube(0.2));//adding default cube

    szene.addChild(spin);//add tranfromation group 
    universe.addBranchGraph(szene);
    boolean connect=true;
    byte sen[]=new byte[3];
    while(connect)
      {
        try
        {
            bluetooth.in.read(sen);//read accelerometer values
            if(sen[0]>75)
                sen[0]=(byte)(sen[0]-256);
            if(sen[1]>75)
               sen[1]=(byte)(sen[1]-256);
            if(sen[2]>75)
               sen[2]=(byte)(sen[2]-256);
        }
        catch(Exception e)
        {
                System.err.print(e);
        }
        
    spin.getTransform(ourTrans);
    drehung.rotX((Math.PI)*(sen[0])/150.0d);        // rotate cube as per x y z values
    drehung2.rotY((Math.PI )*(sen[1]/75.0d));
    drehung.mul(drehung2);
    drehung.mul(drehung3);
    ourTrans.mul(drehung);
    spin.setTransform(drehung);

  }
  }
  public void destroy() {
    universe.removeAllLocales();    //destory universe 
  }
 
}