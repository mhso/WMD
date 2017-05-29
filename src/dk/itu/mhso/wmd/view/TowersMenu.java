package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.controller.UnitFactory;
import dk.itu.mhso.wmd.model.Ally;

public class TowersMenu extends JPopupMenu {
	private Ally selectedUnit;
	private GameOverlay overlay;
	
	public TowersMenu(GameOverlay overlay) {
		this.overlay = overlay;
		setLayout(new BorderLayout(0, 0));
		setBackground(Style.OVERLAY_MENU_MAIN);
		
		overlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(selectedUnit != null) {
					Game.addAlly(selectedUnit);
					selectedUnit.setLocation(e.getPoint());
					Cursor cursor = CursorImage.getDefault();
					setCursor(cursor);
					overlay.setCursor(cursor);
				}
				if(WindowGame.canvas.getHighlighedUnit() != null) {
					if(!WindowGame.canvas.isDrawingUnitRange()) WindowGame.canvas.drawUnitRange(true);
					else WindowGame.canvas.drawUnitRange(false);
				}
				selectedUnit = null;
			}
		});
		
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Style.OVERLAY_MENU_MAIN);
		add(topPanel, BorderLayout.NORTH);
		
		JButton buttonDeflateLeft = new JButton("<");
		buttonDeflateLeft.addActionListener(e -> setVisible(false));
		topPanel.add(buttonDeflateLeft);
		buttonDeflateLeft.setPreferredSize(new Dimension(41, 23));
		buttonDeflateLeft.setEnabled(true);
		
		JPanel panelTowers = new JPanel();
		panelTowers.setBackground(Style.OVERLAY_MENU_MAIN);
		add(panelTowers, BorderLayout.CENTER);
		panelTowers.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnNewButton = new JButton("Tower 1");
		btnNewButton.addActionListener(e -> addToSelected("SNIPER"));
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
	
	private void addToSelected(String unitName) {
		selectedUnit = (Ally) UnitFactory.createUnit(unitName);
		Cursor cursor = CursorImage.getCursor(selectedUnit.getIcon(), this);
		setCursor(cursor);
		overlay.setCursor(cursor);
	}
	
	public void showDropdown(JComponent source) {
		show(source, source.getLocation().x, source.getLocation().y);
	}
}
