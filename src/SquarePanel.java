import java.awt.Color;

import javax.swing.JPanel;



public class SquarePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	//0 snake, 1 food, 2 empty
	public static Color[] C={Color.BLACK, Color.BLUE, Color.WHITE};
	public static final int snake=0;
	public static final int food=1;
	public static final int empty = 2;
	
	public SquarePanel(int i)
	{
		setBackground(C[i]);
	}

	public void Changecolor(int i)
	{
		setBackground(C[i]);
		repaint();
	}

}
