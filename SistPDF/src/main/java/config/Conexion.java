
package config;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {
    public static Connection getConexion() {

        Connection cn = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            cn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/sistpdf",
                    "root",
                    "");

            System.out.println("CONECTADO");

        } catch (Exception e) {

            System.out.println("ERROR: " + e);
        }

        return cn;
    }
}
