import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class keyboardlistener extends KeyAdapter {
	public static final int left = 37;
	public static final int right = 39;
	public static final int up = 38;
	public static final int down = 40;
	public static final int space = 32;
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case left: 
			if(snakecontroller.direction!=snakecontroller.right) 
				snakecontroller.direction = snakecontroller.left;
			break;
		case right:
			if(snakecontroller.direction!=snakecontroller.left) 
				snakecontroller.direction = snakecontroller.right;
			break;
		case up:
			if(snakecontroller.direction!=snakecontroller.down) 
				snakecontroller.direction = snakecontroller.up;
			break;
		case down:
			if(snakecontroller.direction!=snakecontroller.up) 
				snakecontroller.direction = snakecontroller.down;
			break;
		case space:
			snakecontroller.pause = !snakecontroller.pause;
		default:
			break;
		}
	}

}
