import java.util.Collection;
import java.util.LinkedList;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel {
	public boolean playing = false; // whether the game is running 
	private JLabel status; // Current status text, i.e. "Running..."
	private GameTile[][] board;
	private final int width = 5;
	private final int height = 5;
	private int[] columnVoltorbs;
	private int[] rowVoltorbs;
	private int[] columnSums;
	private int[] rowSums;
	private int currScore;
	private Collection<GameTile> numTiles = new LinkedList<GameTile>();
	private Collection<GameTile> flippedTiles = new LinkedList<GameTile>();
	private boolean winState;
	private boolean loseState;
	private final int BOARD_HEIGHT = 800;
	private final int BOARD_WIDTH = 800;
	private Indicator[] columnInd;
	private Indicator[] rowInd;
	private static Score score;
	private int difficulty;
	private JLabel totalScore = new JLabel();
	private JButton advance = new JButton();
	private int finalScore;

	public Board(JLabel status, JLabel totalScore, JButton advance) {
		finalScore = 0;
		difficulty = 0;
		currScore = 0;
		score = new Score(currScore);
		winState = false;
		loseState = false;
		board = new GameTile[width][height];
		columnInd = new Indicator[width];
		rowInd = new Indicator[height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j] = new GameTile();
			}
		}

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (playing) {
				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {
						if (e.getX() >= (i * 120) + 120 && e.getX() <= (i * 120) + 220) {
							if (e.getY() >= (j * 120) + 120 && e.getY() <= (j * 120) + 220) {
								flipGameTile(board[i][j]);
							}
						}
					}
				}
				repaint();
				}
			}
		});

		columnVoltorbs = new int[width];
		rowVoltorbs = new int[height];
		columnSums = new int [width];
		for (int x = 0; x < columnSums.length; x++) {
			columnSums[x] = 5;
		}
		rowSums = new int[height];
		for (int y = 0; y < rowSums.length; y++) {
			rowSums[y] = 5;
		}

		for (int x = 0; x < width; x++) {
			columnInd[x] = new Indicator(x, 0);
			columnInd[x].setSum(columnSums[x]);

		}
		for (int y = 0; y < height; y++) {
			rowInd[y] = new Indicator(y, 1);
			rowInd[y].setSum(rowSums[y]);
		}

		this.status = status;
		this.totalScore = totalScore;
		Timer timer = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!
		this.advance = advance;
		advance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getWin()) {
					advanceReset();
				}
			}
		});
	}

	//GETTERS
	public GameTile[][] getBoard() {
		return board;
	}

	public int getcolumnVoltorb(int column) {
		return columnVoltorbs[column];
	}

	public int getRowVoltorb(int row) {
		return rowVoltorbs[row];
	}

	public int getcolumnSum(int column) {
		return columnSums[column];
	}

	public int getRowSum(int row) {
		return rowSums[row];
	}

	public int getCurrScore() {
		return currScore;
	}

	public Score getScore() {
		return score;
	}

	public boolean getWin() {
		return winState;
	}

	public boolean getLose() {
		return loseState;
	}

	public int getFinalScore() {
		return finalScore;
	}

	//addVoltorb function
	public int[] addVoltorb() {
		int[] location = new int[2];
		location[0] = -1;
		location[1] = -1;
		if(!isFull()) {
			boolean added = false;
			while (!added) {
				int randomX = (int) (Math.random() * 5);
				int randomY = (int) (Math.random() * 5);
				if (board[randomX][randomY].isDefault()) {
					board[randomX][randomY] = new VoltorbTile();
					updateVoltorb(randomX, randomY);
					updateSums(randomX, randomY);
					location[0] = randomX;
					location[1] = randomY;
					added = true;
				}
			}
		}
		return location;
	}

	public void addVoltorb(int x, int y) {
		board[x][y] = new VoltorbTile();
		updateVoltorb(x, y);
	}

	//addNumTile function
	public int[] addNumTile() {
		int[] location = new int[2];
		location[0] = -1;
		location[1] = -1;
		if (!isFull()) {
			boolean added = false;
			while (!added) {
				int randomX = (int) (Math.random() * 5);
				int randomY = (int) (Math.random() * 5);
				if (board[randomX][randomY].isDefault()) {
					board[randomX][randomY] = new NumTile();
					updateSums(randomX, randomY);
					numTiles.add(board[randomX][randomY]);
					location[0] = randomX;
					location[1] = randomY;
					added = true;
				}
			}
		}
		return location;
	}

	public void addNumTile(int x, int y) {
		board[x][y] = new NumTile();
		updateSums(x, y);
	}

	private boolean isFull() {
		boolean b = true;
		while (b) {
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (board[i][j].isVoltorb()) {
						continue;
					}
					else if (board[i][j].isNumTile()) {
						continue;
					}
					else {
						b = false;
					}
				}
			}
		}
		return b;
	}

	private void updateVoltorb(int x, int y) {
		columnVoltorbs[x]++;
		rowVoltorbs[y]++;
		columnInd[x].addVoltorb();
		rowInd[y].addVoltorb();
	}

	private void updateSums(int x, int y) {
		int sumX = 0;
		int sumY = 0;
		for (int i = 0; i < width; i++) {
			sumY += board[i][y].getNum();
		}
		for (int j = 0; j < height; j++) {
			sumX += board[x][j].getNum();
		}
		columnSums[x] = sumX;
		rowSums[y] = sumY;
		columnInd[x].setSum(columnSums[x]);
		rowInd[y].setSum(rowSums[y]);
	}

	public void flipGameTile(GameTile t) {
		t.flip();
		flippedTiles.add(t);
		updateCurrScore();
		score.updateCurrScore(currScore);
		if (checkWin()) {
			winState = true;
		}
		else if (t.isVoltorb()) {
			loseState = true;
		}
		repaint();
	}

	private void updateCurrScore() {
		int middle = 1;
		for (GameTile t: flippedTiles) {
			middle *= t.getNum();
		}
		currScore = middle;
	}

	private boolean checkWin() {
		int productNumTiles = 1;
		int productFlippedTiles = 1;
		for (GameTile t: numTiles) {
			productNumTiles *= t.getNum();
		}
		for (GameTile t: flippedTiles) {
			productFlippedTiles *= t.getNum();
		}
		if (productNumTiles == productFlippedTiles) {
			return true;
		}
		else return false;
	}

	//TODO: Reset function
	public void advanceReset() {
		removeAll();
		updateUI();
		winState = false;
		loseState = false;
		currScore = 0;
		score.updateCurrScore(currScore);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j] = new GameTile();
				updateVoltorb(i, j);
				updateSums(i, j);
			}
		}
		for (int x = 0; x < width; x++) {
			columnInd[x] = new Indicator(x, 0);
			columnInd[x].setSum(columnSums[x]);

		}
		for (int y = 0; y < height; y++) {
			rowInd[y] = new Indicator(y, 1);
			rowInd[y].setSum(rowSums[y]);
		}
		numTiles.removeAll(numTiles);
		flippedTiles.removeAll(flippedTiles);

		for(int i = 0; i < 5 + difficulty; i++) {
			addVoltorb();
			addNumTile();
		}
		playing = true;
		status.setFont(new Font("Arial", Font.BOLD, 15));
		status.setText("Running...");
	}

	public void totalReset() {
		removeAll();
		updateUI();
		winState = false;
		loseState = false;
		currScore = 0;
		score = new Score(currScore);
		difficulty = 0;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j] = new GameTile();
				updateVoltorb(i, j);
				updateSums(i, j);
			}
		}
		for (int x = 0; x < width; x++) {
			columnInd[x] = new Indicator(x, 0);
			columnInd[x].setSum(columnSums[x]);

		}
		for (int y = 0; y < height; y++) {
			rowInd[y] = new Indicator(y, 1);
			rowInd[y].setSum(rowSums[y]);
		}
		numTiles.removeAll(numTiles);
		flippedTiles.removeAll(flippedTiles);

		for(int i = 0; i < 5 + difficulty; i++) {
			addVoltorb();
			addNumTile();
		}
		playing = true;
		status.setFont(new Font("Arial", Font.BOLD, 15));
		status.setText("Running...");
		totalScore.setText("$" + Score.getScore());
	}

	void tick(){
		if (playing) {
			// check for the game end conditions
			if (winState) {
				playing = false;
				if (difficulty < 7) {
					difficulty++;
				}
				score.addAccScore(currScore);
				status.setFont(new Font("Arial", Font.BOLD, 15));
				status.setText("You win!");
				totalScore.setText("$" + Score.getScore());
				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {
						board[i][j].flip();
					}
				}
				repaint();
			} else if (loseState) {
				playing = false;
				status.setFont(new Font("Arial", Font.BOLD, 15));
				status.setText("You lose!");
				totalScore.setText("$" + Score.getScore());
				finalScore = Score.getScore();
				for (int i = 0; i < width; i++) {
					for (int j = 0; j < height; j++) {
						board[i][j].flip();
					}
				}
				repaint();
				try {
					Game.highScores("files/HighScores.txt", finalScore);
				} catch (IOException e) {
					System.out.println("File Input Failure.");
					e.printStackTrace();
				}

			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				board[i][j].setX((i * 120) + 120);
				board[i][j].setY((j * 120) + 120);
				board[i][j].draw(g);
				columnInd[i].setX((i * 120) + 120);
				columnInd[i].setY(0);
				columnInd[i].draw(g);
				rowInd[j].setX(0);
				rowInd[j].setY((j * 120) + 120);
				rowInd[j].draw(g);
				score.draw(g);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
	}
}	

