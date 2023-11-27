package enginrecherche.structure;

public class Liste<T> {
    public Noeud<T> premier;
    public String nom = "";

    public Liste() {
        this.premier = null;
    }

    public Liste(Noeud<T> premier) {
        this.premier = premier;
    }

    public Liste(Noeud<T> premier, String nom) {
        this(premier);
        this.nom = nom;
    }

    // Ajoute un élement à la fin de la liste
    public void ajouter(T element) {
        premier = ajouter(premier, element);
    }

    private Noeud<T> ajouter(Noeud<T> n, T element) {
        if (n == null) {
            return new Noeud<>(element, null);
        } else {
            n.prochain = ajouter(n.prochain, element);
            return n;
        }
    }
  
    // Cette fonction concatène une liste à la liste actuelle
    public void ajouterListe(Liste<T> list) {ajouterListe(premier, list);
    }

    private void ajouterListe(Noeud<T> n, Liste<T> list) {
        if (n == null) { // Cas le noeud (Liste) est vide
            premier = list.premier;
        } else if (n.prochain == null) { // Cas: le n actuel est le dernier noeud de notre liste
            n.prochain = list.premier;
        } else { // Cas: le n actuel n'est pas la fin de la liste
            ajouterListe(n.prochain, list);
        }
    }

    // Cette methode ajoute le tableau à la fin de la liste
    public void ajouterTableau(T[] tabElement) {
        for (T elem : tabElement) {
            this.ajouter(elem);
        }
    }

    // Cette méthode prend comme paramètre un tableau avec une longueur >= de notre liste
    // et ajoute les éléments de notre liste dans le tableau
    public void  enTableau(T[] tab) {
        Noeud<T> n = premier;

        int i = 0;
        while (n != null) {
            tab[i] = n.valeur;
            i++;
            n = n.prochain;
        }
    }

    // Cette methode cherche le noeud qui contient l'élement T et le retourne. Si elle ne trouve pas l'élement
    // elle retourne null
    public Noeud<T> trouver(T elem) {
        Noeud<T> n = premier;

        while (n != null) {
            if (n.valeur.equals(elem)) {
                return n;
            }
            n = n.prochain;
        }

        return null;
    }

    // Cette methode enlève le nœud qui contient la valeur elem
    public void enlever(T elem) {
        Noeud<T> n = premier;

        if (premier == null) return;

        if (premier.valeur.equals(elem)) {
            premier = premier.prochain;
            return;
        }

        while (n.prochain != null && !n.prochain.valeur.equals(elem)) n = n.prochain;
        if (n.prochain != null) n.prochain = n.prochain.prochain;
    }
}
