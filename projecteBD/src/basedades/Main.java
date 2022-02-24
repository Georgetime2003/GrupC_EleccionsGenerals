package basedades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Calendar;

public class Main {

    public static Connection con;

    public static void main(String[] args) {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/mydb", "perepi", "pastanaga");

            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            //Importacio de les Comunitats Autonomes
            Importacions.importarComunitatsAutonomes();

<<<<<<< Updated upstream
=======
            //Importacio de les eleccions
            Importacions.importareleccions();

            //Importacio de provincies
            Importacions.importarProvincies();

            //Importacio de municipis
            Importacions.importarMunicipis();

            //Importacio de Partits Canditatures
            Importacions.importarPartitsCandtatures();
            //System.out.println(Importacions.treureprovincia_id(6));
            //Importacio de Vots a nivell municipal
            Importacions.importarVotsMunicipis();
>>>>>>> Stashed changes
            //Tanquem la connexi?
            //con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

       Importacions.importarProvincies();
        Importacions.importarVotsMunicipis();

        }
    }
