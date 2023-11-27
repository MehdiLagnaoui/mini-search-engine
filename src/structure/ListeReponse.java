package enginrecherche.structure;

public class ListeReponse extends Liste<DocFreq> {
    public ListeReponse() {
        super();
    }
    public ListeReponse(Liste<DocFreq> d) {
        super(d.premier, d.nom);
    }

    // Cette methode fait un tris en ordre décroissant (Quicksort) de la ListeRreponse
    // basé sur la fréquence globale
    public void enOrdre() {
        if (this.premier == null) return;
        if (premier.prochain == null) return;

        DocFreq pivot = premier.valeur;
        enlever(premier.valeur);

        ListeReponse l1 = new ListeReponse();
        ListeReponse l2 = new ListeReponse();

        separer(pivot, l1, l2);

        l1.enOrdre();
        l2.enOrdre();

        premier = l1.premier;
        this.ajouter(pivot);

        // concat
        ajouterListe(l2);
    }

    private void separer(DocFreq pivot, ListeReponse l1, ListeReponse l2) {
        Noeud<DocFreq> n = premier;
        while (n != null) {
            if (n.valeur.frequence >= pivot.frequence) {
                l1.ajouter(n.valeur);
            } else {
                l2.ajouter(n.valeur);
            }

            n = n.prochain;
        }
    }
}
