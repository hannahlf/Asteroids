import java.awt.Color;
import java.awt.Graphics;

public class Star extends Circle{
	
	public Star(Point center, int radius) {
		super(center, radius);
	}

	@Override
	public void paint(Graphics brush, Color color) {
		// TODO Auto-generated method stub
		brush.setColor(color);
		brush.fillOval((int)center.x,(int)center.y, radius, radius+1);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}

}
