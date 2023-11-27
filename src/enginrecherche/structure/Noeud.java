package enginrecherche.structure;

public class Noeud<T> {
    public T valeur;
    public Noeud<T> prochain;

    public Noeud(T valeur, Noeud<T> prochain) {
        this.valeur = valeur;
        this.prochain = prochain;
    }

    public Noeud(T valeur) {
        this.prochain = null;
        this.valeur = valeur;
    }
}
