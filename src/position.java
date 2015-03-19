
public class position {
	public int x;
	public int y;
	position(position p)
	{
		this.x = p.x;
		this.y = p.y;
	}
	position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void change(int x, int y){
		this.x=x;
		this.y=y;
	}
	public void change(position p){
		this.x=p.x;
		this.y=p.y;
	}
	
	public boolean isequal(position p)
	{
		return this.x==p.x && this.y == p.y;
	}
}
