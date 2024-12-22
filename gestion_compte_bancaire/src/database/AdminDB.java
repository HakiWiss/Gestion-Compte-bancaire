package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.*;

public class AdminDB {
    private Connection connection;

    public AdminDB(Connection connection){this.connection=connection;}

    public Admin getAdmin_info(int identi){
        String query = "SELECT * FROM Admin WHERE identifiant = ? ;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, identi);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Retourner l'identifiant si l'admin est trouvé
                    return new Admin(
                    rs.getInt("identifiant"),
                    rs.getString("password"),
                    rs.getString("bank_name")
                );
                } else {
                    // Retourner 0 ou une autre valeur pour indiquer l'échec
                    return null;
                }
            }
        }catch (SQLException e){
            System.err.println("error lors check Admin login "+e.getMessage());
            return null;
        }
    }
    public Client getClientById(int id) throws SQLException {
        String query = "SELECT * FROM Client WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Client(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("adresse"),
                        resultSet.getDate("dateNaissance"),
                        resultSet.getString("email"),
                        resultSet.getString("tel"),
                        resultSet.getString("genre"),
                        resultSet.getString("bank_name")

                    );
                }
            }
        }
        return null; // Return null if no record is found
    }
    public void insertClient(Client client) throws SQLException {
        // Use placeholders for each value in the INSERT query
        String query = "INSERT INTO Client (nom, prenom, adresse, dateNaissance, email, tel, genre, bank_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set values for each placeholder
            statement.setString(1, client.getNom());
            statement.setString(2, client.getPrenom());
            statement.setString(3, client.getAdress());
            statement.setDate(4, client.getDateNaissance()); // Ensure date is java.sql.Date
            statement.setString(5, client.getEmail());
            statement.setString(6, client.getTel());
            statement.setString(7, client.getGenre());
            statement.setString(8, client.getBank_name());
            
            // Execute the query
            statement.executeUpdate();
        }
    }

    public void updateClient(Client client) throws SQLException {
        
        String query = "UPDATE Client SET nom = ?, prenom = ?, adresse = ?, dateNaissance = ?, email = ?, tel = ?, genre = ?, bank_name = ? WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getNom());
            statement.setString(2, client.getPrenom());
            statement.setString(3, client.getAdress());
            statement.setDate(4, client.getDateNaissance()); 
            statement.setString(5, client.getEmail());
            statement.setString(6, client.getTel());
            statement.setString(7, client.getGenre());
            statement.setString(8, client.getBank_name());
            statement.setInt(9, client.getId());
                        statement.executeUpdate();
        }
    }
    public void deleteClient(Client client)throws SQLException{
        String query="DELETE FROM Client where id=?";
        try(PreparedStatement statement=connection.prepareStatement(query)){
            statement.setInt(1, client.getId());
            statement.executeUpdate();
        }
    }

    
    
    
}
