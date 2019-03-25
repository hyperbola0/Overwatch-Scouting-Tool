package com.hyperbola0.scoutingtool.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hyperbola0.scoutingtool.MatchStats;
import com.hyperbola0.scoutingtool.UpdatePlayers;

public class Match extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GUI gui;
	
	private JPanel header;
	private JPanel teams;
	private JPanel bottom;
	
	private JLabel urlLabel = new JLabel("Tespa URL: ");
	
	public JLabel teamSR = new JLabel("Average team SR: ");
	
	private JButton loadMatch = new JButton("Fetch match");
	private JButton t1Button = new JButton("TEAM 1");
	private JButton t2Button = new JButton("TEAM 2");
	private JButton helpBtn = new JButton("?");
	
	private JTextField urlField = new JTextField();
	
	private MatchStats matchInfo;
	
	public Match(GUI g) {
		this.gui = g;
		setLayout(new GridLayout(3, 1));
		
		loadMatch.addActionListener(this);
		t1Button.addActionListener(this);
		t2Button.addActionListener(this);
		helpBtn.addActionListener(this);
		
		header = new JPanel();
		header.setLayout(new GridLayout(1, 3));
		header.add(urlLabel);
		header.add(urlField);
		header.add(loadMatch);
		header.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		teams = new JPanel();
		teams.setLayout(new GridLayout(1, 2));
		teams.add(t1Button);
		teams.add(t2Button);
		teams.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		
		bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.add(teamSR, BorderLayout.WEST);
		bottom.add(helpBtn, BorderLayout.EAST);
		
		add(header);
		add(teams);
		add(bottom);
		
		this.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 10));
	}
	
	private void fetchMatch() {
		matchInfo = new MatchStats(urlField.getText());
				
		t1Button.setText(matchInfo.getTeam1Name());
		t2Button.setText(matchInfo.getTeam2Name());
	}
	
	private void fillTeam1() {
		gui.setPlayerNames(matchInfo.getTeam1Roster());
	}
	
	private void fillTeam2() {
		gui.setPlayerNames(matchInfo.getTeam2Roster());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source.equals(loadMatch)) {
			fetchMatch();
		}
		else if(source.equals(t1Button)) {
			t1Button.setEnabled(false);
			t2Button.setEnabled(true);
			teamSR.setText("Average team SR: ");
			fillTeam1();
			UpdatePlayers refresh = new UpdatePlayers(this.gui);
			refresh.start();
		}
		else if(source.equals(t2Button)) {
			t1Button.setEnabled(true);
			t2Button.setEnabled(false);
			teamSR.setText("Average team SR: ");
			fillTeam2();
			UpdatePlayers refresh = new UpdatePlayers(this.gui);
			refresh.start();
		}
		else if(source.equals(helpBtn)) {
			new HelpWindow();
		}
	}

}
