package cat.sapa.uf2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import static cat.sapa.uf2.Main.con;

public class Importacions {
    public static void importarComunitatsAutonomes() {
        File file = new File("./fitxers/07021606.DAT");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;

            while ((st = br.readLine()) != null) {
                int codiINEComunitat = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                int codiINE = Integer.parseInt(llegirSegonsLlargada(12,2,st));
                String nomComunitat = llegirSegonsLlargada(15, 50, st).trim();

                if (codiINE == 99 && codiINEComunitat != 99) {
                    // the mysql insert statement
                    String query = "INSERT INTO comunitats_autonomes (nom,codi_ine)"
                            + " values (?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setString(1, nomComunitat);
                    preparedStmt.setInt(2, codiINEComunitat);

                    // execute the preparedstatement
                    preparedStmt.execute();
                }
            }
            System.out.println("La taula de Comunitats Autonomes s'ha importat correctament.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static String llegirSegonsLlargada(int longiInci, int llargadaALlegir, String st) {
        String stringTornar = "";

        longiInci--;

        for (int i = 0; i < llargadaALlegir; ++i, ++longiInci) {
            stringTornar += String.valueOf(st.charAt(longiInci));


        }
        return stringTornar;
    }
}
