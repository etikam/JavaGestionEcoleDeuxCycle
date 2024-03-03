import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USERNAME = "ety";
    private static final String PASSWORD = "Etienne";
    private Connection con;
    public DBConnection(){
        con = null;
        try {
            // Chargement du pilote
            Class.forName("oracle.jdbc.driver.OracleDriver");
            //System.out.println("le pilote a bien charger");

            // Etablissement de la connexion
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connection reusi");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Impossible de charger le pilote JDBC Oracle");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur de connexion à la base de données Oracle");
        }


    }
    public Connection getCon(){
        return this.con;
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erreur lors de la fermeture de la connexion à la base de données Oracle");
            }
        }
    }
}
