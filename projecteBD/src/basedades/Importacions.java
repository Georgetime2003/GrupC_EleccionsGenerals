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
            String[] nom;

            while ((st = br.readLine()) != null) {

                String nom_pers = llegirSegonsLlargada(26, 25, st).trim();
                String cog1_pers = llegirSegonsLlargada(51, 25, st).trim();
                String cog2_pers = llegirSegonsLlargada(76, 25, st).trim();

                //modifica el cog1_pers si nomes conte un "i" per la segona paraula del nom.
                if (cog1_pers.equals("i")) {
                    nom = nom_pers.split(" ");
                    nom_pers = nom[0];
                    cog1_pers = nom[1];
                }

                int dia = Integer.parseInt(llegirSegonsLlargada(102, 2, st));
                int mes = Integer.parseInt(llegirSegonsLlargada(104, 2, st));
                int any = Integer.parseInt(llegirSegonsLlargada(106, 4, st));
                String query = "INSERT INTO persones (nom, cog1, cog2, data_naixement)"
                        + " values (?, ?, ?, ?)";

                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setString(1, nom_pers);
                preparedStmt.setString(2, cog1_pers);
                preparedStmt.setString(3, cog2_pers);

                //si la data es un 0 el camp sera null pero si no escriu la data.
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
            String[] nom;

            while ((st = br.readLine()) != null) {
                String candidatura_codi = llegirSegonsLlargada(16, 6, st);
                int candidatura_id = obtenirCandidatura_id(candidatura_codi);
                String nom_pers = llegirSegonsLlargada(26, 25, st).trim();
                String cog1_pers = llegirSegonsLlargada(51, 25, st).trim();
                String cog2_pers = llegirSegonsLlargada(76, 25, st).trim();
                if (cog1_pers.equals("i")) {
                    nom = nom_pers.split(" ");
                    nom_pers = nom[0];
                    cog1_pers = nom[1];
                }

                int persona_id = obtenirPersona_id(nom_pers, cog1_pers, cog2_pers);

                int codiINEPro = Integer.parseInt(llegirSegonsLlargada(10,2,st));
                int provincia_id  = obtenirProvincia_id(codiINEPro);

                int num_ordre = Integer.parseInt(llegirSegonsLlargada(22, 3, st));
                String tipus = llegirSegonsLlargada(25, 1, st);

                //System.out.println(candidatura_id + " " + persona_id + " " + provicia_id + " " + num_ordre + " " + tipus);

                String query = "INSERT INTO candidats (candidatura_id, persona_id, provincia_id, num_ordre, tipus)"
                        + " values (?, ?, ?, ?, ?)";

                PreparedStatement preparedStmt = con.prepareStatement(query);

                preparedStmt.setInt(1, candidatura_id);
                preparedStmt.setInt(2, persona_id);
                preparedStmt.setInt(3, provincia_id);
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
                int any = Integer.parseInt(llegirSegonsLlargada(3, 4, st));
                int mes = Integer.parseInt(llegirSegonsLlargada(7, 2, st));
                int eleccio_id = obtenirEleccioId(mes, any);

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

    public static void importarVotsProvincies() {
        File file = new File("./fitxers/08021606.DAT");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {

                int provincia_INE = Integer.parseInt(llegirSegonsLlargada(12, 2, st));
                int provincia_id = obtenirProvincia_id(provincia_INE);
                String codi_candidatura = llegirSegonsLlargada(15, 6, st);
                int candidatura_id = obtenirCandidatura_id(codi_candidatura);
                int vots = Integer.parseInt(llegirSegonsLlargada(21, 8, st));
                int candidats_obtinguts = Integer.parseInt(llegirSegonsLlargada(29, 5, st));

                //agafar nomes les dades que la provincia no es 99
                if (provincia_INE != 99 ) {

                    // the mysql insert statement
                    String query = "INSERT INTO vots_candidatures_prov (provincia_id, candidatura_id,vots,candidats_obtinguts)"
                            + " values (?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setInt(1, provincia_id);
                    preparedStmt.setInt(2, candidatura_id);
                    preparedStmt.setInt(3, vots );
                    preparedStmt.setInt(4, candidats_obtinguts);

                    // execute the preparedstatement
                    preparedStmt.execute();
                }

            }
            System.out.println("La taula de Vots Candidatures per Provincies s'ha importat correctament.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void importarVotsCandidatures_ca(){
        File file = new File("./fitxers/08021606.DAT");

        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {

                int provincia_INE = Integer.parseInt(llegirSegonsLlargada(12, 2, st));
                int comunitat_autonoma_ine = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                int comunitat_autonoma_id = obtenirIdComunAmbINE(comunitat_autonoma_ine);
                String codi_candidatura = llegirSegonsLlargada(15, 6, st);
                int candidatura_id = obtenirCandidatura_id(codi_candidatura);
                int vots = Integer.parseInt(llegirSegonsLlargada(21, 8, st));


                //agafar nomes les dades que la comunitat_autonoma no es 99
                if (comunitat_autonoma_ine != 99 && provincia_INE == 99 ) {

                    // the mysql insert statement
                    String query = "INSERT INTO vots_candidatures_ca (comunitat_autonoma_id, candidatura_id,vots)"
                            + " values (?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setInt(1, comunitat_autonoma_id);
                    preparedStmt.setInt(2, candidatura_id);
                    preparedStmt.setInt(3, vots );

                    // execute the preparedstatement
                    preparedStmt.execute();
                }

            }
            System.out.println("La taula de Vots Candidatures per Comunitats Autonomes s'ha importat correctament.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    public static void importarEleccionsMunicipi(){
        File file = new File("./fitxers/05021606.DAT");
        int municipi_id = 0;
        try (
                BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;

            while ((st = br.readLine()) != null) {
                //Afegir 1 a municipi_id
                municipi_id++;
                //Treure districtes
                int districe = Integer.parseInt(llegirSegonsLlargada(17, 2, st));
                if (districe == 99) {
                    int num_meses = Integer.parseInt(llegirSegonsLlargada(137, 5, st));
                    int cens = Integer.parseInt(llegirSegonsLlargada(150, 8, st));
                    int vots_canditatures = Integer.parseInt(llegirSegonsLlargada(206, 8, st));
                    int vots_blanc = Integer.parseInt(llegirSegonsLlargada(190, 8, st));
                    int vots_nuls = Integer.parseInt(llegirSegonsLlargada(198, 8, st));

                    //Agafar codi INE municipis per aixi treure municipi_id
                    int ine_municipi = Integer.parseInt(llegirSegonsLlargada(14, 3, st));
                    int ine_provincia = Integer.parseInt(llegirSegonsLlargada(12, 2, st));
                    municipi_id = obtenirMunicipiid(ine_municipi, ine_provincia);

                    //Treure els vots emesos i valids
                    int vots_emesos = vots_blanc + vots_nuls + vots_canditatures;
                    int vots_valids = vots_emesos - vots_nuls;

                    //Treure codi id de eleccions
                    int any = Integer.parseInt(llegirSegonsLlargada(3, 4, st));
                    int mes = Integer.parseInt(llegirSegonsLlargada(7, 2, st));
                    int eleccio_id = obtenirEleccioId(mes, any);

                    // the mysql insert statement
                    String query = "INSERT INTO eleccions_municipis (eleccio_id,municipi_id,num_meses,cens,vots_emesos,vots_valids,vots_candidatures,vots_blanc,vots_nuls)"
                            + " values (?, ?, ?, ? ,?, ?, ?, ?, ?)";

                    // create the mysql insert preparedstatement
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setInt(1, eleccio_id);
                    preparedStmt.setInt(2, municipi_id);
                    preparedStmt.setInt(3, num_meses);
                    preparedStmt.setInt(4, cens);
                    preparedStmt.setInt(5, vots_emesos);
                    preparedStmt.setInt(6, vots_valids);
                    preparedStmt.setInt(7, vots_canditatures);
                    preparedStmt.setInt(8, vots_blanc);
                    preparedStmt.setInt(9, vots_nuls);

                    // execute the preparedstatement
                    preparedStmt.execute();
                    //System.out.println(eleccio_id + " " + municipi_id + " " + num_meses + " " + cens + " " + vots_emesos + " " + vots_valids + " " + vots_canditatures + " " + vots_blanc + " " + vots_nuls);
                }
            }
            System.out.println("La taula de Eleccions Municipi s'ha importat correctament.");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    public static void importarVotsMunicipis() {
        File file = new File("./fitxers/06021606.DAT");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;
            while ((st = br.readLine()) != null) {
                int districe = Integer.parseInt(llegirSegonsLlargada(15, 2, st));
                if (districe == 99) {
                    int any = Integer.parseInt(llegirSegonsLlargada(3, 4, st));
                    int mes = Integer.parseInt(llegirSegonsLlargada(7, 2, st));
                    int eleccio_id = obtenirEleccioId(mes, any);
                    int municipi_INE = Integer.parseInt(llegirSegonsLlargada(12, 3, st));
                    int provincia_INE = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                    int municipi_id = obtenirMunicipiid(municipi_INE, provincia_INE);
                    String candidatura_codi = llegirSegonsLlargada(17, 6, st);
                    int candidatura_id = obtenirCandidatura_id(candidatura_codi);
                    int vots = Integer.parseInt(llegirSegonsLlargada(23, 8, st));
                    String query = "INSERT INTO vots_candidatures_mun (eleccio_id,municipi_id,candidatura_id,vots)"
                            + " values (?, ?, ?, ?)";
                    PreparedStatement preparedStmt = con.prepareStatement(query);

                    preparedStmt.setInt(1, eleccio_id);
                    preparedStmt.setInt(2, municipi_id);
                    preparedStmt.setInt(3, candidatura_id);
                    preparedStmt.setInt(4, vots);
                    // execute the preparedstatement
                    preparedStmt.execute();
                }
            }
            System.out.println("La taula de Vots Candidatures per Municipi s'ha importat correctament.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int obtenirEleccioId(int mes, int any) {
        int eleccioid = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT eleccio_id " +
                    " FROM eleccions " +
                    "WHERE any = ? AND mes = ?";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1, any);
            preparedStmt.setInt(2, mes);

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

    private static int obtenirPersona_id(String nom_pers, String cog1_pers, String cog2_pers) {
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

    private static int obtenirCandidatura_id(String candidatura_codi) {
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

    private static int obtenirProvincia_id(int codiINEPro) {
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


    private static int obtenirIdComunAmbINE(int codiINEComunitat) {
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

    private static int obtenirMunicipiid(int ine_municipi, int ine_provincia){
        int municipi_id = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENTÈNCIA SELECT
            //Preparem una sentència amb paràmetres.
            String query = "SELECT municipi_id " +
                    " FROM municipis m " +
                    "INNER JOIN provincies p ON p.provincia_id = m.provincia_id " +
                    "WHERE m.codi_ine = " + ine_municipi + " && p.codi_ine = " + ine_provincia;

            PreparedStatement preparedStmt = con.prepareStatement(query);


            ResultSet rs = preparedStmt.executeQuery();

            while(rs.next()) {
                //System.out.println(rs.getInt("provincia_id"));
                municipi_id = rs.getInt("municipi_id");
                //System.out.println(municipi_id);
            }

        }catch(Exception e){
            System.out.println(e);
            return 0;
        }


        return municipi_id;
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