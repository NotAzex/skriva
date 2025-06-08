package mom.zesty.skriva.managers;

import mom.zesty.skriva.Skriva;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FileManager {

    private final Logger logger;

    public FileManager() {
        this.logger = Skriva.getInstance().getLogger();
    }

    public File getFile(String path) {
        File file = new File(path);
        return file.exists() ? file : null;
    }

    public boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }

    public void createFile(String path) {

        Path filePath = Paths.get(path);

        try {
            Files.createDirectories(filePath.getParent());
            Files.createFile(filePath);
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            logger.info("Failed to create file " + filePath + " due to " + e.getMessage());
        }

    }

    public void deleteFile(String path) {

        try {
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException e) {
            logger.info("Failed to delete file " + path + " due to " + e.getMessage());
        }

    }

    public void writeInFile(File file, String content) {
        try {
            Files.writeString(file.toPath(),
                    System.lineSeparator() + content,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.info("Failed to write in file " + file + " due to " + e.getMessage());
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
            logger.info("Failed to set line in file " + file + " due to " + e.getMessage());
        }
    }

    public String getTextBefore(String before, String full) {
        int index = full.indexOf(before);
        if (index != -1) { return full.substring(0, index); }
        return null;
    }

    public String getTextAfter(String after, String full) {
        int index = full.indexOf(after);
        if (index != -1) { return full.substring(index + after.length()); }
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
            logger.info("Failed to replace content in file " + file + " due to " + e.getMessage());
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
