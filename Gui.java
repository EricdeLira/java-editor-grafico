import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
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
    private JButton jbColor = new JButton("Color");
    private JButton jbClear = new JButton("Clear");
    private JButton jbExit = new JButton("Exit");

    private JLabel jlLineWeight = new JLabel(" Lineweight: " + String.format("%-5s", 1));
    private JSlider jsLineWeight = new JSlider(1, 50, 1);

    private JCheckBox jcbUsingViewport = new JCheckBox("Viewport");
    boolean usingViewport = false;

    
    /* Menu bar and components */
    private JMenuBar jMenuBar = new JMenuBar();

    private JMenu jmFile, _optionsMenu, _optionsMenuColors, _optionsMenuWindowType, jmHelp;
    private JMenuItem _menuSaveFile, _menuReadFile, jmExit, jmAbout, _optionsMenuColorsCel, 
    _optionsMenuColorsBackground, _optionsMenuColorsOriginals;
    private JCheckBoxMenuItem _rbItemMenu;
    /*---------------------------------------------*/

    private JLabel msg = new JLabel("Msg: ");
    private DrawingPainel drawingPainel = new DrawingPainel(msg, currentType, currentColor, 10);

    public Gui(int width, int height){
        super("Graphic Editor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);

        // Add components to the toolBar
        toolBar.add(jbPoint);
        toolBar.add(jbClear);
        toolBar.add(jbColor);
        toolBar.add(jlLineWeight);
        toolBar.add(jsLineWeight);
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
        jbClear.addActionListener(e -> {
            drawingPainel.removeAll();
            jsLineWeight.setValue(1);
            drawingPainel.getGraphics().clearRect(Constants.XW_MIN, Constants.YW_MIN, Constants.WIDTH, Constants.HEIGHT-20);
            drawingPainel.setCurrentColor(Color.BLACK);
            jbColor.setForeground(Color.BLACK);
            currentType = PrimitiveTypes.NONE;
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

        _menuSaveFile = new JMenuItem("Save", KeyEvent.VK_G);
        _menuSaveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        _menuSaveFile.getAccessibleContext().setAccessibleDescription("");
        jmFile.add(_menuSaveFile);

        _menuReadFile = new JMenuItem("Read", KeyEvent.VK_L);
        _menuReadFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
        _menuReadFile.getAccessibleContext().setAccessibleDescription("");
        jmFile.add(_menuReadFile);

        jmExit = new JMenuItem("Exit", KeyEvent.VK_S);
        jmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        jmExit.getAccessibleContext().setAccessibleDescription("");
        jmExit.addActionListener(e -> {
            System.exit(0);
        });
        jmFile.add(jmExit);

        _optionsMenu = new JMenu("View");
        _optionsMenu.setMnemonic(KeyEvent.VK_V);
        jMenuBar.add(_optionsMenu);

        ButtonGroup group = new ButtonGroup();
        _rbItemMenu = new JCheckBoxMenuItem("Viewport");
        _rbItemMenu.setMnemonic(KeyEvent.VK_W);

        group.add(_rbItemMenu);
        _optionsMenu.add(_rbItemMenu);

        _optionsMenuColors = new JMenu("Colors");
        _optionsMenuColors.setMnemonic(KeyEvent.VK_C);
        _optionsMenu.add(_optionsMenuColors);

        _optionsMenuColorsCel = new JMenuItem("Cel color", KeyEvent.VK_C);
        _optionsMenuColorsCel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        _optionsMenuColorsCel.getAccessibleContext().setAccessibleDescription("");

        _optionsMenuColors.add(_optionsMenuColorsCel);

        _optionsMenuColorsBackground = new JMenuItem("Cor de Background da Celula", KeyEvent.VK_F);
        _optionsMenuColorsBackground.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_F, ActionEvent.ALT_MASK));
        _optionsMenuColorsBackground.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        //       _optionsMenuColorsBackground.addActionListener(this);
        _optionsMenuColors.add(_optionsMenuColorsBackground);

        // Adiciona separador
        _optionsMenuColors.addSeparator();
        // Cores item Cor Borda
        _optionsMenuColorsOriginals = new JMenuItem("Restaura Cores Originais", KeyEvent.VK_R);
        _optionsMenuColorsOriginals.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_R, ActionEvent.ALT_MASK));
        _optionsMenuColorsOriginals.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        //        _optionsMenuColorsOriginais.addActionListener(this);
        _optionsMenuColorsOriginals.setEnabled(false);
        _optionsMenuColors.add(_optionsMenuColorsOriginals);

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
        String texto = "Testador de Primitivos Gr�ficos\nVersao 1.0\n\n"
                + "(c) Copyright 2021. Todos os direitos reservados.\n\n"
                + "Computa��o Gr�fica e Processamento de Imagens\n"
                + "Prof. Dr. Julio Arakaki\nCiencia da Computacao - PUCSP";

        JOptionPane.showMessageDialog(null, texto, "Sobre Testador de Primitivos Gr�ficos", JOptionPane.INFORMATION_MESSAGE);
    }

}
