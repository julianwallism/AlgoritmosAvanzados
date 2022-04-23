package capitol3.vista;

import capitol3.PerEsdeveniments;
import capitol3.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vista extends JFrame implements PerEsdeveniments {

    private final PanellCentral panellCentral;
    private PanellGrafic panellGrafic;
    private final JPanel panellSuperior, barraInputs, barraBotons;
    private final JComboBox selector;
    private final JButton executar, aturar, buidar, estudi;
    private final JTextField num1, num2, umbral;
    private final JProgressBar panellInferior;
    private main prog;

    public Vista(String titol, main p) {
        this.prog = p;
        this.setTitle(titol);
        this.setIconImage(new ImageIcon("logo.png").getImage());

        this.panellSuperior = new JPanel();
        this.panellSuperior.setBackground(new Color(102, 178, 255));
        this.barraBotons = new JPanel();
        this.barraBotons.setBackground(new Color(102, 178, 255));
        this.barraInputs = new JPanel();
        this.barraInputs.setBackground(new Color(102, 178, 255));
        this.panellCentral = new PanellCentral(p, this.getWidth(), this.getHeight());
        this.panellInferior = new JProgressBar();

        // Creació dels components del panell superior
        this.num1 = new JTextField();
        this.num1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    prog.notificar("Nombre 1: " + num1.getText());
            }
        });
        this.num1.setToolTipText("Primer nombre a multiplicar");
        this.num1.setColumns(25);

        this.num2 = new JTextField();
        this.num2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Nombre 2: " + num2.getText());
            }
        });
        this.num2.setToolTipText("Segon nombre a multiplicar");
        this.num2.setColumns(25);

        this.selector = new JComboBox();
        this.selector.setBackground(Color.white);
        this.selector.setModel(new DefaultComboBoxModel(this.prog.getModel().getAlgorismes()));
        this.selector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prog.notificar("Algorisme: " + selector.getSelectedItem());
            }
        });

        this.executar = new JButton("Executar");
        this.executar.setBackground(Color.white);
        this.executar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!num1.getText().isEmpty() && !num2.getText().isEmpty()) {
                    panellInferior.setIndeterminate(true);
                    prog.notificar("Executar");
                } else {
                    System.out.println(num1.getText().isEmpty());
                    System.out.println(num2.getText().isEmpty());
                    JOptionPane.showMessageDialog(Vista.this,
                            "Uns dels nombres està buid", "Executar", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        this.aturar = new JButton("Aturar");
        this.aturar.setBackground(Color.white);
        this.aturar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panellInferior.setIndeterminate(false);
                prog.notificar("Aturar");
            }
        });

        this.buidar = new JButton("Buidar");
        this.buidar.setBackground(Color.white);
        this.buidar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panellCentral.buidar();
            }
        });

        this.estudi = new JButton("Estudi");
        this.estudi.setBackground(Color.white);
        this.estudi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Vista.this,
                        "Després d'acceptar aquest missatge es començarà a fer l'estudi, "
                        + "finalment, es mostrarà un gràfic amb els resultats, "
                        + "sigui pacient.", "Estudi", JOptionPane.INFORMATION_MESSAGE);
                prog.notificar("Estudi");
            }
        });

        this.umbral = new JTextField(Integer.toString(prog.getModel().getUmbral()));
        this.umbral.setToolTipText("Umbral");
        this.umbral.setColumns(3);
        this.umbral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(umbral.getText()) > 700) {
                    JOptionPane.showMessageDialog(Vista.this,
                            "L'umbral introduït és massa alt, per favor, torna a posar un més baix. L'umbral màxim és de 700 xifres.", "Umbral massa alt", JOptionPane.ERROR_MESSAGE);
                    umbral.setText(prog.getModel().getUmbral() + "");
                } else {
                    prog.notificar("Umbral: " + umbral.getText());
                }
            }
        });

        barraInputs.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lab_num1 = new JLabel("Número 1 ");
        lab_num1.setForeground(Color.white);
        barraInputs.add(lab_num1);
        barraInputs.add(this.num1);

        JLabel lab_num2 = new JLabel("Número 2 ");
        lab_num2.setForeground(Color.white);
        barraInputs.add(lab_num2);
        barraInputs.add(this.num2);

        JLabel lab_umb = new JLabel("Umbral");
        lab_umb.setForeground(Color.white);
        barraInputs.add(lab_umb);
        barraInputs.add(umbral);

        barraBotons.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel lab_algorisme = new JLabel("Algorisme ");
        lab_algorisme.setForeground(Color.white);

        barraBotons.add(lab_algorisme);
        barraBotons.add(this.selector);
        barraBotons.add(this.executar);
        barraBotons.add(this.aturar);
        barraBotons.add(this.buidar);
        barraBotons.add(this.estudi);

        this.panellSuperior.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
        this.panellSuperior.add(this.barraInputs);
        this.panellSuperior.add(this.barraBotons);

        this.add(panellSuperior, BorderLayout.NORTH);
        this.add(panellCentral, BorderLayout.CENTER);
        this.add(panellInferior, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(1600, 800));
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void creaGrafic() {
        JDialog jd = new JDialog(this, "Estudi", false);
        jd.setTitle("Gràfic");
        jd.setSize(new Dimension(1400, 800));
        jd.setResizable(false);
        jd.setLocationRelativeTo(null);
        jd.setLayout(new BorderLayout());

        panellGrafic = new PanellGrafic(1400, 800, this.prog.getModel());
        jd.add(panellGrafic);
        jd.setVisible(true);
        this.umbral.setText(Integer.toString(prog.getModel().getUmbral()));
        // panellGrafic.creaGrafic(dades);
    }

    @Override
    public void notificar(String s) {
        if (s.startsWith("Resultat")) {
            panellInferior.setIndeterminate(false);
            this.panellCentral.notificar(s);
        } else if (s.startsWith("Fet")) {
            creaGrafic();
        }
    }
}
