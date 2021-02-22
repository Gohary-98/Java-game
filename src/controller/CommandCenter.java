package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CannotTreatException;
import exceptions.CitizenAlreadyDeadException;
import exceptions.IncompatibleTargetException;
import model.disasters.*;
import model.events.SOSListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.*;
import simulation.*;
import view.GameOver;
import view.view;

public class CommandCenter implements SOSListener {

	private Simulator engine;
	private ArrayList<ResidentialBuilding> visibleBuildings;
	private view view;
	private ArrayList<Citizen> visibleCitizens;
	private ArrayList<JButton> gridButtons;
	private JTextArea log;
	private JTextArea infoPanel;
	private JTextArea activeDisasters;
	//	private JButton Ambulance;
	//	private JButton GasControlUnit;
	//	private JButton FireTruck;
	//	private JButton Evacuator;
	//	private JButton DiseasControlUnit;
	private ArrayList<JToggleButton> availableUnits;
	private JButton nextCycle;
	private JButton hint;
	private JTextArea currentCycle;
	private JTextArea numOfCasulties;
	private int minHP = 100;
	private int minStructuralIntegrity = 100;
	private int maxFireDamage = 0;
	private int maxGasLeak = 0 ;
	private int maxFoundationDamage =0 ;
	private int maxToxicity = 0 ;
	private int maxBloodLoss = 0;
	boolean collapsed = false ;

	
	

	@SuppressWarnings("unused")
	private ArrayList<Unit> emergencyUnits;

	public CommandCenter() throws Exception {
		//emergencyUnits=engine.getEmergencyUnits();
		view=new view();
		activeDisasters=new JTextArea();
		engine = new Simulator(this);
		visibleBuildings = new ArrayList<ResidentialBuilding>();
		visibleCitizens = new ArrayList<Citizen>();
		emergencyUnits = engine.getEmergencyUnits();
		gridButtons=new ArrayList<JButton>();
		availableUnits=new ArrayList<JToggleButton>();
		for (int i=0;i<100;i++) {
			Boolean flag=false;
			int y=i%10;
			int z=i/10;
			int x1=z;
			JButton x=new JButton(""+x1+y);
			for(ResidentialBuilding b:engine.getBuildings())
				if(b.getLocation().getX()==x1&&b.getLocation().getY()==y) {

					flag=true;
				}
				else x.setBackground(Color.white);
			if(!flag) {
				for(Citizen c:engine.getCitizens())
					if(c.getLocation().getX()==x1&&c.getLocation().getY()==y) {

						flag=true;
					}
					else x.setBackground(Color.white);}
			for(Unit c:engine.getEmergencyUnits())
				if(c.getLocation().getX()==x1&&c.getLocation().getY()==y) {

					flag=true;
				}
				else x.setBackground(Color.white);
			if(!flag) 
				x.setEnabled(false);
			x.setBackground(Color.white);
			x.setFont(new Font("TimesRoman", Font.BOLD,9));
			x.addActionListener(new GridButtonsActionListner());
			gridButtons.add(x);
		}
		//==========================================================================================
		ImageIcon ambulance=new ImageIcon("ambulance.png");
		Image amb=ambulance.getImage();
		amb=amb.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		ambulance=new ImageIcon(amb);
		ImageIcon disease=new ImageIcon("disease.png");
		Image des=disease.getImage();
		des=des.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		disease=new ImageIcon(des);
		ImageIcon evacuator=new ImageIcon("evacuator.png");
		Image eva=evacuator.getImage();
		eva=eva.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		evacuator=new ImageIcon(eva);
		ImageIcon firetruck=new ImageIcon("fire-truck (1).png");
		Image fit=firetruck.getImage();
		fit=fit.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		firetruck=new ImageIcon(fit);
		ImageIcon gascontrol=new ImageIcon("pipes.png");
		Image gas=gascontrol.getImage();
		gas=gas.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		gascontrol=new ImageIcon(gas);
		for (int i=0;i<engine.getEmergencyUnits().size();i++) {
			Unit u=engine.getEmergencyUnits().get(i);
			if (u instanceof FireTruck) {
				JToggleButton b=new JToggleButton();
				b.setName(u.getUnitID());
				b.setIcon(firetruck);
				b.setBorder(null);
				b.setBorderPainted(false);
				b.setContentAreaFilled(false);
				b.setOpaque(false);
				b.setPreferredSize(new Dimension(100,100));
				b.setBackground(Color.black);
				b.setForeground(Color.white);
				b.addActionListener(new UnitsActionListener());
				availableUnits.add(b);
				view.getPanel1().add(b);

			}
			if (u instanceof Ambulance) {
				JToggleButton b=new JToggleButton();
				b.setName(u.getUnitID());
				b.setIcon(ambulance);
				b.setBorder(null);
				b.setBorderPainted(false);
				b.setContentAreaFilled(false);
				b.setOpaque(false);
				b.setPreferredSize(new Dimension(100,100));
				b.setBackground(Color.black);
				b.setForeground(Color.white);
				b.addActionListener(new UnitsActionListener());
				availableUnits.add(b);
				view.getPanel1().add(b);

			}
			if (u instanceof GasControlUnit) {
				JToggleButton b=new JToggleButton();
				b.setName(u.getUnitID());
				b.setIcon(gascontrol);
				b.setBorder(null);
				b.setBorderPainted(false);
				b.setContentAreaFilled(false);
				b.setOpaque(false);
				b.setPreferredSize(new Dimension(100,100));
				b.setBackground(Color.black);
				b.setForeground(Color.white);
				b.addActionListener(new UnitsActionListener());
				availableUnits.add(b);
				view.getPanel1().add(b);

			}
			if (u instanceof Evacuator) {
				JToggleButton b=new JToggleButton();
				b.setName(u.getUnitID());
				b.setIcon(evacuator);
				b.setBorder(null);
				b.setBorderPainted(false);
				b.setContentAreaFilled(false);
				b.setOpaque(false);
				b.setPreferredSize(new Dimension(100,100));
				b.setBackground(Color.black);
				b.setForeground(Color.white);
				b.addActionListener(new UnitsActionListener());
				availableUnits.add(b);
				view.getPanel1().add(b);

			}
			if (u instanceof DiseaseControlUnit) {
				JToggleButton b=new JToggleButton();
				b.setName(u.getUnitID());
				b.setIcon(disease);
				b.setBorder(null);
				b.setBorderPainted(false);
				b.setContentAreaFilled(false);
				b.setOpaque(false);
				b.setPreferredSize(new Dimension(100,100));
				b.setBackground(Color.black);
				b.setForeground(Color.white);
				b.addActionListener(new UnitsActionListener());
				availableUnits.add(b);
				view.getPanel1().add(b);

			}


		}
		//Ambulance is button 0
		/*	availableUnits.get(0).setBorder(null);
		availableUnits.get(0).setBorderPainted(false);
		availableUnits.get(0).setContentAreaFilled(false);
		availableUnits.get(0).setOpaque(false);
		availableUnits.get(0).setIcon(ambulance);
		//gas control unit is button 1
		availableUnits.get(1).setBorder(null);
		availableUnits.get(1).setBorderPainted(false);
		availableUnits.get(1).setContentAreaFilled(false);
		availableUnits.get(1).setOpaque(false);
		availableUnits.get(1).setIcon(gascontrol);
		//Evacuator is button 2
		availableUnits.get(2).setBorder(null);
		availableUnits.get(2).setBorderPainted(false);
		availableUnits.get(2).setContentAreaFilled(false);
		availableUnits.get(2).setOpaque(false);
		availableUnits.get(2).setIcon(evacuator);
		//Diseas control unit is button 3
		availableUnits.get(3).setBorder(null);
		availableUnits.get(3).setBorderPainted(false);
		availableUnits.get(3).setContentAreaFilled(false);
		availableUnits.get(3).setOpaque(false);
		availableUnits.get(3).setIcon(disease);
		//Fire truck control unit is button 4
		availableUnits.get(4).setIcon(firetruck);
		availableUnits.get(4).setBorder(null);
		availableUnits.get(4).setBorderPainted(false);
		availableUnits.get(4).setContentAreaFilled(false);
		availableUnits.get(4).setOpaque(false);*/
		//===========================================================================================
		view.genGridButtons(gridButtons);
		infoPanel=new JTextArea();
		infoPanel.setBackground(Color.pink);
		infoPanel.setFont(new Font("Ariel", Font.BOLD,15));
		infoPanel.setForeground(Color.black);
		infoPanel.setBorder(BorderFactory.createTitledBorder("info panel"));
		infoPanel.setEditable(false);
		JScrollPane jsp2=new JScrollPane(infoPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		log=new JTextArea();
		Border b=BorderFactory.createTitledBorder("log");
		log.setBorder(b);
		log.setEditable(false);
		log.setBackground(Color.pink);
		log.setForeground(Color.black);
		activeDisasters.setBackground(Color.pink);
		activeDisasters.setFont(new Font("Ariel", Font.BOLD,15));
		activeDisasters.setForeground(Color.black);
		activeDisasters.setBorder(BorderFactory.createTitledBorder("Active Disasters "));
		activeDisasters.setEditable(false);
		view.genGridButtons(gridButtons);
		nextCycle=new JButton("next cycle");
		nextCycle.setForeground(Color.black);
		nextCycle.setBackground(Color.pink);
		nextCycle.setFont(new Font("Ariel", Font.BOLD,40));
		// how to make a invisible button
		nextCycle.setBorder(null);
		nextCycle.setBorderPainted(false);
		nextCycle.setContentAreaFilled(false);
		nextCycle.setOpaque(false);
		//================================
		nextCycle.addActionListener(new nextCycleActionListener());
		hint=new JButton("HINT");
		hint.setForeground(Color.black);
		hint.setBackground(Color.pink);
		hint.setFont(new Font("Ariel", Font.BOLD,40));
		// how to make a invisible button
		hint.setBorder(null);
		hint.setBorderPainted(false);
		hint.setContentAreaFilled(false);
		hint.setOpaque(false);
		//================================
		hint.addActionListener(new hintActionListener());
		currentCycle=new JTextArea(" the game haven't started");
		currentCycle.setBackground(Color.pink);
		currentCycle.setForeground(Color.black);
		currentCycle.setFont(new Font("TimesRoman", Font.BOLD,30));
		numOfCasulties=new JTextArea(" so good so far");
		numOfCasulties.setFont(new Font("TimesRoman", Font.BOLD,20));
		numOfCasulties.setBackground(Color.pink);
		numOfCasulties.setForeground(Color.black);
		numOfCasulties.setEditable(false);

		//view.genUnitsButtons(Ambulance, GasControlUnit, FireTruck, Evacuator, DiseasControlUnit);
		view.addPanel4(jsp2);
		view.addPanel4(log);
		view.addPanel4(activeDisasters);
		view.addpanel5(nextCycle);
		view.addpanel5(hint);

		view.addpanel5(currentCycle);
		view.addpanel5(numOfCasulties);
		view.setVisible(true);
		view.setResizable(false);

	}
	private void checkGameOver() throws Exception {
		if(engine.checkGameOver()==true) {
			view.dispose();
			new GameOver(engine.calculateCasualties());
		}


	}

	@Override
	public void receiveSOSCall(Rescuable r) {

		if (r instanceof ResidentialBuilding) {

			if (!visibleBuildings.contains(r))
				visibleBuildings.add((ResidentialBuilding) r);

		} else {

			if (!visibleCitizens.contains(r))
				visibleCitizens.add((Citizen) r);
		}

	}
	public void setCurrentCycle(int currentcycle) {
		currentCycle.setText("current cycle is"+" "+currentcycle);
	}
	public static void main(String[] args) throws Exception {
		new CommandCenter();
	}

	public ArrayList<JButton> getGridButtons() {
		return gridButtons;
	}
	public class GridButtonsActionListner implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.getOccupants().removeAll();
			view.getOccupants().revalidate();
			view.getOccupants().repaint();
			JButton clicked=(JButton)e.getSource();
			int index=gridButtons.indexOf(clicked);
			int y=index%10;
			index=index/10;
			int x=index;
			updateInfoPanel(x,y);
			Boolean flag=false;
			ArrayList<ResidentialBuilding> buildings=engine.getBuildings();
			//ArrayList<Unit> units=engine.getEmergencyUnits();
			ArrayList<Citizen> citizens=engine.getCitizens();
			for (int i=0;i<buildings.size();i++) {
				ResidentialBuilding building=buildings.get(i);
				if((building.getLocation().getX()==x)&&(building.getLocation().getY()==y)) {
					flag=false;
					for(JToggleButton b: availableUnits) 
						if(b.isSelected()) {
							for(Unit u:engine.getEmergencyUnits()) {
								if(b.getName().equals(u.getUnitID())) 

									try {
										u.respond(building);
										flag=true;
										for(JToggleButton bb: availableUnits)
											bb.setSelected(false);
									} catch (CannotTreatException | IncompatibleTargetException
											| CitizenAlreadyDeadException
											| BuildingAlreadyCollapsedException e1) {
										for(JToggleButton bb: availableUnits)
											bb.setSelected(false);
										JOptionPane.showMessageDialog(null,e1.getMessage());
										flag=true;
									}


							}

						}

				}
			}
			if (!flag) {
				for (int i=0;i<citizens.size();i++) {
					Citizen citizen=citizens.get(i);
					if((citizen.getLocation().getX()==x)&&(citizen.getLocation().getY()==y)) { 
						for(JToggleButton b: availableUnits) {
							if(b.isSelected()) {
								for(Unit u:engine.getEmergencyUnits()) {
									if(b.getName().equals(u.getUnitID())) 
										try {
											u.respond(citizen);
											for(JToggleButton bb: availableUnits)
												bb.setSelected(false);
										} catch (CannotTreatException | IncompatibleTargetException
												| CitizenAlreadyDeadException | BuildingAlreadyCollapsedException e1) {
											JOptionPane.showMessageDialog(null,e1.getMessage());
											for(JToggleButton bb: availableUnits)
												bb.setSelected(false);
										}
								}
							}
						}		
					}
				}
			}
		}


	} 
	public void updateInfoPanel(int x, int y) {
		ArrayList<Citizen> citizens=engine.getCitizens();
		ArrayList<ResidentialBuilding> buildings=engine.getBuildings();
		ArrayList<Unit> units=engine.getEmergencyUnits();
		String info="";
		Boolean flag=false;
		view.getOccupants().removeAll();
		for (ResidentialBuilding b:buildings) {
			ImageIcon image2=new ImageIcon("sakn.png");
			Image im2=image2.getImage();
			im2=im2.getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH);
			image2=new ImageIcon(im2);
			if(b.getLocation().getX()==x &&b.getLocation().getY()==y) {
				flag=true;
				info+=updateInfoPanel(b);
				if(b.getOccupants().size()>0) {
					for(Citizen c:b.getOccupants()) {
						JButton bb=new JButton();
						bb.setIcon(image2);
						//bb.setText(c.getName());
						bb.addActionListener(new OccupantsActionListener());
						bb.setName(c.getNationalID());
						view.getOccupants().add(bb);
						view.getOccupants().revalidate();
						view.getOccupants().repaint();
					}
				}
			}
		}
		if(!flag) {
			int Count=0;
			for (Citizen c:citizens) {
				if(c.getLocation().getX()==x&&c.getLocation().getY()==y) {
					Count++;
				}
			}
			if(Count==1) {
				for (Citizen c:citizens) {
					if(c.getLocation().getX()==x&&c.getLocation().getY()==y) {
						info+=updateInfoPanel(c);
					}
				}	
			}
			else {
				ImageIcon image2=new ImageIcon("sakn.png");
				Image im2=image2.getImage();
				im2=im2.getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH);
				image2=new ImageIcon(im2);
				for (Citizen c:citizens) {
					if(c.getLocation().getX()==x&&c.getLocation().getY()==y) {
						info+=updateInfoPanel(c);
						info+="~~~~~~~~~~~~~~~~~~~~~~~~~~"+"\n";
						JButton bb=new JButton();
						bb.setIcon(image2);
						bb.addActionListener(new OccupantsActionListener());
						bb.setName(c.getNationalID());
						view.getOccupants().add(bb);
						view.getOccupants().revalidate();
						view.getOccupants().repaint();
					}
				}	
			}

		}
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		ImageIcon ambulance=new ImageIcon("ambulance.png");
		Image amb=ambulance.getImage();
		amb=amb.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		ambulance=new ImageIcon(amb);
		ImageIcon disease=new ImageIcon("disease.png");
		Image des=disease.getImage();
		des=des.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		disease=new ImageIcon(des);
		ImageIcon evacuator=new ImageIcon("evacuator.png");
		Image eva=evacuator.getImage();
		eva=eva.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		evacuator=new ImageIcon(eva);
		ImageIcon firetruck=new ImageIcon("fire-truck (1).png");
		Image fit=firetruck.getImage();
		fit=fit.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		firetruck=new ImageIcon(fit);
		ImageIcon gascontrol=new ImageIcon("pipes.png");
		Image gas=gascontrol.getImage();
		gas=gas.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
		gascontrol=new ImageIcon(gas);
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		for (Unit u:units) {
			if (u.getLocation().getX()==x&&u.getLocation().getY()==y){
				info+=updateInfoPanel(u);
				JButton bb=new JButton();
				bb.setName(u.getUnitID());
				if(u instanceof Ambulance)
					bb.setIcon(ambulance);
				if(u instanceof Evacuator)
					bb.setIcon(evacuator);
				if(u instanceof GasControlUnit)
					bb.setIcon(gascontrol);
				if(u instanceof Ambulance)
					bb.setIcon(ambulance);
				if(u instanceof DiseaseControlUnit)
					bb.setIcon(disease);
				if(u instanceof FireTruck)
					bb.setIcon(firetruck);

				bb.setBorder(null);
				bb.setBorderPainted(false);
				bb.setContentAreaFilled(false);
				bb.setOpaque(false);
				bb.addActionListener(new unitsInfoActionListener());
				view.getOccupants().add(bb);
				view.getOccupants().revalidate();
				view.getOccupants().repaint();
			}

		}
		infoPanel.setText(info);
	}
	public String updateInfoPanel(Unit u) {
		String info="";
		if (u instanceof FireTruck) {
			info+="the unit's ID is   "+u.getUnitID()+"\n";
			info+="this unit is a fire truck"+"\n";
			info+="the unit location is  "+u.getLocation()+"\n";
			info+="the unit steps per cycle is  "+u.getStepsPerCycle()+"\n";
			info+="the unit state is  "+u.getState()+"\n";
			if(u.getTarget()!=null) {
				info+="info is going to  "+u.getTarget()+"   in  "+u.getTarget().getLocation()+"\n";
			}
			else info+="the unit have no target"+"\n";
		}
		if (u instanceof Ambulance) {
			info+="the unit's ID is   "+u.getUnitID()+"\n";
			info+="this unit is a Ambulance"+"\n";
			info+="the unit location is  "+u.getLocation()+"\n";
			info+="the unit steps per cycle is  "+u.getStepsPerCycle()+"\n";
			info+="the unit state is  "+u.getState()+"\n";
			if(u.getTarget()!=null) {
				info+="info is going to  "+u.getTarget()+"   in  "+u.getTarget().getLocation()+"\n";
			}
			else info+="the unit have no target"+"\n";
		}
		if (u instanceof GasControlUnit) {
			info+="the unit's ID is   "+u.getUnitID()+"\n";
			info+="this unit is a Gas Control Unit"+"\n";
			info+="the unit location is  "+u.getLocation()+"\n";
			info+="the unit steps per cycle is  "+u.getStepsPerCycle()+"\n";
			info+="the unit state is  "+u.getState()+"\n";
			if(u.getTarget()!=null) {
				info+="info is going to  "+u.getTarget()+"   in  "+u.getTarget().getLocation()+"\n";
			}
			else info+="the unit have no target"+"\n";
		}
		if (u instanceof DiseaseControlUnit) {
			info+="the unit's ID is   "+u.getUnitID()+"\n";
			info+="this unit is a Disease Control Unit"+"\n";
			info+="the unit location is  "+u.getLocation()+"\n";
			info+="the unit steps per cycle is  "+u.getStepsPerCycle()+"\n";
			info+="the unit state is  "+u.getState()+"\n";
			if(u.getTarget()!=null) {
				info+="info is going to  "+u.getTarget()+"   in  "+u.getTarget().getLocation()+"\n";
			}
			else info+="the unit have no target"+"\n";
		}
		if (u instanceof Evacuator) {
			info+="the unit's ID is   "+u.getUnitID()+"\n";
			info+="this unit is a Evacuator Unit"+"\n";
			info+="the unit location is  "+u.getLocation()+"\n";
			info+="the unit steps per cycle is  "+u.getStepsPerCycle()+"\n";
			info+="the unit state is  "+u.getState()+"\n";
			if(u.getTarget()!=null) {
				info+="info is going to  "+u.getTarget()+"   in  "+u.getTarget().getLocation()+"\n";
			}
			else info+="the unit have no target"+"\n";
			if(u.getState()==UnitState.TREATING) {
				info+="the number of occupants is  "+((PoliceUnit) u).getPassengers().size()+"\n";

			}
		}
		info+="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"\n";
		return info;
	}
	public String updateInfoPanel(ResidentialBuilding building) {
		String info="";
		info+="Building"+"\n";
		info+="location:"+""+building.getLocation()+"\n";
		info+="structrual integrity:"+ ""+building.getStructuralIntegrity()+"\n";
		info+="Fire Damage:"+""+building.getFireDamage()+"\n";
		info+="Gas Level:"+""+building.getGasLevel()+"\n";
		info+="Foundation Damage:"+""+building.getFoundationDamage()+"\n";
		info+="number of occupants:"+""+building.getOccupants().size()+"\n";
		info+="Foundation Damage:"+""+building.getFoundationDamage()+"\n";
		if(building.getDisaster()!=null) {
			if(building.getDisaster() instanceof Fire)
				info+="this building is on Fire"+"\n";
			if(building.getDisaster() instanceof GasLeak)
				info+="there is a gas leak in this building"+"\n";
			if(building.getDisaster() instanceof Collapse)
				info+="the building has collapsed"+"\n";
		}
		info+="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"\n";
		for (int i=0;i<building.getOccupants().size();i++) {
			Citizen occupant=building.getOccupants().get(i);
			info+="occupant"+(i+1)+"\n";
			info+="name:"+ ""+occupant.getName()+"\n";
			info+="age:"+""+occupant.getAge()+"\n";
			info+="nationel id:"+""+occupant.getNationalID()+"\n";
			info+="HP:"+""+occupant.getHp()+"\n";
			info+="blood loss:"+""+occupant.getBloodLoss()+"\n";
			info+="toxicity level:"+""+occupant.getToxicity()+"\n";
			info+="state:"+""+occupant.getState()+"\n";
			info+="~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+"\n";
		}
		return(info);
		//curr.setText(info);
		//view.setCuurd(curr);
	}
	public String updateInfoPanel(Citizen citizen) {
		String info="";
		info+="citizen"+"\n";
		info+="location:"+""+citizen.getLocation()+"\n";
		info+="name:"+ ""+citizen.getName()+"\n";
		info+="age:"+""+citizen.getAge()+"\n";
		info+="nationel id:"+""+citizen.getNationalID()+"\n";
		info+="HP:"+""+citizen.getHp()+"\n";
		info+="blood loss:"+""+citizen.getBloodLoss()+"\n";
		info+="toxicity level:"+""+citizen.getToxicity()+"\n";
		info+="state:"+""+citizen.getState()+"\n";
		if(citizen.getDisaster()!=null) {
			if(citizen.getDisaster() instanceof Injury)
				info+="this citizen is injuried";
			if(citizen.getDisaster() instanceof Infection)
				info+="this citizen is infected";
		}

		return info;
		//view.setCuurd(info);

	}


	public class nextCycleActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.getOccupants().removeAll();
			view.getOccupants().revalidate();
			view.getOccupants().repaint();
			try {
				engine.nextCycle();
			} catch (CitizenAlreadyDeadException | BuildingAlreadyCollapsedException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			}
			setCurrentCycle(engine.getCurrentCycle());
			setNumberOfCasulties(engine.calculateCasualties());
			updateLog();
			updateGrid();
			updateUnits();
			updateActiveDisasters();
				try {
					checkGameOver();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			infoPanel.setText("");
		}



		private void updateActiveDisasters() {
			String info="";
			for(ResidentialBuilding b:visibleBuildings)
				if(b.getDisaster().isActive()==true) {
					if(b.getDisaster() instanceof Fire)
						info+="there is an active fire disaster on the building in location "+b.getLocation()+"\n";
					if(b.getDisaster() instanceof GasLeak)
						info+="there is an active Gas Leak disaster on the building in location "+b.getLocation()+"\n";
					if(b.getDisaster() instanceof Collapse)
						info+="there is an active collapse disaster on the building in location "+b.getLocation()+"\n";
				}
			for(Citizen c:visibleCitizens)
				if(c.getDisaster().isActive()==true) {
					if(c.getDisaster() instanceof Injury)
						info+="there is an active injury disaster on the citizen in location "+c.getLocation()+"\n";
					if(c.getDisaster() instanceof Infection)
						info+="there is an active infection disaster on the building in location "+c.getLocation()+"\n";

				}
			activeDisasters.setText(info);

		}



		public void updateUnits() {
			ArrayList<Unit> units=engine.getEmergencyUnits();
			for(JToggleButton b: availableUnits) {
				for(Unit u:units) {
					if(b.getName().equals(u.getUnitID()))
						if(u.getState()==UnitState.IDLE)
							view.getPanel1().add(b);		    
						else if(u.getState()==UnitState.RESPONDING) {
							view.getPanel1().remove(b);
							view.getPanel2().add(b);
						}
						else { 
							view.getPanel3().add(b);
							view.getPanel1().remove(b);
							view.getPanel2().remove(b);
						}

				}
				b.setSelected(false);
				view.getPanel1().revalidate();
				view.getPanel2().revalidate();
				view.getPanel3().revalidate();
				view.getPanel1().repaint();
				view.getPanel2().repaint();
				view.getPanel3().repaint();
			}
		}



		private void updateGrid() {
			for (ResidentialBuilding b : visibleBuildings ) {
				int x1=b.getLocation().getX();
				int y1=b.getLocation().getY();
				int index1=(x1*10)+y1;
				JButton clicked1=gridButtons.get(index1);
				ImageIcon image1=new ImageIcon("office.png");
				Image im1=image1.getImage();
				im1=im1.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				image1=new ImageIcon(im1);
				clicked1.setIcon(image1);
				if(b.getDisaster().isActive()==true) {
					if(b.getDisaster() instanceof GasLeak) 
						clicked1.setBackground(Color.green);
					else if(b.getDisaster() instanceof Fire) 
						clicked1.setBackground(Color.red);
					else if(b.getDisaster() instanceof Collapse) 
						clicked1.setBackground(Color.black);
				}
				else clicked1.setBackground(Color.white);

			}
			for (Citizen c : visibleCitizens) {
				Boolean flag=true;
				for(ResidentialBuilding b:visibleBuildings)
					if(b.getLocation().getX()==c.getLocation().getX()&&b.getLocation().getY()==c.getLocation().getY()) {
						flag=false;
					}
				if(flag) {
					int x2=c.getLocation().getX();
					int y2=c.getLocation().getY();
					int index2=(x2*10)+y2;
					JButton clicked2=gridButtons.get(index2);
					ImageIcon image2=new ImageIcon("sakn.png");
					Image im2=image2.getImage();
					im2=im2.getScaledInstance(40,40, java.awt.Image.SCALE_SMOOTH);
					image2=new ImageIcon(im2);
					clicked2.setIcon(image2);
					if(c.getDisaster().isActive()==true) {
						if(c.getDisaster() instanceof Infection) 
							clicked2.setBackground(Color.pink);
						else if(c.getDisaster() instanceof Injury ) 
							clicked2.setBackground(Color.CYAN);
					}
					else clicked2.setBackground(Color.white);
				}
			}
		}

		private void setNumberOfCasulties(int calculateCasualties) {
			numOfCasulties.setText("number of casulties is"+" "+calculateCasualties);

		}

		private void updateLog() {
			String info="";
			ArrayList<Disaster> disasters=engine.getExecutedDisasters();
			for(Disaster d :disasters) {
				if (d instanceof Fire)
					info+="New fire disaster strkies a building"+"\n";
				if (d instanceof Collapse)
					info+="Building have collapsed"+"\n";
				if (d instanceof GasLeak)
					info+="there is a gas leak in a building"+"\n";
				if (d instanceof Infection)
					info+="A citizen is infected "+"\n";
				if (d instanceof Injury)
					info+="A citizen is injuried "+"\n";
			}
			for (Citizen c:visibleCitizens) {
				if (c.getState()==CitizenState.DECEASED)
					info+="the citizen called  "+c.getName()+"has passed away in"+c.getLocation()+"\n";
			}
			for(ResidentialBuilding b:visibleBuildings) {
				if (b.getStructuralIntegrity()==0)
					if(b.getOccupants().size()>0)
						info+="the citizens in building in location "+b.getLocation()+"have all passed away in";
			}
			log.setText(info);
			log.setFont(new Font("TimesRoman", Font.BOLD,20));
		}
	}
	public class UnitsActionListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String info="";
			ArrayList<Unit> units=engine.getEmergencyUnits();
			JToggleButton clicked=(JToggleButton)e.getSource();
			Boolean flag=false;
			for(Unit u:units) {
				if(!flag) {
					if(clicked.getName().equals(u.getUnitID())) {

						for(JToggleButton b: availableUnits)
							if(!(b.getName().equals(u.getUnitID()))) { 
								b.setSelected(false);
								view.getPanel1().revalidate();
								view.getPanel2().revalidate();
								view.getPanel3().revalidate();
								}
						info=updateInfoPanel(u);
						flag=true;

					}

				/*	if((clicked.getName().equals(u.getUnitID()))) {
						for(JToggleButton b: availableUnits)
							if(!(b.getName().equals(u.getUnitID()))) 
								b.setSelected(false);
						info=updateInfoPanel(u);
						flag=true;
					}
					if((clicked.getName().equals(u.getUnitID()))) {
						for(JToggleButton b: availableUnits)
							if(!(b.getName().equals(u.getUnitID()))) 
								b.setSelected(false);
						info=updateInfoPanel(u);
						flag=true;
					}

					if((clicked.getName().equals(u.getUnitID()))) {
						for(JToggleButton b: availableUnits)
							if(!(b.getName().equals(u.getUnitID()))) 
								b.setSelected(false);
						info=updateInfoPanel(u);
						flag=true;
					}
					if((clicked.getName().equals(u.getUnitID()))) {
						for(JToggleButton b: availableUnits)
							if(!(b.getName().equals(u.getUnitID()))) 
								b.setSelected(false);
						info=updateInfoPanel(u);	
						flag=true;}*/
					//======================================================
				}
			}
			infoPanel.setText(info);
		}

	}
	public class OccupantsActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton bb=(JButton) e.getSource();
			for(Citizen C:engine.getCitizens()) {
				if(bb.getName().equals(C.getNationalID())) { 
					infoPanel.setText(updateInfoPanel(C));

					for(JToggleButton b: availableUnits) 
						if(b.isSelected()) {
							for(Unit u:engine.getEmergencyUnits()) 
								if(b.getName().equals(u.getUnitID())) 
									try {
										u.respond(C);
										for(JToggleButton bbb:availableUnits)
											bbb.setSelected(false);

									} catch (CannotTreatException | IncompatibleTargetException
											| CitizenAlreadyDeadException
											| BuildingAlreadyCollapsedException e1) {
										JOptionPane.showMessageDialog(null,e1.getMessage());
										for(JToggleButton bbb:availableUnits)
											bbb.setSelected(false);
}

						}
				}
			}
		}
	}
	public class unitsInfoActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b=(JButton) e.getSource();
			for(Unit u:engine.getEmergencyUnits())
				if(u.getUnitID().equals(b.getName()))
					infoPanel.setText(updateInfoPanel(u));
		}

	}
	public class hintActionListener implements ActionListener{
		public boolean ambulanceResponding() {
			for(Unit z : engine.getEmergencyUnits()) {
				if( z instanceof Ambulance)
				{
					Ambulance y = (Ambulance)z;
					if (y.getState() !=UnitState.IDLE) {
						return true;
					}
				}
			}
			return false ;
			
			
			
		}
		public boolean fireTruckResponding() {
			for(Unit z : engine.getEmergencyUnits()) {
				if( z instanceof FireTruck)
				{
					FireTruck y = (FireTruck)z;
					if (y.getState() !=UnitState.IDLE) {
						return true;
					}
				}
			}
			return false ;
			
			
			
		}
		public boolean diseaseControlUnitResponding() {
			for(Unit z : engine.getEmergencyUnits()) {
				if( z instanceof DiseaseControlUnit)
				{
					DiseaseControlUnit y = (DiseaseControlUnit)z;
					if (y.getState() !=UnitState.IDLE) {
						return true;
					}
				}
			}
			return false ;
			
			
			
		}
		public boolean gasControlUnitResponding() {
			for(Unit z : engine.getEmergencyUnits()) {
				if( z instanceof GasControlUnit)
				{
					GasControlUnit y = (GasControlUnit)z;
					if (y.getState() !=UnitState.IDLE) {
						return true;
					}
				}
			}
			return false ;
			
			
			
		}


		public void actionPerformed(ActionEvent e) {
			try {
				checkGameOver();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			for(Citizen x : engine.getCitizens()) {
				if (maxBloodLoss<x.getBloodLoss())
					maxBloodLoss = x.getBloodLoss();
				if (maxToxicity<x.getToxicity())
					maxToxicity = x.getToxicity();
					
			}
			for(ResidentialBuilding x : engine.getBuildings()) {
				if(x.getDisaster() instanceof Collapse)
					collapsed = true;
				
				if (maxFireDamage<x.getFireDamage())
					maxFireDamage = x.getFireDamage();
				if (maxGasLeak<x.getGasLevel())
					maxGasLeak = x.getGasLevel();
				if (minStructuralIntegrity > x.getStructuralIntegrity() ) 
					minStructuralIntegrity =x.getStructuralIntegrity();		

				}

				boolean found = false;	
				for(Unit z :engine.getEmergencyUnits()) {
					if (z instanceof Evacuator) {

						Evacuator y = (Evacuator)z;
						if (y.getState()==UnitState.IDLE) {
							found = true;

						}
						else {
							found = false;
						}
					}
				}

			    if (collapsed && found) {
			    	for(ResidentialBuilding x : engine.getBuildings()) {
			    		if (x.getDisaster() instanceof Collapse&&x.getStructuralIntegrity()!=0 && x.getFoundationDamage() >0) {
			    			JOptionPane.showMessageDialog(null,"Please send an Evacuator to the collapsed building at location ("+x.getLocation().getX() +","+x.getLocation().getY()+")" );
					    	found = false;
					    	collapsed = false;
					    	minStructuralIntegrity = 100;
					    	maxBloodLoss = 0;
					    	maxFireDamage = 0 ;
					    	maxGasLeak = 0 ;
					    	maxToxicity=0;
					    	return;
			    		}
			    	}
			    }
			    
			    
			   
				
			   
	
			    for(Unit z :engine.getEmergencyUnits()) {
					if (z instanceof GasControlUnit) {

						GasControlUnit y = (GasControlUnit)z;
						if (y.getState()==UnitState.IDLE) {
							found = true;

						}
						else {
							found = false;
						}
					}
				}

			    if ((maxGasLeak>maxBloodLoss && maxGasLeak>maxToxicity && maxGasLeak>=maxFireDamage && found )|| 
			    		( maxGasLeak>maxToxicity && maxGasLeak>=maxFireDamage && found && ambulanceResponding()) || 
			    		( maxGasLeak>maxToxicity && maxGasLeak>maxBloodLoss && found && fireTruckResponding())|| 
			    		( maxGasLeak>maxBloodLoss && maxGasLeak>=maxFireDamage && found && diseaseControlUnitResponding())|| 
			    		( maxGasLeak>maxToxicity  && found && ambulanceResponding() &&fireTruckResponding())|| 
			    		( maxGasLeak>maxBloodLoss  && found && diseaseControlUnitResponding() &&fireTruckResponding()) || 
			    		( maxGasLeak>=maxFireDamage  && found && ambulanceResponding() &&diseaseControlUnitResponding()) || 
			    		(maxGasLeak > 0 && ambulanceResponding() && diseaseControlUnitResponding() && fireTruckResponding()) &&found) {
			    	for(ResidentialBuilding x : engine.getBuildings()) {
			    		if (x.getDisaster() instanceof GasLeak &&x.getStructuralIntegrity()!=0 && x.getGasLevel()> 0) {
			    			JOptionPane.showMessageDialog(null,"Please send a Gas Control Unit to the Gas leaked building at location ("+x.getLocation().getX() +","+x.getLocation().getY()+")" );
					    	found = false;
					    	minStructuralIntegrity = 100;
					    	maxBloodLoss = 0;
					    	maxFireDamage = 0 ;
					    	maxGasLeak = 0 ;
					    	maxToxicity=0;
					    	return;
			    		}
			    	}	
			    }
			 
			    

			    
			    for(Unit z :engine.getEmergencyUnits()) {
					if (z instanceof DiseaseControlUnit) {

						DiseaseControlUnit y = (DiseaseControlUnit)z;
						if (y.getState()==UnitState.IDLE) {
							found = true;

						}
						else {
							found = false;
						}
					}
				}

			    if ((maxToxicity>maxFireDamage && maxToxicity>maxBloodLoss && maxToxicity>maxGasLeak && found)|| 
			    		( maxToxicity>maxBloodLoss && maxToxicity>maxFireDamage && found && gasControlUnitResponding()) || 
			    		( maxToxicity>maxBloodLoss && maxToxicity>maxGasLeak && found && fireTruckResponding())|| 
			    		( maxToxicity>maxGasLeak && maxToxicity>maxFireDamage && found && ambulanceResponding())|| 
			    		( maxToxicity>maxBloodLoss  && found && gasControlUnitResponding() &&fireTruckResponding())|| 
			    		( maxToxicity>maxGasLeak && found && ambulanceResponding() &&fireTruckResponding()) || 
			    		( maxToxicity>maxFireDamage  && found && gasControlUnitResponding() &&ambulanceResponding()) || 
			    		(maxToxicity > 0 && ambulanceResponding() && fireTruckResponding() && gasControlUnitResponding())&&found ) {
			    	for(Citizen x : engine.getCitizens()) {
			    		if (x.getDisaster() instanceof Infection && x.getHp()!=0 && x.getToxicity() >0) {
			    			JOptionPane.showMessageDialog(null,"Please send a Disease Control unit to the infected  Citizen ("+x.getLocation().getX() +","+x.getLocation().getY()+")" );
					    	found = false;
					    	minStructuralIntegrity = 100;
					    	maxBloodLoss = 0;
					    	maxFireDamage = 0 ;
					    	maxGasLeak = 0 ;
					    	maxToxicity=0;
					    	return;
			    		}
			    	}	
			    }
			    for(Unit z :engine.getEmergencyUnits()) {
					if (z instanceof FireTruck) {

						FireTruck y = (FireTruck)z;
						if (y.getState()==UnitState.IDLE) {
							found = true;

						}
						else {
							found = false;
						}
					}
				}
			 
			    


			    if      ((maxFireDamage>maxBloodLoss && maxFireDamage>maxToxicity && maxFireDamage>=maxGasLeak && found )|| 
			    		( maxFireDamage>maxToxicity && maxFireDamage>maxGasLeak && found && ambulanceResponding()) || 
			    		( maxFireDamage>maxToxicity && maxFireDamage>maxBloodLoss && found && gasControlUnitResponding())|| 
			    		( maxFireDamage>maxBloodLoss && maxFireDamage>maxGasLeak && found && diseaseControlUnitResponding())|| 
			    		( maxFireDamage>maxToxicity  && found && ambulanceResponding() &&gasControlUnitResponding())|| 
			    		( maxFireDamage>maxBloodLoss  && found && diseaseControlUnitResponding() &&gasControlUnitResponding()) || 
			    		( maxFireDamage>maxGasLeak && found && ambulanceResponding() &&diseaseControlUnitResponding()) || 
			    		(maxFireDamage > 0 && ambulanceResponding() && diseaseControlUnitResponding() && gasControlUnitResponding()) &&found) {

			    	for(ResidentialBuilding x : engine.getBuildings()) {
			    		if (x.getDisaster() instanceof Fire && x.getStructuralIntegrity()!=0 && x.getFireDamage() >0) {
			    			JOptionPane.showMessageDialog(null,"Please send a FireTruck  to the burning building at location ("+x.getLocation().getX() +","+x.getLocation().getY()+")" );
					    	found = false;
					    	minStructuralIntegrity = 100;
					    	maxBloodLoss = 0;
					    	maxFireDamage = 0 ;
					    	maxGasLeak = 0 ;
					    	maxToxicity=0;
					    	return;
			    		}
			    	}	
			    }
			    for(Unit z :engine.getEmergencyUnits()) {
					if (z instanceof Ambulance) {

						Ambulance y = (Ambulance)z;

						if (y.getState()==UnitState.IDLE) {
							found = true;

						}
						else {
							found = false;
						}
					}
				}

			    if ((maxBloodLoss>maxFireDamage && maxBloodLoss>maxToxicity && maxBloodLoss>maxGasLeak && found)|| 
			    		( maxBloodLoss>maxToxicity && maxBloodLoss>maxFireDamage && found && gasControlUnitResponding()) || 
			    		( maxBloodLoss>maxToxicity && maxBloodLoss>maxGasLeak && found && fireTruckResponding())|| 
			    		( maxBloodLoss>maxGasLeak && maxBloodLoss>maxFireDamage && found && diseaseControlUnitResponding())|| 
			    		( maxBloodLoss>maxToxicity  && found && gasControlUnitResponding() &&fireTruckResponding())|| 
			    		( maxBloodLoss>maxGasLeak && found && diseaseControlUnitResponding() &&fireTruckResponding()) || 
			    		( maxBloodLoss>maxFireDamage  && found && gasControlUnitResponding() &&diseaseControlUnitResponding()) || 
			    		(maxBloodLoss > 0 && diseaseControlUnitResponding() && fireTruckResponding() && gasControlUnitResponding())&&found ) {
			    	for(Citizen x : engine.getCitizens()) {
			    		if (x.getDisaster() instanceof Injury && x.getHp()!=0 && x.getBloodLoss() >0) {
			    			JOptionPane.showMessageDialog(null,"Please send an Ambulance to the injuried  Citizen ("+x.getLocation().getX() +","+x.getLocation().getY()+")" );
					    	found = false;
					    	minStructuralIntegrity = 100;
					    	maxBloodLoss = 0;
					    	maxFireDamage = 0 ;
					    	maxGasLeak = 0 ;
					    	maxToxicity=0;
					    	return;
			    		}
			    	}	
			    }
			    
			    	
			    	
				JOptionPane.showMessageDialog(null,"No Hints avalaible");
				return;
				
					
			}
			

	}
}
