import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
        saveGame("D:/Games/savegames/save1.dat", new GameProgress(20, 5, 10, 34.5));
        saveGame("D:/Games/savegames/save2.dat", new GameProgress(25, 7, 12, 45.7));
        saveGame("D:/Games/savegames/save3.dat", new GameProgress(31, 9, 15, 42.8));
        zipFiles("D:/Games/savegames/zip.zip", "D:/Games/savegames/save1.dat"
                , "D:/Games/savegames/save2.dat", "D:/Games/savegames/save3.dat");
    }

    private static void saveGame(String filePath, GameProgress progress) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(progress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void zipFiles(String zipPath, String... filesPath) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String filePath : filesPath) {
                File file = new File(filePath);
                if (file.exists()) {
                    FileInputStream inputStream = new FileInputStream(filePath);
                    zos.putNextEntry(new ZipEntry(file.getName()));
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                    inputStream.close();
                    file.delete();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
