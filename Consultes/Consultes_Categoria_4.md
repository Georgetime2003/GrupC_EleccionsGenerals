# Categoria 4

**1. Volem saber quins són els guanyadors de cada elecció per província. Els camps que s'han de mostrar són el nom de província amb l'àlies de Província, el nom curt de les candidatures amb l'àlies Candidatura els vots de cada candidatura, el màxim de vots que ha tingut aquella província en aquelles eleccions posant l’àlies de vots_guanyador i marcar en un altre camp si ha sigut el guanyador posant un 1 o si no un 0. Ordenat pel nom de província i eleccions_id.**
```sql
SELECT p.nom AS Provincia , c.nom_curt AS Candidatura, vcp.vots, MAX(vcp.vots) 
OVER (PARTITION BY vcp.provincia_id) AS vots_guanyador, 
IF(vots IN (SELECT MAX(vots) provincies
				FROM vots_candidatures_prov vcp
            INNER JOIN candidatures c ON c.candidatura_id = vcp.candidatura_id
			GROUP BY vcp.provincia_id, c.eleccio_id), 1, 0) AS Guanyadors
	FROM vots_candidatures_prov vcp
INNER JOIN candidatures c ON c.candidatura_id = vcp.candidatura_id
INNER JOIN provincies p ON vcp.provincia_id = p.provincia_id
GROUP BY vcp.provincia_id, c.eleccio_id, vcp.candidatura_id
ORDER BY p.nom, c.eleccio_id;
```