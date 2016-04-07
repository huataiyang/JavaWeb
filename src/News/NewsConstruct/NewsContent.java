package News.NewsConstruct;

import java.net.URL;

/**
 * Created by gaoyuan
 * Create on 2016/4/7.
 */

/**
 * 新闻内容类
 */
public class NewsContent {

    /** 文字内容，每条代表一个段落*/
    private String[] contents;

    /** 图片内容，以URL的形式确定*/
    private URL[] imgURL;

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    public URL[] getImgURL() {
        return imgURL;
    }

    public void setImgURL(URL[] imgURL) {
        this.imgURL = imgURL;
    }
}
