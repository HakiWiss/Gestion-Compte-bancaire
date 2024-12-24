import java.sql.*;

import Model.*;
import database.*;

public class App {
  static database.Cnx cnxDb;

  public static void main(String[] args) throws Exception {
    System.out.print("\033\143");
    System.out.println("Hello, World!");
    Date date = Date.valueOf("2002-07-25");
    ;
    cnxDb = new Cnx();
    Bank bank = new Bank("CIH", "RABAT MAROC");

    Client client = new Client(1, "souhail", "yassin", "rabat", date,
        "yassin@gmail.com", "0612131415", "M", "CIH");
    Client client2 = new Client(1, "Ali", "Mohamed", "rabat", date,
        "yassin@gmail.com", "0612131415", "M", "CIH");

    AdminDB adminDB;
    ClientDB clientdb;
    Courant crt = new Courant(1, "AE101", 0, "0000", 1, 0);
    Etudiant etd = new Etudiant(2, "AE102", 0, "1111", 1, 0);
    // checklogin
    int IDAdmin = cnxDb.checkAdmin_cnx(101, "admin101");
    if (IDAdmin != 0) {
      System.out.println("Admin " + IDAdmin + " login success");
      adminDB = new AdminDB(cnxDb.getconnection());
      Admin admin = adminDB.getAdmin_info(IDAdmin);
      System.out.println("idantifiant de admin est: " + admin.getIdentifiant() + " work in : " + admin.getBank_name());
      // adminDB.insertClient(client); // work
      // adminDB.updateClient(client2); // work
      Client client1 = adminDB.getClientById(1);
      if (client1 != null) {
        System.out.println("Client " + client1.getId() + ": " + client1.getPrenom() + " " + client1.getNom());
      } else {
        System.out.println("Client not found");
      }

      // adminDB.insertCompte(crt); // work
      // adminDB.insertCompte(etd); // work
      // adminDB.UpdateCompte(etd); // work
      // adminDB.DeleteCompte(etd); // work
    } else {
      System.out.println("login failed ");
    }

    int IDCompte = cnxDb.checkCompte_cnx("AE101", "0000");
    if (IDCompte != 0) {
      System.out.println("Compte " + IDCompte + " login success");
      clientdb = new ClientDB(cnxDb.getconnection());
      Compte compte1 = clientdb.getCompteByRib("AE102");
      if (compte1 != null) {
        // if(compte1 instanceof Courant){}
        System.out.println("Compte: " + compte1.getRib() + " avec le solde " + compte1.getSolde());
        Releve releve = clientdb.retirerArgent(etd, 600);
        if (releve != null) {
          System.out.println("Action: " + releve.getType() + " avec le Montant " + releve.getMontant());
        }
        if(clientdb.TranserfArgent(crt, etd, 200)){
          System.out.println("La transfert de l'argent est bien success");
        }
      } else {
        System.out.println("compte not found");
      }
      // get comptes list ??
    } else {
      System.out.println("login failed ");
    }
  }
}
