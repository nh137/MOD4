package cosc3550assignment4;

public class Mouse {
	int x, y, vx, vy; 

	boolean upKey = false, downKey = false, leftKey = false, rightKey = false;

	final static int WIDTH = 40;
	final static int HEIGHT = 40;
	final static int SPEED = 2;

	public Mouse(){
		x = MainGame.WIDTH/2;
		y = MainGame.HEIGHT/4;
		vx = 0;
		vy = 0;
	}

	public void move(){
		x += vx;
		y += vy;
		if (upKey && getTop()>0)
			vy -= SPEED;
		if (downKey && getBottom()<MainGame.HEIGHT)
			vy += SPEED;
		if(leftKey && (getX()-(WIDTH/2))>0)
			vx -= SPEED;
		if(rightKey && (getX()+(WIDTH/2))<MainGame.WIDTH)
			vx += SPEED;
		if (x < 0)
			vx = -vx;
		if (x+WIDTH > MainGame.WIDTH)
			vx = -vx;
		if (y < 0)
			vy = -vy;
		if(y+HEIGHT > MainGame.HEIGHT)
			vy = -vy;
	}

	public void reset(){
		x = MainGame.WIDTH/2;
		y = MainGame.HEIGHT/4;
		vx = 0;
		vy = 0;
	}

	public void reverse(){
		vx = -vx;
		vy = -vy;
	}

	public void render(GraphicsContext gc){
		gc.setFill(Color.BLUE);
		gc.fillRect(x, y, WIDTH, HEIGHT);
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
}
