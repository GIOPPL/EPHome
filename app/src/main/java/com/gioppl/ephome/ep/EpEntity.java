package com.gioppl.ephome.ep;

/**
 * Created by GIOPPL on 2017/12/5.
 */

public class EpEntity {

    /**
     * id : 8
     * title : 猪粪
     * content : 猪粪含有丰富的氮、磷、钾和有机质，是有机肥的好原料。但同时，猪粪中也含有很多挥发性物质、病原微生物、寄生虫卵及重金属等，若不做处理而直接施用于农田，会对生态环境和人畜健康带来不良影响。猪粪要做为真正的有机肥用于农田，还需要借助肥料发酵剂做进一步的发酵处理。<br>1.猪粪直接还田：猪粪直接返田是猪粪最原始的利用方式。<br>2.沼气发酵：是微生物在厌氧下，将有机质通过复杂的分解代谢，最终产生沼气和污泥。<br>3.好氧堆肥:腐熟的猪粪作为有机肥不仅可以提高作物的产量和品质，而且可以增加土壤。<br>4.猪粪当作饲料来利用亦即粪便资源：饲料化，是畜禽粪便综合利用的重要途径，是节约粮食资源的重要措施，在世界许多国家得到广泛重视与推广。
     */

    private int id;
    private String title;
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EpEntity( String title, String content) {
        this.title = title;
        this.content = content;
    }
}
