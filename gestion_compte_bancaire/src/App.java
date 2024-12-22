import java.sql.*;

import Model.*;
import database.*;


public class App {
    static database.Cnx cnxDb;

    
    public static void main(String[] args) throws Exception {
        System.out.print("\033\143");
        System.out.println("Hello, World!");
        Date date=Date.valueOf("2002-07-25");;
        cnxDb=new Cnx();
        Bank bank=new Bank("CIH", "RABAT MAROC");

        Client client=new Client(1,"souhail", "yassin", "rabat", date,
          "yassin@gmail.com", "0612131415", "M", "CIH");
        Client client2=new Client(1, "Ali", "Mohamed", "rabat", date,
          "yassin@gmail.com", "0612131415", "M", "CIH"); 

        AdminDB adminDB;
        ClientDB clientdb;
        Courant crt=new Courant(1, "AE101", 0, "0000", 1, 0);
        Etudiant etd=new Etudiant(3, "AE102", 0, "1111", 1, 0);
        //checklogin
        int IDAdmin=cnxDb.checkAdmin_cnx(101, "admin1010");
        if(IDAdmin!=0){
            System.out.println("Admin "+IDAdmin+" login success");
            adminDB=new AdminDB(cnxDb.getconnection());
            Admin admin=adminDB.getAdmin_info(IDAdmin);
            System.out.println("idantifiant de admin est: "+admin.getIdentifiant() +" work in : "+admin.getBank_name());
            // adminDB.insertClient(client);        // work
            // adminDB.updateClient(client2);       // work
            Client client1=adminDB.getClientById(1);
            System.out.println("Client "+client1.getId()+": "+client1.getPrenom()+" "+client1.getNom());
        }
        else{System.out.println("login failed ");}
        // cnxDb.insertCompte(etd);                 // work
        // cnxDb.UpdateCompte(etd);                 // work
        // cnxDb.DeleteCompte(etd);                 // work
        int IDCompte=cnxDb.checkCompte_cnx("AE101", "0000");
        if(IDCompte!=0){
            System.out.println("Compte "+IDCompte+" login success");
            clientdb=new ClientDB(cnxDb.getconnection());
            //get compte info ??
        }
        else{System.out.println("login failed ");}
    }
}
