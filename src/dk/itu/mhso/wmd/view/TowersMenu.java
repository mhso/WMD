package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import dk.itu.mhso.wmd.Main;
import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.controller.UnitFactory;
import dk.itu.mhso.wmd.model.Ally;
import dk.itu.mhso.wmd.model.Unit;
import dk.itu.mhso.wmd.model.UnitType;

public class TowersMenu extends JPopupMenu implements ChangeListener {
	private Ally selectedUnit;
	private GameOverlay overlay;
	private List<TowerButton> towerButtons = new ArrayList<>();
	
	public TowersMenu(GameOverlay overlay) {
		this.overlay = overlay;
		setLayout(new BorderLayout(0, 0));
		setBackground(Style.OVERLAY_MENU_MAIN);
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Game.addChangeListener("menu", this);
		
		overlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2 && WindowGame.canvas.getHighlighedUnit() != null) {
					WindowGame.canvas.getHighlighedUnit().showUpgradeWindow();
					return;
				}
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(selectedUnit != null && !Game.isWithinMainPath(e.getPoint())) {
						Game.addAlly(selectedUnit);
						Game.decrementMoney(selectedUnit.getCost());
						Game.setChanged(this, "overlay");
						Game.setChanged(this, "menu");
						selectedUnit.setLocation(e.getPoint());
						selectedUnit.createUpgradeWindow();
						deselectUnit();
					}
				}
				else if(e.getButton() == MouseEvent.BUTTON3) {
					if(selectedUnit != null) deselectUnit();
				}
				
				if(WindowGame.canvas.getHighlighedUnit() != null) {
					if(!WindowGame.canvas.isDrawingUnitRange()) 
						WindowGame.canvas.setUnitRangeCircle(WindowGame.canvas.getHighlighedUnit()
								.getRangeCircle());
					else WindowGame.canvas.setUnitRangeCircle(null);
				}
				else WindowGame.canvas.setUnitRangeCircle(null);
			}
		});
		Main.window.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if(selectedUnit != null) deselectUnit();
					if(WindowGame.canvas.getHighlighedUnit() != null) {
						if(WindowGame.canvas.isDrawingUnitRange()) WindowGame.canvas.setUnitRangeCircle(null);
					}
				}
			}	
		});
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setBackground(Style.OVERLAY_MENU_MAIN);
		add(topPanel, BorderLayout.NORTH);
		
		JButton buttonDeflateLeft = new JButton("<");
		buttonDeflateLeft.setFont(new Font(buttonDeflateLeft.getFont().getName(), Font.PLAIN, 15));
		buttonDeflateLeft.setForeground(Color.WHITE);
		buttonDeflateLeft.setBackground(Style.OVERLAY_MENU_MAIN);
		buttonDeflateLeft.addActionListener(e -> setVisible(false));
		topPanel.add(buttonDeflateLeft, BorderLayout.WEST);
		
		JPanel panelTowers = new JPanel();
		panelTowers.setBackground(Style.OVERLAY_MENU_MAIN);
		add(panelTowers, BorderLayout.CENTER);
		panelTowers.setLayout(new GridLayout(0, 2, 0, 0));
		
		for(UnitType ut : UnitType.values()) {
			Unit unit = UnitFactory.createUnit(ut.toString());
			if(unit instanceof Ally) {
				Ally ally = (Ally)UnitFactory.createUnit(ut.toString());
				TowerButton unitButton = new TowerButton(ally);
				
				unitButton.addActionListener(e -> addToSelected((Ally)UnitFactory.createUnit(ut.toString())));
				if(Game.getMoneyAmount() < ally.getCost()) unitButton.setEnabled(false);
				panelTowers.add(unitButton);
				towerButtons.add(unitButton);
			}
		}
	}
	
	private void deselectUnit() {
		Cursor cursor = CursorImage.getDefault();
		setCursor(cursor);
		overlay.setCursor(cursor);
		selectedUnit = null;
	}
	
	private void addToSelected(Ally unit) {
		selectedUnit = unit;
		Cursor cursor = CursorImage.getCursor(selectedUnit.getIcon(), this);
		setCursor(cursor);
		overlay.setCursor(cursor);
	}
	
	public void showDropdown(JComponent source) {
		show(source, source.getLocation().x, source.getLocation().y);
	}

	@Override
	public void stateChanged(ChangeEvent e) { for(TowerButton button : towerButtons) button.stateChanged(e); }
}
