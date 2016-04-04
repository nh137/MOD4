package cosc3550assignment4;

import java.util.Random;
import javafx.scene.image.Image;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cat extends Sprite{
	// Just need one random generator shared
	static Random rng = new Random();

	int x, y;   // top-left corner
	double w, h;   // width and height
	double vx, vy; // velocity vector
	int health = 1;
	int speed = 2;

	public Cat(Image i){
		super(i);
		w = i.getWidth();
		h = i.getHeight();
		vx = 2 + rng.nextInt(7);
		if (rng.nextDouble() < 0.5)
			vx = -vx;
		vy = 2 + rng.nextInt(7);
		if (rng.nextDouble() < 0.5)
			vy = -vy;
		// Choose random position on the Canvas
		x = 5 + rng.nextInt((int) (MainGame.WIDTH - w - 10));
		y = 5 + rng.nextInt((int) (MainGame.HEIGHT - h - 10));
	}

	public void move(){
		x += vx; y += vy;
		// Check for walls
		if (x < 0)
			vx = -vx;
		if (x+w > MainGame.WIDTH)
			vx = -vx;
		if (y < 0)
			vy = -vy;
		if(y+h > MainGame.HEIGHT)
			vy = -vy;
	}

	public boolean overlaps(Cat c){
		int y1 = y;
		int x1 = x;
		double h1 = h;
		double w1 = w;

		int y2 = c.y;
		int x2 = c.x;
		double h2 = c.h;
		double w2 = c.w;

		if(y1 <= (y2+h2) && x2 <= (x1+w1) && y2 <=(y1+h1) && x1 <= (x2+w2)){
			return true;
		}
		return false;
	}
	
	public boolean overlaps(Mouse m){
		int y1 = y;
		int x1 = x;
		double h1 = h;
		double w1 = w;

		int y2 = m.y;
		int x2 = m.x;
		int h2 = m.HEIGHT;
		int w2 = m.WIDTH;

		if(y1 <= (y2+h2) && x2 <= (x1+w1) && y2 <=(y1+h1) && x1 <= (x2+w2)){
			return true;
		}

		return false;
	}

	public void reverse(){
		vx = -vx;
		vy = -vy;
	}

	public void render(GraphicsContext gc){
		if (visible) {
			gc.drawImage(image, x, y);
		}
	}
	
	public void loseHealth(){
		health--;
	}
	
	public void headTo(Mouse m)
	{
		double vx = m.x - x;
		double vy = m.y - y;
		double len = Math.sqrt(vx*vx+vy*vy);
		this.vx = vx*speed/len;
		this.vy =  vy*speed/len;
	}
}

