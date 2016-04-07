package News.NewsCatcher.Inspector;

import News.NewsProtal.Protal;
import News.NewsProtal.ProtalDesKey;
import News.NewsProtal.ProtalinfosGenerator;
import News.NewsConstruct.News;
import News.NewsConstruct.NewsBrief;
import News.NewsConstruct.NewsCategories;
import News.NewsConstruct.NewsContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by gaoyuan
 * Create on 2016/4/7.
 */

/**
 * NewsInspector类用于抓取特定新闻门户上的新闻
 */
public class NewsInspector {

    /** 抓取目标网站 */
    private Protal protal;

    /**
     * 构造函数
     * @param protal 抓取目标网站
     */
    public NewsInspector(Protal protal) {
        this.protal = protal;
    }

    /**
     * 访问特定新闻的rss网站，并且解析其获取新闻概要(NewsBrief)
     * @param categories 新闻分类
     * @return 新闻概要(NewsBrief)集合
     */
    private NewsBrief[] catchNewsBrief(NewsCategories categories) {
        RssInspector rssInspector = new RssInspector(protal.getRssSite().get(categories.name()));
        return rssInspector.getAllRssNewsBrief();
    }

    /**
     * 从门户网站上抓取最近的几条新闻，具体的条数由rss feed决定
     * @param categories 新闻分类
     * @return 新闻条目（NewsConstruct）
     */
    public News[] catchNews(NewsCategories categories) {

        //获取该类新闻的新闻概要
        NewsBrief[] briefs = catchNewsBrief(categories);

        //初始化新闻集合，随后用户存放新闻
        ArrayList<News> newses = new ArrayList<>();

        //由新闻门户初始化其JQUERY中新网内容以及新闻图片选择器
        String JqueryImgSelectState = protal.getDesInfos().get(ProtalDesKey.JquerySelectImgState.name());
        String JqueryWordsSelectState = protal.getDesInfos().get(ProtalDesKey.JquerySelectWordsState.name());

        //遍历每条新闻概要
        for (NewsBrief brief : briefs) {
            try {
                //由新闻概要中的link域访问新闻的主体网站
                Document doc = Jsoup.connect(brief.getLink()).timeout(30000).get();

                //由Jquery的选择语句选择出文字内容以及图片内容
                Elements wordsContainer = doc.select(JqueryWordsSelectState);
                Elements imgContainer = doc.select(JqueryImgSelectState);

                //sections集合用于存放文章的段落信息，即每个段落是一个section
                ArrayList<String> sections = new ArrayList<>();

                //遍历文字内容
                StringBuilder sb = new StringBuilder();
                for (Element e : wordsContainer) {
                    String piece = e.ownText().trim();
                    //如果改行文字为空那么代表段落结束
                    if (piece.equals("")) {
                        //段落结束时将本段落的内容加入到sections中
                        sections.add(sb.toString());
                        sb.delete(0, sb.length());
                    }
                    //如果段落尚未结束那么将段落内容保存
                    else {
                        sb.append(piece);
                    }
                }
                sections.add(sb.toString());

                //遍历图片内容
                ArrayList<URL> imgURLs = new ArrayList<>();
                for (Element e : imgContainer) {
                    URL imgURL = new URL(protal.getDesInfos().get(ProtalDesKey.homeSite.name()) + e.attr("src"));
                    // TODO: 2016/4/7 根据图片的url将图片下载，并且在本地创建文件夹保存图片
                    //将图片的URL保存
                    imgURLs.add(imgURL);
                }

                //利用文字内容以及图片内容构造新闻内容
                NewsContent content = new NewsContent();
                content.setContents(sections.toArray(new String[sections.size()]));
                content.setImgURL(imgURLs.toArray(new URL[imgURLs.size()]));

                //利用新闻简介以及新闻内容构造新闻
                News news = new News(brief, content);

                //将新闻添加到集合中
                newses.add(news);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return newses.toArray(new News[newses.size()]);
    }

    public static void main(String[] args) {
        Protal protal = ProtalinfosGenerator.getInstance().getProtalInfos()[0];
        NewsInspector inspector = new NewsInspector(protal);
    }

}
