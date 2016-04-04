package cosc3550assignment4;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

class Sprite{
	double x,y;
	double dx,dy;

	Image image;

	boolean active=true, visible=true;
	
	public Sprite(Image i) {
		  image = i;
	  }

	void updatePosition(){
		x += dx;
		y += dy;

		//dy += GRAVITY;
	}

	void setPosition(double x2, double y2){
		x = x2; y = y2;
	}

	void setVelocity(double a, double b){
		dx = a; dy = b;
	}

	boolean isCloserThan(Sprite s, double dist){
		// Return true if Sprite s is closer to
		// the current Sprite (this) than specified
		// distance dist.
		double dx = x-s.x;
		double dy = y-s.y;
		return dx*dx+dy*dy < dist*dist;
	}

	boolean isActive(){
		return active;
	}

	void suspend(){
		active = false; visible = false;
	}

	void resume(){
		active = true; visible = true;
	}

	void updateSprite() {}
	
	void render(GraphicsContext gc) {
		render(gc);
		gc.setStroke(Color.BLACK);
		BoundingBox bb = getBoundingBox();
		gc.strokeRect(bb.getMinX(), bb.getMinY(), bb.getWidth()  , bb.getHeight());
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