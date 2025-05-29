package mom.zesty.skriva.managers;

import mom.zesty.skriva.Skriva;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    public File getFile(String path) {

        File file = new File(path);

        if (file.exists()) {
            return new File(path);
        } return null;
    }

    public boolean fileExists(String path) {

        File file = new File(path);
        return file.exists();

    }

    public void createFile(String path) {

        File file = new File(path);
        file.getParentFile().mkdirs();

        try {
            file.createNewFile();
        } catch (IOException e) {
            Skriva.getInstance().getLogger().info("Failed to create file " + file + " due to " + e.getMessage());
        }

    }

    public void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    public void writeInFile(File file, String content) {
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(System.lineSeparator() + content);
            writer.close();
        } catch (IOException e) {
            Skriva.getInstance().getLogger().info("Failed to write in file " + file + " due to " + e.getMessage());
        }
    }

    public String readLine(File file, int line) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            for (int i = 0; i < line; i++) {
                reader.readLine();
            }

            String read = reader.readLine();

            reader.close();
            return read;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setLine(File file, int[] line, String to) {

        List<String> lines = getContent(file);

        for (int i : line) {
            if (i < 0) { continue; }

            while (i >= lines.size()) {
                lines.add("");
            }
            lines.set(i, to);
        }

        try {
            FileWriter writer = new FileWriter(file);
            for (String currentLine : lines) {
                writer.write(currentLine + System.lineSeparator());
            }
            writer.close();

        } catch (IOException e) {
            Skriva.getInstance().getLogger().info("Failed to set line in file " + file + " due to " + e.getMessage());
        }
    }

    public String getTextBefore(String before, String full) {
        int index = before.indexOf(full);
        if (index != -1) {
            return before.substring(0, index);
        }
        return null;
    }

    public String getTextAfter(String after, String full) {
        int index = after.indexOf(full);
        if (index != -1) {
            return after.substring(index + full.length());
        }
        return null;
    }

    public List<Integer> linesThatContainString(File file, String string) {

        List<Integer> lines = new ArrayList<>();

        int looped = 0;
        for (String line : getContent(file)) {
            looped++;
            if (line.contains(string)) {
                lines.add(looped);
            }
        }
        return lines;

    }

    public void replaceAll(File file, String original, String to) {

        Path path = file.toPath();

        try {
            Path temp = Files.createTempFile("replace", ".tmp");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = Files.newBufferedWriter(temp);

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line.replaceAll(original, to));
                writer.newLine();
            }

            reader.close();
            writer.close();

            Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            Skriva.getInstance().getLogger().info("Failed to replace content in file " + file + " due to " + e.getMessage());
        }
    }

    public List<String> getContent(File file) {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
            return list;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
