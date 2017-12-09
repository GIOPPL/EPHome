package com.gioppl.ephome.ep;

/**
 * Created by GIOPPL on 2017/12/4.
 */

public class GoodsPriceEntity {

    /**
     * Gid : 1
     * Gname : 鸡粪
     * price : 200.0
     */

    private int Gid;
    private String Gname;
    private double price;

    public int getGid() {
        return Gid;
    }

    public void setGid(int Gid) {
        this.Gid = Gid;
    }

    public String getGname() {
        return Gname;
    }

    public void setGname(String Gname) {
        this.Gname = Gname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
