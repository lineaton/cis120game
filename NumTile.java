import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class NumTile extends GameTile{
	
	public NumTile() {
		double random = Math.random();
		if (random < .33) {
			num = 3;
		}
		else {
			num = 2;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		Font newFont = new Font("Sans Serif", Font.BOLD, 40);
		g.setFont(newFont);
		if (!flipped) {
			g.setColor(new Color(50, 200, 100));
			g.fillRect(getX(), getY(), getWidth(), getHeight());
			g.setColor(Color.WHITE);
			g.drawRect(getX(), getY(), getWidth(), getHeight());
//			drawStringMiddle(g, "#", getX(), getY(), getWidth(), getHeight(), 
//					new Font("Sans Serif", Font.BOLD, 40));
		}
		else {
			if (num == 3) {
				g.setColor(new Color(150, 255, 200));
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
			else {
				g.setColor(new Color(220, 255, 255));
				g.fillRect(getX(), getY(), getWidth(), getHeight());
			}
			g.setColor(Color.BLACK);
			drawStringMiddle(g, "" + num, getX(), getY(), getWidth(), getHeight(), 
					new Font("Sans Serif", Font.BOLD, 40));
		}
	}


	
	
	
}
