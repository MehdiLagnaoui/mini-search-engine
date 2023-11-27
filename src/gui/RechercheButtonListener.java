package enginrecherche.gui;
import enginrecherche.Engin;
import enginrecherche.structure.DocFreq;
import enginrecherche.structure.Liste;
import enginrecherche.structure.Noeud;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RechercheButtonListener implements ActionListener {
    private Engin engin;
    private JTextArea resultatRecherche;

    public RechercheButtonListener(Engin engin, JTextArea resultatRecherche) {
        this.engin = engin;
        this.resultatRecherche = resultatRecherche;
    }
    public void actionPerformed(ActionEvent e) {
        // Affiche popup input
        String requete = JOptionPane.showInputDialog("Lancer une requête");
        if (requete == null) {
            return;
        }

        Liste<DocFreq> recherche = this.engin.rechercher(requete);

        // Si aucun des mots n'est trouvé
        if (recherche.premier == null){
            this.resultatRecherche.setText("Aucun résultat");
            return;
        }

        Noeud<DocFreq> n = recherche.premier;
        String resultat = "";
        while (n != null) {
            resultat += "Occurence dans: "+ n.valeur.doc + " avec une fréquence globale de " + n.valeur.frequence + "\n";
            n = n.prochain;
        }

        this.resultatRecherche.setText(resultat);
    }
}
