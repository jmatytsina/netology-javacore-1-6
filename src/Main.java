import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 3, 5, 1500.5);
        GameProgress progress2 = new GameProgress(95, 7, 10, 3000.0);
        GameProgress progress3 = new GameProgress(110, 9, 15, 5830.7);

        String root = "E://Games/Save Games/";
        String save1 = root + "save1.dat";
        String save2 = root + "save2.dat";
        String save3 = root + "save3.dat";
        String zip = root + "zip.zip";

        saveGame(save1, progress1);
        saveGame(save2, progress2);
        saveGame(save3, progress3);

        ArrayList<String> savesList = new ArrayList<>();
        savesList.add(save1);
        savesList.add(save2);
        savesList.add(save3);

        zipFiles(zip, savesList);
    }

    private static void saveGame(String filePath, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(progress);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void zipFiles(String filePath, ArrayList<String> objects) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(filePath))) {
            for (int i = 0; i < objects.size(); i++) {
                String object = objects.get(i);
                try(FileInputStream fis = new FileInputStream(object)) {
                    ZipEntry entry = new ZipEntry("packed_save" + (i + 1) + ".dat");
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                File file = new File(object);
                try {
                    file.delete();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
