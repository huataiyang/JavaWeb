package News.NewsProtal;

import java.net.URL;
import java.util.Map;

/**
 * Created by gaoyuan
 * Create on 2016/4/7.
 */

/**
 * 新闻门户类
 */
public class Protal {

    /** 门户基本信息的集合 */
    private Map<String, String> desInfos;

    /** 门户提供的rss订阅集合 */
    private Map<String, URL> rssSite;

    
    public Map<String, URL> getRssSite() {
        return rssSite;
    }

    public void setRssSite(Map<String, URL> rssSite) {
        this.rssSite = rssSite;
    }


    public Map<String, String> getDesInfos() {
        return desInfos;
    }

    public void setDesInfos(Map<String, String> desInfos) {
        this.desInfos = desInfos;
    }

}
