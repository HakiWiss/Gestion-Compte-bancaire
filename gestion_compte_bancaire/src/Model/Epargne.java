package Model;

public class Epargne extends Compte {
    private float tauxInteret;

    public float getTauxInteret() {
        return tauxInteret;
    }

    public Epargne() {
    }
    public Epargne(int id, String rib, float solde, String password, int client_id, float tauxInteret) {
        super(id, rib, solde, password, client_id, "epn");
        this.tauxInteret = tauxInteret;
    }
}
