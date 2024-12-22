package Model;

public class Courant extends Compte{
    private float decouverAutorise;

    public float getdecouverAutorise() {
        return decouverAutorise;
    }

    public Courant() {
    }
    public Courant(int id, String rib, float solde, String password, int client_id, float decouverAutorise) {
        super(id, rib, solde, password, client_id, "crt");
        this.decouverAutorise = decouverAutorise;
    } 
}
