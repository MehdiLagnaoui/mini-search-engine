// Ce programme forme un simple engin de recherche d'information. Il consiste à indexer un ensemble
// de documents à partir desquels on crée une structure d'index qui stocke chaque document avec les
// mots qu'il contient ainsi que leurs fréquences dans une liste chainée et après ce programme nous
// permet de créer une structure d'index inverse qui stocke chaque mot associé aux documents qui le
// contiennent avec la fréquence associé. Puis on peut utiliser cette structure inverse pour lancer une
// recherche (requete) qui produit comme resultat une liste contenant les document conforment à cette
// requete dans un ordre décroissant selon la fréquence globale.
//

package enginrecherche;
import enginrecherche.gui.Fenetre;

public class Main {
    public static void main(String[] args) {
        Fenetre f = new Fenetre();
        f.setVisible(true);
    }
}
