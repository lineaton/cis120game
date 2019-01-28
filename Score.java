import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

@SuppressWarnings("serial")
public class Score extends Tile {
	private static int accScore;
	private int currScore;
	
	public Score(int currScore) {
		accScore = 0;
		this.currScore = currScore;
	}
	
	public void addAccScore(int score) {
		accScore += score;
	}
	public void updateCurrScore(int currScore) {
		this.currScore = currScore;
	}
	
	public static int getScore() {
		return accScore;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(250, 200, 50));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.drawString("Coins", getX() + 35, getY() + 80);
		drawStringMiddle(g, "" + currScore, getX(), getY(), getWidth(), getHeight(), 
				new Font("Arial", Font.BOLD, 30));
	}

}
