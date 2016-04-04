package cosc3550assignment4;

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
    if (visible)
    {
      gc.setFill(color);
      gc.fillOval(x-RADIUS, y-RADIUS, 2*RADIUS, 2*RADIUS);
    }
  }
}
