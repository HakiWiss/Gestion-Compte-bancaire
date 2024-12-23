package database;

import java.sql.*;
import java.util.ArrayList;

import Model.*;

public class ClientDB {
    private Connection connection;

    public ClientDB(Connection connection) {
        this.connection = connection;
    }

    // get Compte by id && get Compte info 
    public Compte getCompteByRib(String rib) throws SQLException {
        String query = "SELECT * FROM Compte c LEFT JOIN Courant cr ON c.id = cr.compte_id LEFT JOIN Etudiant e ON c.id = e.compte_id where c.rib=?;";
        String type;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rib);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    type = resultSet.getString("type");
                    if (type.equals("crt")) {
                        return new Courant(
                                resultSet.getInt("id"),
                                resultSet.getString("rib"),
                                resultSet.getFloat("solde"),
                                resultSet.getString("password"),
                                resultSet.getInt("client_id"),
                                resultSet.getFloat("decouvertAutorise")
                        );
                    }else if (type.equals("etd")) {
                        return new Etudiant(
                                resultSet.getInt("id"),
                                resultSet.getString("rib"),
                                resultSet.getFloat("solde"),
                                resultSet.getString("password"),
                                resultSet.getInt("client_id"),
                                resultSet.getFloat("plafondRetrait")
                        );
                    }else if (type.equals("epn")) {
                    return new Epargne(
                            resultSet.getInt("id"),
                            resultSet.getString("rib"),
                            resultSet.getFloat("solde"),
                            resultSet.getString("password"),
                            resultSet.getInt("client_id"),
                            resultSet.getFloat("tauxInteret")
                    );
                }
                }
            }
        }
        return null; // Return null if no record is found
    }
    // get comptes list
    public ArrayList<Compte> getCompteByRib(int client_id) throws SQLException {
        String query = "SELECT * FROM Compte c LEFT JOIN Courant cr ON c.id = cr.compte_id LEFT JOIN Etudiant e ON c.id = e.compte_id where c.client_id=?;";
        String type;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, client_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                ArrayList<Compte> lstcomptes=new ArrayList<>();
                if (resultSet.next()) {
                    type = resultSet.getString("type");
                    if (type.equals("crt")) {
                        lstcomptes.add(new Courant(
                                resultSet.getInt("id"),
                                resultSet.getString("rib"),
                                resultSet.getFloat("solde"),
                                resultSet.getString("password"),
                                resultSet.getInt("client_id"),
                                resultSet.getFloat("decouvertAutorise")) 
                        );
                    }else if (type.equals("etd")) {
                        lstcomptes.add(new Etudiant(
                                resultSet.getInt("id"),
                                resultSet.getString("rib"),
                                resultSet.getFloat("solde"),
                                resultSet.getString("password"),
                                resultSet.getInt("client_id"),
                                resultSet.getFloat("plafondRetrait")) 
                        );
                    }else if (type.equals("epn")) {
                    lstcomptes.add(new Epargne(
                            resultSet.getInt("id"),
                            resultSet.getString("rib"),
                            resultSet.getFloat("solde"),
                            resultSet.getString("password"),
                            resultSet.getInt("client_id"),
                            resultSet.getFloat("tauxInteret")) 
                    );
                }
                } 
                return lstcomptes;
            }
        }
    }
}
