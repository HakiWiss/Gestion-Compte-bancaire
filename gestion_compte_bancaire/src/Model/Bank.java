package Model;

public class Bank {
    private String name;
    private String adress;

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Bank() {
    }

    public Bank(String Name, String Adress) {
        this.name = Name;
        this.adress = Adress;
    }

}
