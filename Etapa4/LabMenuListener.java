import java.awt.event.*; 
import java.awt.Adjustable;

import javax.swing.*;

public class LabMenuListener implements ActionListener {
   private MyWorld  world;
   public LabMenuListener (MyWorld  w){
      world = w;
   }
   public void actionPerformed(ActionEvent e) {
      JMenuItem menuItem = (JMenuItem)(e.getSource());
      String text = menuItem.getText();
      
      
      if (text.equals("Stop")) {
    	  world.stop();
      }
      if( world.isRunning()) {
		  final JComponent[] comps = new JComponent[] {
			  new JLabel("Debe detener la simualcion antes de hacer esta accion.")
		  };
		  JOptionPane.showMessageDialog(null, comps, "Simulation",  JOptionPane.PLAIN_MESSAGE);
		  return;
	  }
      
      // Actions associated to main manu options
      if (text.equals("My scenario")) {  // here you define Etapa2's configuration
    	  double mass 		= 1.0;  // 1 [kg] 
	      double radius 	= 0.1;  // 10 [cm] 
	      double position 	= 0.5;  // 1 [m] 
	      double speed 	= 0;   	// 0.5 [m/s]
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
		  
		  JScrollBar sbMass 	= new JScrollBar(Adjustable.HORIZONTAL, 1, 0, 1, 100),		// value/10
					sbRadius 	= new JScrollBar(Adjustable.HORIZONTAL, 10, 0, 1, 20),		// value/100
					sbSpeed		= new JScrollBar(Adjustable.HORIZONTAL, 0, 0, -50, 50);		// value/10
		  final String	vMass 	= 1+"",		//init value [kg]
						vRadius = 10+"",	//init value [cm]
						vSpeed	= 0+"";		//init value [m/s]
		  final JLabel	lMass 	= new JLabel("Mass: "  +vMass  +" [kg]"),
						lRadius = new JLabel("Radius: "+vRadius+" [cm]"),
						lSpeed	= new JLabel("Speed: " +vSpeed +" [m/s]");
		
		  
		  final JComponent[] comps = new JComponent[] {
			  lMass, sbMass,
			  lRadius, sbRadius,
			  lSpeed, sbSpeed };
		  
		  sbMass.addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent evt) {
					lMass.setText("Mass: "+evt.getValue()/10 +" [kg]");
				}
			}
		  );
		  sbRadius.addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent evt) {
					lRadius.setText("Radius: "+evt.getValue() +" [cm]");
				}
			}
		  );
		  sbSpeed.addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent evt) {
					lSpeed.setText("Speed: "+evt.getValue()/10 +" [m/s]");
				}
			}
		  );
		  
		  JOptionPane.showMessageDialog(null, comps, "New Ball",  JOptionPane.PLAIN_MESSAGE);
		  
		  double mass 		= (double) sbMass.getValue()/10;      // [kg] 
	      double radius	= (double) sbRadius.getValue()/100;   // [cm] 
	      double position 	= 1.0; 	 							   // 1[m] 
	      double speed 	= (double) sbSpeed.getValue()/10;     // [m/s]
		
	      world.addElement(new Ball(mass, radius, position, speed));
	      world.repaintView();
      }
      if (text.equals("Fixed Hook")){
		  Object[] opts = {"Yes", "No"};
          int ans = JOptionPane.showOptionDialog(null,
					"Quiere que sea colisionable ?",
					"New FixedHook",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					opts,
					opts[1]);
		  if( ans == 1)
			world.addElement(new FixedHook(0.1, 0.5));
		  else
			world.addElement(new FixedHook(0.1, 0.5, true));
			
    	  world.repaintView();
      }
      if (text.equals("Spring")) {
		  int maxSteveness	 = 10,	// value / 10
				minSteveness = 1;
		  
		  JScrollBar sbSteveness;	// Steve is THE answer ! STEVE STIFLER
		  sbSteveness 	= new JScrollBar(Adjustable.HORIZONTAL, 1, 0, minSteveness, maxSteveness);
		  sbSteveness.setBlockIncrement(1);
		  
		  final String vSteve	= 1+"";		//init value
		  final JLabel	lSteve 	= new JLabel("Stifness: "+vSteve+" [kg/s^2]");
		  
		  final JComponent[] comps = new JComponent[] {
			  lSteve, sbSteveness };
		  
		  sbSteveness.addAdjustmentListener(
			new AdjustmentListener() {
				public void adjustmentValueChanged(AdjustmentEvent evt) {
					lSteve.setText("Stifness: "+evt.getValue()/10+" [kg/s^2]");
				}
			}
		  );
			  
		  JOptionPane.showMessageDialog(null, comps, "New Spring",  JOptionPane.PLAIN_MESSAGE);
		  
    	  world.addElement(new Spring(1.0, (double) sbSteveness.getValue()/10));
    	  world.repaintView();
      }

      // Actions associated to MyWorld submenu
      if (text.equals("Start")) {
    	  world.start();
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
