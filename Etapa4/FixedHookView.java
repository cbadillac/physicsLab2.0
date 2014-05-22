import java.awt.*;
import java.awt.geom.*;

public class FixedHookView {
   private double width;  // fixed point width
   private Color color = Color.GREEN;
   private Rectangle2D.Double shape = null;
   private FixedHook hook;
   
   public FixedHookView (FixedHook f){
	      hook = f;
	   }
	   
	   public void draw(Graphics2D g){
		   double radius = hook.getRadius();
		   shape = new Rectangle2D.Double(hook.getPosition()-radius, -radius, 2*radius, 2*radius);
		   g.setColor(color);
		   g.fill(shape);
	   }
	   
	   
	   public boolean contains (double x, double y){
	      // to be coded
		   return false;
	   }
	   public void setSelected (){
	      color = Color.RED;
	   }
	   public void setReleased() {
	      color = Color.GREEN;
	   }
	   void updateView(Graphics2D g) {
		   double radius = hook.getRadius();
		   shape = new Rectangle2D.Double(hook.getPosition()-radius, -radius, 2*radius, 2*radius);
		   g.setColor(color);
		   g.fill(shape);
	   }
/* to be coded */
}