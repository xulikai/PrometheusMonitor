package prometheus.messages.start;

import prometheus.messages.db.LoadDbConfig;
import prometheus.messages.threads.FetchThread;
import prometheus.messages.threads.SendThread;
import prometheus.messages.util.ConfigContainer;
import prometheus.messages.util.Loger;
import java.util.concurrent.*;


public class Start {
    private static TimeUnit unit = TimeUnit.SECONDS;// 线程池维护线程所允许的空闲时间的单位

    static {
        Loger.Info_log.info("[info] 程序启动。。。");

        ConfigContainer.load();	    //加载配制文件

        LoadDbConfig.Load();   		//加载数据库配置表
    }



    public static void main(String[] args){

        //从数据库中抓取待发告警信息线程
        FetchThread ft = new FetchThread();
        //根据配置文件配置的线程数启动fatch线程
        for(int i = 0; i < ConfigContainer.getFetchthreads(); i++){
            new Thread(ft).start();
        }

        //创建ThreadPoolExecutor线程池，维护告警内容发送线程
        ThreadPoolExecutor pool = new ThreadPoolExecutor( ConfigContainer.getCorePoolSize(),
                ConfigContainer.getMaximumPoolSize(), ConfigContainer.getKeepAliveTime(), unit,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadPoolExecutor.DiscardOldestPolicy()) {

            // 线程执行之前运行
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
            }

            // 线程执行之后运行
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
            }

            // 整个线程池停止之后
            protected void terminated() {
            }
        };

        while (true){
            //当内存中的数据为空时，休眠2秒跳过此次线程创建
            if(FetchThread.getVlist().size()<=0){
                try {
                    Thread.sleep(2000);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //创建发送告警信息线程
            pool.execute(new SendThread());
            try {
                Thread.sleep(100);
                continue;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
