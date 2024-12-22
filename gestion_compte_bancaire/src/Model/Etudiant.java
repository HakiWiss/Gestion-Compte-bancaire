package Model;

public class Etudiant extends Compte {
    private float platfondRetrait;

    public float getplatfondRetrait() {
        return platfondRetrait;
    }

    public Etudiant() {
    }
    public Etudiant(int id, String rib, float solde, String password, int client_id, float platfondRetrait) {
        super(id, rib, solde, password, client_id, "etd");
        this.platfondRetrait = platfondRetrait;
    }
}
