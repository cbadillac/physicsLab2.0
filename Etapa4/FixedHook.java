import java.util.*;
import java.awt.*;

public class FixedHook extends PhysicsElement implements SpringAttachable, Simulateable {
	private static int id=10;  // Ball identification number
    Double ancho;
    Double position;
    private ArrayList<Spring> springs;
    FixedHookView view;
    
    
	private FixedHook() {
		this(1.0,1);
		// TODO Auto-generated constructor stub
	}
	
	public FixedHook(double radius, double position){
		super(id++);
		this.position = position;
        this.ancho = radius;
        springs = new ArrayList<Spring>();
        view = new FixedHookView(this);
		
	}

	@Override
	public void attachSpring(Spring s) {
		springs.add(s);
		
	}

	@Override
	public void detachSpring(Spring s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getPosition() {
		// TODO Auto-generated method stub
		return this.position;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "FixedHook_" + this.getId() + ":x\t";
	}
	
	public double getRadius(){
		return this.ancho;
	}

	@Override
	public String getState() {
		
		return Double.toString(this.getPosition())+ "\t";
	}

	@Override
	public void draw(Graphics2D g) {
		this.view.draw(g);
		
	}

	@Override
	public void updateView(Graphics2D g) {
		this.view.updateView(g);
		
	}

	@Override
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return view.contains(x, y);
	}

	@Override
	public void setSelected() {
		view.setSelected();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setReleased() {
		view.setReleased();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dragTo(double x) {
		position = x;
		
	}
/* to be coded
*/

	@Override
	public void computeNextState(double delta_t, MyWorld w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateState() {
		// TODO Auto-generated method stub
		
	}
}