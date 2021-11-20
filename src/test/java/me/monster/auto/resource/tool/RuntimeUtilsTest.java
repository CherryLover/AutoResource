package me.monster.auto.resource.tool;

import org.junit.Test;

import static org.junit.Assert.*;

public class RuntimeUtilsTest {

    @Test
    public void isDebug() {
        String os = System.getProperty("os.name", "");
        String user = System.getProperty("user.name", "");
        System.out.println("os " + os + " user " + user);
        System.out.println(RuntimeUtils.isDebug());
    }
}