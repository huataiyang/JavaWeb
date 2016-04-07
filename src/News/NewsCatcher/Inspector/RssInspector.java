package News.NewsCatcher.Inspector;

import News.NewsConstruct.NewsBrief;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaoyuan
 * Create on 2016/4/7.
 */

/**
 * 根据rss订阅链接生成新闻概述类的集合
 */
public class RssInspector {

    /** rss订阅地址 */
    private URL rssRul;

    /**
     * 构造方法
     * @param rssRul 需要侦测的rss订阅链接
     */
    public RssInspector(URL rssRul) {
        setRssRul(rssRul);
    }

    /**
     * 从订阅的rss连接获取当前提供的所有新闻简述（NewsBrief）
     * @return 新闻讲述合集（当前时段）
     */
    public  NewsBrief[] getAllRssNewsBrief() {

        ArrayList<NewsBrief> briefs = new ArrayList<>();

        try {
            //使用xml解析器以及rss解析器解析订阅rss的信息
            XmlReader reader = new XmlReader(rssRul);
            SyndFeedInput feedInput = new SyndFeedInput();
            SyndFeed feed = feedInput.build(reader);
            List entries=feed.getEntries();

            //遍历所有的新闻条目
            for (Object o : entries) {
                SyndEntry entry = (SyndEntry) o;

                //初始化新闻概述的内容
                NewsBrief brief = new NewsBrief();
                brief.setTitle(entry.getTitle());
                brief.setLink(entry.getLink());
                brief.setPublishedDate(entry.getPublishedDate());
                brief.setValue(entry.getDescription().getValue());
                briefs.add(brief);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return briefs.toArray(new NewsBrief[briefs.size()]);
    }

    public URL getRssRul() {
        return rssRul;
    }

    public void setRssRul(URL rssRul) {
        this.rssRul = rssRul;
    }

}
