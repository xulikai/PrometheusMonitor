package prometheus.messages.util;


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ConfigContainer {

    private static int  fetchCount = 20;   //每次读取数据量
    private static int  fetchthreads = 1;   //抓取线程数
    private static int  sendthreads = 1;    //发送线程数
    private static int  fetchLimt = 200;    //内存中存储数据阈值
    private static int  sendCount = 1;      //每次从内存中去数据量
    private static int  corePoolSize = 1; //线程池维护线程的最少数量
    private static int  maximumPoolSize = 2; //线程池维护线程的最大数量
    private static int  keepAliveTime = 3; //线程池维护线程所允许的空闲时间，单位分钟


    /**
     * 本方法提供配置文件信息加载功能
     * */
    public static void load(){
        Map<?,?> map = loadFunction("common");
        fetchCount = Integer.parseInt(getInfo("fetchCount","50", map));
        fetchthreads = Integer.parseInt(getInfo("fetchthreads","2", map));
        sendthreads = Integer.parseInt(getInfo("sendthreads","2", map));
        fetchLimt = Integer.parseInt(getInfo("fetchLimt","200", map));
        corePoolSize = Integer.parseInt(getInfo("corePoolSize","1", map));
        maximumPoolSize = Integer.parseInt(getInfo("maximumPoolSize","2", map));
        keepAliveTime = Integer.parseInt(getInfo("keepAliveTime","3", map));
        sendCount = Integer.parseInt(getInfo("sendCount","1", map));
    }

    /**
     * 此方法可以获得节点属性
     * @param e 节点
     * @param defult 节点属性为空时的默认值
     * @param map
     * @return result 查询结果
     * */
    public static String getInfo(String e , String  defult , Map<?,?> map)
    {
        String result = (String) map.get(e);
        if("".equals(result))
        {
            result = defult;
        }
        return result;
    }

    /**
     * 此方法实现将节点的所有子节点和属性存放到map中并返回
     * @param e 节点
     * @return map
     * */
    public  static Map<?, ?> loadFunction(String e)
    {
        //创建解析器
        SAXReader saxreader = new SAXReader();

        //读取文档
        Document doc = null;
        Map<String,String> map = null;
        try {
            //src/main/resources/config.xml
            doc = saxreader.read(new File(System.getProperty("user.dir") + File.separator + "config/config.xml"));
            map = new HashMap<String,String>();
            //获取根，节点
            Element root = doc.getRootElement().element(e);
            //将所有节点和属性存放到map中
            for ( Iterator<?> iterInner = root.elementIterator(); iterInner.hasNext(); ) {
                Element elementInner = (Element) iterInner.next();
                map.put(elementInner.getName(), root.elementText(elementInner.getName()));
            }
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        return map;
    }


    public static int getFetchCount() { return fetchCount; }

    public static int getCorePoolSize() { return corePoolSize; }

    public static int getMaximumPoolSize() { return maximumPoolSize; }

    public static int getKeepAliveTime() { return keepAliveTime; }

    public static void setFetchCount(int fetchCount) { ConfigContainer.fetchCount = fetchCount; }

    public static void setCorePoolSize(int corePoolSize) { ConfigContainer.corePoolSize = corePoolSize; }

    public static void setMaximumPoolSize(int maximumPoolSize) { ConfigContainer.maximumPoolSize = maximumPoolSize; }

    public static void setKeepAliveTime(int keepAliveTime) { ConfigContainer.keepAliveTime = keepAliveTime; }

    public static void setSendCount(int sendCount) { ConfigContainer.sendCount = sendCount; }

    public static int getFetchthreads() { return fetchthreads; }

    public static void setFetchthreads(int fetchthreads) {
        ConfigContainer.fetchthreads = fetchthreads;
    }

    public static int getSendthreads() {
        return sendthreads;
    }

    public static void setSendthreads(int sendthreads) {
        ConfigContainer.sendthreads = sendthreads;
    }

    public static int getFetchLimt() {
        return fetchLimt;
    }

    public static void setFetchLimt(int fetchLimt) {
        ConfigContainer.fetchLimt = fetchLimt;
    }

    public static int getSendCount() { return sendCount; }
}