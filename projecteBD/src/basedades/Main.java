package basedades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;

public class Main {

    public static Connection con;

    public static void main(String[] args) {

        //Descomprimir els .DAT de un zip concret
        TractarFitxers.descomprimirDATsZip();

        //Fer la conexio a la base de dades i les importacions
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/mydb", "perepi", "pastanaga");

            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            //Importacio de les Comunitats Autonomes
            Importacions.importarComunitatsAutonomes();

            //Importacio de les Provincies
            Importacions.importarProvincies();

            //Importacio de les Municipis
            Importacions.importarMunicipis();

            //Importacio de Eleccions
            Importacions.importarEleccions();

            //Importacio de eleccions_municipis
            Importacions.importarEleccionsMunicipi();

            //Importacio de les Persones
            Importacions.importarPersones();

            //Importacio de les Candidatures
            Importacions.importarCandidatures();

            //Importacio de les Candidats
            Importacions.importarCandidats();

            //Importacio de les Vots Candidatures per Provincies
            Importacions.importarVotsProvincies();

            //Importacio de les Vots Candidatures per Provincies
            Importacions.importarVotsCandidatures_ca();

            //Importacio de Vots Candidatures per Municipis
            Importacions.importarVotsMunicipis();


            //Tanquem la connexió
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}