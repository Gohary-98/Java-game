package view;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class view extends JFrame{
	JFrame frame;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	JPanel panel5;
	JTextArea txt1;
	JTextArea txt2;
	JTextArea txt3;
	JTextArea exd;
	JTextArea cuurd;
	JPanel unitsbgd;
	JPanel gridpanel;
	JPanel unitspanel;
	JButton nextcycle;
	JButton hint;
	JPanel occupants;
	
	
	public view(){
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//making the grid panel-------------------------------
		occupants=new JPanel();
		//panel1.setBounds(0, 50, screenSize.width/3, 300);
		occupants.setLayout(new GridLayout(2,4,20,20));
		occupants.setBorder(BorderFactory.createTitledBorder("content of this cell"));
		occupants.setBackground(Color.PINK);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		gridpanel=new JPanel();
		gridpanel.setBackground(Color.orange);
		//this.setSize(new Dimension((int)screenSize.getWidth(),(int)screenSize.getHeight()));
		//gridpanel.setSize(new Dimension((int)screenSize.getWidth()/3,(int)screenSize.getHeight()/3));
		//dool el mafrood ya3ni el units fahem ?=====================================================================
				panel1=new JPanel();
				//panel1.setBounds(0, 50, screenSize.width/3, 300);
				panel1.setLayout(new GridLayout(2,4,20,20));
				panel1.setBorder(BorderFactory.createTitledBorder("Available Units"));
				panel1.setBackground(Color.pink);
				panel2=new JPanel();
				panel2.setLayout(new GridLayout(2,4,20,20));
				panel2.setBorder(BorderFactory.createTitledBorder("Responding Units"));
				//panel2.setBounds(0, 400, screenSize.width/3, 200);
				panel2.setBackground(Color.pink);
				panel3=new JPanel();
				panel3.setLayout(new GridLayout(2,4,20,20));
				panel3.setBorder(BorderFactory.createTitledBorder("Treating Units"));
				//panel3.setBounds(0, 640, screenSize.width/3, 150);
				panel3.setBackground(Color.pink);
				
		//el shkl el 3la el ymeen da el ana msh 3aref azbto lsa 
				unitsbgd=new JPanel();
				unitsbgd.setBackground(Color.pink);
				unitsbgd.setLayout(new GridLayout(4,1,10,10));
				unitsbgd.add(panel1);
				unitsbgd.add(panel2);
				unitsbgd.add(panel3);
				unitsbgd.add(occupants);
		//trying to add a scroll bar =================================
				JTextArea currd=new JTextArea();
				//currd.setBorder((BorderFactory.createTitledBorder("log")));
				JTextArea yarab=new JTextArea();
			
				JScrollPane jsp2=new JScrollPane(currd,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				JScrollPane jsp3=new JScrollPane(yarab,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//============================================================
				JTextArea currentCycle=new JTextArea("current cycle ");
				currentCycle.setFont(new Font("TimesRoman", Font.BOLD,50));
				JTextArea numberOfCasulties=new JTextArea("number of casulties is ");
				numberOfCasulties.setFont(new Font("TimesRoman", Font.BOLD,40));
				nextcycle=new JButton("next cycle ");
				nextcycle.setFont(new Font("TimesRoman", Font.BOLD,40));
				hint=new JButton("Hint");
				hint.setFont(new Font("TimesRoman", Font.BOLD,40));
				panel5=new JPanel();
				panel5.setLayout(new GridLayout(4,1,10,10));
				panel5.setBackground(Color.pink);
				
		//tlata 3la el shmal=======================================
				panel4=new JPanel();
				panel4.setBackground(Color.pink);
				panel4.setLayout(new GridLayout(4,1,20,20));
				panel4.add(panel5);
		//the jframe-------------------------------------------------------
				//this.setMinimumSize(new Dimension(screenSize.width, screenSize.height));
			    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			    this.setBackground(Color.pink);
			    this.setLayout(new GridLayout(1,3,10,10));
			    this.getContentPane().add(panel4);
			    this.getContentPane().add(gridpanel);
			    this.getContentPane().add(unitsbgd);
			    //this.revalidate();
			    //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
			    //this.pack();
			    this.setVisible(true);
	}
	public void genGridButtons(ArrayList<JButton> gridButtons) {
		gridpanel.setLayout(new GridLayout(10,10,6,6));
		for (int i = 0; i < 100; i++) {
			gridpanel.add(gridButtons.get(i));
			
		}
		
	}
	public void genUnitsButtons(ArrayList<JToggleButton> units) {
		panel1.setLayout(new GridLayout(4,2,20,20));
		for(int i=0;i<units.size();i++) {
			panel1.add(units.get(i));
		}
	
	}
		public void addRespondingUnits(JToggleButton unit) {
			panel2.add(unit);
			
		}
		public void addTeatingUnits(JToggleButton unit) {
			panel3.add(unit);
		
		}
		public void addoccupants(JButton unit) {
			occupants.add(unit);
		
		}
		public JPanel getOccupants() {
			return occupants;
		
		}
		
	public static void main(String[] args) {
		new view();
			}
	public JTextArea getCuurd() {
		return cuurd;
	}
	public JButton getNextcycle() {
		return nextcycle;
	}
	public JButton getHint() {
		return hint;
	}
	public void getUnitsbgd(JPanel p) {
		unitsbgd.add(p);
	}
	public void setCuurd(JTextArea cuurd) {
		this.cuurd = cuurd;
		
	}
	public JPanel getPanel4() {
		return panel4;
	}
	public void addPanel4(JTextArea txt) {
		panel4.add(txt);
	}
	public void addPanel4(JScrollPane txt) {
		panel4.add(txt);
	}
	public void addpanel5(JButton button) {
		panel5.add(button);
	}
	public void addpanel5(JTextArea txt) {
		panel5.add(txt);
	}
	public JPanel getPanel1() {
		return panel1;
	}
	public JPanel getPanel2() {
		return panel2;
	}
	public JPanel getPanel3() {
		return panel3;
	}
	public void setPanel1(JPanel panel1) {
		this.panel1 = panel1;
	}
	public void setPanel2(JPanel panel2) {
		this.panel2 = panel2;
	}
	public void setPanel3(JPanel panel3) {
		this.panel3 = panel3;
	}


}
