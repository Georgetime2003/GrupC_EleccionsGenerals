import java.sql.*;
import java.util.Calendar;

public class ImportarComunitats {
	public static void main(String args[]) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://192.168.75.129:3306/rrhh", "usuari", "paraulapas");

			// Preparem el Date
			Calendar calendar = Calendar.getInstance();
			java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

			// the mysql insert statement
			String query = " INSERT INTO empleats (empleat_id,nom,cognoms,email,telefon,data_contractacio,feina_codi,salari)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

			// create the mysql insert preparedstatement
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setInt(1, 300);
			preparedStmt.setString(2, "Pere");
			preparedStmt.setString(3, "Pi");
			preparedStmt.setString(4, "perepi@sapalomera.cat");
			preparedStmt.setString(5, "972350909");
			preparedStmt.setDate(6, startDate);
			preparedStmt.setString(7, "IT_PROG");
			preparedStmt.setFloat(8, 5000.12f);

			// execute the preparedstatement
			preparedStmt.execute();

			// Tanquem la connexió
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
