package entity;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by shikee_app03 on 16/7/12.
 */
@Table
public class NewsModel {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getPageViews() {
        return pageViews;
    }

    public void setPageViews(long pageViews) {
        this.pageViews = pageViews;
    }

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id;
    private String title;
    private String time;
    private String content;
    private String url;
    private String source;
    private long pageViews;
}
