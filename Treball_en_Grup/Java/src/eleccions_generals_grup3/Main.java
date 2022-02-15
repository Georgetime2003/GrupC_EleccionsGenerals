package eleccions_generals_grup3;
import java.sql.*;
import java.util.Calendar;
public class Main {

    public static void main(String[] args) {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con=DriverManager.getConnection("jdbc:mysql://192.168.56.101:3306/mydb","perepi","pastanaga");


            //Preparem el Date
            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            // the mysql insert statement
            String query = " INSERT INTO empleats (candidatura_id,eleccio_id,municipi_id,vots)"
                    + " values (?, ?, ?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt    (1, 300);
            preparedStmt.setString (2, "Pere");
            preparedStmt.setString (3, "Pi");
            preparedStmt.setString (4, "perepi@sapalomera.cat");

            // execute the preparedstatement
            preparedStmt.execute();

            //Tanquem la connexió
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}
