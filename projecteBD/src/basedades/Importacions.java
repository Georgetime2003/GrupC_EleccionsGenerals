package basedades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

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
            System.out.println("La taula de Comunitats Autonomes s'ha importat correctament.");
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
                int comunitat_aut_id = obtenirIdComunAmbINE(codiINEComunitat);

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
            System.out.println("La taula de Provincies s'ha importat correctament.");
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
                String nomMunicipi = llegirSegonsLlargada(19, 100, st).trim();
                int codiINEmunicipi = Integer.parseInt(llegirSegonsLlargada(14,3,st));
                int codiINEprovincia = Integer.parseInt(llegirSegonsLlargada(12,2,st));
                int num_districte = Integer.parseInt(llegirSegonsLlargada(17,2,st));


                //tenir el codi de provincia_id
                int provincia_id = obtenirProvincia_id(codiINEprovincia);

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
                    //System.out.println(nomMunicipi + " " + codiINEmunicipi + " "+ num_districte );
                }
            }
            System.out.println("La taula de Municipis s'ha importat correctament.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void importarPersones() {
        File file = new File("./fitxers/04021606.DAT");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;

            while ((st = br.readLine()) != null) {
                String nom_pers = llegirSegonsLlargada(26, 25, st).trim();
                String cog1_pers = llegirSegonsLlargada(51, 25, st).trim();
                String cog2_pers = llegirSegonsLlargada(76, 25, st).trim();
                int dia = Integer.parseInt(llegirSegonsLlargada(102, 2, st));
                int mes = Integer.parseInt(llegirSegonsLlargada(104, 2, st));
                int any = Integer.parseInt(llegirSegonsLlargada(106, 4, st));
                String query = "INSERT INTO persones (nom, cog1, cog2, data_naixement)"
                        + " values (?, ?, ?, ?)";

                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setString(1, nom_pers);
                preparedStmt.setString(2,cog1_pers);
                preparedStmt.setString(3, cog2_pers);
                if (any != 0) {
                    Date data_naixement = Date.valueOf(any + "-" + mes + "-" + dia);
                    //System.out.println(nom_pers + " " + cog1_pers + " " + cog2_pers + " "  + data_naixement);
                    preparedStmt.setDate(4, data_naixement);

                }
                else{
                    //System.out.println(nom_pers + " " + cog1_pers + " " + cog2_pers + " " );
                    preparedStmt.setNull(4, Types.DATE);
                }
                preparedStmt.execute();
            }
            System.out.println("La taula de Persones s'ha importat correctament.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void importarCandidats() {
        File file = new File("./fitxers/04021606.DAT");

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;

            while ((st = br.readLine()) != null) {
                String candidatura_codi = llegirSegonsLlargada(16, 6, st);
                int candidatura_id = obtenirCandidatura_id(candidatura_codi);
                String nom_pers = llegirSegonsLlargada(26, 25, st).trim();
                String cog1_pers = llegirSegonsLlargada(51, 25, st).trim();
                String cog2_pers = llegirSegonsLlargada(76, 25, st).trim();
                int persona_id = obtenirPersona_id(nom_pers, cog1_pers, cog2_pers);

                int codiINEPro = Integer.parseInt(llegirSegonsLlargada(10,2,st));
                int provicia_id  = obtenirProvincia_id(codiINEPro);

                int num_ordre = Integer.parseInt(llegirSegonsLlargada(22, 3, st));
                String tipus = llegirSegonsLlargada(25, 1, st);

                //System.out.println(candidatura_id + " " + persona_id + " " + provicia_id + " " + num_ordre + " " + tipus);

                String query = "INSERT INTO candidats (candidatura_id, persona_id, provincia_id, num_ordre, tipus)"
                        + " values (?, ?, ?, ?, ?)";

                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setInt(1, candidatura_id);
                preparedStmt.setInt(2, persona_id);
                preparedStmt.setInt(3, provicia_id);
                preparedStmt.setInt(4, num_ordre);
                preparedStmt.setString(5, tipus);
                //System.out.println(preparedStmt);
                preparedStmt.execute();
            }
            System.out.println("La taula de Candidats s'ha importat correctament.");
        } catch (IOException | SQLException e) {

            e.printStackTrace();
        }
    }

    public static void importarEleccions(){
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
        System.out.println("La taula de Eleccions s'ha importat correctament.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void importarCandidatures() {
        File file = new File("./fitxers/03021606.DAT");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {

                String codi_canditatura = llegirSegonsLlargada(9, 6, st);
                int codi_acumulacio_provincia = Integer.parseInt(llegirSegonsLlargada(215, 6, st));
                int codi_acumulacio_comautonoma = Integer.parseInt(llegirSegonsLlargada(221, 6, st));
                int codi_acumulacio_nacional = Integer.parseInt(llegirSegonsLlargada(227, 6, st));
                String nom_curt = llegirSegonsLlargada(15, 50, st).trim();
                String nom_llarg = llegirSegonsLlargada(65, 150, st).trim();

                //Treure codi id de eleccions
                int eleccio_id = obtenirEleccioId();

                // the mysql insert statement
                String query = "INSERT INTO candidatures (eleccio_id,codi_candidatura,nom_curt,nom_llarg,codi_acumulacio_provincia,codi_acumulacio_ca,codi_acumulario_nacional)"
                        + " values (?, ?, ?, ? ,?, ?, ?)";

                // create the mysql insert preparedstatement
                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setInt(1, eleccio_id);
                preparedStmt.setString(2, codi_canditatura);
                preparedStmt.setString(3, nom_curt);
                preparedStmt.setString(4, nom_llarg);
                preparedStmt.setInt(5, codi_acumulacio_provincia);
                preparedStmt.setInt(6, codi_acumulacio_comautonoma);
                preparedStmt.setInt(7, codi_acumulacio_nacional);

                // execute the preparedstatement
                preparedStmt.execute();
                //System.out.println(eleccio_id + " " + codi_canditatura + " " + nom_curt + " " + nom_llarg + " " + codi_acumulacio_provincia + " " + codi_acumulacio_comautonoma + " " + codi_acumulacio_nacional);

            }
        System.out.println("La taula de Candidatures s'ha importat correctament.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static int obtenirEleccioId() {
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

    public static int obtenirPersona_id(String nom_pers, String cog1_pers, String cog2_pers) {
        int id_persona = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Preparem una sentència.
            String query = "SELECT persona_id " +
                                " FROM persones " +
                            "WHERE nom = ? AND cog1 = ? AND cog2 = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, nom_pers);
            preparedStmt.setString( 2, cog1_pers);
            preparedStmt.setString(3, cog2_pers);
            //System.out.println(preparedStmt);

            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                id_persona = rs.getInt("persona_id");
            }

        }catch(Exception e){
            System.out.println(e);
        }


        return id_persona;

    }

    public static int obtenirCandidatura_id(String candidatura_codi) {
        int id_candidatura = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Preparem una sentència.
            String query = "SELECT candidatura_id" +
                    " FROM candidatures " +
                    "WHERE codi_candidatura = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, candidatura_codi);
            //System.out.println(preparedStmt);
            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                id_candidatura = rs.getInt("candidatura_id");
            }

        }catch(Exception e){
            System.out.println(e);
        }


        return id_candidatura;

    }

    public static int obtenirProvincia_id(int codiINEPro) {
        int id_provincia = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT provincia_id " +
                    " FROM provincies " +
                    "WHERE codi_ine = ? ";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, codiINEPro);


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

    public static int obtenirIdComunAmbINE(int codiINEComunitat) {
        int id_comuni = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT comunitat_aut_id FROM comunitats_autonomes WHERE codi_ine = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, codiINEComunitat);

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

    private static String llegirSegonsLlargada(int longiInci, int llargadaALlegir, String st) {
        String stringTornar = "";

        longiInci--;

        for (int i = 0; i < llargadaALlegir; ++i, ++longiInci) {
            stringTornar += String.valueOf(st.charAt(longiInci));


        }
        return stringTornar;
    }
}

