import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;




//walk when AstarSearch algorithm can't find a path in auto mode

public class blindwalk {
	public static ArrayList<ArrayList<SquarePanel>> Grid = Window.Grid;
	public static LinkedList<position> bodyposition = snakecontroller.bodyposition;
	public static ArrayList<ArrayList<AStarSearch>> AstarArray = AStarSearch.AstarArray;

	private static int row = Window.row;
	private static int col = Window.col;
	public static ArrayList<ArrayList<Boolean>> BFSarray = new ArrayList<ArrayList<Boolean>>()
	{
		private static final long serialVersionUID = 1L;
		{
		for(int i=0; i<row; i++){
			ArrayList<Boolean> tmp = new ArrayList<Boolean>();
			for(int j=0; j<col; j++){
				tmp.add(new Boolean(false));
			}
			add(tmp);
		}
	}};
	
	blindwalk()
	{
	}
	
	public void resetBFSarray()
	{
		for(int i=0; i<row; i++)
		{
			for(int j=0; j<col; j++)
			{
				BFSarray.get(i).set(j, false);
			}
		}
	}
	
	public int doBFS(position p)
	{
		if(BFSarray.get(p.y).get(p.x)==true) return -1;
		
		Queue<AStarSearch> bfs = new LinkedList<AStarSearch>();
		bfs.add(AstarArray.get(p.y).get(p.x));
		BFSarray.get(p.y).set(p.x, true);
		int a=1;
		int b=0;
		int count=0;
		while(!bfs.isEmpty())
		{
			AStarSearch tmp = bfs.poll();
			a--;
			count++;
			AStarSearch tmp2 = AstarArray.get(tmp.p.y).get((tmp.p.x-1+col)%col); //left;
			if(Grid.get(tmp2.p.y).get(tmp2.p.x).getBackground()!=SquarePanel.C[SquarePanel.snake]
					&& BFSarray.get(tmp2.p.y).get(tmp2.p.x)==false){
				bfs.add(tmp2);
				BFSarray.get(tmp2.p.y).set(tmp2.p.x, true);
				b++;
			}
			
			tmp2 = AstarArray.get(tmp.p.y).get((tmp.p.x+1)%col); //right;
			if(Grid.get(tmp2.p.y).get(tmp2.p.x).getBackground()!=SquarePanel.C[SquarePanel.snake]
					&& BFSarray.get(tmp2.p.y).get(tmp2.p.x)==false){
				bfs.add(tmp2);
				BFSarray.get(tmp2.p.y).set(tmp2.p.x, true);
				b++;
			}
			
			tmp2 = AstarArray.get((tmp.p.y-1+row)%row).get(tmp.p.x); //up;
			if(Grid.get(tmp2.p.y).get(tmp2.p.x).getBackground()!=SquarePanel.C[SquarePanel.snake]
					&& BFSarray.get(tmp2.p.y).get(tmp2.p.x)==false){
				bfs.add(tmp2);
				BFSarray.get(tmp2.p.y).set(tmp2.p.x, true);
				b++;
			}
			
			tmp2 = AstarArray.get((tmp.p.y+1)%row).get(tmp.p.x); //right;
			if(Grid.get(tmp2.p.y).get(tmp2.p.x).getBackground()!=SquarePanel.C[SquarePanel.snake]
					&& BFSarray.get(tmp2.p.y).get(tmp2.p.x)==false){
				bfs.add(tmp2);
				BFSarray.get(tmp2.p.y).set(tmp2.p.x, true);
				b++;
			}
			
			if(a==0){
				a=b;
				b=0;
			}
			
		}
		System.out.println("BFS at x="+p.x+" y="+p.y+"\n");
		System.out.println("count ="+count+"\n");
		return count;		
		
	}
	
	public position findnextnode(){
		resetBFSarray();
		int maxcount = 0;
		int count;
		position chosenpos=null;
		position p = new position(snakecontroller.headposition);
		position[] bfsnode = {new position(p.x, (p.y-1+row)%row),       //up
							new position((p.x+1)%col, p.y),				//right
							new position(p.x, (p.y+1)%row),				//down
							new position((p.x-1+col)%col, p.y)};		//left;
		for(position i:bfsnode)
		{
			if(Grid.get(i.y).get(i.x).getBackground()==SquarePanel.C[SquarePanel.empty])
			{
				count = doBFS(i);
				if(count>maxcount)
				{
					maxcount = count;
					chosenpos = i;
				}
			}
		}
		if(maxcount == 0) chosenpos = p;
		System.out.println("bw: nextnode is x="+chosenpos.x+", y="+chosenpos.y+"\n");
		return chosenpos;
		
		/*
		position p = new position(snakecontroller.headposition);
			if(Grid.get((p.y-1+row)%row).get(p.x).getBackground()==SquarePanel.C[SquarePanel.empty])
				p.y= (p.y-1+row)%row;
			else if(Grid.get((p.y+1)%row).get(p.x).getBackground()==SquarePanel.C[SquarePanel.empty])
				p.y = (p.y+1)%row;
			else if(Grid.get(p.y).get((p.x-1+col)%col).getBackground()==SquarePanel.C[SquarePanel.empty])
				p.x = (p.x-1+col)%col;
			else if(Grid.get(p.y).get((p.x+1)%col).getBackground()==SquarePanel.C[SquarePanel.empty])
				p.x = (p.x+1)%col;		
		//}
		 */
		//System.out.println("nextnode is x="+p.x+", y="+p.y+"\n");
		//return p;
	}
}


	
	
	
	
	
	

