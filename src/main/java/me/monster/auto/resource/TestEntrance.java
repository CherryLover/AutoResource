package me.monster.auto.resource;


import me.monster.auto.resource.action.GeekReadInfoAction;
import me.monster.auto.resource.action.LovelyAction;

public class TestEntrance {
    public static void main(String[] args) {
        GeekReadInfoAction geekReadInfoAction = new GeekReadInfoAction();
        geekReadInfoAction.fetchInfo();
    }
}
