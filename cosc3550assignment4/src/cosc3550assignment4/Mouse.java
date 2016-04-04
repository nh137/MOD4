package cosc3550assignment4;

import javafx.geometry.BoundingBox;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Mouse extends Sprite {
	int x, y, vx, vy, health; 
	double h, w;
	HealthBar healthbar;
	
	boolean upKey = false, downKey = false, leftKey = false, rightKey = false;

	public boolean invulnerable = false;

	final static int WIDTH = 40;
	final static int HEIGHT = 40;
	final static int SPEED = 5;
	
	Bullet bullet;

	public Mouse(Image i, Bullet b, Image hb){
		super(i);
		w = i.getWidth();
		h = i.getHeight();
		x = (int) (MainGame.WIDTH/2);
		y = (int) (MainGame.HEIGHT/2);
		
		//vx = 0;
		//vy = 0;
		health = 6;
		bullet = b;
		healthbar = new HealthBar(hb);
	}

	public void move(){
		if (upKey && getTop()>0)
			y -= SPEED;
		if (downKey && getBottom()<MainGame.HEIGHT)
			y += SPEED;
		if(leftKey && (getX()-(WIDTH/2))>0)
			x -= SPEED;
		if(rightKey && (getX()+(WIDTH/2))<MainGame.WIDTH)
			x += SPEED;
		if (x < 0)
			//vx = -vx;
		if (x+WIDTH > MainGame.WIDTH)
			//vx = -vx;
		if (y < 0)
			//vy = -vy;
		if(y+HEIGHT > MainGame.HEIGHT){
		
		}
			/*vy = -vy;*/
	}

	public void reset(){
		x = (int) (MainGame.WIDTH/2);
		y = (int) (MainGame.HEIGHT/4);
		vx = 0;
		vy = 0;
	}

	public void reverse(){
		vx = -vx;
		vy = -vy;
	}

	public void render(GraphicsContext gc){
		gc.drawImage(image, x, y);
		gc.setStroke(Color.BLACK);
		BoundingBox bb = getBoundingBox();
		gc.strokeRect(bb.getMinX(), bb.getMinY(), bb.getWidth()  , bb.getHeight());
		healthbar.render(gc, health);
		
		
		/*if(!invulnerable){
			gc.setFill(Color.BLUE);
			gc.fillRect(x, y, WIDTH, HEIGHT);
		}
		else{
			gc.setFill(Color.RED);
			gc.fillRect(x, y, WIDTH, HEIGHT);
		}*/
		
	}

	public void setUpKey(Boolean val){
		upKey = val;
	}

	public void setDownKey(Boolean val){
		downKey = val;
	}

	public void setLeftKey(Boolean val){
		leftKey = val;
	}

	public void setRightKey(Boolean val){
		rightKey = val;
	}

	public int getX(){
		return x+(WIDTH/2);
	}

	public int getTop(){
		return y;
	}

	public int getBottom(){
		return y+HEIGHT;
	}
	
	public void loseHealth(){
		health--;
	}
	
	public void fireBullet()
	  {
	    bullet.setPosition(x, y);
	    bullet.setVelocity(0.0, -40.0);
	    bullet.resume();
	  }
	
	public BoundingBox getBoundingBox(){
		double width = image.getWidth();
		double height = image.getHeight();
		double xoff = (width*(1.0f - MainGame.BBscale)/2.0f);
		double yoff = (height*(1.0f - MainGame.BBscale)/2.0f);
		double bbw = (width*MainGame.BBscale);
		double bbh = (height*MainGame.BBscale);
		return new BoundingBox(x+xoff, y+yoff, bbw, bbh);
	}
	
	public boolean collision(Sprite h){
		BoundingBox bb = getBoundingBox();
		return bb.intersects(h.getBoundingBox());
	}
}
