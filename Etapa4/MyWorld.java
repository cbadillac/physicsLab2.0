import java.util.*;
import java.io.*;
import javax.swing.Timer;
import java.awt.event.*;

/**
 * Se encarga de hacer interctuar los elementos fisicos entre ellos
 * mismo. Controla la simulacion, permitiendo: inciarlas, pausarla y
 * reanudarla. Además, vincula las vistas con los estados internos de
 * cada elemento, dando coherencia a lo que se puede visualizar.<br>
 * En su estado almacena:
 * <ul>
 *  <li>salida de impresiones</li>
 *  <li><i>View</i> de <code>MyWorld</code></li>
 *  <li>tiempo de refresco de pantalla</li>
 * 	<li>delta de tiempo entre cada interaccion</li>
 * 	<li>lista de <code>PhysicsElement</code>></li>
 * </ul>
 * 
 * @author Agustin Gonzalez
 * @author Cristobal Badilla
 * @author Roberto Farías
 * @author Cristóbal Ramírez
 * 
 * @version 3.0
 * @since 1.0
 */
public class MyWorld implements ActionListener {
   private PrintStream out;
   
   private ArrayList<PhysicsElement> elements;  // array to hold everything in my world.
   private MyWorldView view;   	// NEW
   private Timer passingTime;   	// NEW
   private double t;        		// simulation time
   private double delta_t;        // in seconds
   private double refreshPeriod;  // in seconds
   private boolean n;
   
   /**
    * Constructor por defecto. Sólo asigna como salida
    * <code>System.out</code>.
    */
   public MyWorld(){
      this(System.out);  			// delta_t= 0.1[ms] and refreshPeriod=200 [ms]
   }
   /**
    * Determina valores iniciales para su estado:
    * <ul>
    * 	<li><code>out = output</code></li>
    * 	<li><code>t = 0</code> (tiempo inicial)</li>
    * 	<li><code>refresPeriod = 0.06</code>[s]</li>
    * 	<li><code>delta_t = 0.00001</code>[s]</li>
    * 	<li><code>passingTime = Timer(refresgPeriod*1000)</code>[s]</li>
    * 	<li>inicializa <code>elements</code></li>
    * 	<li><code>view</code> como <code>null</code></li>
    *  </ul>
    * 
    * @param output	Salida a elección.
    */
   public MyWorld(PrintStream output){
      out = output;
      t = 0;
      refreshPeriod = 0.06;     // 60 [ms]
      delta_t 	= 0.00001;      // 0.01 [ms]
      elements 	= new ArrayList<PhysicsElement>();
      view 		= null;
      passingTime = new Timer((int)(refreshPeriod*1000), this);
      n = false;
   }
   
   public boolean eatN() {
	   boolean ret = n;
	   n = (n)?!n:n;
	   return ret;
   }
   public void nPressed() {
	   n = true;
   }
   
   /**
    * Agrega un <code>PhysicsElement</code> a <code>MyWorld</code>.
    * 
    * @param e	<code>PhysicsElement</code> a agregar.
    */
   public void addElement(PhysicsElement e) {
      elements.add(e);
      view.repaintView();
   }
   
   /**
    * Asigna <code>view</code> como nueva vista.
    * 
    * @param view	nueva vista a asignar.
    */
   public void setView(MyWorldView view) {
      this.view = view;
   }
   
   /**
    * Asigna un nuevo delta de tiempo.
    * 
    * @param delta	nuevo delta de tiempo.
    */
   public void setDelta_t(double delta) {
      delta_t = delta;
   }
   
   /**
    * Asigna nuevo refreshTime.
    * 
    * @param rp	nuevo <code>refreshTime</code>
    */ 
   public void setRefreshPeriod (double rp) {
      refreshPeriod = rp;
      passingTime.setDelay((int)(refreshPeriod*1000)); 	// convert from [s] to [ms]
   }
   
   /**
    * Indica estado de simulacion.
    * 
    * @return 	<code>true/<code> si la simulacion esta corriendo,
    * 			<code>false</code> si la simulacion esta parada
    */
    public boolean isRunning() {
		return passingTime.isRunning();
	}
   
   /**
    * Inicia o reanuda la simulacion.
    */
   public void start() {
      if(passingTime.isRunning()) return;
      passingTime.start(); 
   }
   /**
    * Detiene la simulacion.
    */
   public void stop(){
	   passingTime.stop();
   }
   
   /**
    * Implementa la simulacion de los elementos agregados hasta el 
    * siguiente tiempo de <i>repaint</i> (simula intervalos entre
    * cada <code>refreshPeriod</code>).
    * 
    * @param event	evento capturado por <i>Listener</i>.
    */
   public void actionPerformed (ActionEvent event) {  	// like simulate method of Assignment 1, 
      double nextStop=t+refreshPeriod;                	// the arguments are attributes here.
      for (; t<nextStop; t+=delta_t){
         for (PhysicsElement e: elements)
            if (e instanceof Simulateable) {
               Simulateable s = (Simulateable) e;
               s.computeNextState(delta_t, this); 		// compute each element next state based on current global state
            }
         for (PhysicsElement e: elements)  			// for each element update its state. 
            if (e instanceof Simulateable) {
               Simulateable s = (Simulateable) e;
               s.updateState();            				// update its state
            }
         this.view.repaint();
      }
   }
   
   /**
    * Pide a <code>view</code> redibujar el canvas.
    */
   public void repaintView() {
      view.repaintView();
   }

   /**
    * Devuelve elementos agregados.
    * 
    * @return		<code>ArrayList<PhysicsElement></code> con los
    * 				elementos agregados a <code>MyWorld</code>
    */
   public ArrayList<PhysicsElement> getPhysicsElements(){
      return elements;
   }
   
   /**
    * Busca dentro de los elementos agregados, el primero que encuentre
    * que este en el punto <i>(x,y)</i>, a menos que sea un 
    * <code>Spring</code>, en ese caso se guarda y se devuelve solo
    * en caso de que no se encuentre otro elemento.
    * 
    * @param x		coordenada <i>x</i>
    * @param y		coordenada <i>y</i>
    * 
    * @return 		retorna un <code>PhysicsElemnt</code> que se
    * 				encuentre en <i>(x,y)</i>. En caso de no encontrar
    * 				ninguno, devuelve <code>null</code>
    */
   public PhysicsElement find(double x, double y) {
	  PhysicsElement someSpring = null;
	  
      for (PhysicsElement e: elements) {
    	  if (e.contains(x,y)) {
			  if( e instanceof	 Spring)
				someSpring = e;
			  else if( eatN())
			    continue;
			  else	
				return e;
          }
      }
    	  
      return someSpring;
   }
   
   /**
    * Busca <code>Ball</code> con las que <code>me</code> colisiona y
    * la retorna.
    * 
    * @param me	<code>Ball</code> a la que se le busca con quien
    * 				esta colisonando.
    * @return		Retorna <code>Ball</code> con la que <code>me</code>
    * 				esta colisionando, null en caso de que no haya 
    * 				colision.
    */
   public Ball findCollidingBall(Ball me) {
      for (PhysicsElement e: elements)
         if ( e instanceof Ball) {
            Ball b = (Ball) e;
            if ((b!=me) && b.collide(me)) return b;
         }
         else if( e instanceof FixedHook) {
			 if( !((FixedHook)e).isCollidable()) return null;
			 
			 Ball b = new Ball(me.getMass(), ((FixedHook)e).getRadius(), ((FixedHook)e).getPosition(), -me.getSpeed());
			 if (b.collide(me)) return b;
		 }
      return null;
   }
   
   /**
    * Busca elementos <code>SpringAttachable</code> en las cercanias a
    * <code>x</code>.
    * 
    * @param x 	coordenada <i>x</i>.
    * @return		<code>SpringAttachable</code> encontrado, o
    * 				<code>null</code> en su defecto.
    */
   public SpringAttachable findAttachableElement(double x) {
	   for(PhysicsElement e: elements){
		   if(e.contains(x, 0) && !(e instanceof Spring)) return (SpringAttachable)e;
	   }
	   return null;
   }
   /**
    * Busca elementos <code>Spring</code> en las cercanias a
    * <code>x</code>.
    * 
    * @param x 	coordenada <i>x</i>.
    * @return		<code>Spring</code> encontrado, o <code>null</code>
    * 				en su defecto.
    */
   public Spring findSpringElement(double x) {
	   for(PhysicsElement e: elements) {
		   if(e.contains(x, 0) && (e instanceof Spring)) return (Spring)e;
	   }
	   return null;
   } 
} 
