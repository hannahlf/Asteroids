/**
 * A representation of a ship.
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ship extends Polygon implements java.awt.event.KeyListener {
	public static final int SHIP_WIDTH = 40;
	public static final int SHIP_HEIGHT = 25;
	public boolean forwards = false;
	public boolean right = false;
	public boolean left = false;
	public boolean space = false;
	public int lives = 3;

	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}
	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();

	// Create paint method to paint a ship
	public void paint(Graphics brush, Color color) {
		Point[] points = getPoints();
		int[] xPoints = new int[points.length];
		int[] yPoints = new int[points.length];
		int nPoints = points.length;
		for(int i = 0; i < nPoints; ++i) {
			xPoints[i] = (int) points[i].x;
			yPoints[i] = (int) points[i].y;
		}
		brush.setColor(color);
		brush.fillPolygon(xPoints, yPoints, nPoints);
	}

	public void move() {

		// we'll just demonstrate the ship moving across the x axis.
		//position.x += 5;

		/**
		 * If the ship moves off of the screen either along the
		 * x or y axis, have the ship re-appear coming from the other side.
		 */
		if(position.x > Asteroids.SCREEN_WIDTH) {
			position.x -= Asteroids.SCREEN_WIDTH;
		} else if(position.x < 0) {
			position.x += Asteroids.SCREEN_WIDTH;
		}
		if(position.y > Asteroids.SCREEN_HEIGHT) {
			position.y -= Asteroids.SCREEN_HEIGHT;
		} else if(position.y < 0) {
			position.y += Asteroids.SCREEN_HEIGHT;
		}
		if(forwards == true)
		{
			position.x += 3 * Math.cos(Math.toRadians(rotation));
			position.y += 3 * Math.sin(Math.toRadians(rotation));
		}
		if(right == true)
		{
			rotate(10);
		}
		if(left == true)
		{
			rotate(-10);
		}
		if(space == true)
		{
				Point p = new Point(position.x, position.y);
				Bullet b = new Bullet(p, rotation);
				bullets.add(b);
		}


	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			forwards = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			space = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			forwards = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			space = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Bullet> getBullets() {
		// TODO Auto-generated method stub
		return bullets;
	}
}
