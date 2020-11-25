import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import static javax.swing.GroupLayout.Alignment.*;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import javax.swing.*;

public class SudokuFrame {

    private final JFrame frame = new JFrame("Sudoku Game");
    private SudokuGrid grid;
    int hgap = 5;
    int vgap = 5;

    public SudokuFrame() {
      
       JPanel contentPane = new JPanel();
       
       contentPane.setBorder(BorderFactory.createEmptyBorder(hgap, hgap, hgap, hgap));
       contentPane.add(grid = new SudokuGrid());
       frame.setContentPane(contentPane);
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildMenu();
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem open   = new JMenuItem("Open");
        JMenuItem about = new JMenuItem("About");

        fileMenu.add(open);
        fileMenu.addSeparator();
        fileMenu.add(about);

        bar.add(fileMenu);

        open.addActionListener((ActionEvent e) -> {
            JFileChooser chooser = new JFileChooser("./");
            int status = chooser.showOpenDialog(frame);

            if (status == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                grid.openFile(file);
            }
        });

      // "Solve the puzzle by placing a number from 1 to 9 in each box."+" No number can appear more than once in any row, column or mini 3 by 3 square.", 
                    

        about.addActionListener((ActionEvent e) -> {
             String html2 = "<html><body width='%1s'><h1>Sudoku Game</h1>"
                + "<p>Solve the puzzle by placing a number from 1 to 9 in each box. "
                + "No number can appear more than once in any row, column or mini 3 by 3 square."
                + "<br><br>"
                + "<p>By Natalie Shafer";
                int w = 300;
          JOptionPane.showMessageDialog(
                    null,String.format(html2, w, w),
                                        
                   "About", 
                    JOptionPane.INFORMATION_MESSAGE);
        });

        frame.setJMenuBar(bar);
    }

   
}