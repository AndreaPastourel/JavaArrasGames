import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Connexion{
    private String BDD = "cake_arrasgames";
    private String url = "jdbc:mysql://localhost:3306/"+BDD;
    private String user = "root";
    private String pass = "root";

            private Connection con;

            public Connection getCon() {
                try {
                    // Charge le driver JDBC
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    return DriverManager.getConnection(url,user, pass);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC introuvable.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données.");
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection(){
        if (con != null) {
            try {
                con.close();
                System.out.println("Connexion fermée avec succès.");
            } catch (SQLException e) {
                System.err.println("Erreur : Impossible de fermer la connexion.");
                e.printStackTrace();
            }
        }
    }

    public Connexion() {
        try {
            // Chargement du driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Établissement de la connexion
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connexion établie avec succès.");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Le driver JDBC est introuvable.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur : Échec de la connexion à la base de données.");
            e.printStackTrace();
        }




}}
