package News.NewsConstruct;

/**
 * Created by gaoyuanb
 * Create on 2016/4/6.
 */

/**
 * 新闻类
 */
public class News {

    /** 该条新闻的概述 */
    private NewsBrief brief;

    /** 该条新闻的内容 */
    private NewsContent content;

    /**
     * 构造函数
     * @param brief 新闻概述
     * @param content 新闻内容
     */
    public News(NewsBrief brief, NewsContent content) {
        setBrief(brief);
        setContent(content);
    }

    public NewsBrief getBrief() {
        return brief;
    }

    public void setBrief(NewsBrief brief) {
        this.brief = brief;
    }

    public NewsContent getContent() {
        return content;
    }

    public void setContent(NewsContent content) {
        this.content = content;
    }
}
