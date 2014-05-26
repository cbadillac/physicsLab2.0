import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.*;
import java.awt.geom.Point2D;

public class MouseListener extends MouseAdapter {
   private MyWorld world;
   private PhysicsElement currentElement;
   
   public MouseListener (MyWorld w){
      world = w;
   }
   /*
   public void mouseMoved(MouseEvent e) {
      Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
      
      MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters.
      //System.out.println(p);
      PhysicsElement newElement = world.find(p.getX(), p.getY()); 
      if (newElement == currentElement) return;
      if (currentElement != null) {
         currentElement.setReleased();
         currentElement = null;
      }
      if (newElement != null) { 
         currentElement = newElement;
         
         currentElement.setSelected();
      }
      world.repaintView();
   }*/
   public void mousePressed(MouseEvent e) {
	   Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
	      
	  MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters
	  PhysicsElement newElement = world.find(p.getX(), p.getY());
	  
	  System.out.println(newElement);
	  
	  this.currentElement = newElement;
	  currentElement.setSelected();
   }
   public void mouseDragged(MouseEvent e) {
	   Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
	      
	      MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters
	      
	      if (currentElement != null) {			  
	    	  currentElement.dragTo(p.getX());
	    	  world.repaintView();
	      }
   }
   public void mouseReleased(MouseEvent e) {
      if (currentElement == null) return;
      if (currentElement instanceof Spring) {
         Point2D.Double p= new Point2D.Double(0,0);
         MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);

          // we dragged a spring, so we look for and attachable element near by  
         SpringAttachable element = world.findAttachableElement(p.getX());
         if (element == null)
			element = world.findAttachableElement(p.getX() - ((Spring)currentElement).getRestLength());
         if (element != null) {
            // we dragged a spring and it is near an attachable element,
            // so we hook it to a spring end.
            
            Object[] opts = {"Yes", "No"};
            int ans = JOptionPane.showOptionDialog(null,
					"Quiere adjuntar " + ((PhysicsElement)element).getDescription() + "?",
					"Adjuntar",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					opts,
					opts[0]);
            
            if (ans == 0) { //	"Yes"
				Spring spring = (Spring) currentElement;
				double a=spring.getAendPosition();
				if (a==p.getX())
				   spring.attachAend(element);
				double b=spring.getBendPosition();
				if (b==p.getX())
				   spring.attachBend(element);
			}
         }
      }
       
      currentElement.setReleased();
      currentElement = null;
      
      world.repaintView();
   }
}
