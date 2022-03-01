# Categoria 2

**1. Necessitem que es mostri l'id de candidats, nom, cognoms concatenats amb un espai entremig. A més a més volem que en el sexe de cada candidat en comptes de M o F que posi Home o Dona, farem ús de l'àlies "sexe".**

```sql
SELECT c.candidat_id, p.nom, CONCAT(p.cog1 , " " , p.cog2) AS cognoms, IF(p.sexe = 'M', "Home", IF(p.sexe = 'F', "Done", NULL)) AS sexe
	FROM candidats c
INNER JOIN persones p ON p.persona_id = c.persona_id;   
```

**2. Fes el necessari per mostra els candidats de la província “Barcelona” i mostra també els camps candidat_id, nom, cog1 i candidatura_id.**

```sql
SELECT c.candidat_id, p.nom, p.cog1, c.candidatura_id 
    FROM candidats c
INNER JOIN persones p ON p.persona_id = c.persona_id
INNER JOIN provincies pro ON pro.provincia_id = c.provincia_id
WHERE pro.nom = "Barcelona";
```

**3. Quants candidats hi ha en cada candidatura, mostra el nom curt de la candidatura i la seva quantitat de candidats amb el nom de camp "Quantitat de candidats"**

```sql
SELECT cs.nom_curt, COUNT(c.candidat_id) AS "Quantitat de candidats"
	FROM candidats c
INNER JOIN candidatures cs ON cs.candidatura_id = c.candidatura_id
GROUP BY cs.candidatura_id;
```

**4. Volem saber el nom llarg de les candidatures que tenen eleccions entre els mesos de gener a juny.**

```sql
SELECT cs.nom_llarg
	FROM candidatures cs
INNER JOIN eleccions e ON e.eleccio_id = cs.eleccio_id
WHERE e.mes BETWEEN 1 AND 6;
```

**5. Ens han demanat que busquem la quantitat de vots_blanc que hi ha en el municipi "Malgrat de Mar" en l'any 2016.**

```sql
SELECT SUM(em.vots_blanc)
	FROM eleccions_municipis em
INNER JOIN municipis m ON m.municipi_id = em.municipi_id
INNER JOIN eleccions e ON e.eleccio_id = em.eleccio_id
WHERE m.nom = "Malgrat de Mar" AND e.any = 2016;
```

**6. Quina és la quantitat de dones s'han presentat a candidates del tipus "T" , mostri les dades amb l’àlies de Num_Dones.**

```sql
SELECT COUNT(candidat_id) AS "Num_Dones"
	FROM candidats c
INNER JOIN persones p ON p.persona_id = c.persona_id
WHERE p.sexe = 'F' AND c.tipus = 'T';
```

**7. Volem veure el màxim de meses (num_meses) que s'han utilitzat en tots els municipis, farem servir el nom de camp municipi i l'alies "màxim de meses utilitzades" en el camp del màxim de meses.**

```sql
SELECT m.nom, MAX(em.num_meses) AS "maxim de meses utilitzades"
	FROM eleccions_municipis em
INNER JOIN municipis m ON m.municipi_id = em.municipi_id
GROUP BY m.municipi_id;
```

**8. Fes una consulta per saber el nom de totes les províncies i quants candidats hi han per província. Ordenat per nom de província.**

```sql
SELECT p.nom AS Provincia, COUNT(c.candidat_id) AS num_candidats
	FROM candidats c
INNER JOIN provincies p ON p.provincia_id = c.provincia_id
GROUP BY p.nom
ORDER BY p.nom;
```

**9. Mostrar el nom i el primer cognom dels candidats i el nom llarg de les seves candidatures amb l'àlies "candidatura". Ordenat primer pel nom i després pel cognom de la persona.**

```sql
SELECT p.nom, p.cog1 AS cognoms, cs.nom_llarg
	FROM candidats c
INNER JOIN persones p ON p.persona_id = c.persona_id
INNER JOIN candidatures cs ON cs.candidatura_id = c.candidatura_id
ORDER BY p.nom, p.cog1;
```

**10. Mostrar el nom i el primer cognom, units en un camp separats per una coma, de tots els candidats i mostra només el nom curt de les candidatures i el nom de les eleccions que van tindre eleccions l'any 2016. Utilitzant els alies de "candidat", "candidatura i "eleccio" en els camps corresponents.**

```sql
SELECT CONCAT(p.nom , " , " , p.cog1) "candidat", cs.nom_curt "candidatura" , e.nom "eleccio"
	FROM candidats c
INNER JOIN persones p ON p.persona_id = c.persona_id
LEFT JOIN candidatures cs ON cs.candidatura_id = c.candidatura_id
LEFT JOIN eleccions e ON e.eleccio_id = cs.eleccio_id
WHERE e.any = 2016;
```