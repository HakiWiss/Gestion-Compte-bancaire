package database;
import java.sql.*;
import Model.*;

abstract class query_check {
    public static boolean checkCompte_exist(Connection connection,String rib) throws SQLException{
        String query="select count(*) as count from Compte where rib=?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, rib);
            try(ResultSet resultSet=statement.executeQuery()){
                if(resultSet.next()){
                    if(resultSet.getInt("count")>0){return true;}
                }
            }
        }
        return false;
    }
    
}
