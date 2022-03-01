# Categoria 1

**1. Mostra el codi de persona, nom i el seu cognom en una mateixa columna a la qual li afegirem un àlies ( nom_complet ) de les persones que hagin nascut en 1990.**
```sql
SELECT persona_id, CONCAT(nom, cog1) AS nom_complet
	FROM persones
WHERE YEAR(data_naixement) = 1990;
```
**2. Quina és la quantitat de persones de gènere femení que no tenen el segon cognom registrat en la base de dades.**
```sql
SELECT COUNT(*)
	FROM persones
WHERE sexe = 'F' AND cog2 IS NULL;
```
**3. Necessitem saber els vots vàlids, vots en blanc, i vots nuls on el seu número de meses sigui un mínim de 2. Ordenals pel seu ID de municipi.**
```sql
SELECT municipi_id, vots_valids, vots_blanc, vots_nuls
	FROM eleccions_municipis
WHERE num_meses > 2
ORDER BY municipi_id;
```
**4. Mostra la província_id, el número d'escons i el nom de cada província amb la seva primera lletra en minúscula i la resta en majúscula.**
```sql
SELECT provincia_id,num_escons, CONCAT(LOWER(LEFT(nom,1)), UPPER(SUBSTRING(nom, 2,LENGTH(nom)))) AS nom
	FROM provincies;
```
**5. Volem saber quins són els candidats i quants vots han obtingut la província de Girona (26).**
```sql
SELECT candidats_obtinguts, vots
	FROM vots_candidatures_prov
WHERE provincia_id = 26;
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
SELECT COUNT(*) quants
    FROM persones
WHERE nom RLIKE '^R' AND month(data_naixement) = 2;
```
**8. Volem calcular la mitjana de vots emesos per municipi.**
```sql
SELECT municipi_id, AVG(vots_emesos)
    FROM eleccions_municipis 
GROUP BY municipi_id;
```
**9. Mostra el màxim de número d'escons obtinguts per una província i mostra també l'id de la província i el codi_ine.**
```sql
SELECT num_escons, provincia_id, codi_ine
	FROM provincies
ORDER BY num_escons DESC
LIMIT 1;
```
**10. Presenta les següents dades en una sola columna :  el nom i els dos cognoms que tinguin una longitud inferior de 20 caràcters. En una altra columna mostra l'ID de la persona i el seu sexe amb una barra (/) entremig.**
```sql
SELECT CONCAT(nom, cog1, cog2) AS nom_complet, CONCAT(persona_id, "/",sexe) AS "id/sexe"
	FROM persones
WHERE LENGTH(CONCAT(nom, cog1, cog2)) < 20;
```
