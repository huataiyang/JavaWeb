package News.NewsProtal;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by gaoyuan
 * Create on 2016/4/7.
 */

/**
 * 新闻门户类Protal的生产工厂
 */
public class ProtalinfosGenerator {

    /** 保存新闻门户properties的文件夹 */
    public static String propertiesPath = "properties\\NewsProtalProperties\\";

    /** 本类的单例 */
    private static ProtalinfosGenerator single;

    /**
     * 私有构造方法
     */
    private ProtalinfosGenerator() {

    }

    /**
     * 获取单例
     * @return 本类的单例引用
     */
    public static ProtalinfosGenerator getInstance() {
        if (single == null) {
            single = new ProtalinfosGenerator();
        }
        return single;
    }

    /**
     * 获取在propertiesPath目录下的所有新闻门户信息
     * 并且解析生成protal类
     * @return 由所有的新闻门户生成的protal类集合
     */
    public Protal[] getProtalInfos() {

        //用于保存protal类的集合
        ArrayList<Protal> protalInfos = new ArrayList<>();

        //获取配置目录下的所有文件
        File[] propertiesFiles = listPropertiesFiles();

        //遍历文件，并且根据其生成相关protal类
        for (File file : propertiesFiles) {
            protalInfos.add(generateProtalinfos(file));
        }

        return protalInfos.toArray(new Protal[protalInfos.size()]);
    }

    /**
     * 列出配置文件夹中的所有文件
     * @return 所有文件的file类集合
     */
    private File[] listPropertiesFiles() {

        File file = new File(propertiesPath);

        ArrayList<File> propertiesFiles = new ArrayList<>();

        File[] array = file.listFiles();

        for (File propertiesFile : array) {
            if (propertiesFile.isFile()) {
                propertiesFiles.add(propertiesFile);
            }
        }

        return propertiesFiles.toArray(new File[propertiesFiles.size()]);
    }

    /**
     * 根据properties类生成当个Protal实例
     * @param properties properties配置文件
     * @return 由该配置文件生成的protal实例
     */
    private Protal generateProtalinfos(File properties) {
        //初始化Protal类
        Protal protalInfos = new Protal();
        try {
            //从配置文件中读取properties键值对
            Properties protalProper = new Properties();
            protalProper.load(new FileInputStream(properties));

            //初始化保存位置基本信息的集合
            Map<String, String> desInfo = new HashMap<>();
            //初始化网站rss订阅链接的集合
            Map<String, URL> rssSite = new HashMap<>();

            //遍历所有的键值对
            for (Object s : protalProper.keySet()) {
                String key = (String) s;
                //如果是rss订阅网址
                if (key.endsWith("Rss")) {
                    //将新闻类型以及rss网址保存
                    rssSite.put(key.split("Rss")[0], new URL(protalProper.getProperty(key)));
                }
                //否则是正常的网站消息
                else  {
                    desInfo.put(key, protalProper.getProperty(key));
                }
            }

            protalInfos.setDesInfos(desInfo);
            protalInfos.setRssSite(rssSite);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return protalInfos;
    }
}
