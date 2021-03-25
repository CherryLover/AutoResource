package me.monster.auto.resource.tool;

import me.monster.auto.resource.bean.RunConfig;

public class DataHolder {

    private RunConfig runConfig;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        return Holder.instance;
    }

    public RunConfig getRunConfig() {
        return runConfig;
    }

    public void setRunConfig(RunConfig runConfig) {
        this.runConfig = runConfig;
    }

    private static final class Holder {
        private static final DataHolder instance = new DataHolder();
    }
}
