package entity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.*;

/**
 * Created by shikee_app03 on 16/7/20.
 */
@Entity
public class NewsBriefModel {

    public NewsBriefModel(NewsModel newsModel)
    {
        this.title=newsModel.getTitle();
        this.nId=newsModel.getId();
        this.source=newsModel.getSource();
        this.time=newsModel.getTime();
        String contentHtml=newsModel.getContent();
        Document doc = Jsoup.parse(contentHtml);
        if(contentHtml.contains("<img")){
            Elements elements=doc.getElementsByTag("img");
            if(elements!=null&&elements.size()>0){
                String img_url=elements.first().attr("src");
                this.imgUrl=img_url;
            }
        }

        if(doc.text().length()>100){
            this.brief=doc.text().substring(0, 100);
        }else{
            this.brief=doc.text();
        }
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long nbId;//新闻简介ID
    private String title;//新闻标题

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getNbId() {
        return nbId;
    }

    public void setNbId(Long nbId) {
        this.nbId = nbId;
    }
    @Column(columnDefinition="TEXT")
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    @Column(nullable=false)
    public Long getnId() {
        return nId;
    }

    public void setnId(Long nId) {
        this.nId = nId;
    }

    private String brief;//新闻摘要
    private String imgUrl;//新闻出现的第一张图片URL
    private String time;//新闻发布时间
    private String source;//新闻来源
    private Long nId;//所属的新闻ID
}
