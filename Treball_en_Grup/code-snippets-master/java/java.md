#### Connexió a una SGBD MySQL
Codi molt simple per veure les funcions bàsiques de connexió a un SGBD MySQL i  realitzar les operacioins d'INSERT i SELECT 

Els exemples estan contextualitzats a la BD rrhh.

Podeu trobar més informació:
- Instal·lació: https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-installing.html
- Exemples: https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-examples.html

Cal afegir la llibreria mysql-connector-java-8.0.19.jar de la carpeta libs/mysql o descarregar-les del web oficial.

**INSERT**
```java
import java.sql.*;
import java.util.Calendar;

try {
	Class.forName("com.mysql.cj.jdbc.Driver");

	Connection con=DriverManager.getConnection("jdbc:mysql://<IP>:3306/rrhh","usuari","paraulapas");


	//Preparem el Date
	Calendar calendar = Calendar.getInstance();
	java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

	// the mysql insert statement
	String query = " INSERT INTO empleats (empleat_id,nom,cognoms,email,telefon,data_contractacio,feina_codi,salari)"
			+ " values (?, ?, ?, ?, ?, ?, ?, ?)";

	// create the mysql insert preparedstatement
	PreparedStatement preparedStmt = con.prepareStatement(query);
	preparedStmt.setInt    (1, 300);
	preparedStmt.setString (2, "Pere");
	preparedStmt.setString (3, "Pi");
	preparedStmt.setString (4, "perepi@sapalomera.cat");
	preparedStmt.setString (5, "972350909");
	preparedStmt.setDate   (6, startDate);
	preparedStmt.setString (7, "IT_PROG");
	preparedStmt.setFloat  (8, 5000.12f);

	// execute the preparedstatement
	preparedStmt.execute();

	//Tanquem la connexió
	con.close();
}
catch(Exception e){ 
	System.out.println(e);
}
```

**SELECT/QUERY**
```java

import java.sql.*;
import java.util.Calendar;

try {
	Class.forName("com.mysql.cj.jdbc.Driver");

	Connection con=DriverManager.getConnection("jdbc:mysql://<IP>:3306/rrhh","usuari","paraulapas");
            
	//SENTÈNCIA SELECT            
	//Preparem una sentència amb paràmetres.
	String query = "SELECT * " + 
			" FROM empleats " +
			"WHERE data_contractacio BETWEEN ? AND ?";
	PreparedStatement preparedStmt = con.prepareStatement(query);

	//Preparem les dates
	Calendar cDataInici = Calendar.getInstance();
	cDataInici.add(Calendar.YEAR, -50);
	
	Calendar cDataFi = Calendar.getInstance();
	cDataFi.add(Calendar.DATE,1);
	
	java.sql.Date dataInici = new java.sql.Date(cDataInici.getTime().getTime());
	java.sql.Date dataFi = new java.sql.Date(cDataFi.getTime().getTime());
	
	preparedStmt.setDate(1,dataInici);
	preparedStmt.setDate(2,dataFi);
	
	ResultSet rs = preparedStmt.executeQuery();
	
	/*
	Sentència sense paràmetres.
	Statement stmt=con.createStatement();
	ResultSet rs=stmt.executeQuery("SELECT * FROM empleats");
	*/
	/* while(rs.next()) Obtenir els valors per índex de columnes.
		System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));*/

	while(rs.next()) {
		System.out.println(rs.getInt("empleat_id") +
				"  " + rs.getString("nom") + 
				"  " + rs.getDate("data_contractacio"));
	}
	con.close();
}
catch(Exception e){ 
	System.out.println(e);}
}
    
```
#### Descomprimir un fitxer comprimit .zip
Exemple de descromprimir un fitxer a un directori "outoput"
```java
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;

//Obtenim el directori actual
Path pathActual = Paths.get(System.getProperty("user.dir"));

//Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "prova.zip"
String nomFitxer = "02197706_MESA.zip";

String unzipDir = "output";

Path pathFitxer = Paths.get(pathActual.toString(), "dades", nomFitxer );
Path pathUnzipDir = Paths.get(pathActual.toString(), unzipDir);

// Create zip file stream.
try (ZipArchiveInputStream fitxerZip = new ZipArchiveInputStream(
		new BufferedInputStream(new FileInputStream(pathFitxer.toString())))) {

	ZipArchiveEntry entrada;
	while ((entrada = fitxerZip.getNextZipEntry()) != null) {
		// Print values from entry.
		System.out.println(entrada.getName());
		System.out.println(entrada.getMethod()); // ZipEntry.DEFLATED is int 8

		File file = new File(Paths.get(pathActual.toString(), unzipDir, entrada.getName()).toString());
		System.out.println("Unzipping - " + file);
		
		Files.createDirectories(pathUnzipDir);

		// copiem el contingu del fitxer.
		IOUtils.copy(fitxerZip, new FileOutputStream(file));

	}
} catch (IOException e) {
	e.printStackTrace();
}
```

#### Llegir un fitxer pla
Exemple de lectura d'un fixer pla per només lectura.
```java
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import java.nio.file.*;

BufferedReader bfLector = null;
try {
 
	//Obtenim el directori actual
	Path pathActual = Paths.get(System.getProperty("user.dir"));

	//Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "03021911.DAT"
	Path pathFitxer = Paths.get(pathActual.toString(), "dades", "03021911.DAT");

	//objReader = new BufferedReader(new FileReader(pathFitxer.toString()));

	bfLector = Files.newBufferedReader(pathFitxer, StandardCharsets.ISO_8859_1);
	String strLinia;
	while ((strLinia = bfLector.readLine()) != null) {
		System.out.println(strLinia);
	}
	
} catch (IOException e) {
	e.printStackTrace();
} finally {
	try {
		if (bfLector != null)
			bfLector.close();
	} catch (IOException ex) {
		ex.printStackTrace();
	}
}
```

#### Llegir un fitxer .xls
Exemple de lectura d'un fixer XLS. l'exemple està basat en el fitxer que es troba dins d'algun dels fitxers .zip de `<Resultados Eleccions Generales - 02_199306_1.zip>` : <https://github.com/robertventura/databases/tree/master/db_eleccions_generals/data/resultats_x_municipi>

Cal afegir les llibreries de la carpeta libs/poi o descarregar-les del web oficial.

Podeu trobar més informació a:
- https://poi.apache.org/
- https://mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/ 

```java
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

try {

	//Obtenim el directori actual
	Path pathActual = Paths.get(System.getProperty("user.dir"));

	//Concatenem el directori actual amb un subdirectori "dades" i afegim el fitxer "03021911.DAT"
	String nomFitxer = "02_201904_1.xlsx";
  
	Path pathFitxer = Paths.get(pathActual.toString(), "dades",nomFitxer );
	
	FileInputStream excelFile = new FileInputStream(new File(pathFitxer.toString()));
	Workbook workbook = new XSSFWorkbook(excelFile);
	Sheet fulla = workbook.getSheetAt(0); // Obtenim la primera fulla
	
	/*
	fila 7 comencen les dades
	Columnes:
	   1: Nom de la comunitat
	   2: Codi de Provincia
	   3: Nom de la Provincia
	   4: Codi de Municipi
	   5: Nom de Municipi
	   6: Població
	   7: Número de meses
	   8: Total del cens electoral
	   9: Total de vots
	   10: Vots vàlids
	   11: Vots a candidatures
	   12: Vots en blanc
	   13: Vots nuls
	   14: shee.max_column (partits polítics)
	*/
	
	//fulla.getLastRowNum() retornem índex de la última fila
	//fila.getLastCellNum() retornem índex de la última cela dins de la fila.
	
	//Recorrem  97 files de la fulla            
	for(int i = 7; i<100; i++) {
		Row fila = fulla.getRow(i);
		//Imprimim els valors de la columna 4 i 5
		Cell cela = fila.getCell(3);
		
		System.out.print(cela.getNumericCellValue() + "--");
		
		System.out.println(fila.getCell(4).getStringCellValue() + "--");                
	}
	
	/*
	-- Construïm un interador sobre la fulla.            
	*/
	Iterator<Row> rowIterator = fulla.iterator();
	
	 // Bucle per recòrrer totes les files i columnes de la fulla
	while(rowIterator.hasNext()) {
		
		Row filaActual  = rowIterator.next();
		
		//Construïm un interador per les columnes.
		Iterator<Cell> cellIterator = filaActual.iterator();                
		while (cellIterator.hasNext()) {
			
			Cell celaActual = cellIterator.next();
			
			if (celaActual.getCellType() == CellType.STRING) {
				System.out.print(celaActual.getStringCellValue() + "--");
			} else if (celaActual.getCellType() == CellType.NUMERIC) {
				System.out.print(celaActual.getNumericCellValue() + "--");
			}

		}
		System.out.println();
	}

} catch (FileNotFoundException e) {
	e.printStackTrace();
} catch (IOException e) {
	e.printStackTrace();
}
```


