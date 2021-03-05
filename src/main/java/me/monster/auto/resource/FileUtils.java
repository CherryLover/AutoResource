package me.monster.auto.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.monster.auto.resource.bean.BingImageElement;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/5 3:24 下午
 */
public class FileUtils {
    private static final String BING_FILE_PATH = System.getProperty("user.dir") + "/BingImage.json";

    static void appendBingImage(BingImageElement element) throws IOException {
        List<String> allLines = Files.readAllLines(Paths.get(BING_FILE_PATH));
        StringBuilder jsonBuilder = new StringBuilder();
        for (String allLine : allLines) {
            jsonBuilder.append(allLine);
        }

        Type typeOfImageElement = new TypeToken<List<BingImageElement>>() {
        }.getType();
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        List<BingImageElement> imageList = gson.fromJson(jsonBuilder.toString(), typeOfImageElement);
        if (imageList == null) {
            imageList = new ArrayList<>();
        }
        imageList.add(element);
        String json = gson.toJson(imageList);
        Files.write(Paths.get(BING_FILE_PATH), json.getBytes(StandardCharsets.UTF_8));
    }

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
