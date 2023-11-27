package enginrecherche.structure;

public class DocFreq {
    public int frequence; // Fréquence du mot
    public String doc; // Nom du fichier

    public DocFreq(String doc,  int frequence) {
        this.frequence = frequence;
        this.doc = doc;
    }

    // Compare si le nom du document est pareil
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DocFreq)) {
            return false;
        }

        DocFreq elem = (DocFreq) obj;

        return elem.doc.equals(doc);
    }
}
