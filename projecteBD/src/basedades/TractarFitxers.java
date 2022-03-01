package basedades;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class TractarFitxers {

    public static void crearEstructuraCarpetes() {
        String carpeta = "./resources";
        File directori = new File(carpeta);
        File tmp = new File("./resources/tmp");
        File tractat = new File("./resources/tractat");
        if (!directori.exists()) {
            directori.mkdir();
            tmp.mkdir();
            tractat.mkdir();
            System.out.println("L'estructura de carpetes s'ha creat");
            return;
        } else {
            try {
                FileUtils.deleteDirectory(directori);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("La carpeta \""+ carpeta + "\" s'ha borrat");
        }

    }

    public static void descomprimirDATsZip() {
        String fileName = "zips/02201606_MESA.zip";

        try (FileInputStream fis = new FileInputStream(fileName);
             BufferedInputStream bis = new BufferedInputStream(fis);
             ZipInputStream zis = new ZipInputStream(bis)) {

            ZipEntry ze;

            while ((ze = zis.getNextEntry()) != null) {

                if (!ze.getName().substring(ze.getName().length() - 4).equals(".DAT")) continue;

                System.out.println("File:" + ze.getName() + " s'esta descomprimint");

                File arxiu = new File(String.valueOf("./resources/tmp/" + ze.getName()));
                arxiu.createNewFile();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moureArxiusTrac() {
        String nomArxiuNoModi = "./resources/tmp/01021606.DAT";
        String nomArxiuModi = "./resources/tractat/01021606.DAT";

        File tmp = new File("./resources/tmp/");

        String[] llistat = tmp.list();

        for (String s : llistat) {
            System.out.println(s);


            File fo = new File("./resources/tmp/" + s);
            File fd = new File("./resources/tractat/" + s);

            if(!fo.exists()) continue;

            boolean creada = fo.renameTo(fd);
            if (creada) fo.delete();
            if (creada) System.out.println("L'arxiu' \""+ fo + "\" s'ha copiat a \""+ nomArxiuModi + "\"");
            else System.out.println("L'arxiu \""+ fo + "\" no s'ha copiat a \""+ nomArxiuModi + "\"");
        }

    }


}
