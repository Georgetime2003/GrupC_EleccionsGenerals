package basedades;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


public class TractarFitxers {

    public static void descomprimirDATsZip() {
        String fileName = "./zips/02201606_MESA.zip";

        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {
                ZipFile zf = new ZipFile(fileName);

            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {

                if (!ze.getName().substring(ze.getName().length() - 4).equals(".DAT")) continue;

                ZipEntry e = zf.getEntry(ze.getName());

                InputStream is = zf.getInputStream(e);

                Files.copy(is, Paths.get("./fitxers/" + ze.getName()));

                System.out.println("El fitxer: " + ze.getName() + " s'ha afegit a la carpeta \"./fitxers/\"");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
