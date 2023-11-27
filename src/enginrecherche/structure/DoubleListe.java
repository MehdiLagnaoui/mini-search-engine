package enginrecherche.structure;

public class DoubleListe<T> extends Liste<Liste<T>>{
    // Cette methode cherche si un nom (mot ou nom du document) correspond à une liste et si elle le trouve, elle
    // retourne cette liste sinon elle retourne null
    public Liste<T> trouverNomListe(String nom) {
        Noeud<Liste<T>> n = premier;

        while (n != null) {
            if (n.valeur.nom.equals(nom)) {
                return n.valeur;
            }
            n = n.prochain;
        }
        return null;
    }

    // Cette méthode ajoute la liste element dans la DoubleListe selon l'ordre alphabétique
    // des listes dans DoubleListe
    public void ajouterOrdreNom(Liste<T> element){
        String nom = element.nom;
        if (this.premier == null || nom.compareToIgnoreCase(this.premier.valeur.nom) < 0){
            this.premier = new Noeud<>(element, this.premier);
            return;
        }

        Noeud<Liste<T>> n = this.premier;
        while (n.prochain != null && nom.compareToIgnoreCase(n.prochain.valeur.nom) > 0) {
            n = n.prochain;
        }

        n.prochain = new Noeud<>(element, n.prochain);
    }
}
