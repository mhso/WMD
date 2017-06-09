package dk.itu.mhso.wmd.view.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;

import dk.itu.mhso.wmd.Resources;
import dk.itu.mhso.wmd.Util;
import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.model.LevelInfo;
import dk.itu.mhso.wmd.model.UnitPath;
import dk.itu.mhso.wmd.view.CursorImage;
import dk.itu.mhso.wmd.view.Style;
import dk.itu.mhso.wmd.view.panels.EditorCanvas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.InputEvent;

public class WindowLevelEditor extends JFrame {
	private EditorCanvas canvas;
	private List<UnitPath> paths = new ArrayList<>();
	private List<Path2D> areas = new ArrayList<>();
	private JButton buttonPathDone;
	private JTextField textFieldLevelName;
	private LevelInfo info = new LevelInfo();
	private String recentFile;
	private boolean snapToLine = true;
	private JMenuItem miExportLevel;

	public WindowLevelEditor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setBackground(Style.OVERLAY_MENU_MAIN);
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		canvas = new EditorCanvas(this);
		contentPane.add(canvas, BorderLayout.CENTER);
		canvas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown()) {
					if(e.getKeyCode() == KeyEvent.VK_S) saveLevel();
					else if(e.getKeyCode() == KeyEvent.VK_O) {
						try {
							showSavedFiles();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		JPanel panelTop = new JPanel();
		panelTop.setBackground(Style.OVERLAY_MENU_MAIN);
		contentPane.add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new BorderLayout(0, 0));
		
		JPanel panelAddElements = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelAddElements.getLayout();
		flowLayout.setHgap(8);
		panelAddElements.setOpaque(false);
		panelTop.add(panelAddElements, BorderLayout.WEST);
		
		JButton buttonBack = new JButton("Back");
		buttonBack.setBackground(Style.TOWER_BUTTON_BG);
		buttonBack.setForeground(Color.WHITE);
		buttonBack.addActionListener(e -> {
			new WindowMainMenu();
			dispose();
		});
		panelAddElements.add(buttonBack);
		
		buttonPathDone = new JButton("Finish Path");
		buttonPathDone.setBackground(Style.TOWER_BUTTON_BG);
		buttonPathDone.setForeground(Color.WHITE);
		panelAddElements.add(buttonPathDone);
		buttonPathDone.setEnabled(false);
		buttonPathDone.addActionListener(e -> addPath());
		
		JButton buttonSetBGImage = new JButton("Set BG Image");
		buttonSetBGImage.setBackground(Style.TOWER_BUTTON_BG);
		buttonSetBGImage.setForeground(Color.WHITE);
		buttonSetBGImage.addActionListener(e -> loadImage());
		panelAddElements.add(buttonSetBGImage);
		
		JButton buttonPencil = new JButton(new ImageIcon(Style.PENCIL_ICON));
		buttonPencil.addActionListener(e -> canvas.setCurrentTool(0));
		
		JButton buttonLine = new JButton(new ImageIcon(Style.LINE_ICON));
		buttonLine.addActionListener(e -> canvas.setCurrentTool(1));
		
		JButton[] toolButtons = new JButton[] {buttonPencil, buttonLine};
		for(JButton button : toolButtons) {
			button.addActionListener(new ToolListener(button, toolButtons));
			button.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
			button.setBorderPainted(false);
			button.setBackground(Style.UPGRADE_MENU_HEADER);
			panelAddElements.add(button);
		}
		buttonLine.setBorderPainted(true);
		
		JButton buttonInvis = new JButton(new ImageIcon(Style.INVIS_ICON));
		buttonInvis.setBackground(Style.UPGRADE_MENU_HEADER);
		buttonInvis.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
		buttonInvis.setBorderPainted(false);
		buttonInvis.addActionListener(e -> invisToggled(buttonInvis));
		panelAddElements.add(buttonInvis);
		
		JCheckBox checkEnableSnapping = new JCheckBox("Snap To Line");
		checkEnableSnapping.setBackground(Style.TOWER_BUTTON_BG);
		checkEnableSnapping.setForeground(Color.WHITE);
		checkEnableSnapping.setSelected(snapToLine);
		checkEnableSnapping.addActionListener(e -> snapToLine = checkEnableSnapping.isSelected());
		panelAddElements.add(checkEnableSnapping);
		
		JPanel panelLevelName = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelLevelName.getLayout();
		flowLayout_1.setHgap(8);
		panelLevelName.setOpaque(false);
		panelTop.add(panelLevelName, BorderLayout.CENTER);
		
		JLabel labelLevelName = new JLabel("Level Name:");
		labelLevelName.setForeground(Color.WHITE);
		labelLevelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelLevelName.add(labelLevelName);
		
		textFieldLevelName = new JTextField("Awesome Level");
		textFieldLevelName.setBackground(Style.TOWER_BUTTON_BG);
		textFieldLevelName.setForeground(Color.WHITE);
		textFieldLevelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelLevelName.add(textFieldLevelName);
		textFieldLevelName.setColumns(10);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Style.OVERLAY_MENU_MAIN);
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		mnFile.setForeground(Color.WHITE);
		menuBar.add(mnFile);
		
		JMenuItem miSaveLevel = new JMenuItem("Save");
		miSaveLevel.addActionListener(e -> saveLevel());
		
		JMenuItem miNewLevel = new JMenuItem("New");
		miNewLevel.addActionListener(e -> newLevel());
		miNewLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mnFile.add(miNewLevel);
		miSaveLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mnFile.add(miSaveLevel);
		
		JMenuItem miLoadLevel = new JMenuItem("Load");
		miLoadLevel.addActionListener(e -> {
			try {
				showSavedFiles();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		miLoadLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mnFile.add(miLoadLevel);
		
		miExportLevel = new JMenuItem("Export");
		miExportLevel.setEnabled(false);
		miExportLevel.addActionListener(e -> levelDone());
		miExportLevel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0));
		mnFile.add(miExportLevel);
		
		JMenu mnEdit = new JMenu("Edit");
		mnEdit.setForeground(Color.WHITE);
		menuBar.add(mnEdit);
		
		JMenuItem miConfig = new JMenuItem("Configurations");
		miConfig.addActionListener(e -> openConfigWindow());
		mnEdit.add(miConfig);
		
		JMenuItem miUndo = new JMenuItem("Undo");
		miUndo.addActionListener(e -> canvas.undo());
		miUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		mnEdit.add(miUndo);
		
		JMenuItem miRedo = new JMenuItem("Redo");
		miRedo.addActionListener(e -> canvas.redo());
		miRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		mnEdit.add(miRedo);
		
		JMenu mnHelp = new JMenu("Help");
		mnHelp.setForeground(Color.WHITE);
		menuBar.add(mnHelp);
		
		setPreferredSize(WMDConstants.WINDOW_SIZE_BASE);
		pack();
		
		if(Files.exists(Paths.get(Resources.getEditorPath() + "/meta.bin"))) {
			recentFile = (String)Util.readObjectFromFile(Resources.getEditorPath() + "/meta.bin");
			try { loadLevel(recentFile); } catch(IOException ex) {  }
		}
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public boolean isSnappingToLine() {
		return snapToLine;
	}
	
	private void invisToggled(JButton invisButton) {
		canvas.toggleInvisTool();
		invisButton.setBorderPainted(!invisButton.isBorderPainted());
	}
	
	private void openConfigWindow() {
		JDialog dialog = new JDialog(this);
		JPanel dc = (JPanel) dialog.getContentPane();
		dc.setLayout(new BorderLayout(0, 25));
		dialog.setPreferredSize(new Dimension(300, 350));
		
		JLabel label = new JLabel("Edit Configurations");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 20));
		dc.add(label, BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		dc.add(centerPanel);
		
		JPanel enemyAmountPanel = new JPanel();
		FlowLayout flow = (FlowLayout) enemyAmountPanel.getLayout();
		flow.setHgap(10);
		centerPanel.add(enemyAmountPanel);
		
		label = new JLabel("<html><body>Amount of Enemies<br>Spawning Simultaneously:</body></html>");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		enemyAmountPanel.add(label, BorderLayout.NORTH);
		
		Integer[] pathAmount = new Integer[canvas.paths()];
		for(int i = 0; i < pathAmount.length; i++) pathAmount[i] = i+1;
		if(pathAmount.length == 0) pathAmount = new Integer[]{0};
		
		JComboBox<Integer> enemyAmountBox = new JComboBox<Integer>(pathAmount);
		enemyAmountBox.addActionListener(e -> info.setEnemiesPerSpawn((int)enemyAmountBox.getSelectedItem()));
		enemyAmountBox.setSelectedItem(info.getEnemiesPerSpawn());
		enemyAmountPanel.add(enemyAmountBox);
		
		JPanel enemySpeedPanel = new JPanel();
		flow = (FlowLayout) enemySpeedPanel.getLayout();
		flow.setHgap(10);
		centerPanel.add(enemySpeedPanel);
		
		label = new JLabel("Speed of Enemies:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		enemySpeedPanel.add(label, BorderLayout.NORTH);
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL);
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				info.setEnemiesSpeed(slider.getValue()-1);
			}
		});
		slider.setValue(info.getEnemySpeed()+1);
		slider.setPaintLabels(true);
		slider.setMaximum(8);
		slider.setMinimum(1);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(7);
		slider.setSnapToTicks(true);
		enemySpeedPanel.add(slider);
		
		JPanel startingMoney = new JPanel();
		flow = (FlowLayout) startingMoney.getLayout();
		flow.setHgap(10);
		centerPanel.add(startingMoney);
		
		label = new JLabel("Starting Money:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		startingMoney.add(label, BorderLayout.NORTH);
		
		JTextField txtStartingMoney = new JTextField(5);
		txtStartingMoney.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!txtStartingMoney.getText().isEmpty()) info.setStartingMoney(Integer.parseInt(txtStartingMoney.getText()));
			}
		});
		((AbstractDocument) txtStartingMoney.getDocument()).setDocumentFilter(new SearchFilter(txtStartingMoney));
		txtStartingMoney.setText(""+info.getStartingMoney());
		startingMoney.add(txtStartingMoney);
		
		JPanel lives = new JPanel();
		flow = (FlowLayout) lives.getLayout();
		flow.setHgap(10);
		centerPanel.add(lives);
		
		label = new JLabel("Lives:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lives.add(label, BorderLayout.NORTH);
		
		JTextField txtLives = new JTextField(5);
		txtLives.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!txtLives.getText().isEmpty()) info.setLives(Integer.parseInt(txtLives.getText()));
			}
		});
		((AbstractDocument) txtLives.getDocument()).setDocumentFilter(new SearchFilter(txtLives));
		txtLives.setText(""+info.getLives());
		lives.add(txtLives);
		
		dialog.pack();
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}
	
	private void newLevel() {
		getContentPane().remove(canvas);
		canvas = new EditorCanvas(this);
		canvas.setCursor(CursorImage.getDrawCursor(Color.BLACK));
		buttonPathDone.setEnabled(false);
		miExportLevel.setEnabled(false);
		textFieldLevelName.setText("Awesome Level");
		paths = new ArrayList<>();
		areas = new ArrayList<>();
		info = new LevelInfo();
		getContentPane().add(canvas);
		revalidate();
	}
	
	private void saveLevel() {
		String pathName = "resources/editor/" + textFieldLevelName.getText();
		if(!isSaveConfirmed(pathName)) return;
		try {
			if(Files.exists(Paths.get(pathName))) {
				deleteDirectory(Paths.get(pathName));
			}
			Files.createDirectory(Paths.get(pathName));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Cursor cursor = canvas.getCursor();
		canvas.setCursor(CursorImage.getDefault());
		Util.writeObjectToFile(canvas, pathName + "/canvas.bin");
		canvas.setCursor(cursor);
		writeThingsToBinary(pathName);
		if(canvas.getBGImage() == null) return;
		try {
			ImageIO.write(canvas.getBGImage(), "png", new File(pathName + "/bg.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		recentFile = pathName;
		Util.writeObjectToFile(recentFile, Resources.getEditorPath() + "/meta.bin");
	}
	
	private void loadLevel(String fileName) throws IOException {
		getContentPane().remove(canvas);
		canvas = (EditorCanvas) Util.readObjectFromFile(fileName + "/canvas.bin");
		canvas.setWindow(this);
		getContentPane().add(canvas);
		BufferedImage image = ImageIO.read(new File(fileName + "/bg.png"));
		if(image != null) canvas.setBGImage(image);
		
		info = (LevelInfo) Util.readObjectFromFile(fileName + "/config.bin");
		paths = (List<UnitPath>) Util.readObjectFromFile(fileName + "/paths.bin");
		areas = (List<Path2D>) Util.readObjectFromFile(fileName + "/areas.bin");
		textFieldLevelName.setText(new File(fileName).getName());
		if(canvas.paths() > 0) miExportLevel.setEnabled(true);
		canvas.setCursor(CursorImage.getDrawCursor(Color.BLACK));
		
		checkResize(image);
		recentFile = fileName;
		Util.writeObjectToFile(recentFile, Resources.getEditorPath() + "/meta.bin");
	}
	
	private void showSavedFiles() throws IOException {
		JDialog dialog = new JDialog(this);
		JPanel dc = (JPanel) dialog.getContentPane();
		dc.setLayout(new BorderLayout());
		List<Path> files = new ArrayList<>();
		Iterator<Path> it = Files.list(Paths.get("resources/editor")).iterator();
		while(it.hasNext()) {
			Path p = it.next();
			if(Files.isDirectory(p)) files.add(p);
		}
		
		String[] fileNames = new String[files.size()];
		for(int i = 0; i < files.size(); i++) {
			fileNames[i] = files.get(i).toFile().getName();
		}
		
		JList<String> list = new JList<>(fileNames);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					int index = list.getSelectedIndex();
					if(index > -1) {
						try { loadLevel(files.get(index).toString()); } catch(IOException ex) {  }
						dialog.dispose();
					}
				}
			}
		});
		
		JScrollPane scroll = new JScrollPane(list);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		dc.add(scroll);
		
		dialog.pack();
		
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	private void loadImage() {
		JFileChooser fileChooser = new JFileChooser("user.dir");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png"));
		int val = fileChooser.showOpenDialog(this);
		if(val == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				BufferedImage image = ImageIO.read(file);
				canvas.setBGImage(image);
				checkResize(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void checkResize(BufferedImage image) {
		setPreferredSize(new Dimension(image.getWidth()+20, image.getHeight()+105));
		pack();
		repaint();
	}
	
	public void pathStarted() {
		buttonPathDone.setEnabled(true);
	}
	
	private void addPath() {
		paths.add(canvas.getUnitPath());
		canvas.pathCompleted();
		areas.add(canvas.getCurrentArea());
		buttonPathDone.setEnabled(false);
		miExportLevel.setEnabled(true);
		repaint();
	}
	
	private void levelDone() {
		String pathName = Resources.getLevelsPath() + "/" + textFieldLevelName.getText();
		BufferedImage bgImage = canvas.getBGImage();
		if(bgImage == null) {
			bgImage = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
			for(int x = 0; x < bgImage.getWidth(); x++) {
				for(int y = 0; y < bgImage.getHeight(); y++) {
					bgImage.setRGB(x, y, Color.WHITE.getRGB());
				}
			}
		}
		try {
			if(isSaveConfirmed(pathName)) {
				if(Files.exists(Paths.get(pathName))) {
					deleteDirectory(Paths.get(pathName));
				}
				
				Files.createDirectory(Paths.get(pathName));
				ImageIO.write(bgImage, "png", new File(pathName + "/bg.png"));
				writeThingsToBinary(pathName);
				Game.addLevel(new Level(pathName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void deleteDirectory(Path path) throws IOException {
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
	         @Override
	         public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
	             throws IOException
	         {
	             Files.delete(file);
	             return FileVisitResult.CONTINUE;
	         }
	         @Override
	         public FileVisitResult postVisitDirectory(Path dir, IOException e)
	             throws IOException
	         {
	             if (e == null) {
	                 Files.delete(dir);
	                 return FileVisitResult.CONTINUE;
	             } else {
	                 throw e;
	             }
	         }
	     });
	}
	
	private boolean isSaveConfirmed(String pathName) {
		int confirm = JOptionPane.OK_OPTION;
		if(Files.exists(Paths.get(pathName))) confirm = JOptionPane.showConfirmDialog(this, "Do You Want To Overwrite The File?", 
				"Name Already Exists!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
		return confirm == JOptionPane.OK_OPTION;
	}
	
	private void writeThingsToBinary(String pathName) {
		Util.writeObjectToFile(paths, pathName + "/paths.bin");
		Util.writeObjectToFile(areas, pathName + "/areas.bin");
		Util.writeObjectToFile(info, pathName + "/config.bin");
	}
	
	public static void main(String[] args) {
		new WindowLevelEditor();
	}
	
	private class SearchFilter extends DocumentFilter {
		private JTextField source;
		
		public SearchFilter(JTextField source) {
			this.source = source;
		}
		
        @Override
        public void replace(FilterBypass fb, int offset, int length, String newText,
                            AttributeSet attr) throws BadLocationException {
        	for(char c : newText.toCharArray()) if(!Character.isDigit(c)) return;
            super.replace(fb, offset, length, newText, attr);
        }
    }
	
	private class ToolListener implements ActionListener {
		private JButton sourceButton;
		private JButton[] otherButtons;
		
		public ToolListener(JButton sourceButton, JButton[] otherButtons) {
			this.sourceButton = sourceButton;
			this.otherButtons = otherButtons;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sourceButton.setBorderPainted(true);
			for(JButton button : otherButtons) {
				if(button != sourceButton) button.setBorderPainted(false);
				button.repaint();
			}
		}
	}
}
