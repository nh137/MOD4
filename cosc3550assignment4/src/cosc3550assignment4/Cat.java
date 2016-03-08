package cosc3550assignment4;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Cat extends Sprite{
	// Just need one random generator shared
	// by all the boxes (so make it static)
	static Random rng = new Random();

	int x, y;   // top-left corner
	int w, h;   // width and height
	int vx, vy; // velocity vector

	public Cat(){
		// Choose random size and movement
		w = 20 + rng.nextInt(51);
		h = 20 + rng.nextInt(51);
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

	public boolean overlaps(Cat b){
		int y1 = y;
		int x1 = x;
		int h1 = h;
		int w1 = w;
		int y2 = b.y;
		int x2 = b.x;
		int h2 = b.h;
		int w2 = b.w;

		if(y1 <= (y2+h2) && x2 <= (x1+w1) && y2 <=(y1+h1) && x1 <= (x2+w2)){
			this.reverse();
			b.reverse();
		}

		return false;
	}

	public void reverse(){
		vx = -vx;
		vy = -vy;
	}

	public void render(GraphicsContext gc){
		gc.setFill(Color.BLACK);
		gc.fillRect(x,  y,  w,  h);
	}
}

