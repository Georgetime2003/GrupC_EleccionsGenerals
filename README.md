# Importació de dades
### Codi Importacions
Agafa el fitxer que necessita per fer la seva lectura.
```java
    File file = new File("./fitxers/02021606.DAT");
 ```
Comprovar que hi hagi text a cada linia per importar les dades cada vegada que acabi una sentència SQL.
```java
    BufferedReader br = new BufferedReader(new FileReader(file))) {
    String st;

    while ((st = br.readLine()) != null)
```
Funció per agafar un camp del fitxer .DAT en format enter i String respectivament.
```java
    int provincia_INE = Integer.parseInt(llegirSegonsLlargada(12, 2, st));
    String nom_pers = llegirSegonsLlargada(26, 25, st).trim();
```
Sentència per inserir les dades en la taula.
```java
    String query = "INSERT INTO candidats (candidatura_id, persona_id, provincia_id, num_ordre, tipus)"
            + " values (?, ?, ?, ?, ?)";
```
Afegir a la sentència SQL les dades obtingudes del fitxer .DAT tant com String com enter i un cop seguit s'executa la sentència.
```java
    preparedStmt.setInt(1, persona_id);  
    preparedStmt.setString(2, nom_pers);
    preparedStmt.execute();
```
### Codi per obtenir un camp d'un altre taula

Funció que es cridada en la varaiable d'un camp que depent d'un altre taula. Variables que pasen per poder cercar el camp corresponent.
```java
    private static int obtenirPersona_id(String nom_pers, String cog1_pers, String cog2_pers) {
    int id_persona = 0;
```
Sentència per seleccionar el camp que necessitem.
```java
    String query = "SELECT persona_id " +
                        "FROM persones " +
                    "WHERE nom = ? AND cog1 = ? AND cog2 = ?";
```            
Afegir a la sentència SQL les variables que has passat substituint els interrogants.
```java
    PreparedStatement preparedStmt = con.prepareStatement(query);
                preparedStmt.setString(1, nom_pers);
                preparedStmt.setString( 2, cog1_pers);
                preparedStmt.setString(3, cog2_pers);
``` 
Bucle que va comprovant si és la dada que està buscant. 
```java
    ResultSet rs = preparedStmt.executeQuery();
    while(rs.next()) {
        id_persona = rs.getInt("persona_id");
    }
        
```
Retorna el resultat a la funció anterior.
```java
    return id_persona;
```
   
# Consultes
- **[Categoria 1:](https://github.com/Georgetime2003/GrupC_EleccionsGenerals/blob/main/Consultes/Consultes_Categoria_1.md)** Hem fet servir funcions d'agrupar, ordenar, de caràcters i operadors 
- **[Categoria 2:](https://github.com/Georgetime2003/GrupC_EleccionsGenerals/blob/main/Consultes/Consultes_Categoria_2.md)** Hem fet servir INNER JOIN i el LEFT JOIN.
- **[Categoria 3:](https://github.com/Georgetime2003/GrupC_EleccionsGenerals/blob/main/Consultes/Consultes_Categoria_3.md)** Hem fet servir subconsultes 
- **[Categoria 4:](https://github.com/Georgetime2003/GrupC_EleccionsGenerals/blob/main/Consultes/Consultes_Categoria_4.md)** Hem fet servir el WINDOW FUNCTIONS. També conte una subconsulta, JOINs, funcions d'agrupació i d'ordenació.  

# Repositori GIT

  - El treball acabat està en la branca main.
  - Cada persona ha anat pujant en la seva branca les coses que ha anat fent.

# Tractament de fitxers
El nostre codi de tractament de fitxers el que fa és agafar un fitxer zip descomprimir-lo i guardar el sé contingut en una altra carpeta.

Bucle que va comprovant si hi ha fitxers dintre del zip.
```java
    while ((ze = zis.getNextEntry()) != null) {
```
Comprova si els fitxers no són .DAT, si no ho són torna a començar el bucle. 
```java
    if (!ze.getName().substring(ze.getName().length() - 4).equals(".DAT")) continue;
``` 
Agafa els diferents fitxers.
```java
    ZipEntry e = zf.getEntry(ze.getName());
```
 Agafa el fitxer sense.
```java
    InputStream is = zf.getInputStream(e);
```
Copia el fitxer del zip en una altra carpeta.
```java
    Files.copy(is, Paths.get("./fitxers/" + ze.getName()));
```
Mostra el missatge que el fitxer s'ha afegit a la carpeta.
```java
    System.out.println("El fitxer: " + ze.getName() + " s'ha afegit a la carpeta \"./fitxers/\"");
```
