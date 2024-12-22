package database;

import java.sql.*;


public class Cnx {
    private static String url = "jdbc:mysql://localhost:3306/GestionCompteBancaire";
    private static String username = "wissam";
    private static String password = "jrwiss2025";
    private Connection connection;

    public Cnx() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    // reconnection
    public Connection getconnection() {
        return connection;
    }

    // fermer la connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection fermer");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
        }
    }

    public int checkAdmin_cnx(int identi, String passwrd) {
        String query = "SELECT identifiant FROM Admin WHERE identifiant = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, identi);
            stmt.setString(2, passwrd);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("identifiant");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public int checkCompte_cnx(String rib, String passwrd) {
        String query = "SELECT id FROM Compte WHERE rib = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, rib);
            stmt.setString(2, passwrd);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    

}
