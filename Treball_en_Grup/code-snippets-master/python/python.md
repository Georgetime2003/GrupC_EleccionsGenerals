#### Connexió a una SGBD MySQL
Codi molt simple per veure les funcions bàsiques de connexió a un SGBD MySQL i  realitzar les operacioins d'INSERT i SELECT 

Els exemples estan contextualitzats a la BD rrhh.

Podeu trobar més informació:
- Instal·lació: https://dev.mysql.com/doc/connector-python/en/connector-python-installation.html
- Exemples: https://dev.mysql.com/doc/connector-python/en/connector-python-examples.html

**INSERT**
```python

import mysql.connector
import datetime

cnx = mysql.connector.connect(host='127.0.0.1',user='usuari',password='paraulapas', database='rrhh')
cursor = cnx.cursor()

avui = datetime.datetime.now().date()
#dema = datetime.now().date() + timedelta(days=1)
#date(1977, 6, 14)

stm_insert_empleat = ("INSERT INTO empleats "
            "(empleat_id, nom,cognoms,email,data_contractacio,feina_codi,salari) "
            "VALUES (%s, %s, %s, %s, %s, %s, %s)")
dades_empleat = (500,'Geert', 'Vanderkelen','gvanderkelen@sapalomera.cat', tomorrow, 'IT_PROG',77.99)

# Executem l'INSERT
cursor.execute(stm_insert_empleat, dades_empleat)
# Si la taula tenia un valor autoincremental aquest es pot recollir mitjançant lastrowid o _last_insert_id.
empleat_id = cursor.lastrowid    
print(cursor._last_insert_id)

# Ens assegurem de realitzar un commit a la BD
cnx.commit()
#allibarem recursos
cursor.close()
cnx.close()
```

**SELECT/QUERY**
```python

import mysql.connector
import datetime

cnx = mysql.connector.connect(host='127.0.0.1',user='usuari',password='paraulapas', database='rrhh')
cursor = cnx.cursor()

query = ("SELECT empleat_id,nom,cognoms,data_contractacio 
         "   FROM empleats "
         "WHERE data_contractacio BETWEEN %s AND %s")

inici = datetime.date(1990, 1, 1)
fi = datetime.date(2016, 12, 31)

cursor.execute(query, (inici, fi))

for (e_id, e_nom, e_cognoms, e_data_contractacio) in cursor:
    print("{} - {}, {} va ser contractat el {:%d %b %Y}".format(e_id,e_nom,e_cognoms,e_data_contractacio))

cursor.close()
cnx.close()
    
```
#### Descomprimir un fitxer comprimit .zip
Exemple de lectura d'un fixer pla per només lectura.
```python
import zipfile
import os

dirActual = os.path.dirname(__file__)
print (dirActual)
nomFitxerZip = "prova.zip"
dirUnzip = os.path.join(os.path.dirname(__file__) , "unzip")
pathFitxerZip = os.path.join(dirActual,nomFitxerZip)
print (dirUnzip)

# Extraiem el contingut de pathFitxerZip a dirUnzip
with zipfile.ZipFile(pathFitxerZip, 'r') as zipRef:
zipRef.extractall(dirUnzip)
```

#### Llegir un fitxer pla
Exemple de lectura d'un fixer pla per només lectura.
```python
import sys

# Nom o path del fitxer
pathFitxer = "00021911.DAT"

try :
    # Intentem obrir el fitxer en només lectura
    with open(pathFitxer, "r") as fitxer:
        for linia in fitxer:
            # Tractem la línia del fitxer
            print(linia)
except OSError as e:
    print("No s'ha pogut obrir el fitxer " + nomFitxer)
```

#### Llegir un fitxer .xls
Exemple de lectura d'un fixer XLS. l'exemple està basat en el fitxer que es troba dins d'algun dels fitxers .zip de `<Resultados Eleccions Generales - 02_199306_1.zip>` : <https://github.com/robertventura/databases/tree/master/db_eleccions_generals/data/resultats_x_municipi>

Podeu trobar més informació a:
- https://www.datacamp.com/community/tutorials/python-excel-tutorial
- Documentació oficial de openpyxl https://openpyxl.readthedocs.io/en/stable/- 
- Documentació oficial de Pandas https://pandas.pydata.org/docs/

```python
import os
import pandas as pd # pip install pandas / pip install xlrd
import openpyxl as xl

#Tractar un fitxer Excel
dirActual = os.path.dirname(__file__)
nomFitxerXls = "02_201904_1.xlsx"
pathFitxerXls = os.path.join(dirActual,nomFitxerXls)

wb = xl.load_workbook(pathFitxerXls, read_only=True)
sheet = wb.get_sheet_by_name("Municipios")
#print (sheet.max_row) #retorna la quantiat màxima de files
#print (sheet.max_column) #retorna la quantitat màixma de columnes
"""
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

"""
for i in range(7,100):
    print (i, sheet.cell(row=i,column=4).value
            , sheet.cell(row=i,column=5).value )
```


