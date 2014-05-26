import java.util.*;
import java.awt.*;

/**
 * Clase para crear instancias del objeto fisico <code>Ball</code>, 
 * simulando el comportamiento de una pelota con choque elsstico. Es
 * visualizable en una GUI al implementar <i>Simulateable</i>, pudiendo
 * dibujarse a si misma.
 * 
 * Almacena:
 * <ul>
 * 	<li>Identificador</li>
 * 	<li>masa</li>
 * 	<li>radio</li>
 * 	<li>posicion</li>
 * 	<li>velocidad</li>
 * 	<li><i>View</i></li>
 * 	<li>valores necesarios para computar su proximo estado</li>
 * </ul>
 * 
 * @author Agustin Gonzalez
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
    * Permite asignar masa, radio, posicion inicial y velocidad incial
    * deseadas. El ID que se asigna a la pelota es el siguiente entero
    * al ID asignado anteriormente.
    * 
    * @param mass		masa de la pelota [kg]
    * @param radius	radio de la pelota [m]
    * @param positon	posicion inicial de la pelota [m]
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
    * Permite obtener posicion actual de la pelota.
    */
   public double getPosition() {
      return pos_t;
   }
   
   /**
    * Permite obtener rapidez actual de la pelota.
    */
   public double getSpeed() {
      return speed_t;
   }
   
   /**
    * Permite computar proximo estado de la pelota, distinguiendo entre si
    * esta chocando o no.
    * 
    * @param world		Objeto con los demas objetos fisicos creados.
    * @param delta_t	Delta de tiempo con que se calcula proxima posicion.
    */
   public void computeNextState(double delta_t, MyWorld world) {
     Ball b;  											// Assumption: on collision we only change speed.   
     if ((b=world.findCollidingBall(this))!= null) {	// elastic collision
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
    * @param b	<code>Ball</code> con la que se evalua colision.
    * @return 	<code>true</code> si la pelota esta chocando con <code>b<code>.
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
    * Actualiza estado actual de la pelota, obteniendolo de los valores del
    * proximo estado calculado luego de una posible colision.
    */
   public void updateState(){
     pos_t = pos_tPlusDelta;
     speed_t = speed_tPlusDelta;
   }
   
   /**
    * Actualiza vista de <code>BallView</code> encapsulado por la clase.
    * 
    * @param g	<code>Graphics2D</code> a actualizar
    */
   public void updateView (Graphics2D g) {   // NEW
     view.updateView(g);  // update this Ball's view in Model-View-Controller design pattern     
   }
   
   /**
    * Permite saber si un punto <i>(x,y)</i> esta contenido en el circulo que
    * representa a la pelota.
    * 
    * @param x 	coordenada <i>x</i>
    * @param y 	coordenada <i>y</i>
    * @return 		<code>true</code> si punto <i>(x,y)</i> pertenece al circulo de la pelota.
    * 				<code>false</code> en caso contrario.
    */
   public boolean contains(double x, double y) {
      return view.contains(x,y);
   }
   
   /**
    * Pide dibujarse al <code>View</code> de la pelota.
    * 
    * @param g		Un elemento <code>Graphics2D Ball</code>
    */
   public void draw(Graphics2D g){
	   view.draw(g);
   }
   
   /**
    * Asigna el <code>View b</code> a la pelota.
    * 
    * @param b		Nueva <code>View</code> de la pelota
    */
   public void setView(BallView b){
	   this.view = b;
   }
   
   /**
    * Agarra el <code>View</code> de la pelota.
    */
   public void setSelected(){
      view.setSelected();
   }
   
   /**
    * Suelta el <code>View</code> de la pelota.
    */
   public void setReleased(){
      view.setReleased();
   }
   
   /**
    * Asigna nueva posicion.
    * 
    * @param x		coordenada <i>x</i>
    */
   public void dragTo(double x){
      pos_t=x;
   }
   
   /**
    * Retorna descripcion de la pelota.
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
    * Permite conocer fuerza neta hecha por los resortes sobre pelota.
    * 
    * @return	Fuerza neta hecha por los resorte adjuntados
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
    @Override
   public void attachSpring(Spring spring) {
       springs.add(spring);
   }
   
   /**
    * Responde si <code>spring</code> es un resorte al cual se esta
    * adjunto.
    * 
    * @param	spring	resorte a adjuntar.
    * @return			<code>true</code> si esta adjunta a ese resorte
    * 					<code>false</code> en caso contrario.
    */
   @Override
   public boolean isAttachedTo(Spring spring) {
	   for (Spring i: springs)
			if (i == spring) return true;
		
		return false;
   }
   
   /**
    * Elimina resorte ya adjuntado.
    * 
    * @param s		Resorte a desatachar
    */
   @Override
   public void detachSpring(Spring s) {
	// TODO Auto-generated method stub
   }
   
   /**
    * Calcula la magnitud de un numero.
    * 
    * @param	a	número real del que se obtiene magnitud
    * @return		Devuele magnitud de parámetro <code>a</code>
    */
   private static double abs(double a) {
       return (a <= 0.0F) ? 0.0F - a : a;
   }
}
