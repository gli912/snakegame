import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;





public class snakecontroller extends Thread{
	private static int snakesize;
	public static long speed;
	public static position headposition;
	ArrayList<ArrayList<SquarePanel>> Grid;
	public static LinkedList<position> bodyposition;
	public static blindwalk bw;
	private boolean foodahead;
	private static position nexthead;
	private static boolean collision;
	public static boolean exit;
	public static boolean run;
	public static boolean auto;
	public static boolean haspath;
	
	public static final int left=0;
	public static final int right =1;
	public static final int up=2;
	public static final int down=3;
	public static boolean pause;
	
	public static int direction;
	
	snakecontroller(position p)
	{
		headposition = p;
		Grid = Window.Grid;
		direction = right;
		snakesize=3;
		bodyposition = new LinkedList<position>();
		for(int i=0; i<snakesize; i++)
		{
			SquarePanel tmp = Window.Grid.get(p.y).get(p.x-i);
			tmp.Changecolor(SquarePanel.snake);
			bodyposition.add(new position(p.x-i, p.y));
		}
		generatefood();
		foodahead = false;
		nexthead = new position(p.x+1, p.y);
		pause =false;
		collision = false;
		exit = false;
		run = false;
		auto = false;
		haspath = false;
		speed = 100;
		bw = new blindwalk();
		AStarSearch.reset();
	}
		
	public void terminate()
	{
		System.out.println("COllision!! \n");
		exit =true;
	}
	
	public void delay(long speed)
	{
		try {
			sleep(speed);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void move()
	{
		if(collision == true){
			terminate();
			return;
		}
		
		bodyposition.addFirst(nexthead);
		headposition = nexthead;
		AStarSearch.headposition = headposition;
		
		Grid.get(headposition.y).get(headposition.x).Changecolor(SquarePanel.snake);
		if(foodahead == false) 
		{
			position tmp = bodyposition.getLast();
			if(!tmp.isequal(headposition)) 
				Grid.get(tmp.y).get(tmp.x).Changecolor(SquarePanel.empty);
			bodyposition.removeLast();
		}
		else 
		{
			snakesize++;
			foodahead=false;
			generatefood();
		}

	}
	
	public void check_collision_and_food()
	{
		Color c = Grid.get(nexthead.y).get(nexthead.x).getBackground();
		if(c==SquarePanel.C[SquarePanel.empty])
		{
			foodahead = false;
			collision = false;
		}
		else if(c==SquarePanel.C[SquarePanel.food])
		{
			foodahead = true;
			collision = false;
		}
		else
		{
			foodahead = false;
			if(nexthead.isequal(bodyposition.getLast())==true) 
					collision = false;
			else collision =true;
		}
	}
	
	public void nextstep()
	{
		nexthead = new position(headposition.x,headposition.y);
		switch(direction)
		{
			case left:
				nexthead.x = ((headposition.x-1)+Window.col)%Window.col;
				break;
			case right:
				nexthead.x = (headposition.x+1)%Window.col;
				break;
			case up:
				nexthead.y = ((headposition.y-1)+Window.row)%Window.row;
				break;
			case down:
				nexthead.y = (headposition.y+1)%Window.row;
				break;
			default: break;
		}
	}
	
	public void generatefood()
	{
		position p = new position(0,0);
		//p.change(9, 9);
		p.x = (int) (Math.random()*Window.col);
		p.y = (int) (Math.random()*Window.row);
		while(Grid.get(p.y).get(p.x).getBackground()==SquarePanel.C[SquarePanel.snake])
		{
			p.x = (int) (Math.random()*Window.col);
			p.y = (int) (Math.random()*Window.row);			
		}
		
		Grid.get(p.y).get(p.x).Changecolor(SquarePanel.food);	
		AStarSearch.reset();
		AStarSearch.foodposition = p;
		haspath = false;
		
	}
	
	@Override
	public void run() {
		run = true;
		
		while(exit == false)
		{
			if (pause==false) {
				if(auto == false) nextstep();
				else
				{
					//if(haspath==false)
					//{
						haspath = AStarSearch.findpath();
						if(haspath==true)
						{
							position p = AStarSearch.nexthead();
							nexthead = new position(p);
						}
						else 
						{
							position p = bw.findnextnode();
							nexthead = new position(p);
							//AStarSearch.reset();
						}
						AStarSearch.reset();
					//}
					//else
					//{
					//	position p = AStarSearch.nexthead();
					//	nexthead = new position(p);
					//}
					
				}
				check_collision_and_food();
				move();		
			}
			delay(speed);

		}
		
		
	}

}
