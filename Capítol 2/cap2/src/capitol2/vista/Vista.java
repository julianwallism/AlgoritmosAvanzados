package capitol2.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import capitol2.main;
import capitol2.MeuError;
import capitol2.PerEsdeveniments;
import java.awt.Color;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Vista extends JFrame implements ActionListener, PerEsdeveniments {
    private main prog;
    private JComboBox selector, selector2;
    private JProgressBar barra;
    private PanellDibuix panell;

    public Vista(String s, main p) {
        super(s);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prog = p;

        //Codi per el panell de dibuix
        this.panell = new PanellDibuix(500, 500, prog.getModel(), this);

        //Codi per al panell dels botons
        JPanel panellSuperior = new JPanel();
        JLabel titol = new JLabel();
        JLabel label = new JLabel();
        JLabel label2 = new JLabel();
        selector = new JComboBox();
        selector2 = new JComboBox();

        panellSuperior.setBackground(Color.lightGray);
        titol.setFont(new Font("Times New Roman", Font.BOLD, 32));
        label.setFont(new Font("Poppins", Font.PLAIN, 18));
        label2.setFont(new Font("Poppins", Font.PLAIN, 18));
        titol.setText("Recorregut Eulerìà");
        label.setText("Tamany tauler");
        label2.setText("Triar peça");
        selector.setModel(new DefaultComboBoxModel(p.getModel().tamanysTauler));
        selector.setSelectedIndex(2);
        selector2.setModel(new DefaultComboBoxModel(p.getModel().peces));

        GroupLayout botsLayout = new GroupLayout(panellSuperior);
        botsLayout.setHorizontalGroup(
                botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(botsLayout.createSequentialGroup()
                                .addGap(60)
                                .addComponent(titol)
                                .addGap(60)
                                .addComponent(label)
                                .addGap(10)
                                .addComponent(selector)
                                .addGap(30)
                                .addComponent(label2)
                                .addGap(10)
                                .addComponent(selector2)
                                .addGap(30))
        );
        botsLayout.setVerticalGroup(
                botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(botsLayout.createSequentialGroup()
                                .addGap(15)
                                .addGroup(botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(titol)
                                        .addComponent(label)
                                        .addComponent(selector)
                                        .addComponent(label2)
                                        .addComponent(selector2))
                                .addGap(15))
        );
        panellSuperior.setLayout(botsLayout);

        //Codi per posar el panell de botons, el de dibuix i la barra de progres
        this.barra = new JProgressBar();
        this.barra.setStringPainted(true);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panell))
                        .addComponent(panellSuperior)
                        .addComponent(this.barra)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(panellSuperior)
                                .addComponent(panell)
                                .addComponent(this.barra))
        );
        getContentPane().setLayout(layout);
    }

    public void mostrar() {
        this.pack();
        this.setVisible(true);
        try {
            Thread.sleep(500);
        } catch (Exception e) {
            MeuError.informaError(e);
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    @Override
    public void notificar(String s) {
        
    }
}
