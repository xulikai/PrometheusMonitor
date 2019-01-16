package prometheus.messages.threads;

import prometheus.messages.entity.Pending_messages;
import prometheus.messages.threads.functions.FetchMessages;
import prometheus.messages.util.ConfigContainer;
import prometheus.messages.util.Loger;

import java.util.List;
import java.util.Vector;

import static java.lang.Thread.sleep;

/**
 * 获取待发告警信息线程
 * 定时获取，当内存队列中的数据量大于规定限制，则停止抓取任务
 */
public class FetchThread implements Runnable {
    //存储数据库待发表中的告警内容，内存队列
    private static Vector<Pending_messages> vlist = new Vector<Pending_messages>();

    @Override
    public void run() {
        Loger.Info_log.info("[info] fetch线程启动");

        while(true) {
            //当内存队列的数据量超过规定数据量，休眠2秒，停止本次抓取数据
            if (vlist.size() >= ConfigContainer.getFetchLimt()) {
                try {
                    sleep(2000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //获取数据，采用单例模式实例化FetchMessages
            FetchMessages fm = FetchMessages.getInstance();
            List<Pending_messages> list = fm.getMessage();
            //当获取数据为null，休眠5秒，停止本次抓取数据，否则加入内存队列
            if (null == list || list.size() == 0) {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            } else {
                for (Pending_messages pm : list) {
                    if (null == pm) {
                        continue;
                    }
                    vlist.add(pm);
                }
            }
            //休眠5秒后，进行下次数据抓取
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Vector<Pending_messages> getVlist() {
        return vlist;
    }
}
