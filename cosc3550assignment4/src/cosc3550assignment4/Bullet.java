package cosc3550assignment4;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Bullet extends Sprite
{
  Color color = Color.BLACK;
  public static final double RADIUS = 4;

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
