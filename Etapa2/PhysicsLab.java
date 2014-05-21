import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Container;

public class PhysicsLab {
   public static void main(String[] args) {
      PhysicsLab_GUI lab_gui = new PhysicsLab_GUI();
      lab_gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      lab_gui.setVisible(true);
   }
}

class PhysicsLab_GUI extends JFrame {
   public PhysicsLab_GUI() {
      setTitle("My Small and Nice Physics Laboratory");
      setSize(MyWorldView.WIDTH, MyWorldView.HEIGHT+100);  // height+50 to account for menu height
      MyWorld world = new MyWorld();
      MyWorldView  worldView = new MyWorldView(world);
      setLayout(new BorderLayout());
      LabMenuListener menuListener = new LabMenuListener(world);
      add(createLabMenuBar(menuListener),BorderLayout.NORTH);
      world.setView(worldView);
      add(worldView);  
      createConfiguration(world);
      
      
      /*  .....   */;
   }

   public JMenuBar createLabMenuBar(LabMenuListener menu_l) {
      JMenuBar mb = new JMenuBar();
      
      JMenu menu = new JMenu ("Configuration");
      mb.add(menu);
      JMenu subMenu = new JMenu("Insert");  
      menu.add(subMenu);
      
      JMenuItem menuItem = new JMenuItem("Ball");
      menuItem.addActionListener(menu_l);
      subMenu.add(menuItem);
 /*....*/      
      menu = new JMenu("MyWorld");
      mb.add(menu);
      menuItem = new JMenuItem("Start");
      menuItem.addActionListener(menu_l);
      menu.add(menuItem);
/* ...*/
      return mb;          
   }
   
   private void createConfiguration(MyWorld world) {  // Please note how similar it is to "Etapa 4" of T1
	      double mass = 1.0;      // 1 [kg] 
	      double radius = 0.1;    // 10 [cm] 
	      double position = 0.0;  // 1 [m] 
	      double speed = 0.5;     // 0.5 [m/s]
	      Ball b0 = new Ball(mass, radius, position, speed);
	      Ball b1 = new Ball(mass, radius, 2.0, 0);
	      world.addElement(b0);
	      world.addElement(b1);
	      world.start();
	   }
}