# Categoria 3

**1. Quin és el nom i cognom dels candidats a la província de Córdoba?**
```sql
SELECT p.persona_id, p.nom, p.cog1, p.cog2
	FROM persones p
    INNER JOIN candidats c ON c.persona_id = p.persona_id
WHERE c.provincia_id = ( SELECT provincia_id
						FROM provincies
                        WHERE nom = 'Córdoba');

```
**2. Mostra el nom curt i nom llarg de les candidatures amb el numero d'ordre 1.**
```sql
SELECT nom_curt, nom_llarg
	FROM candidatures 
WHERE candidatura_id IN (SELECT candidatura_id
						FROM candidats
						WHERE num_ordre = 1);
```
**3. Fes un recompte dels municipis van participar a les eleccions de l'any 2016.**
```sql
SELECT COUNT(municipi_id)
	FROM eleccions_municipis 
    WHERE eleccio_id IN(SELECT eleccio_id
						FROM eleccions
                        WHERE any = 2016);

```
**4. Mostra les elecciones amb més vots en blanc que les eleccions de l'any 2017.**
```sql
SELECT eleccio_id, vots_blanc
	FROM eleccions_municipis
WHERE vots_blanc > (SELECT em.vots_blanc
						FROM eleccions_municipis em
					INNER JOIN eleccions e ON e.eleccio_id = em.eleccio_id
					WHERE e.any = 2017);
```
**5. Quin és el percentatge de vots vàlids de cada municipi en la provincia de Almería?**
```sql
SELECT CONCAT(
ROUND(
em.vots_valids * 100 / (
SELECT SUM(em2.vots_valids)
		FROM eleccions_municipis em2
        INNER JOIN municipis m2 ON m2.municipi_id = em2.municipi_id
        INNER JOIN provincies p2 ON m2.provincia_id = p2.provincia_id
        WHERE p2.nom = 'Almería'
        )
	, 2 ),'%' ) AS porcentaje, m.nom
	FROM eleccions_municipis em
	INNER JOIN municipis m ON m.municipi_id = em.municipi_id
	INNER JOIN provincies p ON m.provincia_id = p.provincia_id
        WHERE p.nom = 'Almería'
        GROUP BY m.nom, em.vots_valids ;
```
