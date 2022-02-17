package basedades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static basedades.Main.con;

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

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void importarProvincies() {
        File file = new File("./fitxers/07021606.DAT");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {
                String nomProvincia = llegirSegonsLlargada(15, 50, st).trim();
                int codiINEComunitat = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                int codiINE = Integer.parseInt(llegirSegonsLlargada(12,2,st));
                int codiComunitat = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                int numEscons = Integer.parseInt(llegirSegonsLlargada(150,6,st));

                //Treure codi id de provincies
                int comunitat_aut_id = treureIdComunAmbINE(codiINEComunitat);

                if (codiINE != 99 && codiComunitat != 99) {

                    // the mysql insert statement
                    String query = "INSERT INTO provincies (comunitat_aut_id,nom,codi_ine,num_escons)"
                            + " values (?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setInt(1, comunitat_aut_id);
                    preparedStmt.setString(2, nomProvincia);
                    preparedStmt.setInt(3, codiINE);
                    preparedStmt.setInt(4, numEscons);

                    // execute the preparedstatement
                    preparedStmt.execute();

                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void importarMunicipis() {
        File file = new File("./fitxers/05021606.DAT");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {
                String nomMunicipi = llegirSegonsLlargada(19, 100, st);
                int codiINE = Integer.parseInt(llegirSegonsLlargada(12,2,st));
                int codiComunitat = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                int numEscons = Integer.parseInt(llegirSegonsLlargada(150,6,st));

                if (codiINE != 99 && codiComunitat != 99) {
                    System.out.println(nomMunicipi + " " + codiINE + " " + numEscons + " " + codiComunitat);
                }



            }
        } catch (IOException e) {
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

    public static int treureIdComunAmbINE(int codiINEComunitat) {
        int id_comuni = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT comunitat_aut_id FROM comunitats_autonomes WHERE codi_ine = " + codiINEComunitat + ";";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                //System.out.println(rs.getInt("comunitat_aut_id"));
                id_comuni = rs.getInt("comunitat_aut_id");
            }


        }catch(Exception e){
            System.out.println(e);
        }


        return id_comuni;
    }



    }

