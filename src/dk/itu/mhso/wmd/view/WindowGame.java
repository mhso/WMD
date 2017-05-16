package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import dk.itu.mhso.wmd.WMDConstants;

public class WindowGame extends JFrame {
	public WindowGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		setPreferredSize(WMDConstants.WINDOW_SIZE_BASE);
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
