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


public class MainGame extends Application{
	
	final String appName = "Mouse Under Attack";
	final int FPS = 30; // frames per second
	final static double WIDTH = 800;
	final static double HEIGHT = 600;
	public static final int DYING = 80;
	public static float BBscale = 1.0f;
	
	boolean lostGame = false;
	boolean wonGame = false;
	
	Font font = Font.font("TimesRoman", FontPosture.ITALIC, 60.0);
	
	Mouse mouse;
	Cat cat;
	Cat cat1;
	ArrayList<Cat> cats;
	int count = 10000;
	
	void initialize(){
		cats = new ArrayList<Cat>();
		mouse = new Mouse();
		for(int i = 0; i < 2; i++){
			cat = new Cat();
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
	}
	
	private void update() {
		// TODO Auto-generated method stub
		mouse.move();
		while(mouse.invulnerable){
			count--;
			if(count == 0){
				count = 10000;
				mouse.invulnerable = false;
			}
		}
		for(int i = 0; i < cats.size(); i++){
			cats.get(i).move();
			if(cats.get(i).overlaps(mouse)){
				if(!mouse.invulnerable){
					mouse.invulnerable = true;
					mouse.loseHealth();
				}
				else{
					
				}
			}
		}
		for (int i = 1; i < cats.size(); i++){
			for (int j = 0; j < i; j++){
				if(cats.get(i).overlaps(cats.get(j))){
					cats.get(i).reverse();
					cats.get(j).reverse();
					cats.get(i).loseHealth();
					cats.get(j).loseHealth();
				}
				if(cats.get(i).health == 0){
					cats.remove(i);
				}
				if(cats.get(j).health == 0){
					cats.remove(j);
				}
			}
			if(cats.get(i).health == 0){
				cats.remove(i);
			}
		}
		if(cats.isEmpty()){
			wonGame = true;
		}
		if(mouse.health == 0){
			lostGame = true;
		}
	}
	
	/**
	 *  Draw the game world
	 */
	
	void render(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(0.0, 0.0, WIDTH, HEIGHT);
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
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

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
