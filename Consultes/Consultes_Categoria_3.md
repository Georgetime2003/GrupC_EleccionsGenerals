# Categoria 3

**1. Quin és el nom i cognom dels candidats a la província de Girona?**
```sql
SELECT p.nom, p.cog1, p.cog2
	FROM persones p
WHERE persona_id = ( SELECT c.persona_id
					    FROM candidats c
                    INNER JOIN provincies p2 ON p2.provincia_id = c.provincia_id
                    WHERE p2.nom = 'Girona');

```
**2. Mostra el nom curt i nom llarg de les candidatures de tipus T.**
```sql
SELECT cs.nom_curt, cs.nom_llarg
	FROM candidatures cs
INNER JOIN candidats c ON c.candidatura_id = cs.candidatura_id
WHERE c.candidatura_id = (SELECT candidatura_id
							    FROM candidats
							WHERE tipus = 'T');
```
**3. Fes un recompte dels municipis van participar a les eleccions de l'any 2004.**
```sql
SELECT COUNT(municipi_id)
	FROM eleccions_municipis 
WHERE eleccio_id IN(SELECT eleccio_id
						FROM eleccions
                    WHERE any = 2004);

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
**5. Quin és el percentatge de vots vàlids de cada candidatura del municipi de Mataro en les eleccions amb un id 1.**
```sql
SELECT c.candidatura_id, m.nom , em.eleccio_id, CONCAT(ROUND((em.vots_valids/ (SELECT SUM(em2.vots_valids)
																					FROM eleccions_municipis em2 ) *100 ), 2 ),'%' ) AS porcentaje
    FROM eleccions_municipis em
INNER JOIN candidatures c ON c.eleccio_id = em.eleccio_id
INNER JOIN municipis m ON m.municipi_id = em.municipi_id
WHERE m.municipi_id IN (SELECT m2.municipi_id
							FROM municipis m2
						WHERE m2.nom = 'Mataro') AND em.eleccio_id = 1;
```