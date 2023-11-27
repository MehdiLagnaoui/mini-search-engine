package enginrecherche.gui;
import enginrecherche.Fichier;
import enginrecherche.Engin;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;


public class Fenetre extends JFrame implements ActionListener {

    private JButton indexer;
    private JButton inverser;
    private JButton recherche;

    private JButton afficher;

    private JTextArea resultatRecherche;

    private JPanel menu;

    private Engin engin;

    public Fenetre(){
        //
        // Initialization des components de la fenêtre
        //
        this.menu = new JPanel(); // Le menu des boutons
        this.indexer = new JButton("Indexer");
        this.inverser = new JButton("Inverser");
        this.recherche = new JButton("Recherche");
        this.afficher = new JButton("Afficher");

        this.resultatRecherche = new JTextArea();

        //
        // Initialization des instances dépendantes
        //
        this.engin = new Engin();

        //
        // Définition du style de la fenêtre
        //
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(10, 10));
        this.setSize(1000,1000);

        menu.setBackground(Color.lightGray);
        menu.setSize(1000, 50 );
        menu.setLayout(new FlowLayout());
        resultatRecherche.setEditable(false);

        //
        // Ajout des boutons au menu
        //
        menu.add(indexer);
        menu.add(inverser);
        menu.add(recherche);
        menu.add(afficher);

        // Les boutons inverser, recherche et afficher sont désactivés par défaut
        // on les active apres avoir chargé des docs dans notre structure index
        recherche.setEnabled(false);
        inverser.setEnabled(false);
        afficher.setEnabled(false);

        //
        // Ajout des components à la fenêtre
        //
        this.add(menu, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(resultatRecherche);
        scrollPane.setBounds(10,60,780,500);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane, BorderLayout.CENTER);

        this.indexer.addActionListener(this);
        this.inverser.addActionListener(this);
        this.recherche.addActionListener(new RechercheButtonListener(engin, resultatRecherche));
        this.afficher.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == this.indexer) { // Charger fichier ou repertoire quand on clique sur bouton indexer
            String path = Fichier.choixFichierRepertoire();
            if (path == null) return; // Sélection annulée

            try {
                engin.indexer(path);
                if (engin.index.premier != null) {
                    this.inverser.setEnabled(true);
                }

            } catch (IOException err) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la lecture");
            }

        } else if (source == this.inverser) { // Créer la structure indexInverse en inversant la structure index
            this.engin.indexerInv();

            // Activer les boutons recherche et afficher
            this.recherche.setEnabled(true);
            this.afficher.setEnabled(true);

        } else if (source == this.afficher) { // Affiche le contenu de nos structures
            this.engin.afficher();
            this.resultatRecherche.setText(engin.texte);
        }
    }
}
