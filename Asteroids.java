
/*
CLASS: Asteroids
DESCRIPTION: Extending Game, Asteroids is all in the paint method.
NOTE: This class is the metaphorical "main method" of your program,
      it is your control center.
Original code by Dan Leyzberg and Art Simon
 */
import java.awt.*;
import java.util.*;

public class Asteroids extends Game {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;

	static int counter = 0;
	boolean collided = false;
	private int COLLISION_PERIOD = 100;
	public int collisionTime;
	boolean shot = false;
	public int lives = 3;

	private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();
	private java.util.List<Asteroid> removeAsteroids = new ArrayList<Asteroid>(randomAsteroids);
	
	private Ship ship;

	public Star[] stars;

	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Bullet> removebullets = new ArrayList<Bullet>();

	public Asteroids() {
		super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();

		// create a number of random asteroid objects
		randomAsteroids = createRandomAsteroids(10,60,30);

		// create the ship
		ship = createShip();
		this.addKeyListener(ship);

		stars = createStars(40, 12);


	}



	public Star[] createStars(int numberOfStars, int maxRadius) {
		Star[] stars = new Star[numberOfStars];
		for(int i = 0; i < numberOfStars; i++)
		{
			Point center = new Point
					(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);

			int radius = (int) (Math.random() * maxRadius);
			if(radius < 1)
			{
				radius = 1;
			}
			stars[i] = new Star(center, radius);
		}

		return stars;
	}

	// private helper method to create the Ship
	private Ship createShip() {
		// Look of ship
		Point[] shipShape = {
				new Point(0, 0),
				new Point(Ship.SHIP_WIDTH/3.5, Ship.SHIP_HEIGHT/2),
				new Point(0, Ship.SHIP_HEIGHT),
				new Point(Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT/2)
		};
		// Set ship at the middle of the screen
		Point startingPosition = new Point((width -Ship.SHIP_WIDTH)/2, (height - Ship.SHIP_HEIGHT)/2);
		int startingRotation = 0; // Start facing to the right
		return new Ship(shipShape, startingPosition, startingRotation);
	}

	//  Create an array of random asteroids
	private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
			int minAsteroidWidth) {
		java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);

		for(int i = 0; i < numberOfAsteroids; ++i) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation));
		}
		return asteroids;
	}

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);

		// sample code for printing message for debugging
		// counter is incremented and this message printed
		// each time the canvas is repainted
		//collisionTime -= 1;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter,10,10);
		brush.drawString("Lives Left: " + lives, 10, 20);

		bullets = ship.getBullets();
		for(int i = 0; i < bullets.size(); i ++)
		{
			bullets.get(i).paint(brush, Color.BLUE);
			bullets.get(i).move();
			if(bullets.get(i).outOfBounds())
			{
				removebullets.add(bullets.get(i));
			}

			for(int j = 0; j < randomAsteroids.size(); j++)
			{
				if(randomAsteroids.get(j).contains(bullets.get(i).getCenter()))
				{
					removeAsteroids.add(randomAsteroids.get(j));
				}

			}
		}
	

		for(Star stars: stars) {
			stars.paint(brush, Color.YELLOW);
		}

		// display the random asteroids
		for (Asteroid asteroid : randomAsteroids) {
			asteroid.paint(brush,Color.white);
			asteroid.move();
			if(asteroid.Collision(ship) == true)
			{
				collided = true;
			}
		}
		if(collided == true)
		{
			ship.paint(brush, Color.red);
			if(collisionTime == 100)
			{
				lives--;
			}
			collisionTime -= 1;

			if(collisionTime <= 0)
			{
				collided = false;
				collisionTime = COLLISION_PERIOD;
			}
		}else
		{
			ship.paint(brush, Color.magenta);
		}
		ship.move();
		
		for(int i = 0; i < removeAsteroids.size(); i++)
		{
			randomAsteroids.remove(removeAsteroids.get(i));
		}
		for(int i = 0; i < removebullets.size(); i++)
		{
			bullets.remove(removebullets.get(i));
		}
		if(randomAsteroids.isEmpty())
		{
			brush.setColor(Color.black);
			brush.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			brush.setColor(Color.WHITE);
			brush.drawString("You Won!!!!", 390, 290);
		}
		if(lives == 0)
		{
			brush.setColor(Color.black);
			brush.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			brush.setColor(Color.WHITE);
			brush.drawString("You Lost! Better Luck Next Time!", 390, 290);
		}
	}

	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		a.repaint();
	}
}