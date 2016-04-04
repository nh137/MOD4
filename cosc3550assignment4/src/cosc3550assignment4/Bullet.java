package cosc3550assignment4;

import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

class Bullet extends Sprite
{
  Color color = Color.BLACK;
  public static final double RADIUS = 4;
  
  public Bullet(Image i) {
	  super(i);
  }

  void updateSprite()
  {
    if (active)
    {
      updatePosition();
      // If we've dropped off the screen, suspend the bullet
      if (y > MainGame.HEIGHT)
      	suspend();
    }
  }

  void render(GraphicsContext gc)
  {
	  gc.setStroke(Color.BLACK);
		BoundingBox bb = getBoundingBox();
		gc.strokeRect(bb.getMinX(), bb.getMinY(), bb.getWidth()  , bb.getHeight());
    if (visible)
    {
    	gc.drawImage(image, x, y);
    }
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
