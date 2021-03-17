package me.monster.auto.resource;

import me.monster.auto.resource.bean.MdImage;

import java.io.*;
import java.nio.charset.Charset;
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
    public static final String UPDATE_SP = "更新时间：";

    public static String getContent(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
            return "";
        }
        InputStream inputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] buffer = new byte[1024];
        int len = -1;
        StringBuilder sb = new StringBuilder();
        while ((len = (bufferedInputStream.read(buffer))) != -1) {
            sb.append(new String(buffer, 0, len, Charset.defaultCharset()));
        }
        return sb.toString();
    }

    public static void writeImageMdFile(Path filePath, String title, List<MdImage> imgList) throws IOException {
        writeImageMdFile(filePath, title, null, imgList);
    }

    public static final int IMG_MD_PREVIEW_SPAN_COUNT = 3;

    public static void writeImageMdFile(Path filePath, String title, String[] tableTitle, List<MdImage> imgList) throws IOException {
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        title = "# " + title;
        Files.write(filePath, title.getBytes());
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, imgList.get(0).getLargeFormat().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        StringBuilder sb = new StringBuilder();
        if (tableTitle == null || tableTitle.length != IMG_MD_PREVIEW_SPAN_COUNT) {
            sb.append("|      |      |      |");
        } else {
            sb.append("|");
            for (String s : tableTitle) {
                sb.append("   ").append(s).append("   |");
            }
        }
        Files.write(filePath, sb.toString().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, "| :----: | :----: | :----: |".getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        int i = 1;
        for (MdImage images : imgList) {
            Files.write(filePath, ("|" + images.toString()).getBytes(), StandardOpenOption.APPEND);
            if (i % IMG_MD_PREVIEW_SPAN_COUNT == 0) {
                Files.write(filePath, "|".getBytes(), StandardOpenOption.APPEND);
                Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            }
            i++;
        }
        if (i % IMG_MD_PREVIEW_SPAN_COUNT != 1) {
            Files.write(filePath, "|".getBytes(), StandardOpenOption.APPEND);
        }
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
        Files.write(filePath, (UPDATE_SP + TimeUtils.currentTime()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }

    public static void rewriteFile(Path path, String content) throws IOException {
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
    }

    public static void updateTime(Path mdFilePath) throws IOException {
        String content = getContent(mdFilePath.toString());
        String[] split = content.split(UPDATE_SP);
        if (split == null || split.length == 0) {
            return;
        }
        Files.delete(mdFilePath);
        Files.createFile(mdFilePath);
        if (split.length < 2) {
            Files.write(mdFilePath, split[0].getBytes(StandardCharsets.UTF_8));
            Files.write(mdFilePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            Files.write(mdFilePath, (UPDATE_SP + TimeUtils.currentTime()).getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            return;
        }
        split[1] = UPDATE_SP + TimeUtils.currentTime();
        Files.write(mdFilePath, split[0].getBytes(StandardCharsets.UTF_8));
        Files.write(mdFilePath, split[1].getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
    }
}
