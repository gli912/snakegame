import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class AStarSearch{
	private AStarSearch parent;
	public position p;
	private int H;
	private int G;
	private int F;
	private boolean dirtyH;
	private boolean closed;
	private boolean inminheap;
	
	private static int row = Window.row;
	private static int col = Window.col;
	public static position foodposition;
	public static position headposition = snakecontroller.headposition;
	public static ArrayList<ArrayList<SquarePanel>> Grid = Window.Grid;
	public static ArrayList<ArrayList<AStarSearch>> AstarArray = new ArrayList<ArrayList<AStarSearch>>()
	{
		private static final long serialVersionUID = 1L;
		{
		for(int i=0; i<row; i++){
			ArrayList<AStarSearch> tmp = new ArrayList<AStarSearch>();
			for(int j=0; j<col; j++){
				tmp.add(new AStarSearch(j, i));
			}
			add(tmp);
		}
	}};
	public static Comparator<AStarSearch> cmp = new Comparator<AStarSearch>(){
		public int compare(AStarSearch arg0, AStarSearch arg1) {
			return arg0.F - arg1.F;
		}
	};
	public static PriorityQueue<AStarSearch> minheap = new PriorityQueue<AStarSearch>(30,cmp);

	AStarSearch(int x, int y){
		p=new position(x, y);
		closed = false;
		dirtyH = false;
		inminheap = false;
	}
	
	public static boolean findpath()
	{
		AStarSearch start = AstarArray.get(headposition.y).get(headposition.x);
		start.G = 0;
		start.H = start.getH();
		start.F = start.H;
		minheap.add(start);
		start.inminheap = true;
		while(!minheap.isEmpty())
		{
			AStarSearch tmp = minheap.peek();
			minheap.remove();
			tmp.inminheap = false;
			if(tmp.checkarround()==true) 
			{
				System.out.println("findpath succeed \t");
				return true;
			}
			tmp.closed = true;
		}
		System.out.println("findpath failed at head is x="+headposition.x+", y="+headposition.y+".\t");
		
		return false;
	}
	
	public static position nexthead()
	{
		AStarSearch a = AstarArray.get(foodposition.y).get(foodposition.x);		
		while(!a.parent.p.isequal(headposition))
		{
			a = a.parent;
		}
		System.out.println("nexthead is x="+a.p.x+", y="+a.p.y+".\t");
		return a.p;
	}
	
	public boolean checkarround(){
		AStarSearch tmp;
		
		tmp = AstarArray.get((p.y-1+row)%row).get(p.x); //up;
		if(!tmp.closed){
			tmp.getF_addtominheap(this);
			if(tmp.p.isequal(foodposition)) return true;	
		}
		
		tmp = AstarArray.get((p.y)).get((p.x+1)%col); //right;
		if(!tmp.closed){
			tmp.getF_addtominheap(this);
			if(tmp.p.isequal(foodposition)) return true;
		}
		
		tmp = AstarArray.get((p.y+1)%row).get(p.x); //down;
		if(!tmp.closed){
			tmp.getF_addtominheap(this);
			if(tmp.p.isequal(foodposition)) return true;
		}

		tmp = AstarArray.get((p.y)).get((p.x-1+col)%col); //left;
		if(!tmp.closed){
			tmp.getF_addtominheap(this);
			if(tmp.p.isequal(foodposition)) return true;
		}

		return false;
	}
	
	public int getF_addtominheap (AStarSearch a){
		if(Grid.get(this.p.y).get(this.p.x).getBackground()==SquarePanel.C[SquarePanel.snake])
		{
			closed=true;
			return -1;
		}
		int f = getH()+getG(a);
		if(inminheap==false)
		{
			F = f;
			G = getG(a);
			parent = a;
			minheap.add(this);
			inminheap=true;
		}
		else if(F>f)
		{
			minheap.remove(this);
			F = f;
			G = getG(a);
			parent = a;
			minheap.add(this);
		}
		return f;
	}
	
	public int getG (AStarSearch a){
		return a.G+1;
	}
	
	public int getH (){
		if(dirtyH == true) return H;
		int x = Math.abs(this.p.x-foodposition.x);
		x=Math.min(x, col-x);
		int y = Math.abs(this.p.y-foodposition.y);
		y=Math.min(y, col-y);
		H = x+y;
		dirtyH = true;
		return H;	
	}
	
	public static void reset(){
		for(int i=0; i<row; i++)
		{
			for(int j=0; j<col; j++)
			{
				AstarArray.get(i).get(j).p.x = j;
				AstarArray.get(i).get(j).p.y =  i;
				AstarArray.get(i).get(j).closed=false;
				AstarArray.get(i).get(j).dirtyH=false;
				AstarArray.get(i).get(j).inminheap=false;
				minheap.clear();
			}
		}
		headposition = snakecontroller.headposition;
	}
	
	

}
