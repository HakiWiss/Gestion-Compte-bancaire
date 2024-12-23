package database;

import java.sql.*;

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

    public void insertCompte(Compte compte) throws SQLException {
        int compteID;
        String Comptequery = "INSERT INTO Compte (rib, solde, password, client_id, type) VALUES (?, ?, ?, ?, ?)";
        String Courantquery = "INSERT INTO Courant(compte_id,decouvertAutorise) VALUES(?,?)";
        String Etudiantquery = "INSERT INTO Etudiant(compte_id,plafondRetrait) VALUES(?,?)";
        String Epargnequery = "INSERT INTO Epargne(compte_id,tauxInteret) VALUES(?,?)";

        try (PreparedStatement statement = connection.prepareStatement(Comptequery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, compte.getRib());
            statement.setFloat(2, compte.getSolde());
            statement.setString(3, compte.getPassword());
            statement.setInt(4, compte.getClient_id());
            statement.setString(5, compte.getType());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    compteID = generatedId;
                    System.out.println(compteID);
                    if (compte instanceof Courant) {
                        try (PreparedStatement courantStatement = connection.prepareStatement(Courantquery)) {
                            courantStatement.setInt(1, generatedId);
                            courantStatement.setDouble(2, ((Courant) compte).getdecouverAutorise());
                            courantStatement.executeUpdate();
                        }
                    }
                    if (compte instanceof Etudiant) {
                        try (PreparedStatement etudiantStatement = connection.prepareStatement(Etudiantquery)) {
                            etudiantStatement.setInt(1, generatedId);
                            etudiantStatement.setDouble(2, ((Etudiant) compte).getplatfondRetrait());
                            etudiantStatement.executeUpdate();
                        }
                    }
                    if (compte instanceof Epargne) {
                        try (PreparedStatement epargneStatement = connection.prepareStatement(Epargnequery)) {
                            epargneStatement.setInt(1, generatedId);
                            epargneStatement.setDouble(2, ((Epargne) compte).getTauxInteret());
                            epargneStatement.executeUpdate();
                        }
                    }
                } else {
                    throw new SQLException("Inserting compte failed, no ID obtained.");
                }
            }
        }
    }

    // Update Compte
    public void UpdateCompte(Compte compte) throws SQLException {
        String Comptequery = "UPDATE Compte SET solde=?, password=?, client_id=?, type=? where id=?";
        String Courantquery = "UPDATE Courant SET decouvertAutorise=? where compte_id=? ";
        String Etudiantquery = "UPDATE Etudiant SET plafondRetrait=? where compte_id=? ";
        String Epargnequery = "UPDATE Epargne SET tauxInteret=? where compte_id=?  ";

        try (PreparedStatement statement = connection.prepareStatement(Comptequery)) {
            statement.setFloat(1, compte.getSolde());
            statement.setString(2, compte.getPassword());
            statement.setInt(3, compte.getClient_id());
            statement.setString(4, compte.getType());
            statement.setInt(5, compte.getId());
            statement.executeUpdate();
            if (compte instanceof Courant) {
                try (PreparedStatement courantStatement = connection.prepareStatement(Courantquery)) {
                    courantStatement.setDouble(1, ((Courant) compte).getdecouverAutorise());
                    courantStatement.setInt(2, compte.getId());
                    courantStatement.executeUpdate();
                }
            }
            if (compte instanceof Etudiant) {
                try (PreparedStatement etudiantStatement = connection.prepareStatement(Etudiantquery)) {
                    etudiantStatement.setDouble(1, ((Etudiant) compte).getplatfondRetrait());
                    etudiantStatement.setInt(2, compte.getId());
                    etudiantStatement.executeUpdate();
                }
            }
            if (compte instanceof Epargne) {
                try (PreparedStatement epargneStatement = connection.prepareStatement(Epargnequery)) {
                    epargneStatement.setDouble(1, ((Epargne) compte).getTauxInteret());
                    epargneStatement.setInt(2, compte.getId());
                    epargneStatement.executeUpdate();
                }
            }
        }
    }

    // delete Compte
    public void DeleteCompte(Compte compte) throws SQLException {
        String Comptequery = "DELETE FROM Compte where id=?";
        try (PreparedStatement statement = connection.prepareStatement(Comptequery, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, compte.getId());
            statement.executeUpdate();

        }
    }

    
    
    
}
