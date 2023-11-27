package enginrecherche;
import enginrecherche.structure.Liste;
import java.io.*;
import javax.swing.*;

public class Fichier {
    // Cette methode permet de sélectionner un fichier ou un repertoire et retourne son chemin
    public static String choixFichierRepertoire() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        int val = fc.showOpenDialog(null);

        if (val == 0) {
            File f = fc.getSelectedFile();
            return f.getPath();
        }
        return null;
    }

    // Cette methode prend comme paramètre un fichier et retourne le contenu de ce fichier
    // sous forme de tableau de mots
    public static String[] lireMotFichier(File fc) throws IOException {
        Liste<String> texte = new Liste<>();
        int longueurListe = 0;

        BufferedReader r = new BufferedReader(new FileReader(fc));
        String line;

        while((line = r.readLine()) != null) {
            String[] txtSplit = line.split("[-/(){}\\#%!?;:\"\'<>';,.\\n\\r\\s]");

            texte.ajouterTableau(txtSplit);
            longueurListe += txtSplit.length;
        }

        r.close();
        String[] tab = new String[longueurListe];
        texte.enTableau(tab); // Copie les éléments de la liste texte dans le tableau

        return tab;
    }
}
