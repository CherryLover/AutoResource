package me.monster.auto.resource;

import me.monster.auto.resource.bean.MdImage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 3:24 下午
 */
public class FileUtils {

    public static String getContent(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        List<String> allLines = Files.readAllLines(filePath);
        StringBuilder jsonBuilder = new StringBuilder();
        for (String allLine : allLines) {
            jsonBuilder.append(allLine);
        }
        return jsonBuilder.toString();
    }

    public static void writeImageMdFile(Path filePath, List<MdImage> imgList) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        Files.write(filePath, "# Bing Images".getBytes());
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, imgList.get(0).getLargeFormat().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, "|      |      |      |".getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, "| :----: | :----: | :----: |".getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        int i = 1;
        for (MdImage images : imgList) {
            Files.write(filePath, ("|" + images.toString()).getBytes(), StandardOpenOption.APPEND);
            if (i % 3 == 0) {
                Files.write(filePath, "|".getBytes(), StandardOpenOption.APPEND);
                Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            }
            i++;
        }
        if (i % 3 != 1) {
            Files.write(filePath, "|".getBytes(), StandardOpenOption.APPEND);
        }
    }

    public static void rewriteFile(Path path, String content) throws IOException {
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }
}
