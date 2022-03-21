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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @authors Dawid Roch & Julià Wallis
 */
public class Vista extends JFrame implements ActionListener, PerEsdeveniments {
    private main prog;
    private JButton selector, selector2;
    private JDialog dialog;
    private JProgressBar barra;
    private PanellDibuix panell;
    private boolean isTamanyDialog = false;
    private JLabel textDialog = new JLabel();
    private JComboBox comboBoxDialog = new JComboBox();

    public Vista(String s, main p) {
        super(s);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prog = p;

        //Codi per el panell de dibuix
        this.panell = new PanellDibuix(800, 620, prog.getModel(), this);

        //Codi per al panell dels botons
        JPanel panellSuperior = new JPanel();
        panellSuperior.setSize(800, 100);
        JLabel titol = new JLabel();
        selector = new JButton();
        selector.setText("Canviar tamany tauler");
        selector.addActionListener(this);
        selector2 = new JButton();
        selector2.setText("Triar peça");
        selector2.addActionListener(this);

        panellSuperior.setBackground(Color.lightGray);
        titol.setFont(new Font("Times New Roman", Font.BOLD, 32));
        titol.setText("Recorregut Eulerià");

        GroupLayout botsLayout = new GroupLayout(panellSuperior);
        botsLayout.setHorizontalGroup(
                botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(botsLayout.createSequentialGroup()
                                .addGap(100)
                                .addComponent(titol)
                                .addGap(80)
                                .addComponent(selector)
                                .addGap(20)
                                .addComponent(selector2)
                                .addGap(100))
        );
        botsLayout.setVerticalGroup(
                botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addGroup(botsLayout.createSequentialGroup()
                                .addGap(15)
                                .addGroup(botsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(titol)
                                        .addComponent(selector)
                                        .addComponent(selector2))
                                .addGap(15))
        );
        panellSuperior.setLayout(botsLayout);

        //Codi per posar el panell de botons, el de dibuix i la barra de progres
        this.barra = new JProgressBar();
        this.barra.setSize(800, 50);
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
        if (e.getActionCommand().equals("Canviar tamany tauler")) {
            isTamanyDialog = true;
            this.dialog = new JDialog(this, "Canviar tamany tauler");
            textDialog.setText("Tamany desitjat: ");
            comboBoxDialog.setModel(new DefaultComboBoxModel(this.prog.getModel().tamanysTauler));
            comboBoxDialog.setSelectedItem(this.prog.getModel().getTamanyTriat());
            comboBoxDialog.addActionListener(this);
            this.dialog.add(textDialog);
            this.dialog.add(comboBoxDialog);
            this.dialog.setSize(300,300);
            this.dialog.setVisible(true);
        } else if (e.getActionCommand().equals("Triar peça")) {
            this.dialog = new JDialog(this, "Triar peça");
            textDialog.setText("Peça desitjada: ");
            comboBoxDialog.setModel(new DefaultComboBoxModel(this.prog.getModel().getPeces()));
            comboBoxDialog.setSelectedItem(this.prog.getModel().getPeçaTriada());
            comboBoxDialog.addActionListener(this);
            this.dialog.add(textDialog);
            this.dialog.add(comboBoxDialog);
            this.dialog.setSize(300,300);
            this.dialog.setVisible(true);
        } else if (e.getActionCommand().equals("comboBoxChanged")) {
            if (isTamanyDialog) {
                this.prog.getModel().setTamanyTriat(Integer.parseInt(comboBoxDialog.getSelectedItem().toString()));
                this.prog.notificar("Tamany canviat");
            } else {
                this.prog.getModel().setPeçaTriada(comboBoxDialog.getSelectedItem().toString());
            }
        }
    }

    @Override
    public void notificar(String s) {
        
    }
}
