import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import constants.Constants;
import constants.PrimitiveTypes;

public class Gui extends JFrame{
    private PrimitiveTypes currentType = PrimitiveTypes.NONE;
    private Color currentColor = Color.BLACK;
    private int currentLineWeight = 1;

    private JToolBar toolBar = new JToolBar();

    private JButton jbPoint = new JButton("Point");
    private JButton jbLine = new JButton("Line");
    private JButton jbCircle = new JButton("Circle");
    private JButton jbTriangle = new JButton("Triangle");
    private JButton jbRectangle = new JButton("Rectangle");
    private JButton jbPolygon = new JButton("Polygon");
    private JButton jbColor = new JButton("Color");
    private JButton jbEdit = new JButton("Edit");
    private JButton jbExit = new JButton("Exit");

    private JLabel jlLineWeight = new JLabel(" Lineweight: " + String.format("%-5s", 1));
    private JSlider jsLineWeight = new JSlider(1, 50, 1);

    private JLabel jlEdit = new JLabel("Edit: ");
    private JTextField jtfEdit = new JTextField(20);
    

    private JCheckBox jcbUsingViewport = new JCheckBox("Viewport");
    boolean usingViewport = false;

    
    /* Menu bar and components */
    private JMenuBar jMenuBar = new JMenuBar();

    private JMenu jmFile, jmClear, jmHelp, jmTransform;
    private JMenuItem _menuSaveFile, _menuReadFile, jmExit, jmAbout, _clear, _clearAll, _translate, _rotate, _scaleUp, _scaleDown;
    /*---------------------------------------------*/

    private JLabel msg = new JLabel("Msg: ");
    private DrawingPainel drawingPainel = new DrawingPainel(msg, currentType, currentColor, 10);

    public Gui(int width, int height){
        super("Graphic Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(width, height);
        setVisible(true);

        // Add components to the toolBar
        toolBar.add(jbPoint);
        toolBar.add(jbLine);
        toolBar.add(jbCircle);
        toolBar.add(jbTriangle);
        toolBar.add(jbRectangle);
        toolBar.add(jbPolygon);
        toolBar.add(jbColor);
        toolBar.add(jlLineWeight);
        toolBar.add(jsLineWeight);
        toolBar.add(jlEdit);
        toolBar.add(jtfEdit);
        toolBar.add(jbEdit);
        toolBar.add(jcbUsingViewport);
        toolBar.add(jbExit);

        add(toolBar, BorderLayout.NORTH);

        drawingPainel.setLineWeight(currentLineWeight);
        add(drawingPainel, BorderLayout.CENTER);

        msg.setForeground(Color.BLUE);
        add(msg, BorderLayout.SOUTH);

        addMenu();

        /* Component Listeners */
        jbPoint.addActionListener(e -> {
            currentType = PrimitiveTypes.POINT;
            drawingPainel.setType(currentType);
        });
        jbLine.addActionListener(e -> {
            currentType = PrimitiveTypes.LINE;
            drawingPainel.setType(currentType);
        });
        jbCircle.addActionListener(e -> {
            currentType = PrimitiveTypes.CIRCLE;
            drawingPainel.setType(currentType);
        });
        jbTriangle.addActionListener(e -> {
            currentType = PrimitiveTypes.TRIANGLE;
            drawingPainel.setType(currentType);
        });
        jbRectangle.addActionListener(e -> {
            currentType = PrimitiveTypes.RECTANGLE;
            drawingPainel.setType(currentType);
        });
        jbPolygon.addActionListener(e -> {
            currentType = PrimitiveTypes.POLYGON;
            drawingPainel.setType(currentType);
        });
        jbColor.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, " Pick a Color", msg.getForeground()); 
            if (color != null){ 
                currentColor = color;  
            }
            drawingPainel.setCurrentColor(currentColor); 
            jbColor.setForeground(currentColor);
        });  
        jbEdit.addActionListener(e -> {
                drawingPainel.removeAll();
                drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT - 20);
                int [] color = {drawingPainel.getCurrentColor().getRed(), drawingPainel.getCurrentColor().getGreen(), drawingPainel.getCurrentColor().getBlue()};
                drawingPainel.readFile("save.json", jtfEdit.getText(), drawingPainel.getLineWeight(), color, "none");
            
        });
        jsLineWeight.addChangeListener(e -> {
            currentLineWeight = jsLineWeight.getValue(); 
            jlLineWeight.setText(" Lineweight: " + String.format("%-5s", currentLineWeight));
            drawingPainel.setLineWeight(currentLineWeight);
        });
        jcbUsingViewport.addActionListener(e -> {
            AbstractButton absB = (AbstractButton) e.getSource();
            boolean selected = absB.getModel().isSelected();
            if(selected){
                usingViewport = true;
            }else{
                usingViewport = false;
            }
            drawingPainel.setUsingViewport(usingViewport);
        });
        jbExit.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void addMenu(){
        jmFile = new JMenu("Drawing");
        jmFile.setMnemonic(KeyEvent.VK_A);
        jMenuBar.add(jmFile);

        _menuSaveFile = new JMenuItem("Save", KeyEvent.VK_S);
        _menuSaveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        _menuSaveFile.getAccessibleContext().setAccessibleDescription("");
        _menuSaveFile.addActionListener(e -> {
            drawingPainel.saveFile();
        });
        jmFile.add(_menuSaveFile);

        _menuReadFile = new JMenuItem("Read", KeyEvent.VK_L);
        _menuReadFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        _menuReadFile.getAccessibleContext().setAccessibleDescription("");
        _menuReadFile.addActionListener(e -> {
            
            JFileChooser fileChooser = new JFileChooser();

            int response = fileChooser.showOpenDialog(null);
            if(response == 0){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                drawingPainel.removeAll();
                drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT - 20);
                int [] color = {drawingPainel.getCurrentColor().getRed(), drawingPainel.getCurrentColor().getGreen(), drawingPainel.getCurrentColor().getBlue()};
                drawingPainel.readFile(file.toString(), jtfEdit.getText(), drawingPainel.getLineWeight(), color, "none");
            }
            
            
        });
        jmFile.add(_menuReadFile);

        jmExit = new JMenuItem("Exit", KeyEvent.VK_E);
        jmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        jmExit.getAccessibleContext().setAccessibleDescription("");
        jmExit.addActionListener(e -> {
            System.exit(0);
        });
        jmFile.add(jmExit);

        // Clear
        jmClear = new JMenu("Clear");
        jmClear.setMnemonic(KeyEvent.VK_C);
        jMenuBar.add(jmClear);

        _clear = new JMenuItem("Clear Painel", KeyEvent.VK_C);
        _clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        _clear.getAccessibleContext().setAccessibleDescription("");
        _clear.addActionListener(e -> {
            drawingPainel.removeAll();
            drawingPainel.pointCount = 0;
            drawingPainel.lineCount = 0;
            drawingPainel.circleCount = 0;
            drawingPainel.triangleCount = 0;
            drawingPainel.rectangleCount = 0;
            drawingPainel.polygonCount = 0;
            jsLineWeight.setValue(1);
            drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT - 20);
            drawingPainel.setCurrentColor(Color.BLACK);
            jbColor.setForeground(Color.BLACK);
            currentType = PrimitiveTypes.NONE;
            drawingPainel.setType(currentType);
        });
        jmClear.add(_clear);

        _clearAll = new JMenuItem("Clear Memory", KeyEvent.VK_V);
        _clearAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));
        _clearAll.getAccessibleContext().setAccessibleDescription("");
        _clearAll.addActionListener(e -> {
            drawingPainel.removeAll();
            drawingPainel.pointCount = 0;
            drawingPainel.lineCount = 0;
            drawingPainel.circleCount = 0;
            drawingPainel.triangleCount = 0;
            drawingPainel.rectangleCount = 0;
            drawingPainel.polygonCount = 0;
            jsLineWeight.setValue(1);
            drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT-20);
            drawingPainel.setCurrentColor(Color.BLACK);
            jbColor.setForeground(Color.BLACK);
            currentType = PrimitiveTypes.NONE;
            drawingPainel.setType(currentType);
            drawingPainel.clearMemory();
            drawingPainel.saveFile();
        });
        jmClear.add(_clearAll);

        jmTransform = new JMenu("Transform");
        jMenuBar.add(jmTransform);
        _rotate = new JMenuItem("rotate");
        _rotate.addActionListener(e -> {
            drawingPainel.removeAll();
            drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT - 20);
            int[] color = { drawingPainel.getCurrentColor().getRed(), drawingPainel.getCurrentColor().getGreen(), drawingPainel.getCurrentColor().getBlue() };
            drawingPainel.readFile("save.json", jtfEdit.getText(), drawingPainel.getLineWeight(), color, "rotate");
            drawingPainel.saveFile();
        });
        jmTransform.add(_rotate);
        _translate = new JMenuItem("move");
        _translate.addActionListener(e -> {
            drawingPainel.removeAll();
            drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT - 20);
            int[] color = { drawingPainel.getCurrentColor().getRed(), drawingPainel.getCurrentColor().getGreen(), drawingPainel.getCurrentColor().getBlue() };
            drawingPainel.readFile("save.json", jtfEdit.getText(), drawingPainel.getLineWeight(), color, "translate");
            drawingPainel.saveFile();
            
        });
        jmTransform.add(_translate);
        _scaleUp = new JMenuItem("scale up");
        _scaleUp.addActionListener(e -> {
            drawingPainel.removeAll();
            drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT - 20);
            int[] color = { drawingPainel.getCurrentColor().getRed(), drawingPainel.getCurrentColor().getGreen(), drawingPainel.getCurrentColor().getBlue() };
            drawingPainel.readFile("save.json", jtfEdit.getText(), drawingPainel.getLineWeight(), color, "scaleUp");
            drawingPainel.saveFile();
        });
        jmTransform.add(_scaleUp);
        _scaleDown = new JMenuItem("scale down");
        _scaleDown.addActionListener(e -> {
            drawingPainel.removeAll();
            drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT - 20);
            int[] color = { drawingPainel.getCurrentColor().getRed(), drawingPainel.getCurrentColor().getGreen(), drawingPainel.getCurrentColor().getBlue() };
            drawingPainel.readFile("save.json", jtfEdit.getText(), drawingPainel.getLineWeight(), color, "scaleDown");
            drawingPainel.saveFile();
        });
        jmTransform.add(_scaleDown);

        // Help
        jmHelp = new JMenu("Help");
        jmHelp.setMnemonic(KeyEvent.VK_J);
        jMenuBar.add(jmHelp);

        // Help item About
        jmAbout = new JMenuItem("About", KeyEvent.VK_S);
        jmAbout.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_S, ActionEvent.ALT_MASK));
        jmAbout.getAccessibleContext().setAccessibleDescription("");
        jmAbout.addActionListener(e -> {
            about();
        });
        jmHelp.add(jmAbout);

        setJMenuBar(jMenuBar);
    }

    private void about(){
        String text = "Graphic Editor\nVersion 1.0\n\n"
                + "(c) Copyright 2021. All rights reserved.\n\n"
                + "Computacao Gr??fica e Processamento de Imagens\n"
                + "Eric Candido\nErick Clinton de Carvalho Leite\nJo??o Pedro Hegedus Vellenich\nRenan Alencar Dores\nCiencia da Computacao - PUCSP";

        JOptionPane.showMessageDialog(null, text, "About Graphic Editor", JOptionPane.INFORMATION_MESSAGE);
    }

}
