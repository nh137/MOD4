package cosc3550assignment4;

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;


public class MainGame extends Application{
	
	static Image backgroundImage = new Image("background.png");
	
	final String appName = "Mouse Under Attack";
	final int FPS = 30; // frames per second
	final static double WIDTH = backgroundImage.getWidth()*2;
	final static double HEIGHT = backgroundImage.getHeight()*2;
	public static final int DYING = 80;
	public static float BBscale = 1.0f;
	
	Image catImage, mouseImage, bulletImage;
	
	GraphicsContext gc;
	
	boolean lostGame = false;
	boolean wonGame = false;
	
	Font font = Font.font("TimesRoman", FontPosture.ITALIC, 60.0);
	
	Mouse mouse;
	Cat cat;
	ArrayList<Cat> cats;
	Bullet bullet;
	int level = 1;
	int count = 67;	// invincibility time variable
	
	void initialize(){
		//images
		catImage = new Image("cat_100x71.png");
		bulletImage = new Image("waterspray.png");
		mouseImage = new Image("mouse.jpg");
		
		cats = new ArrayList<Cat>();
		bullet = new Bullet(bulletImage);
		mouse = new Mouse(mouseImage, bullet);
		for(int i = 0; i < (level*2); i++){
			cat = new Cat(catImage);
			cats.add(cat);
		}	
	}
	
	void setHandlers(Scene scene){
		scene.setOnKeyPressed(
				e -> {
					KeyCode c = e.getCode();
					switch (c) {
					case W: mouse.setUpKey(true);
					break;
					case S: mouse.setDownKey(true);
					break;
					case A: mouse.setLeftKey(true);
					break;
					case D: mouse.setRightKey(true);
					break;
					default:
						break;
					}
				}
				);

		scene.setOnKeyReleased(
				e -> {
					KeyCode c = e.getCode();
					switch (c) {
					case W: mouse.setUpKey(false);
					break;
					case S: mouse.setDownKey(false);
					break;
					case A: mouse.setLeftKey(false);
					break;
					case D: mouse.setRightKey(false);
					break;
					default:
						break;
					}
				}
		);
		scene.setOnMousePressed(
				e -> {
					if (!bullet.isActive())
						mouse.fireBullet();
				}
				);
	}
	
	private void update() {
		mouse.move();
		if(mouse.invulnerable){
			count--;
			if(count == 0){
				count = 67;
				mouse.invulnerable = false;
			}
		}
		for(int i = 0; i < cats.size(); i++){
			cats.get(i).move();
			cats.get(i).headTo(mouse);
			if(cats.get(i).overlaps(mouse)){
				if(!mouse.invulnerable){
					mouse.invulnerable = true;
					mouse.loseHealth();
				}
				else{
					
				}
			}
		}
		for (int i = 0; i < cats.size(); i++){
			for (int j = 0; j < i; j++){
				if(cats.get(i).collision(cats.get(j))){
					cats.get(i).reverse();
					cats.get(j).reverse();
					cats.get(i).loseHealth();
					cats.get(j).loseHealth();
				}
			}
			/* if(cats.get(i).health == 0){
				cats.remove(i);
			} */
		}
		
		for (int i = 0; i < cats.size(); i++)
		if(cats.get(i).health == 0){
			cats.remove(i);
		}
		
		if(cats.isEmpty()){
			level++;
			for(int i = 0; i < (level*2); i++){
				cat = new Cat(catImage);
				cats.add(cat);
			}
		}
		if(level == 4){
			wonGame = true;
		}
		if(mouse.health == 0){
			lostGame = true;
		}
		if (mouse.bullet.active)
			mouse.bullet.updateSprite();
	}
	
	/**
	 *  Draw the game world
	 */
	
	void render(GraphicsContext gc) {
		//gc.setFill(Color.WHITE);
		//gc.fillRect(0.0, 0.0, WIDTH, HEIGHT);
		
		//pictures of the background
		gc.drawImage(backgroundImage, 0, 0);
		gc.drawImage(backgroundImage, backgroundImage.getWidth(), 0);
		gc.drawImage(backgroundImage, 0, backgroundImage.getHeight());
		gc.drawImage(backgroundImage, backgroundImage.getWidth(), backgroundImage.getHeight());
		mouse.render(gc);
		for(int i = 0; i < cats.size(); i++){
			cats.get(i).render(gc);
		}
		//System.out.println("Begin scenery");
		
		if (wonGame)
		{
			gc.setFill(Color.BLACK);
			gc.setFont(font);
			gc.fillText("You win!",200,200);
		}

		if (lostGame)
		{
			gc.setFill(Color.BLACK);
			gc.setFont(font);
			gc.fillText("You lose.",200,200);
		}
		
	}
	/*
	 * Begin boiler-plate code...
	 * [Animation and events with initialization]
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		//backgroundImage = new Image("background.png");
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		gc = canvas.getGraphicsContext2D();

		// Initial setup
		initialize();
		setHandlers(theScene);
		
		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
					// draw frame
					render(gc);
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
	/*
	 * ... End boiler-plate code
	 */

	
}
