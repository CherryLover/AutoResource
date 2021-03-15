package me.monster.auto.resource.tool;

import com.google.gson.Gson;
import me.monster.auto.resource.bean.RunConfig;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @description
 * @author: Created jiangjiwei in 2021/3/11 10:39 上午
 */
public class ConfigParser {
    private ConfigParser() {
    }

    public static ConfigParser getInstance() {
        return ConfigParserHolder.instance;
    }

    public static void main(String[] args) {
//        try {
//            parse("run_config.yml");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void parse(String fileName) throws IOException, IllegalAccessException {
        RunConfig runConfig = new RunConfig();

        List<String> configAllLineList = Files.readAllLines(Paths.get(fileName));
        for (String lineContent : configAllLineList) {
            if (invalid(lineContent)) {
                continue;
            }
            if (errorSyntax(lineContent)) {
                throw new IOException("yml syntax error, check line number " + configAllLineList.indexOf(lineContent));
            }
            CommandLine commandLine = getCommandLine(lineContent);
            Object object = runConfig;
            if (commandLine.isRoot()) {
                continue;
            }
            Gson gson = new Gson();
            String s = gson.toJson(runConfig);
            gson.fromJson(s, RunConfig.class);
            Field[] fields = runConfig.getClass().getFields();
            for (Field field : fields) {
                if (field.getName().equals(commandLine.command)) {
                    if (field.getType().getName().equals("String") && commandLine.isString()) {
                        field.setAccessible(true);
                        field.set(runConfig, commandLine.value);
                    }
                    break;
                }
            }
        }
    }

    static class CommandLine {
        String command;
        String value;
        List<String> values;
        int commandOrder;

        boolean isRoot() {
            return commandOrder == 1;
        }

        boolean isArray() {
            return values != null;
        }

        boolean isString() {
            return value != null;
        }
    }


    private static CommandLine getCommandLine(String lineContent) {
        String[] split = lineContent.split(":");
        CommandLine commandLine = new CommandLine();
        if (split.length == 1) {
            commandLine.command = split[0].trim();
            int order = 0;
            for (char c : split[0].toCharArray()) {
                if (c != ' ') {
                    break;
                }
                order += 1;
            }
            commandLine.commandOrder = order;
        } else {
            String value = split[1].trim();
            if (value.startsWith("[") && value.endsWith("]")) {
                value = value.substring(0, value.length() - 1);
                String[] valueArray = value.split(",");
                List<String> lineValueList = new ArrayList<>();
                for (String s : valueArray) {
                    lineValueList.add(s.trim());
                }
                commandLine.values = lineValueList;
            } else {
                commandLine.value = value;
            }
        }
        return commandLine;
    }

    private static boolean invalid(String lineContent) {
        if (lineContent == null || lineContent.isEmpty()) {
            return true;
        }
        return lineContent.startsWith("#");
    }

    private static boolean errorSyntax(String lineContent) {
        return !lineContent.contains(":");
    }

    private static class ConfigParserHolder {
        private static final ConfigParser instance = new ConfigParser();
    }
}
