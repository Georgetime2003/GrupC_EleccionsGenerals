package basedades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
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
                String nomComunitat = llegirSegonsLlargada(15, 50, st);

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

    public static void importareleccions(){
        File file = new File("./fitxers/02021606.DAT");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {


                String dia = String.valueOf(llegirSegonsLlargada(13, 2, st));
                String mes = String.valueOf(llegirSegonsLlargada(15, 2, st));
                String any = String.valueOf(llegirSegonsLlargada(17, 4, st));

                // the mysql insert statement
                String query = "INSERT INTO eleccions (nom,data)"
                        + " values (?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setString(1, "Eleccions-06-2016");
                String data = String.join("-", any, mes, dia);
                preparedStmt.setDate(2, Date.valueOf(data));

                // execute the preparedstatement
                preparedStmt.execute();
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
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

    public static void importarMunicipis() {
        File file = new File("./fitxers/05021606.DAT");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {
                String nomMunicipi = llegirSegonsLlargada(19, 100, st).trim();
                int codiINEmunicipi = Integer.parseInt(llegirSegonsLlargada(14,3,st));
                int codiINEprovincia = Integer.parseInt(llegirSegonsLlargada(12,2,st));
                int num_districte = Integer.parseInt(llegirSegonsLlargada(17,2,st));


                //tenir el codi de provincia_id
                int provincia_id = treureprovincia_id(codiINEprovincia);

                if (num_districte == 99 ) {
                    // the mysql insert statement
                    String query = "INSERT INTO municipis (nom,codi_ine,provincia_id, districte)"
                            + " values (?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setString(1, nomMunicipi);
                    preparedStmt.setInt(2, codiINEmunicipi);
                    preparedStmt.setInt(3, provincia_id);
                    preparedStmt.setInt(4, num_districte);

                    // execute the preparedstatement
                    preparedStmt.execute();
                    System.out.println(nomMunicipi + " " + codiINEmunicipi + " "+ num_districte );
                }




            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void importarPartitsCandtatures() {
        File file = new File("./fitxers/03021606.DAT");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {

                int codi_canditatura = Integer.parseInt(llegirSegonsLlargada(9, 6, st));
                int codi_acumulacio_provincia = Integer.parseInt(llegirSegonsLlargada(215, 6, st));
                int codi_acumulacio_comautonoma = Integer.parseInt(llegirSegonsLlargada(221, 6, st));
                int codi_acumulacio_nacional = Integer.parseInt(llegirSegonsLlargada(227, 6, st));
                String nom_curt = llegirSegonsLlargada(15, 50, st).trim();
                String nom_llarg = llegirSegonsLlargada(65, 150, st).trim();

                //Treure codi id de eleccions
                int eleccio_id = treureEleccioId();

                    // the mysql insert statement
                    String query = "INSERT INTO candidatures (eleccio_id,codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulario_nacional)"
                            + " values (?, ?, ?, ? ,?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setInt(1, eleccio_id);
                    preparedStmt.setInt(2, codi_canditatura);
                    preparedStmt.setString(3, nom_curt);
                    preparedStmt.setString(4, nom_llarg);
                    preparedStmt.setInt(5, codi_acumulacio_provincia);
                    preparedStmt.setInt(6, codi_acumulacio_comautonoma);
                    preparedStmt.setInt(7, codi_acumulacio_nacional);

                    // execute the preparedstatement
                    preparedStmt.execute();
                    System.out.println(eleccio_id + " " + codi_canditatura + " " + nom_curt + " " + nom_llarg + " " + codi_acumulacio_provincia + " " + codi_acumulacio_comautonoma + " " + codi_acumulacio_nacional);

            }

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static int treureEleccioId() {
        int eleccioid = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT eleccio_id " +
                    " FROM eleccions ";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            ResultSet rs = preparedStmt.executeQuery();
            while(rs.next()) {
                //System.out.println(rs.getInt("eleccio_id"));
                eleccioid = rs.getInt("eleccio_id");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        return eleccioid;
    }

    private static String llegirSegonsLlargada(int longiInci, int llargadaALlegir, String st) {
        String stringTornar = "";

        longiInci--;

        for (int i = 0; i < llargadaALlegir; ++i, ++longiInci) {
            stringTornar += String.valueOf(st.charAt(longiInci));


        }
        return stringTornar;
    }

    public static int treureprovincia_id(int codiINEComuni) {
        int id_provincia = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT provincia_id " +
                    " FROM provincies " +
                    "WHERE codi_ine = "+ codiINEComuni + ";";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                //System.out.println(rs.getInt("provincia_id"));
                        id_provincia = rs.getInt("provincia_id");
            }

        }catch(Exception e){
            System.out.println(e);
        }

        
        return id_provincia;
    }



    }

