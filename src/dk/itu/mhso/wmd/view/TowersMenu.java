package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class TowersMenu extends JPopupMenu {
	public TowersMenu() {
		setLayout(new BorderLayout(0, 0));

		JPanel topPanel = new JPanel();
		add(topPanel, BorderLayout.NORTH);
		
		JButton buttonDeflateLeft = new JButton("<");
		buttonDeflateLeft.addActionListener(e -> setVisible(false));
		topPanel.add(buttonDeflateLeft);
		buttonDeflateLeft.setPreferredSize(new Dimension(41, 23));
		buttonDeflateLeft.setEnabled(true);
		
		JPanel panelTowers = new JPanel();
		add(panelTowers, BorderLayout.CENTER);
		panelTowers.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnNewButton = new JButton("Tower 1");
		panelTowers.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Tower 2");
		panelTowers.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Tower 3");
		panelTowers.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Tower 4");
		panelTowers.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Tower 5");
		panelTowers.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("Tower 6");
		panelTowers.add(btnNewButton_5);
	}
	
	public void showDropdown(JComponent source) {
		show(source, source.getLocation().x, source.getLocation().y);
	}
}
