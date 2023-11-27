package enginrecherche;
import enginrecherche.structure.*;
import java.io.File;
import java.io.IOException;

public class Engin {
    // Ce String texte comporte les structures index et indexInverse comme une chaine de caractere pour les
    // afficher dans l'écran
    public String texte;

    public DoubleListe<MotFreq> index = new DoubleListe<>();

    DoubleListe<DocFreq> indexInv;

    // Cette méthode met à jour la structure index en ajoutant le(s) document(s) de source dans l'index.
    // Source est un path
    public void indexer(String source) throws IOException {
        File f = new File(source);

        if (f.isDirectory()) {
            indexRepertoire(f);
        } else {
            indexFichier(f);
        }
    }

    // Cette méthode prend comme paramètre un fichier et fait l'indexation du fichier en l'ajoutant
    // dans la structure index
    private void indexFichier(File fichier) throws IOException {
        try {
            String[] motSeparerFichier = Fichier.lireMotFichier(fichier);

            // Création de la liste contenant les mots et leurs fréquences dans le document
            Liste<MotFreq> idx = eliminerRep(trier(motSeparerFichier));
            idx.nom = fichier.getName();

            index.ajouterOrdreNom(idx);
        } catch (IOException err) {
            throw err;
        }
    }

    // Cette méthode prend comme paramètre un File qui est un répertoire
    // et index tous les fichiers présents dans le répertoire
    private void indexRepertoire(File f) throws IOException {
        File[] repertoire = f.listFiles();

        for (File file : repertoire) {
            if (file.isFile()) { // TODO: Ecq on doit indexer les répertoire?
                this.indexFichier(file);
            }
        }
    }

    // Cette méthode crée la structure indexInverse à partir de la première structure d’index (la conversion).
    public void indexerInv() {
        indexInv = new DoubleListe<>();
        Noeud<Liste<MotFreq>> doc = index.premier; // Document actuel

        while (doc != null){ // Pour chaque document, on va parcourir ses mots
            Noeud<MotFreq> motFreq = doc.valeur.premier; // Mot dans le document
            while(motFreq != null){
                String nomDocument = doc.valeur.nom;
                String mot = motFreq.valeur.mot;
                int freq = motFreq.valeur.frequence;

                DocFreq nouveauNoeud = new DocFreq(nomDocument, freq); // Noeud a ajouté

                // On cherche si le mot correspond à une liste dans index inversé
                Liste<DocFreq> listeDansInverse = indexInv.trouverNomListe(mot);

                if (listeDansInverse == null) { // Mot pas dans index inversé
                    Liste<DocFreq> nouvelleListe = new Liste<>(new Noeud<>(nouveauNoeud), mot);
                    indexInv.ajouterOrdreNom(nouvelleListe);
                } else {
                    listeDansInverse.ajouter(nouveauNoeud);
                }
                motFreq = motFreq.prochain;
            }
            doc = doc.prochain;
        }
    }

    // Cette methode tris MergeSort prend comme paramètre un tableau de String le trie puis elle retourne un autre
    // tableau de String trié dans l'ordre croissant (alphabétiquement)
    private String[] trier(String[] tab){
        if (tab.length <= 1) return tab;

        String[] first = new String[tab.length / 2];
        String[] second = new String[tab.length - first.length];

        System.arraycopy(tab, 0, first, 0, first.length);
        System.arraycopy(tab, first.length, second, 0, second.length);

        trier(first);
        trier(second);

        return merge(trier(first), trier(second));
    }

    // Cette méthode prend deux tableaux et les concat ensemble
    public String[] merge(String[] array1, String[] array2) {
        String [] result = new String[array1.length+array2.length];

        int i=0, j=0, k=0;
        // copier à chaque boucle celui qui est plus petit
        while (i<array1.length && j<array2.length) {
            if (array1[i].compareToIgnoreCase(array2[j]) < 0) {
                result[k]=array1[i];
                i++; }
            else {
                result[k]=array2[j];
                j++; }
            k++; }

        // copier ce qui reste dans array1 ou array2
        if (i<array1.length) System.arraycopy(array1,i,result,k,array1.length-i);
        if (j<array2.length) System.arraycopy(array2,j,result,k,array2.length-j);

        return result;
    }

    // Cette méthode prend comme paramètre un tableau de String trié et retourne une liste dont chaque nœud
    // contient le mot et sa fréquence de repetition dans le tableau ce qui permet d'éliminer les repétitions.
    private Liste<MotFreq> eliminerRep(String[] tab){
        Liste<MotFreq> motsFrequences = new Liste<>();

        // tab[i] = mot qu'on veut trouver le nombre de fréquences
        int i =0;
        while(i < tab.length) {
            // on élimine les textes vides
            if (tab[i].equals("")){
                i++;
                continue;
            }

            // Si on est arrivé à la fin du tab
            if (i == tab.length -1) {
                motsFrequences.ajouter(new MotFreq(tab[i], 1));
                break;
            }

            int freq = 1;
            // On trouve la fréquence de tab[i] en comptant le nombre de mots tel que tab[i] == tab[j]
            for (int j = i+1; j < tab.length; j++) {
                if (tab[i].equals(tab[j])) {
                    freq ++;

                    // Arriver à la fin du tab
                    if (j == tab.length-1){
                        motsFrequences.ajouter(new MotFreq(tab[i], freq));
                        return motsFrequences;
                    }
                } else { // tab[i] != tab[j] donc on a finis de compter la fréquence de tab[i]
                    motsFrequences.ajouter(new MotFreq(tab[i], freq));
                    i = j;
                    break;
                }
            }
        }
        return motsFrequences;
    }

    // Cette methode prend comme paramètre un String qui est une requête de recherche et compare les mots dans
    // cette requête avec les mots stockés dans indexInv puis retourne une listeReponse qui contient les
    // documents conforment à cette recherche et ceci dans un ordre décroissant selon la fréquence.
    public ListeReponse rechercher(String requete) {
        String[] mots = requete.split("[-/(){}\\#%!?;:\"\'<>';,.\\n\\r\\s]");

        if (mots.length == 0) {
            return null;
        }

        // Notre liste de réponse est initialisé avec le premier mot de la requête
        ListeReponse reponses = new ListeReponse();
        Liste<DocFreq> listeMot = indexInv.trouverNomListe(mots[0]);

        if (listeMot != null) {
            reponses = new ListeReponse(listeMot);
        }

        // Liste de réponses
        for (int i = 1; i < mots.length; i++) {
            Liste<DocFreq> listeDocFreq = indexInv.trouverNomListe(mots[i]);
            if (listeDocFreq == null) continue; // Mot trouvé dans aucune liste

            Noeud<DocFreq> n = listeDocFreq.premier;
            while (n != null) {
                Noeud<DocFreq> docFreqDansReponses = reponses.trouver(n.valeur);

                if (docFreqDansReponses == null) { // Cas: Le mot actuel se trouve pas dans la liste de réponse
                    reponses.ajouter(n.valeur);
                } else { // Cas: Le mot actuel se trouve dans la liste
                    docFreqDansReponses.valeur.frequence += n.valeur.frequence;
                }
                n = n.prochain;
            }
        }

        reponses.enOrdre();

        return reponses;
    }

    // Cette methode joue à peu près le role de toString() elle permet de stocker les structures index et indexInverse
    // dans un texte et elle permet d'afficher le contenu de ces structures qu'on on a besoin.
    public void afficher(){

        texte = "Structure Index: \n";
        Noeud<Liste<MotFreq>> noeudDoc = this.index.premier;

        while(noeudDoc != null){
            texte += noeudDoc.valeur.nom + ":  ";
            Noeud<MotFreq> noeudMot = noeudDoc.valeur.premier;

            while (noeudMot != null){
                char separateur = noeudMot.prochain == null ? ' ' : '/';
                texte += noeudMot.valeur.mot + '(' + noeudMot.valeur.frequence + ')' + separateur ;
                noeudMot = noeudMot.prochain;
            }
            noeudDoc = noeudDoc.prochain;
            texte += "\n";
        }
        texte += "\n\nStructure IndexInverse: \n";

        Noeud<Liste<DocFreq>> nMot = this.indexInv.premier;

        while(nMot != null){
            texte += nMot.valeur.nom + ":  ";
            Noeud<DocFreq> nDoc = nMot.valeur.premier;

            while (nDoc != null){
                char separateur = nDoc.prochain == null ? ' ' : '/';

                texte += nDoc.valeur.doc + '(' + nDoc.valeur.frequence + ')' + separateur ;
                nDoc = nDoc.prochain;
            }
            nMot = nMot.prochain;
            texte += "\n";
        }
    }
}

