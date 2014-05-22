import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*; 
import java.awt.*;
import java.util.*;

public class MyWorldView extends JPanel { 
//.....
// The same as Stage 3.
//....
   private MouseListener mListener;
   
   public MyWorldView(MyWorld w){
      world = w;
      mListener = new MouseListener(w);
      addMouseMotionListener(mListener);
      addMouseListener(mListener);
   }

// other methods.

   public void enableMouseListener(){
      addMouseMotionListener(mListener);
      addMouseListener(mListener);         
   }
   public void desableMouseListener(){
      removeMouseMotionListener(mListener);
      removeMouseListener(mListener);
   }
}