package Model;

import java.sql.Date;

public class Releve {
    private Integer numeroReleve;
    private Date date;
    private String type;
    private Float montant;
    private Integer compte_id;

    public Releve(){}
    public Releve(Integer numeroReleve, Date date, String type, Float montant, Integer compte_id) {
        this.numeroReleve = numeroReleve;
        this.date = date;
        this.type = type;
        this.montant = montant;
        this.compte_id = compte_id;
    }

    public Integer getNumeroReleve() {
        return numeroReleve;
    }
    public Date getDate() {
        return date;
    }
    public String getType() {
        return type;
    }
    public Float getMontant() {
        return montant;
    }
    public Integer getCompte_id() {
        return compte_id;
    }
}
