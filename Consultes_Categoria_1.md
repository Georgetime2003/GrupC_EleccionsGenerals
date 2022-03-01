# Categoria 1

**1. Mostra el codi de persona, nom i el seu cognom en una mateixa columna a la qual li afegirem un àlies ( nom_complet ) de les persones que hagin nascut en 1990.**
```sql
SELECT persona_id, CONCAT(nom, cognom) AS nom_complet
	FROM persones
WHERE YEAR(data_naixament) = 1990;
```
**2. Quina és la quantitat de persones de gènere femení que no tenen el segon cognom registrat en la base de dades.**
```sql
SELECT COUNT(*)
	FROM persones
WHERE sexe = F AND cog2 IS NULL;
```
**3. Necessitem saber els vots vàlids, vots en blanc, i vots nuls on el seu número de mesos sigui un mínim de 2. Agrupals pel seu ID de municipi.**
```sql
SELECT municipi_id, vots_valids, vots_blanc, vots_nul
	FROM eleccions_municipis
WHERE num_meses > 2
GROUP BY municipi_id;
```
**4. Mostra la província_id, el número d'escons i el nom de cada província amb la seva primera lletra en majúscula i la resta en minúscula.**
```sql
SELECT circumscripcio_id, CONCAT(UPPER(LEFT(nom,1)), LOWER(SUBSTRING(nom, 2,LENGTH(nom)))) AS nom
	FROM provincies;
```
**5. Volem saber quins són els candidats i quants vots han obtingut.**
```sql
SELECT candidats_obtinguts, provincia_id, vots
	FROM vots_candidatures_prov
WHERE provincia_id = ;  #id de girona.
```
**6. Mostra els homes que tinguin un DNI acabat en 'H' i que hagin nascut entre el 1990 i el 1999.**
```sql
SELECT persona_id, sexe, dni, data_naixement
	FROM persones
WHERE YEAR(data_naixement) BETWEEN 1990 AND 1999
AND dni LIKE '%H';
```
**7. Quin és el total de persones que el seu nom comença per la lletra R i han nascut el mes de febrer?**
```sql
SELECT count(*) quants
    FROM persones
WHERE nom RLIKE '^R' AND month(data_naixament) = 2;
```
**8. Volem calcular la mitjana de vots emesos per municipi i mostrar-los de forma ascendent.**
```sql
SELECT avg(vots_emesos), avg(vots_valids)
    FROM eleccions_municipis 
GROUP BY miunicipi_id
ORDER BY vots_emesos;
```
**9. Mostra el número d'escons obtinguts per cada província i mostrals ordenats mitjançant el seu codi INE de forma descendent.**
```sql
SELECT num_escons, provincia_id, codi_ine
	FROM provincies
GROUP BY provincia_id
ORDER BY codi_ine DESC;
```
**10. Presenta les següents dades en una sola columna :  el nom i els dos cognoms que tinguin una longitud inferior de 20 caràcters . En una altra columna mostra l'ID de la persona i el seu sexe amb una barra (/) entremig.**
```sql
SELECT CONCAT(nom, cog1, cog2) AS nom_complet, CONCAT(persona_id, "/",sexe) AS "id/sexe"
	FROM persones;
```