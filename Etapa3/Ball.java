import java.util.*;
import java.awt.*;

public class Ball extends PhysicsElement implements SpringAttachable, Simulateable {
   private static int id=0;  // Ball identification number
   private final double mass;
   private final double radius;
   private double pos_t;     // current position at time t
   private double pos_tPlusDelta;  // next position in delta time in future
   private double speed_t;   // speed at time t
   private double speed_tPlusDelta;   // speed in delta time in future
   private BallView view;  // Ball view of Model-View-Controller design pattern
   private ArrayList<Spring> springs;
   private double a_t; 
   
   private Ball(){   // nobody can create a block without state
     this(1.0,0.1,0,0);
   }
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
   public double getMass() {
      return mass;
   }
   public double getRadius() {
      return radius;
   }
   public double getPosition() {
      return pos_t;
   }
   public double getSpeed() {
      return speed_t;
   }
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
   public void updateState(){
     pos_t = pos_tPlusDelta;
     speed_t = speed_tPlusDelta;
   }
   public void updateView (Graphics2D g) {   // NEW
     view.updateView(g);  // update this Ball's view in Model-View-Controller design pattern     
   }
   public boolean contains(double x, double y) {
      return view.contains(x,y);
   }
   public void draw(Graphics2D g){
	   view.draw(g);
   }
   public void setView(BallView b){
	   this.view = b;
   }
   public void setSelected(){
      view.setSelected();
   }
   public void setReleased(){
      view.setReleased();
   }
   public void dragTo(double x){
      pos_t=x;
   }

   public String getDescription() {
     return "Ball_" + getId()+":x";
   }
   public String getState() {
     return getPosition()+"";
   }
   private double getNetForce() {
       Double net_force = 0.0;
       for (Spring S: springs){
           net_force += S.getForce(this);
       }
       return net_force;
   }
   
   public void attachSpring(Spring spring) {
       springs.add(spring);
   }
   
   @Override
   public void detachSpring(Spring s) {
	// TODO Auto-generated method stub
	
   }
   private static double abs(double a) {
       return (a <= 0.0F) ? 0.0F - a : a;
   }
}
