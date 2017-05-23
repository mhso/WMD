package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dk.itu.mhso.wmd.controller.Game;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;

public class WindowMainMenu extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public WindowMainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 30));
		setContentPane(contentPane);
		
		JPanel centerPanel = new JPanel();
		contentPane.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(0, 1, 0, 15));
		
		JButton buttonContinue = new JButton("Continue");
		centerPanel.add(buttonContinue);
		
		JButton buttonNewGame = new JButton("New Game");
		buttonNewGame.addActionListener(e -> Game.startGame());
		centerPanel.add(buttonNewGame);
		
		JButton buttonExit = new JButton("Exit");
		centerPanel.add(buttonExit);
		
		JLabel lblWeaponsOfMass = new JLabel("Weapons of Mass Defense!");
		lblWeaponsOfMass.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblWeaponsOfMass.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblWeaponsOfMass, BorderLayout.NORTH);
		
		setPreferredSize(new Dimension(500, 400));
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
