package me.monster.auto.resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 3:24 下午
 */
public class FileUtils {

    public static String getContent(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        List<String> allLines = Files.readAllLines(Paths.get(path));
        StringBuilder jsonBuilder = new StringBuilder();
        for (String allLine : allLines) {
            jsonBuilder.append(allLine);
        }
        return jsonBuilder.toString();
    }
}
