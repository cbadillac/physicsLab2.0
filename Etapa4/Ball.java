import java.util.*;
import java.awt.*;

/**
 * Clase para crear instancias del objeto físico "Ball", simulando el
 * comportamiento de una pelota con choque elástico. Es visualizable en una
 * GUI al implementar <i>Simulateable</i>, pudiendo dibujarse a si misma.
 * 
 * Almacena un ID, su masa, radio, posición y velocidad, su vista y valores
 * necesarios para computar su próximo estado.
 * 
 * @author Cristobal Badilla
 * @author Roberto Farías
 * @author Cristóbal Ramírez
 * 
 * @see PhysicsElement
 * @see Simulateable 
 * 
 * @version 2.0
 * @since 1.0
 */
public class Ball extends PhysicsElement implements SpringAttachable, Simulateable {
   private static int id=0;	  		// Ball identification number
   private final double mass;
   private final double radius;
   private double pos_t;     			// current position at time t
   private double pos_tPlusDelta;  	// next position in delta time in future
   private double speed_t;   			// speed at time t
   private double speed_tPlusDelta;   // speed in delta time in future
   private BallView view;				// Ball view of Model-View-Controller design pattern
   private ArrayList<Spring> springs;
   private double a_t; 
   
   /**
    * Calls the constructor overload with
    * <ul>
    * 	<li>mass = 1.0</li>
    * 	<li>radius = 0.1</li>
    * 	<li>position = 0</li>
    * 	<li>speed = 0</li>
    * </ul>
    * Nobody can create a block without state.
    */
   private Ball(){   					// nobody can create a block without state
     this(1.0,0.1,0,0);
   }
   
   /**
    * Sobrecarga de constructor. Permite asignar masa, radio, posición inicial
    * y velocidad incial deseadas. El ID que se asigna a la pelota es el
    * siguiente entero al ID asignado anteriormente.
    * 
    * @param mass		masa de la pelota [kg]
    * @param radius		radio de la pelota [m]
    * @param positon	posición inicial de la pelota [m]
    * @param speed		rapidez inicial de la pelota [m/s]
    */
   public Ball(double mass, double radius, double position, double speed){
      super(id++);
      this.mass = mass;
      this.radius = radius;
      pos_t = position;
      speed_t = speed;
      view = new BallView(this);
      springs = new ArrayList<Spring>();
      a_t = 0;
   }
   
   /**
    * Permite obtener masa de la pelota.
    */
   public double getMass() {
      return mass;
   }
   
   /**
    * Permite obtener radio de la pelota.
    */
   public double getRadius() {
      return radius;
   }
   
   /**
    * Permite obtener posición actúal de la pelota.
    */
   public double getPosition() {
      return pos_t;
   }
   
   /**
    * Permite obtener rapidez actúal de la pelota.
    */
   public double getSpeed() {
      return speed_t;
   }
   
   /**
    * Permite computar próximo estado de la pelota, distinguiendo entre si
    * está chocando o no.
    * 
    * @param world		Objeto con los demas objetos físicos creados.
    * @param delta_t	Delta de tiempo con que se calcula próxima posición.
    */
   public void computeNextState(double delta_t, MyWorld world) {
     Ball b;  // Assumption: on collision we only change speed.   
     if ((b=world.findCollidingBall(this))!= null){ /* elastic collision */
        speed_tPlusDelta=(speed_t*(mass-b.getMass())+2*b.getMass()*b.getSpeed())/(mass+b.getMass());
        pos_tPlusDelta = pos_t;
     } else {
    	 a_t = getNetForce()/mass;
         speed_tPlusDelta = speed_t + a_t * delta_t;
         pos_tPlusDelta = pos_t + speed_t*delta_t + a_t* delta_t * delta_t;
     }
   }
   
   /**
    * Permite saber si pelota esta colisionando con otra pelota <code>b</code>.
    * 
    * @param b	<code>Ball</code> con la que se evalua colisión.
    * @return 	<code>true</code> si la pelota está chocando con <code>b<code>.
    * 			<code>false</code> si no.
    */
   public boolean collide(Ball b) {
	   Double pos_dif = this.getPosition() - b.getPosition();
       Double speed_dif = this.getSpeed() - b.getSpeed();
       
       if((pos_dif > 0 && speed_dif<0)|| (pos_dif < 0 && speed_dif>0) ) {   //si nos acercamos

           if (abs(pos_dif) < b.getRadius() + b.getRadius()) {  //si las bolas se juntan
               return true;
           }
       }

       return false;
   }
   
   /**
    * Actualiza estado actúal de la pelota, obteniendolo de los valores del
    * próximo estado calculado luego de una posible colisión.
    */
   public void updateState(){
     pos_t = pos_tPlusDelta;
     speed_t = speed_tPlusDelta;
   }
   
   /**
    * Actualiza vista de <code>BallView</code> encapsulado por la clase.
    * 
    * @param g	...
    */
   public void updateView (Graphics2D g) {   // NEW
     view.updateView(g);  // update this Ball's view in Model-View-Controller design pattern     
   }
   
   /**
    * @param x 
    * @param y
    * @return 	<code>true</code>.
    * 			<code>false</code>.
    */
   public boolean contains(double x, double y) {
      return view.contains(x,y);
   }
   
   /**
    * @param g
    */
   public void draw(Graphics2D g){
	   view.draw(g);
   }
   
   /**
    * @param b
    */
   public void setView(BallView b){
	   this.view = b;
   }
   
   /**
    * 
    */
   public void setSelected(){
      view.setSelected();
   }
   
   /**
    * 
    */
   public void setReleased(){
      view.setReleased();
   }
   
   /**
    * 
    */
   public void dragTo(double x){
      pos_t=x;
   }
   
   /**
    * Retorna descripción de la pelota.
    * 
    * @return	"Ball_<code>ID</code>:x"
    */
   public String getDescription() {
     return "Ball_" + getId()+":x";
   }
   
   /**
    * Retorna estado de la pelota.
    * 
    * @return	"<code>getPosition()</code>"
    */
   public String getState() {
     return getPosition()+"";
   }
   
   /**
    * @return	Devuelve fuerza neta hecha por los resorte adjuntados
    * 			a la pelota.
    */
   private double getNetForce() {
       Double net_force = 0.0;
       for (Spring S: springs){
           net_force += S.getForce(this);
       }
       return net_force;
   }
   
   /**
    * Adjunta resorte a la pelota.
    * 
    * @param	spring	resorte a adjuntar.
    */
   public void attachSpring(Spring spring) {
       springs.add(spring);
   }
   
   /**
    * Elimina resorte ya adjuntado.
    */
   @Override
   public void detachSpring(Spring s) {
	// TODO Auto-generated method stub
	
   }
   
   /**
    * Calcula la magnitud de un número.
    * 
    * @param	a	número real del que se obtiene magnitud
    * @return		Devuele magnitud de parámetro <code>a</code>
    */
   private static double abs(double a) {
       return (a <= 0.0F) ? 0.0F - a : a;
   }
}
