import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class Tile extends JPanel {
	private static int height = 100;
	private static int width = 100;
	private int x;
	private int y;
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tile() {
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setHeight(int h) {
		height = h;
	}

	public void setWidth(int w) {
		width = w;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	protected void drawStringMiddle(Graphics g, String s, int x, int y, int width, int height
			, Font font) {
	    FontMetrics metrics = g.getFontMetrics(font);
	    x += (width - metrics.stringWidth(s)) / 2;
	    y += ((height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.setFont(font);
	    g.drawString(s, x, y);
	}

	public abstract void draw(Graphics g);
}
