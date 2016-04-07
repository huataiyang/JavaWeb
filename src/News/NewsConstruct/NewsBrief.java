package News.NewsConstruct;

import java.util.Date;

/**
 * Created by gaoyuan
 * Create on 2016/4/7.
 */

/**
 * 新闻简要内容类，记录新闻的基本信息
 */
public class NewsBrief {

    /** 新闻标题 */
    private String title;

    /** 新闻的原始链接 */
    private String link;

    /** 新闻的发布日期 */
    private Date publishedDate;

    /** 新闻的简单介绍 */
    private String value;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "-----"+title + "|" + link + "|" + value + "|" + publishedDate;
    }



}
