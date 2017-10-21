package com.gioppl.ephome.HomePager;

/**
 * Created by GIOPPL on 2017/10/17.
 */

public class HomePointModel {
    private int layout;//界面
    private int raw;//源文件

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public int getRaw() {
        return raw;
    }

    public void setRaw(int raw) {
        this.raw = raw;
    }

    public HomePointModel(int layout, int raw) {
        this.layout = layout;
        this.raw = raw;
    }
}
