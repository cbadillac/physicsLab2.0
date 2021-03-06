import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class MouseListener extends MouseAdapter {
   private MyWorld world;
   private PhysicsElement currentElement;
   
   public MouseListener (MyWorld w){
      world = w;
   }
   
   public void mousePressed(MouseEvent e) {
	   if(world.isRunning()) return;
	   Point2D.Double p = new Point2D.Double(0,0); 	// Change mouse coordenates from
	      
	  MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);	// pixels to meters
	  PhysicsElement newElement = world.find(p.getX(), p.getY());
	  
	  this.currentElement = newElement;
	  currentElement.setSelected();
		
	  world.repaintView();
   }
   
   public void mouseDragged(MouseEvent e) {
	   if(world.isRunning()) return;
	   Point2D.Double p = new Point2D.Double(0,0); // Change mouse coordenates from
	      
	  MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);// pixels to meters
	  
	  if (currentElement != null) {			  
		  currentElement.dragTo(p.getX());
		  world.repaintView();
	  }
   }
   
   public void mouseReleased(MouseEvent e) {
      if (currentElement == null) return;
      if (currentElement instanceof SpringAttachable) {
         Point2D.Double p= new Point2D.Double(0,0);
         MyWorldView.SPACE_INVERSE_TRANSFORM.transform(e.getPoint(),p);
          // we dragged a spring, so we look for and attachable element near by  
         PhysicsElement element = world.findSpringElement(p.getX());
         if (element instanceof Spring && !((SpringAttachable)currentElement).isAttachedTo((Spring)element)) {
			 if( ((SpringAttachable)currentElement).isAttachedTo((Spring)element))
				return;
				
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
				Spring spring = (Spring) element;
				double a=spring.getAendPosition();
				if (currentElement.contains(a,0))
				   spring.attachAend((SpringAttachable)currentElement);
				double b=spring.getBendPosition();
				if (currentElement.contains(b,0))
				   spring.attachBend((SpringAttachable)currentElement);
			}
         }
      }
       
      currentElement.setReleased();
      currentElement = null;
      
      world.repaintView();
   }
}
