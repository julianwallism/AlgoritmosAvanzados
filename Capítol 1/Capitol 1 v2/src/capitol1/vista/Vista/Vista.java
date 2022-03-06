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

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Vista extends JFrame implements ActionListener, PerEsdeveniments, ChangeListener {

    private main prog;
    private JComboBox selector;

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
        bots.add(boto1);
        this.add(BorderLayout.NORTH, bots);
        PanellDibuix panell = new PanellDibuix(800, 400, prog.getModel(), this);
        this.add(BorderLayout.CENTER, panell);
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
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {

    }
}
