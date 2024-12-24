package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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
    public ArrayList<Compte> getClientComptes(int client_id) throws SQLException {
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
    // deposer Argent 
    public Releve deposerArgent(Compte compte, float montant) throws SQLException {
        String query = "UPDATE Compte SET solde = solde + ? WHERE rib = ?;";
        String releveQuery="INSERT INTO Releve(date,type,montant,compte_id) values(?,?,?,?);";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setFloat(1, montant);
            statement.setString(2, compte.getRib());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
                try(PreparedStatement statement2=connection.prepareStatement(releveQuery,Statement.RETURN_GENERATED_KEYS)){
                    statement2.setDate(1, today); //error
                    statement2.setString(2, "deposir");
                    statement2.setFloat(3, montant);
                    statement2.setInt(4, compte.getId());
                    statement2.executeUpdate();
                    try (ResultSet generatedKeys = statement2.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int releveId = generatedKeys.getInt(1);
                            return new Releve(
                                releveId,
                                today,
                                "deposir",
                                montant,
                                compte.getId()
                            );
                        }
                    }
                }
            }
            return null;
        }
    }

    
    // retirer Argent 
    public Releve retirerArgent(Compte compte, float montant) throws SQLException {
        String checkbalancequery = "SELECT solde FROM Compte WHERE rib = ?;";
        String retirerquery = "UPDATE Compte SET solde = solde - ? WHERE rib = ?;";
        String releveQuery="INSERT INTO Releve(date,type,montant,compte_id) values(?,?,?,?);";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkbalancequery)) {
            checkStatement.setString(1, compte.getRib());
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    float solde = resultSet.getFloat("solde");
                    if (solde >= montant) {
                        try (PreparedStatement statement = connection.prepareStatement(retirerquery)) {
                            statement.setFloat(1, montant);
                            statement.setString(2, compte.getRib());
                            int rowsUpdated = statement.executeUpdate();
                            if (rowsUpdated > 0) {
                                java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
                                try(PreparedStatement statement2=connection.prepareStatement(releveQuery,Statement.RETURN_GENERATED_KEYS)){
                                    statement2.setDate(1, today); //error
                                    statement2.setString(2, "retirer");
                                    statement2.setFloat(3, montant);
                                    statement2.setInt(4, compte.getId());
                                    statement2.executeUpdate();
                                    try (ResultSet generatedKeys = statement2.getGeneratedKeys()) {
                                        if (generatedKeys.next()) {
                                            int releveId = generatedKeys.getInt(1);
                                            return new Releve(releveId,today,"retirer",montant,compte.getId());
                                        }
                                    }
                                }
                            }
                        }
                    } 
                }
            }
        }
        return null;
    }
    
    
}
