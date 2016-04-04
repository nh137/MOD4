package cosc3550assignment4;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class HealthBar extends Sprite{
	double h, w;
	
	public HealthBar (Image i){
		super(i);
	}
	
	public void render(GraphicsContext gc, int health){
			for (int i = 0; i < health; i++)
				gc.drawImage(image, i*this.image.getWidth(), 0);
	}
}
