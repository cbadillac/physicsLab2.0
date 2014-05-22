import java.awt.event.*; 

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class LabMenuListener implements ActionListener {
   private MyWorld  world;
   public LabMenuListener (MyWorld  w){
      world = w;
   }
   public void actionPerformed(ActionEvent e) {
      JMenuItem menuItem = (JMenuItem)(e.getSource());
      String text = menuItem.getText();
      System.out.println(text);
      
      // Actions associated to main manu options
      if (text.equals("My scenario")) {  // here you define Etapa2's configuration
    	  double mass = 1.0;      // 1 [kg] 
	      double radius = 0.1;    // 10 [cm] 
	      double position = 0.5;  // 1 [m] 
	      double speed = 0;     // 0.5 [m/s]
	      Ball b0 = new Ball(mass, radius, position, speed);
	      Ball b1 = new Ball(mass,radius,1,0);
	      Ball b2 = new Ball(mass,radius,3,0);
	      FixedHook f1 = new FixedHook(0.1, 0.5);
	      FixedHook f2 = new FixedHook(0.1, 3.5);
	      
	      Spring s = new Spring(0.5,10);
	      Spring s2 = new Spring(0.5,10);
	      s.attachAend(f1);
	      s.attachBend(b0);
	      s2.attachAend(b2);
	      s2.attachBend(f2);
	      world.addElement(b0);
	      world.addElement(b1);
	      world.addElement(b2);
	      world.addElement(s);
	      world.addElement(s2);
	      world.addElement(f1);
	      world.addElement(f2);
	      world.repaintView();
      }
      if (text.equals("Ball")) {
    	  double mass = 1.0;      // 1 [kg] 
	      double radius = 0.1;    // 10 [cm] 
	      double position = 1.0;  // 1 [m] 
	      double speed = 0;     // 0.5 [m/s]
	      world.addElement(new Ball(mass, radius, position, speed));
	      world.repaintView();
    	  // nothing by now       
      }
      if (text.equals("Fixed Hook")){
    	  world.addElement(new FixedHook(0.1, 0.5));
    	  world.repaintView();
    	  
      }
      if (text.equals("Spring")){
    	  world.addElement(new Spring(1.0, 1.0));
      }

      // Actions associated to MyWorld submenu
      if (text.equals("Start")){
    	  world.start();
      }
      if (text.equals("Stop")){
    	  world.stop();
      }
      if (text.equals("Delta time")) {
         String data = JOptionPane.showInputDialog("Enter delta t [s]");
         if(data == null) return;
         world.setDelta_t(Double.parseDouble(data));
      }
      if (text.equals("View Refresh time")) {
    	  String data = JOptionPane.showInputDialog("Enter Refresh time [s]");
    	  if(data == null) return;
          world.setRefreshPeriod(Double.parseDouble(data));
      }
   }
}