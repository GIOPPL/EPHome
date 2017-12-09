package com.gioppl.ephome.HomePager.entity;

/**
 * Created by GIOPPL on 2017/11/28.
 */

public class PollutionEntity {

    public PollutionEntity(String environmental_type, String content) {
        this.environmental_type = environmental_type;
        this.content = content;
    }

    /**
     * environmental_type : 生理性污染
     * content : 生理性污染（physiological pollution ）是指污水排人水体后所引起的感官性状恶化的情况，故亦称感官性污染。主要是嗅、味、外观及透明度等质量下降，这也是衡量水体生理性污染的重要指标。大气降水，地表水和地下水统称为水资源。它们三部分之间的关系是相互依存，相互转化，相辅相成的。与人类关系最密切的是淡水资源，就是指在目前经济技术条件下，可为人类利用的河川径流量。淡水湖泊量及可开采的地下水量。 一般将地面和地下的天然水系统称为水体。既包括江河湖海、池沼、水库，又包括地下水及暗河等。水体经常遭受自然因素的污染，如雨水挟带地面上的泥沙杂质及细菌等流入水体。另一方面，也是当前最重要的人为因素的污染，如工业生产废弃物的排放，给水体带来极其严重的污染后果。在河流、湖泊及池沼等淡水域中发生的浮游生物异常增殖出现象称为水花。而在海洋发生的同类现象则称为红潮。这种双象主要是植物浮游生物，在含营养盐类多的富营养湖沼内过度繁殖造成的。如果水花多，溶解氧在水面呈过饱出状态，水的下层缺氧，从而危害鱼类生长。产生水花的浮游生物有兰藻类的微胞藻及项圈藻，硅藻类的直链藻及湿样藻，鞭毛藻类的服虫藻及多甲藻等。在这些藻类中以微细胞藻及多甲藻的毒性为最大，能杀死鱼类及其它生物。 由于水质污染使静水域富营养化，是引起水花的原因之一，所以对排出的废水必须进行严格处理。同时，对于采用湖水和水库水作为水源设施，应注意防止过滤池的堵塞，以免引起水质发臭。
     * id : 1
     */

    private String environmental_type;
    private String content;
    private int id;

    public String getEnvironmental_type() {
        return environmental_type;
    }

    public void setEnvironmental_type(String environmental_type) {
        this.environmental_type = environmental_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
