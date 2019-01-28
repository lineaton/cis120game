import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


@SuppressWarnings("serial")
public class VoltorbTile extends GameTile{
	public VoltorbTile() {
		voltorb = true;
		num = 0;
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
			g.setColor(Color.WHITE);
			g.drawRect(getX(), getY(), getWidth(), getHeight());
//			drawStringMiddle(g, "?", getX(), getY(), getWidth(), getHeight(), 
//					new Font("Sans Serif", Font.BOLD, 40));
		}
		else {
			g.setColor(Color.RED);
			g.fillOval(getX(), getY(), getWidth(), getHeight());
			g.setColor(new Color(250, 200, 50));
			
			g.drawOval(getX(), getY(), getWidth(), getHeight());
			g.setColor(Color.WHITE);
			drawStringMiddle(g, "V", getX(), getY(), getWidth(), getHeight(), 
					new Font("Sans Serif", Font.BOLD, 40));
		}
	}

	
}
