package Model;

public class Admin {
    private int identifiant;
    private String password;
    private String bank_name;
    public int getIdentifiant() {
        return identifiant;
    }
    public String getPassword() {
        return password;
    }
    public String getBank_name() {
        return bank_name;
    }
    public Admin(){};
    
    public Admin(int Identifiant,String Password,String bank_name){
        this.identifiant=Identifiant;
        this.password=Password;
        this.bank_name=bank_name;
    }
    
    
}
