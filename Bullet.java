import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Circle{
	private static final int RADIUS = 1;
	public double rotation;

	public Bullet(Point center, double rotation) {
		super(center, RADIUS);
		this.rotation = rotation;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics brush, Color color) {
		brush.setColor(color);
		brush.fillOval((int)center.x,(int)center.y, radius, radius+1);
		
	}

	@Override
	public void move() {
		center.x += 2 * Math.cos(Math.toRadians(rotation));
		center.y += 2 * Math.sin(Math.toRadians(rotation));
		
	}
	
	
	public boolean outOfBounds() {
		if(center.x < 0 || center.x > Asteroids.SCREEN_WIDTH ||
				center.y < 0 || center.y > Asteroids.SCREEN_HEIGHT)
		{
			return true;
		}
		return false;
	}
	
	public Point getCenter() {
		Point p = new Point(center.x, center.y);
		return p;
	}
	
}
