package Model;

import java.sql.Date;

public class Client {
    private int id;
    private String nom;
    private String prenom;
    private String adress;
    private Date dateNaissance;
    private String email;
    private String tel;
    private String genre;
    private String bank_name;
    
    public Client(int id, String nom, String prenom, String adress, Date dateNaissance, String email, String tel,
            String genre, String bank_name) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adress = adress;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.tel = tel;
        this.genre = genre;
        this.bank_name = bank_name;
    }
    public int getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getAdress() {
        return adress;
    }
    public Date getDateNaissance() {
        return dateNaissance;
    }
    public String getEmail() {
        return email;
    }
    public String getTel() {
        return tel;
    }
    public String getGenre() {
        return genre;
    }
    public String getBank_name() {
        return bank_name;
    }
}
