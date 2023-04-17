package com.example.test4;
import java.sql.*;

public class Test4 {
    public static void main(String[] args) {

        //Istanzio una connessione
        Connection conn = null;
        PreparedStatement stm;
        ResultSet res;
        //Inizializzo le variabili utili alla connessione
        //Url di connessione al db
        String dbURL = "jdbc:mysql://localhost:3306/";
        //Username
        String username = "root";
        //Password
        String password = "Berau99!";

        //Stringhe contenenti le operazioni SQL da eseguire

        //Creazione dello schema
        String deleteSchema = "DROP DATABASE IF EXISTS testDB;";
        String createSchema = "CREATE DATABASE IF NOT EXISTS testDB;";

        //Creazione della tabella
        String createTable = """
                CREATE TABLE testDB.info_vendite (
                id INT NOT NULL AUTO_INCREMENT,
                utente CHAR NOT NULL,
                anno INT NOT NULL,
                vendite INT NOT NULL,
                PRIMARY KEY (id));""";

        // Popolamento della tabella
        String insertData = """
                INSERT INTO `testDB`.`info_vendite` (utente, anno, vendite) VALUES
                ('A', 2021, 500),
                ('B', 2021, 1000),
                ('C', 2022, 900),
                ('A', 2022, 1200),
                ('B', 2021, 600),
                ('A', 2022, 900),
                ('B', 2021, 500),
                ('C', 2021, 1000),
                ('C', 2021, 700),
                ('B', 2021, 500);""";

        //Query da eseguire
        String selectQuery = """
                SELECT utente as Utente, anno AS  Anno, SUM(vendite) AS VenditeTot,
                                SUM(vendite) -(
                                    SELECT SUM(vendite)
                                    FROM testDB.info_vendite AS info_vendite_2
                                    WHERE info_vendite_2.utente = info_vendite_1.utente AND info_vendite_2.anno = info_vendite_1.anno - 1
                                ) AS differenza
                                FROM testDB.info_vendite AS info_vendite_1
                                GROUP BY utente, anno
                                ORDER BY utente, anno
                """;

        try{
            conn = DriverManager.getConnection(dbURL, username, password);
            if(conn != null){
                DatabaseMetaData dm = conn.getMetaData();
                System.out.println("\n\n*** INFO CONNESSIONE ***");
                System.out.println("\t- Nome del Diver: " + dm.getDriverName());
                System.out.println("\t- Driver version: " + dm.getDriverVersion());
                System.out.println("\t- Product name: " + dm.getDatabaseProductName());
                System.out.println("\t- Product version: " + dm.getDatabaseProductVersion());

                //Esecuzione delle operazioni
                System.out.println("\n\n *** RIMOZIONE DELLO SCHEMA SE PRESENTE ***");
                stm = conn.prepareStatement(deleteSchema);
                stm.executeUpdate(deleteSchema);

                System.out.println("\n\n*** CREAZIONE DELLO SCHEMA ***");
                stm = conn.prepareStatement(createSchema);
                stm.executeUpdate(createSchema);
                System.out.println("\t- Stato: [OK!]");

                System.out.println("\n\n*** CREAZIONE DELLA TABELLA ***");
                stm = conn.prepareStatement(createTable);
                stm.executeUpdate(createTable);
                System.out.println("\t- Stato: [OK!]");

                System.out.println("\n\n*** INSERIMENTO DEI DATI NELLA TABELLA ***");
                stm = conn.prepareStatement(insertData);
                stm.executeUpdate(insertData);
                System.out.println("\t- Stato: [OK!]");

                System.out.println("\n\n*** ESECUZIONE DELLA QUERY ***");
                stm = conn.prepareStatement(selectQuery);
                System.out.println("\t- Stato: [OK!]");

                res = stm.executeQuery();
                System.out.println("\n\n*** RISULTATO ***");
                while (res.next()) {
                    System.out.println("\t- RECORD: [ " + res.getString(1) + " - " + res.getString(2) + " - " + res.getString(3)  + " - " + res.getString(4)  + " ];");
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }
}