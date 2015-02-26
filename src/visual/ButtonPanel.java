package visual;

import cons.EDir;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel z przyciskami do sterowania programem
 * Created by VanitaZ on 2015-02-25.
 */
public class ButtonPanel extends JPanel {

    private OilControl control;

    private JButton bPlay10 = new JButton("10");
    private JButton bPlay50 = new JButton("50");
    private JButton bPlay100 = new JButton("100");
    private JButton bGenerateArea = new JButton("Generate Area");

    private JButton bSetSpill = new JButton("Generate Spill");

    private JLabel lWindOn = new JLabel("Wind");

    private JComboBox<EDir> cWindDir = new JComboBox();
    private JSlider sWindPow = new JSlider();

    private JTextArea aInfo = new JTextArea();

    private JButton bTest = new JButton("Test");

    private JButton bUndo = new JButton("Undo");
    private JButton bRedo = new JButton("Redo");


    public ButtonPanel (OilControl c) {
        this.control = c;

        setLayout(new GridLayout(10,1));

        JPanel panel1 = new JPanel();
        panel1.add(bPlay10);
        bPlay10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.nextIterations(10);
            }
        });
        panel1.add(bPlay50);
        bPlay50.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.nextIterations(50);
            }
        });
        panel1.add(bPlay100);
        bPlay100.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.nextIterations(100);
            }
        });
        this.add(panel1);

        JPanel panel2 = new JPanel();
        panel2.add(bGenerateArea);
        bGenerateArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.generateArea();
            }
        });
        this.add(panel2);

        JPanel panel3 = new JPanel();
        panel3.add(bSetSpill);
        bSetSpill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.setSpillAtSelected();
            }
        });
        this.add(panel3);

        JPanel panel4 = new JPanel();
        panel4.add(lWindOn);
        panel4.add(cWindDir);
        makeWindDirList();
        cWindDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.setWind((EDir)cWindDir.getSelectedItem());
            }
        });
        this.add(panel4);

        JPanel panel5 = new JPanel();
        panel5.add(sWindPow);
        sWindPow.setMinimum(0);
        sWindPow.setMaximum(100);
        sWindPow.setValue(0);
        sWindPow.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                control.setWind((float) (sWindPow.getValue() / 50));
            }
        });
        this.add(panel5);

        JPanel panel6 = new JPanel();
        this.add(panel6);
        panel6.add(bUndo);
        bUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.undo();
            }
        });
        panel6.add(bRedo);
        bRedo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.redo();
            }
        });

        JPanel panel7 = new JPanel();
        this.add(panel7);

        JPanel panel8 = new JPanel();
        this.add(panel8);
        panel8.add(aInfo);
        aInfo.setEditable(false);

        JPanel panel9 = new JPanel();
        this.add(panel9);
        panel9.add(bTest);
        bTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                control.test();
            }
        });
    }

    private void makeWindDirList () {
        for (int i = 0; i < 8; i++) {
            cWindDir.addItem(EDir.values()[i]);
        }
    }

    public void setInfo (String s) {
        aInfo.setText(s);
    }

}
