package basedades;

import java.sql.Connection;

public class Main {

    public static Connection con;

    public static void main(String[] args) {

        /*
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

            //Importacio del Municipis
            Importacions.importarMunicipis();

            //Importacio de les Persones
            Importacions.importarPersones();

            //Tanquem la connexió
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }*/

        //Importacions.proves();


        //Borrar estructura Fitxers
        TractarFitxers.crearEstructuraCarpetes();

        //Descomprimir Fitxers
        TractarFitxers.descomprimirDATsZip();

        //Moure fitxers tractats
        TractarFitxers.moureArxiusTrac();

        }
    }
