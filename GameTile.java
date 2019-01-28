import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameTile extends Tile {

	protected boolean flipped;
	protected int num;
	protected boolean voltorb;

	public GameTile() {
		flipped = false;
		num = 1;
		voltorb = false;
	}
	
	public boolean isFlipped() {
		return flipped;
	}

	public void flip() {
		flipped = true;
	}

	public boolean isVoltorb() {
		return voltorb;
	}

	public boolean isNum(int n) {
		return (n == num);
	}

	public boolean isNumTile() {
		if (num > 1) {
			return true;
		}
		return false;
	}

	public int getNum() {
		return num;
	}

	public boolean isDefault() {
		if (isNum(1)) {
			if (!voltorb) {
				return true;
			}
		}
		return false;
	}
	
	public void setDefault() {
		flipped = false;
		num = 1;
		voltorb = false;
	}

	@Override
	public void draw(Graphics g) {
		Font newFont = new Font("Sans Serif", Font.BOLD, 20);
		g.setFont(newFont);
		if (!flipped) {
			g.setColor(new Color(50, 200, 100));
			g.fillRect(getX(), getY(), getWidth(), getHeight());
			g.setColor(Color.WHITE);
			g.drawRect(getX(), getY(), getWidth(), getHeight());

		}
		else {
			g.setColor(Color.WHITE);
			g.fillRect(getX(), getY(), getWidth(), getHeight());
			g.setColor(Color.BLACK);
			drawStringMiddle(g, "" + num, getX(), getY(), getWidth(), getHeight(), 
					new Font("Sans Serif", Font.BOLD, 40));
		}
	}

}
