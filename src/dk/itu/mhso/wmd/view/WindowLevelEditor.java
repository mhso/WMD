package dk.itu.mhso.wmd.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import dk.itu.mhso.wmd.Resources;
import dk.itu.mhso.wmd.Util;
import dk.itu.mhso.wmd.WMDConstants;
import dk.itu.mhso.wmd.controller.Game;
import dk.itu.mhso.wmd.controller.PathParserNew;
import dk.itu.mhso.wmd.model.Level;
import dk.itu.mhso.wmd.model.UnitPath;
import java.awt.Font;
import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WindowLevelEditor extends JFrame {
	private EditorCanvas canvas;
	private List<UnitPath> paths = new ArrayList<>();
	private List<Path2D> areas = new ArrayList<>();
	private JButton buttonPathDone;
	private JButton buttonDone;
	private JTextField textFieldLevelName;

	public WindowLevelEditor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel contentPane = (JPanel) getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		canvas = new EditorCanvas(this);
		contentPane.add(canvas, BorderLayout.CENTER);
		
		JPanel panelTop = new JPanel();
		contentPane.add(panelTop, BorderLayout.NORTH);
		panelTop.setLayout(new BorderLayout(0, 0));
		
		JPanel panelAddElements = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panelAddElements.getLayout();
		flowLayout.setHgap(8);
		panelAddElements.setOpaque(false);
		panelTop.add(panelAddElements, BorderLayout.WEST);
		
		JButton buttonAddUnitPath = new JButton("Start Path");
		buttonAddUnitPath.addActionListener(e -> canvas.setCurrentDrawColor(Color.BLACK));
		panelAddElements.add(buttonAddUnitPath);
		
		buttonPathDone = new JButton("Finish Path");
		panelAddElements.add(buttonPathDone);
		buttonPathDone.setEnabled(false);
		buttonPathDone.addActionListener(e -> addPath());
		
		JButton buttonSetBGImage = new JButton("Set BG Image");
		buttonSetBGImage.addActionListener(e -> loadImage());
		panelAddElements.add(buttonSetBGImage);
		
		JButton buttonPencil = new JButton(new ImageIcon(Style.PENCIL_ICON));
		panelAddElements.add(buttonPencil);
		buttonPencil.setBackground(Style.UPGRADE_MENU_HEADER);
		
		JButton buttonLine = new JButton(new ImageIcon(Style.LINE_ICON));
		panelAddElements.add(buttonLine);
		buttonLine.setBackground(Style.UPGRADE_MENU_HEADER);
		buttonLine.addActionListener(e -> canvas.setCurrentTool(1));
		buttonPencil.addActionListener(e -> canvas.setCurrentTool(0));
		
		JPanel panelLevelName = new JPanel();
		panelLevelName.setOpaque(false);
		panelTop.add(panelLevelName, BorderLayout.CENTER);
		
		JLabel labelLevelName = new JLabel("Level Name:");
		labelLevelName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelLevelName.add(labelLevelName);
		
		textFieldLevelName = new JTextField("Awesome Level");
		panelLevelName.add(textFieldLevelName);
		textFieldLevelName.setColumns(10);
		
		JPanel panelBottom = new JPanel();
		panelBottom.setOpaque(false);
		getContentPane().add(panelBottom, BorderLayout.SOUTH);
		panelBottom.setLayout(new BorderLayout(0, 0));
		
		buttonDone = new JButton("Level Complete");
		buttonDone.addActionListener(e -> levelDone());
		buttonDone.setEnabled(false);
		buttonDone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelBottom.add(buttonDone, BorderLayout.EAST);
		
		JButton buttonBack = new JButton("Back");
		buttonBack.addActionListener(e -> {
			new WindowMainMenu();
			dispose();
		});
		buttonBack.setFont(new Font("Tahoma", Font.PLAIN, 15));
		panelBottom.add(buttonBack, BorderLayout.WEST);
		
		setPreferredSize(WMDConstants.WINDOW_SIZE_BASE);
		pack();
		
		setLocationRelativeTo(null);
		setVisible(true);
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
				setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pathStarted() {
		buttonPathDone.setEnabled(true);
	}
	
	private void addPath() {
		paths.add(PathParserNew.parsePath(canvas.getMainImage(), canvas.getFirstPoint(),
				new Point((int)canvas.getCurrentPath().getCurrentPoint().getX(), (int)canvas.getCurrentPath().getCurrentPoint().getY())));
		canvas.pathCompleted();
		areas.add(canvas.getCurrentArea());
		buttonPathDone.setEnabled(false);
		buttonDone.setEnabled(true);
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
			int i = 1;
			while(Files.exists(Paths.get(pathName))) {
				pathName = pathName + "(" + i + ")";
			}
			Files.createDirectory(Paths.get(pathName));
			ImageIO.write(bgImage, "png", new File(pathName + "/bg.png"));
			for(int j = 0; j < paths.size(); j++) {
				Util.writeObjectToFile(paths.get(j), pathName + "/path" + (j+1) + ".bin");
				Util.writeObjectToFile(areas.get(j), pathName + "/area" + (j+1) + ".bin");
			}
			
			Game.addLevel(new Level(pathName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new WindowLevelEditor();
	}
}
