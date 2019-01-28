/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Iterator;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.*;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
		//TODO: Implement some sort of I/O

		// NOTE : recall that the 'final' keyword notes immutability even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		String titleText = "Voltorb Flip";

		String bodyText = "Overview:\nVOLTORB FLIP is a classic mingame inspired by Pokemon "
				+ "SoulSilver. The aim of the game is to earn as many coins as possible. There is "
				+ "a 5 x 5 board of tiles,\nand every tile has the potential to be either your "
				+ "best friend, or your worst enemy. Just keep your eyes peeled for the sneaky "
				+ "Voltorbs, if you even find one,\nyou will be dead.\n\nTile Types:\nThere are "
				+ "two "
				+ "categories of tiles: Multiplier and Voltorb.\nMultipliers come in three flavors:"
				+ " 1, 2, or 3. Multiplier tiles act as a multiplier to your coin count. They are "
				+ "summed up into the sum of the indicators for each row\nand column.\nVoltorbs "
				+ "are mischievous, round, red Pokemon, and steal all your coins if they are "
				+ "revealed. They are summed "
				+ "up in the Voltorb values of the indicators for each\nrow and column."
				+ "\n\nControls:\n"
				+ "Click on any unrevealed tile to flip and reveal the hidden truth beneath.\n"
				+ "The Advance button is used to advance to the next round if you have completed "
				+ "the current round. The Reset button is to totally reset your progress :(.\n\n"
				+ "Mechanics:\nThe first "
				+ "tile you pick will be used as your base reference. If you reach the highest "
				+ "possible score for each round, you advance to the next, keeping your coins and\n"
				+ "adding it into your game-long coin case. On the other hand, clicking a Voltorb "
				+ "tile takes away all your coins from the round and ends the game.\n\nObjective:"
				+ "\nAccumulate the most coins in your coin case without meeting a Voltorb!\n\n"
				+ "Good luck!";
		JTextArea title = new JTextArea(titleText);
		JTextArea body = new JTextArea(bodyText);
		JPanel panel = new JPanel();
		panel.add(title, BorderLayout.NORTH);
		panel.add(body);
		title.setFont(new Font("Arial", Font.BOLD, 36));
		body.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		JOptionPane.showMessageDialog(null, panel, "  Game Instructions", 
				JOptionPane.INFORMATION_MESSAGE);

		final JFrame frame = new JFrame("VOLTORB FLIP");
		frame.setLocation(0, 0);

		final JPanel score_panel = new JPanel();
		frame.add(score_panel, BorderLayout.NORTH);
		
		final JLabel scoreTitle = new JLabel("Coin Case: ");
		scoreTitle.setFont(new Font("Arial", Font.BOLD, 40));
		
		final JLabel totalScore = new JLabel("$" + Score.getScore());
		totalScore.setFont(new Font("Arial", Font.PLAIN, 40));
		
		score_panel.add(scoreTitle);
		score_panel.add(totalScore);
		
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.SOUTH);
		
		final JButton advance = new JButton("Advance");
		advance.setFont(new Font("Arial", Font.BOLD, 15));
		control_panel.add(advance);
		
		final JLabel status = new JLabel("Running...");
		advance.setFont(new Font("Arial", Font.BOLD, 15));
		control_panel.add(status);

		


		// Main playing area
		final Board board = new Board(status, totalScore, advance);
		frame.add(board, BorderLayout.CENTER);
		for(int i = 0; i < 5; i++) {
			board.addVoltorb();
			board.addNumTile();
		}
		board.tick();

		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.totalReset();
			}
		});
		reset.setFont(new Font("Arial", Font.BOLD, 15));
		control_panel.add(reset);

		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		board.totalReset();
	}

	public static void highScores(String filename, int highScore) throws IOException {
		String name = JOptionPane.showInputDialog("What's your name? ");

		TreeMap<Integer, String> highScores = new TreeMap<Integer,String>();
		highScores.put(highScore, name);

		BufferedReader in = new BufferedReader(new FileReader(filename));


		String fileString = "";
		while(in.ready()) {
			int thisScore = Integer.parseInt(in.readLine());
			String thisName = in.readLine();
			highScores.put(thisScore, thisName);
			fileString += thisScore + "\n" + thisName + "\n";
		}
		in.close();

		BufferedWriter out = new BufferedWriter(new FileWriter(filename));
		out.write(fileString);
		out.write(highScore + "\n" + name + "\n");
		out.close();

		String printString = "";
		NavigableMap<Integer,String> descScores = highScores.descendingMap();
		Set<Integer> scores = descScores.keySet();
		int x = 0;
		for (Integer i: scores) {
			if (i == 0) {
				continue;
			}
			else {
				if (x < 10) {
					printString += descScores.get(i) + "--- $" + i + "\n";
					x++;
				}
				else {
					continue;
				}
			}
		}
		JOptionPane.showMessageDialog(null, printString, "SCOREBOARD", JOptionPane.PLAIN_MESSAGE);
	}
	/**
	 * Main method run to start and run the game. Initializes the GUI elements specified in Game and
	 * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Game());;

	}
}