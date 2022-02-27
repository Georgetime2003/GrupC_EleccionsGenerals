package basedades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;

public class Main {

    public static Connection con;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://192.168.75.129:3306/mydb", "perepi", "pastanaga");

            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            //Importacio de les Comunitats Autonomes
            Importacions.importarComunitatsAutonomes();

            //Importacio de les eleccions
            Importacions.importareleccions();

            //Importacio de provincies
            Importacions.importarProvincies();

            //Immportacio de municipis
            Importacions.importarMunicipis();

            //Importacio de Partits Candidatures
            Importacions.importarPartitsCandtatures();

            //Importacio d'Eleccions Municipis
            Importacions.importarEleccionsMunicipi();
            //System.out.println(Importacions.treureprovincia_id(6));

            //Tanquem la connexi?
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        //Importacions.importarProvincies();

        }
    }
