package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dk.itu.mhso.wmd.Main;

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
		contentPane.setLayout(new CardLayout());
		setContentPane(contentPane);
		
		createMainPanel();

		contentPane.add(new PanelChooseLevel(), "name_levels");
		
		setPreferredSize(new Dimension(500, 400));
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void createMainPanel() {
		JPanel mainMenuPanel = new JPanel();
		mainMenuPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mainMenuPanel.setLayout(new BorderLayout(0, 30));
		contentPane.add(mainMenuPanel, "name_main");
		
		JPanel centerPanel = new JPanel();
		mainMenuPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(0, 1, 0, 15));
		
		JButton buttonContinue = new JButton("Continue");
		centerPanel.add(buttonContinue);
		
		JButton buttonNewGame = new JButton("New Game");
		buttonNewGame.addActionListener(e -> showLevelsPanel());
		centerPanel.add(buttonNewGame);
		
		JButton buttonLevelEditor = new JButton("Level Editor");
		buttonLevelEditor.addActionListener(e -> {
			new WindowLevelEditor();
			dispose();
		});
		centerPanel.add(buttonLevelEditor);
		
		JButton buttonExit = new JButton("Exit");
		buttonExit.addActionListener(e -> System.exit(0));
		centerPanel.add(buttonExit);
		
		JLabel labelName = new JLabel(Main.NAME);
		labelName.setFont(new Font("Tahoma", Font.PLAIN, 30));
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		mainMenuPanel.add(labelName, BorderLayout.NORTH);
	}

	private void showMainMenuPanel() {
		CardLayout card = (CardLayout) contentPane.getLayout();
		card.show(contentPane, "name_menu");
	}
	
	private void showLevelsPanel() {
		CardLayout card = (CardLayout) contentPane.getLayout();
		card.show(contentPane, "name_levels");
	}
}
