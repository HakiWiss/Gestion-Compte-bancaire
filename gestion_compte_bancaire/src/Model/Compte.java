package Model;

public class Compte {
    protected int id;
    protected String rib;
    protected float solde;
    protected String password;
    protected int client_id;
    protected String type;
    
    public int getId() {
        return id;
    }
    public String getRib() {
        return rib;
    }
    public float getSolde() {
        return solde;
    }
    public String getPassword() {
        return password;
    }
    public int getClient_id() {
        return client_id;
    }
    public String getType() {
        return type;
    }
    public Compte(){};
    public Compte(int id, String rib, float solde, String password, int client_id, String type) {
        this.id = id;
        this.rib = rib;
        this.solde = solde;
        this.password = password;
        this.client_id = client_id;
        this.type = type;
    }

}
