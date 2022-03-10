package capitol1.vista.Vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import capitol1.main;
import capitol1.MeuError;
import capitol1.PerEsdeveniments;
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
    private JComboBox selector;
    private JProgressBar barra;
    private int n_anterior = 0;
    private double temps_anterior = 0;
    private PanellDibuix panell;

    public Vista(String s, main p) {
        super(s);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prog = p;

        //Codi per el panell de dibuix
        this.panell = new PanellDibuix(500, 500, prog.getModel(), this);

        //Codi per al panell dels botons
        JPanel bots = new JPanel();
        JLabel label = new JLabel();
        selector = new JComboBox();
        JButton boto1 = new JButton("Executar");
        JButton boto2 = new JButton("Aturar");

        bots.setBackground(Color.lightGray);
        label.setFont(new Font("Poppins", Font.BOLD, 21));
        label.setText("Quina complexitat vols executar?");
        selector.setModel(new DefaultComboBoxModel(p.getModel().opcions));
        boto1.addActionListener(this);
        boto2.addActionListener(this);

        GroupLayout botsLayout = new GroupLayout(bots);
        botsLayout.setHorizontalGroup(
                botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(botsLayout.createSequentialGroup()
                                .addGap(60)
                                .addComponent(label)
                                .addGap(10)
                                .addComponent(selector)
                                .addGap(60)
                                .addComponent(boto1)
                                .addGap(10)
                                .addComponent(boto2)
                                .addGap(60))
        );
        botsLayout.setVerticalGroup(
                botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(botsLayout.createSequentialGroup()
                                .addGap(15)
                                .addGroup(botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(label)
                                        .addComponent(selector)
                                        .addComponent(boto1)
                                        .addComponent(boto2))
                                .addGap(15))
        );
        bots.setLayout(botsLayout);

        //Codi per posar el panell de botons, el de dibuix i la barra de progres
        this.barra = new JProgressBar();
        this.barra.setStringPainted(true);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(50)
                                .addComponent(panell)
                                .addGap(50))
                        .addComponent(bots)
                        .addComponent(this.barra)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(bots)
                                .addGap(50)
                                .addComponent(panell)
                                .addGap(50)
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
        // això vol dir que s'ha pitjat el botó d'executar
        if (e.paramString().contains("Executar")) {
            prog.getModel().setOpcioTriada(this.selector.getSelectedItem().toString());
            prog.notificar("Executar");
        } else if (e.paramString().contains("Aturar")) {
            n_anterior = 0;
            temps_anterior = 0;
            prog.notificar("Aturar");
        }
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Event iter")) {
            s = s.replaceAll("Event iter ", "");
            int n = Integer.parseInt(s.split(" ")[0]);
            double temps = Double.parseDouble(s.split(" ")[1]);
            int color = Integer.parseInt(s.split(" ")[2]);
            
            // Crida a pintar linia segons n i temps
            panell.pintaGrafic(n, temps, n_anterior, temps_anterior, color);
            if (n != 16) {
                n_anterior = n;
                temps_anterior = temps;
            } else {
                n_anterior = 0;
                temps_anterior = 0;
            }
        } else if (s.startsWith("Barra")) {
            s = s.replaceAll("Barra ", "");
            int valor = Integer.parseInt(s);
            this.barra.setValue(valor);
        }
    }
}
