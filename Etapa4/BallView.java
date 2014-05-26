import java.awt.*;
import java.awt.geom.*;

import javax.swing.JPanel;

/**
 * Determina el <code>View</code> de <code>Ball</code> usando la
 * biblioteca <code>awt</code> de Java. Almacena:
 * <ul>
 * 	<li>color</li>
 * 	<li>forma</li>
 * 	<li>pelota que representa</li>
 * </ul>
 * 
 * @author Agustin Gonzalez
 * @author Cristobal Badilla
 * @author Roberto Farías
 * @author Cristóbal Ramírez
 * 
 * @version 2.0
 * @since 1.0
 */
public class BallView {
   private Color color = Color.BLUE;
   private Ellipse2D.Double shape = null;
   private Ball ball;
   
   /**
    * Crea <code>BallView</code> de la pelota <code>b</code>, sin
    * determinar color ni forma.
    * 
    * @param b		<code>Ball</code> a representar
    */
   public BallView (Ball b){
      ball = b;
   }
   
   /**
    * Le da a <code>g<code> una forma circular y lo rellena con
    * <code>color</code>.
    * 
    * @param g		<code>Graphics2D</code> a dibujar como circulo con
    * 				color de esta pelota.
    */
   public void draw(Graphics2D g){
	   double radius = ball.getRadius();
	   shape = new Ellipse2D.Double(ball.getPosition()-radius, -radius, 2*radius, 2*radius);
	   
	   g.setColor(color);
	   g.fill(shape);
   }
   
   /**
    * Permite saber si <code>BallView</code> esta dibujado en el punto
    * <i>(x,y)</i>.
    * 
    * @param x		coordenada <i>x</i>
    * @param y		coordenada <i>y</i>
    * @return 		<code>true</code> si el punto esta contenido en 
    * 				<code>Ball</code>
    * 				<code>false</code> en caso contrario
    */
   public boolean contains (double x, double y){
	   double radius = ball.getRadius();
	   boolean yCondition = (y >= -radius && y <= radius);
	   boolean xCondition = ( x >= ball.getPosition()-radius && x<= ball.getPosition()+radius);
	   return ( yCondition && xCondition);
   }
   
   /**
    * Asigna el color <i>rojo</i> al relleno del circulo.
    */
   public void setSelected (){
      color = Color.RED;
   }
   
   /**
    * Asigna el color <i>azul</i> al relleno del circulo.
    */
   public void setReleased() {
      color = Color.BLUE;
   }
   
   /**
    * Actualiza valores de <code>g</code> con valores de
    * <code>Ball</code> y <code>color</code>.
    * 
    * @param g		<code>Graphics2D</code> a rellenar.
    */
   void updateView(Graphics2D g) {
      double radius = ball.getRadius();
      shape = new Ellipse2D.Double(ball.getPosition()-radius, -radius, 2*radius, 2*radius);
      g.setColor(color);
      g.fill(shape);
   }
}
