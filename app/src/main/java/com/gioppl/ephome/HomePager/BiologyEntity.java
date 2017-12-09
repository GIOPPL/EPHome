package com.gioppl.ephome.HomePager;

/**
 * Created by GIOPPL on 2017/12/4.
 */

public class BiologyEntity {

    /**
     * id : 2
     * title : 生物质成型燃料加工装备
     * xinxi :
     生物质成型燃料加工装备
     发布时间：2013-09-06   浏览数:597 次

     所 属 领 域：生物质能源与生物基材料>生物质燃料制备、高效利用技术与设备
     成果关键词：生物质成型,燃料加工装备
     研 发 时 间:——
     新 兴 产 业：——
     成果成熟度：——
     技 术 水 平：——
     预期交易方式：——
     预期交易价格：——
     权属类型：——
     产权归属地：北京市
     产权归属者：南京农机化所
     知识产权编号：——
     知识产权说明：——
     　　该装备以枝条、树皮、秸秆等农林剩余物为原料，经一次粉碎机粉碎，粉碎后的物料送入自动喂料装置中，根据颗粒成型质量、负载变化自动调节喂料量，将适合成型的物料送入平模式压块成型机中压块成型，最终形成成型燃料。
     　　生物质成型燃料加工装备还可将多物料一次粉碎机和颗粒燃料成型机通过负压简易脉冲风网系统有机地连接起来，并将系统集成安装在可移动的平板车上。这样一方面确保生物质原料的输送均匀，防止堵塞与结拱，另一方面保证加工系统在加工过程中不会对环境造成严重污染，并尽可能少用人工作业，减少作业劳动强度和用工量，同时可以在农村乡镇之间和大农场内移动加工农林废弃物颗粒燃料。
     　　主要技术参数：
     　　（1）平模直径：520mm；（2）压辊转速:56rpm；（3）压辊压强:100kpa；（4）配用功率:45kw；（5）产量：500～1000kg/h；（6）颗粒直径：6mm-20mm；（7）颗粒长度：30mm；（8）颗粒产品密度：≥1g/cm3。


     联 系 人：  登录后可见
     传    真： 登录后可见
     地    址：——

     ----------------------------------------------------------------

     */

    private int id;
    private String title;
    private String xinxi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getXinxi() {
        return xinxi;
    }

    public void setXinxi(String xinxi) {
        this.xinxi = xinxi;
    }

    public BiologyEntity(int id, String title, String xinxi) {
        this.id = id;
        this.title = title;
        this.xinxi = xinxi;
    }
}
