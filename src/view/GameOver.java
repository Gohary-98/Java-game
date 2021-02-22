package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import controller.CommandCenter;

public class GameOver extends JFrame {
	JTextArea score;
	JButton endGame;
	JButton newGame;
	public GameOver(int casulties) throws Exception {
		score =new JTextArea(""+ casulties);
		score.setFont(new Font("Ariel", Font.BOLD,70));
		score.setForeground(Color.yellow);
		score.setEditable(false);
		score.setOpaque(false);
		endGame=new JButton("end Game");
		endGame.addActionListener(new endGameActionListner());
		newGame=new JButton("new Game");
		newGame.addActionListener(new newGameActionListner());
		//===================================================
		ImageIcon image2=new ImageIcon("game over.png");
		Image im2=image2.getImage();
		im2=im2.getScaledInstance(1540, 800, java.awt.Image.SCALE_SMOOTH);
		image2=new ImageIcon(im2);
		//===================================================
		score.setBounds(800, 370, 500, 100);
		endGame.setBounds(650,600,100,100);
		newGame.setBounds(800, 600, 100, 100);
		//===================================================
		JLabel background=new JLabel();
		background.setLayout(null);
		background.add(new JButton("Start"));
		background.setIcon(image2);
		background.add(score);
		background.add(newGame);
		background.add(endGame);
		//===================================================
		this.setTitle("game over");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.add(background);
		
		this.setVisible(true);
	}
	public static void main(String [] args) throws Exception {
		new GameOver(5);
	}
	public class endGameActionListner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			
		}
		
	}
	public class newGameActionListner implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			try {
				new CommandCenter();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
		}
		
	}

}
