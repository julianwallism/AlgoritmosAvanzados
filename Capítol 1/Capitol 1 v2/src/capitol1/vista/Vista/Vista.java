package capitol1.vista.Vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import capitol1.main;
import capitol1.MeuError;
import capitol1.PerEsdeveniments;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
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
        prog = p;

        //Codi per el panell de dibuix
        this.panell = new PanellDibuix(500, 500, prog.getModel(), this);
        this.panell.setBorder(BorderFactory.createLineBorder(Color.black));
        javax.swing.GroupLayout panellLayout = new javax.swing.GroupLayout(panell);
        panell.setLayout(panellLayout);
        panellLayout.setHorizontalGroup(
                panellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 252, Short.MAX_VALUE)
        );
        panellLayout.setVerticalGroup(
                panellLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 252, Short.MAX_VALUE)
        );

        //Codi per al panell dels botons
        JPanel bots = new JPanel();
        JLabel label = new JLabel();
        selector = new JComboBox();
        JButton boto1 = new JButton("Executar");
        JButton boto2 = new JButton("Aturar");

        bots.setBackground(Color.lightGray);
        label.setText("Quina complexitat vols executar?");
        selector.setModel(new DefaultComboBoxModel(p.getModel().opcions));
        boto1.addActionListener(this);
        boto2.addActionListener(this);

        javax.swing.GroupLayout botsLayout = new javax.swing.GroupLayout(bots);
        bots.setLayout(botsLayout);
        botsLayout.setHorizontalGroup(
                botsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(botsLayout.createSequentialGroup()
                                .addGap(115, 115, 115)
                                .addComponent(label)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(selector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(boto1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(boto2)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        botsLayout.setVerticalGroup(
                botsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(botsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(botsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(label)
                                        .addComponent(selector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(boto1)
                                        .addComponent(boto2))
                                .addContainerGap(17, Short.MAX_VALUE))
        );

        //Codi per posar el panell de botons, el de dibuix i la barra de progres
        this.barra = new JProgressBar();
        this.barra.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addComponent(panell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(127, Short.MAX_VALUE))
                        .addComponent(bots, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(this.barra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(bots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                                .addComponent(panell, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(this.barra, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            System.out.println("N: " + n + ", temps: " + temps);
            
            // Crida a pintar linia segons n i temps
            panell.pintaGrafic(n, temps, n_anterior, temps_anterior, color);
            if (n != 16) {
                System.out.println("N_anterior = " + n_anterior + " n= " + n);

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
