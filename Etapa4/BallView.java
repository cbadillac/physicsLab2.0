import java.awt.*;
import java.awt.geom.*;

import javax.swing.JPanel;

public class BallView {
   private Color color = Color.BLUE;
   private Ellipse2D.Double shape = null;
   private Ball ball;
   
   public BallView (Ball b){
      ball = b;
   }
   
   public void draw(Graphics2D g){
	   double radius = ball.getRadius();
	   shape = new Ellipse2D.Double(ball.getPosition()-radius, -radius, 2*radius, 2*radius);
	   
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
      color = Color.BLUE;
   }
   void updateView(Graphics2D g) {
      double radius = ball.getRadius();
      shape = new Ellipse2D.Double(ball.getPosition()-radius, -radius, 2*radius, 2*radius);
      g.setColor(color);
      g.fill(shape);
   }
}