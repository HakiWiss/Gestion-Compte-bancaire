package Model;

import java.sql.Date;

public class Transfert {
    private Integer id;
    private Date date;
    private Float montant;
    private Integer compte_src_id;
    private Integer compte_des_id;

    public Transfert() {
    };

    public Transfert(Integer id, Date date, Float montant, Integer compte_src_id, Integer compte_des_id) {
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.compte_src_id = compte_src_id;
        this.compte_des_id = compte_des_id;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Float getMontant() {
        return montant;
    }

    public Integer getCompte_src_id() {
        return compte_src_id;
    }

    public Integer getCompte_des_id() {
        return compte_des_id;
    }
}
