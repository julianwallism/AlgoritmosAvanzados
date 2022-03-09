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
    private long temps_anterior = 1;
    private PanellDibuix panell;

    public Vista(String s, main p) {
        super(s);
        prog = p;
        this.getContentPane().setLayout(new BorderLayout());
        JPanel bots = new JPanel();
        bots.setBackground(Color.lightGray);
        JLabel label = new JLabel();
        label.setText("Quina complexitat vols executar?");
        bots.add(label);
        selector = new JComboBox();
        selector.setModel(new DefaultComboBoxModel(p.getModel().opcions));
        bots.add(selector);
        JButton boto1 = new JButton("Executar");
        boto1.addActionListener(this);
        JButton boto2 = new JButton("Aturar");
        boto2.addActionListener(this);
        bots.add(boto1);
        bots.add(boto2);
        this.add(BorderLayout.NORTH, bots);
        this.panell = new PanellDibuix(500, 500, prog.getModel(), this);
        this.add(BorderLayout.CENTER, panell);
        this.barra = new JProgressBar();
        this.barra.setStringPainted(true);
        this.add(BorderLayout.SOUTH, this.barra);
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
            long temps = Long.parseLong(s.split(" ")[1]);
            System.out.println("N: " + n + ", temps: " + temps);
            // Crida a pintar linia segons n i temps
            panell.pintaGrafic(n, temps, n_anterior, temps_anterior);
            if (n != 400) {
                n_anterior = n;
                temps_anterior = temps;
            } else {
                n_anterior = 0;
                temps_anterior = 1;
            }
        } else if (s.startsWith("Barra")) {
            s = s.replaceAll("Barra ", "");
            int valor = Integer.parseInt(s);
            this.barra.setValue(valor);
        }
    }
}
