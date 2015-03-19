import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;



public class Window extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	public static final int row=20;
	public static final int col=20;
	public static snakecontroller snake;
	
	public static ArrayList<ArrayList<SquarePanel>> Grid;
	
	Window(){		
		super("Snake Game");
		this.addKeyListener(new keyboardlistener());
		setSize(col*20, row*20+20);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		addmenubar();
		addgamearea();
		position p = new position(5, row/2);
		snake = new snakecontroller(p);
		
		setVisible(true);
	}
	
	public void addgamearea(){
		Grid = new ArrayList<ArrayList<SquarePanel>>();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, col, 1, 1));
		gamearea_initialize(panel);
		this.add(panel, BorderLayout.CENTER);
	}
	
	public void addmenubar(){
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		JMenuItem auto = new JMenuItem("Auto");
		JMenuItem start = new JMenuItem("Start");
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem[] m = {start, auto, exit};
		for(JMenuItem i: m){
			i.addActionListener(this);
		}
		menu.add(start);
		menu.add(auto);
		menu.addSeparator();
		menu.add(exit);
		bar.add(menu);
		this.add(bar, BorderLayout.NORTH);
	}
	
	public void gamearea_initialize(JPanel p){
		for(int i=0; i<row; i++)
		{
			ArrayList<SquarePanel> tmp = new ArrayList<SquarePanel>();
			for(int j=0; j<col; j++){
				SquarePanel s = new SquarePanel(SquarePanel.empty);
				p.add(s);
				tmp.add(s);
			}
			Grid.add(tmp);
		}
	}
	
	public static void cleangamearea()
	{
		for(int i=0; i<row; i++)
		{
			for(int j=0; j<col; j++) Grid.get(i).get(j).Changecolor(SquarePanel.empty);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String s = arg0.getActionCommand();
		
		switch(s)
		{
		case "Start":
			snakecontroller.auto = false;
			snakecontroller.speed = 100;
			if(snakecontroller.run==false){
				snake.start();
			}
			else
			{
				snakecontroller.exit=true;
				try {
					snake.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cleangamearea();
				position p = new position(5, row/2);
				snake = new snakecontroller(p);
				snakecontroller.auto = false;
				snakecontroller.speed = 100;
				snake.start();	
			}
			break;
			
		case "Auto":
			snakecontroller.auto = true;
			snakecontroller.speed = 50;
			if(snakecontroller.run==false){
				snake.start();
			}
			else{
				snakecontroller.exit=true;
				try {
					snake.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cleangamearea();
				position p = new position(5, row/2);
				snake = new snakecontroller(p);
				snakecontroller.auto = true;
				snakecontroller.speed = 50;
				snake.start();	
			}
			break;			
			
			
		case "Exit":
			snakecontroller.exit = true;
			try {
				snake.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Exit the game");
			System.exit(0);
			break;
		}
		
	}

}
