import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Indicator extends Tile{
	private int numVoltorb;
	private int sum;
	private int column;
	private int row;
	
	public Indicator(int n, int xyDetermine) {
		numVoltorb = 0;
		sum = 0;
		if (xyDetermine == 0) {
			column = n;
			row = -1;
		}
		else {
			column = -1;
			row = n;
		}
		
	}
	
	public void setSum(int sum) {
		this.sum = sum;
	}
	
	public void setVoltorb(int voltorb) {
		this.numVoltorb = voltorb;
	}
	
	public void addVoltorb() {
		numVoltorb++;
	}
	
	public void addNumTile(int n) {
		sum += n;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		Font fontB = new Font("Arial", Font.BOLD, 12);
		FontMetrics metricsB = g.getFontMetrics(fontB);
		String sumString = "" + sum;
	    String volString = "V: " + numVoltorb;
	    int volX = getX() + (getWidth() - metricsB.stringWidth(volString)) / 2;
		g.setColor(new Color(220, 220, 230));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.WHITE);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.BLACK);
		drawStringMiddle(g, sumString, getX(), getY(), getWidth(), getHeight(), 
				new Font("Arial", Font.BOLD, 30));
		g.setFont(fontB);
		g.setColor(Color.RED);
		g.drawString(volString, volX, getY() + 80);
	}
	
}
