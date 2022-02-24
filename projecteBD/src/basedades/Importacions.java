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
                int codiINE = Integer.parseInt(llegirSegonsLlargada(12, 2, st));
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
                String nomProvincia = llegirSegonsLlargada(15, 50, st);
                int codiINEComunitat = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                int codiINE = Integer.parseInt(llegirSegonsLlargada(12, 2, st));
                int codiComunitat = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                int numEscons = Integer.parseInt(llegirSegonsLlargada(150, 6, st));

                if (codiINE != 99 && codiComunitat != 99) {
                    System.out.println(nomProvincia + " " + codiINE + " " + numEscons + " " + codiComunitat);
                }


            }
        } catch (IOException e) {
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
                int codiINE = Integer.parseInt(llegirSegonsLlargada(12, 2, st));
                int codiComunitat = Integer.parseInt(llegirSegonsLlargada(10, 2, st));
                int numEscons = Integer.parseInt(llegirSegonsLlargada(150, 6, st));

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

    private static int treureIdComunAmbINE(String codiINEComuni) {
        int id_comuni = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENT�NCIA SELECT
            //Preparem una sent�ncia amb par�metres.
            String query = "SELECT comunitat_aut_id " +
                    " FROM comunitats_autonomes " +
                    "WHERE codi_ine = ";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getInt("empleat_id") +
                        "  " + rs.getString("nom") +
                        "  " + rs.getDate("data_contractacio"));
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        return id_comuni;
    }

    public static void importarVotsMunicipis() {
        File file = new File("./fitxers/06021606.DAT");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String st;
            while ((st = br.readLine()) != null) {
                int eleccio_id = extreureEleccio_id();
                int municipi_id = extreureMunicipi_id();
                int candidatura_id = extreureCandidatura_id();
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static int extreureEleccio_id() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENT�NCIA SELECT
            //Preparem una sent�ncia amb par�metres.
            String query = "SELECT eleccio_id " +
                    " FROM eleccions_municipis " +
                    "WHERE eleccio_id = ";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            ResultSet rs = preparedStmt.executeQuery();
            return rs.getInt("eleccio_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int extreureMunicipi_id() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENT�NCIA SELECT
            //Preparem una sent�ncia amb par�metres.
            String query = "SELECT municipi_id " +
                    " FROM eleccions_municipis " +
                    "WHERE municipi_id = ";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            ResultSet rs = preparedStmt.executeQuery();
            return rs.getInt("municipi_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int extreureCandidatura_id() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //SENT�NCIA SELECT
            //Preparem una sent�ncia amb par�metres.
            String query = "SELECT candidatura_id " +
                    " FROM candidatures " +
                    "WHERE candidatura_id = ";
            PreparedStatement preparedStmt = con.prepareStatement(query);


            ResultSet rs = preparedStmt.executeQuery();
            return rs.getInt("eleccio_id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


}

