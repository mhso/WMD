package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dk.itu.mhso.wmd.controller.Game;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;

public class WindowPauseMenu extends JDialog {
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public WindowPauseMenu() {
		setModal(true);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 15));
		setContentPane(contentPane);
		
		JLabel labelHeader = new JLabel("Paused");
		labelHeader.setHorizontalAlignment(SwingConstants.CENTER);
		labelHeader.setFont(new Font("Tahoma", Font.PLAIN, 25));
		contentPane.add(labelHeader, BorderLayout.NORTH);
		
		JPanel panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.SOUTH);
		panelCenter.setLayout(new GridLayout(2, 0, 0, 10));
		
		JButton buttonResume = new JButton("Resume");
		buttonResume.addActionListener(e -> {
			Game.setPaused(false);
			dispose();
		});
		panelCenter.add(buttonResume);
		
		JButton buttonExit = new JButton("Exit");
		buttonExit.addActionListener(e -> System.exit(0));
		panelCenter.add(buttonExit);
		
		pack();
		setLocationRelativeTo(null);
		
		setVisible(true);
	}

}
