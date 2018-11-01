package monitor.Prometheus;

import java.io.IOException;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.exporter.PushGateway;
import io.prometheus.client.hotspot.DefaultExports;



/**
 * 测试Prometheus监控
 * created by wangml
 * 2018-10-18
 *
 */
public class App {
	public  static void main(String args[]){
		App ap = new App();
		//启动服务器 直接对接Prometheus
//		ap.servertest();
		
		ap.pushtest();
	}
	
	//测试直接将监控信息暴露给Prometheus
	private void servertest(){
		try {
			//默认jmx监控信息  需引用simpleclient_hotspot
			DefaultExports.initialize();
			
			//自定义监控信息 Counter类型  此类型只能累加 不能减
			Counter requests = Counter.build()
				     .name("my_javatest_counter").help("Total counts.")
				     .labelNames("category", "fruit_type" ).register();
			requests.labels("food", "apple").inc();
			
			//自定义监控信息 Gauge类型  此类型可加可减
			Gauge inprogressRequests = Gauge.build()
				     .name("inprogress_requests").help("Inprogress requests.").labelNames("mytest","mytest1").register();
//			inprogressRequests.inc(142);
			 inprogressRequests.labels("testmy","testmy1").set(142); 
			
			 //启动http接口
			new HTTPServer("0.0.0.0", 9001);
			System.out.println("start...");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("启动异常。。。");
		}
	}
	
	
	//将待监控信息推送至pushgateway
	private void pushtest(){
		CollectorRegistry registry = new CollectorRegistry();
//		  Gauge duration = Gauge.build()
//		     .name("my_batch_job_duration_seconds").help("Duration of my batch job in seconds.").register(registry);
//		  Gauge.Timer durationTimer = duration.startTimer();
		  try {
		    // Your code here.
		  
		    // This is only added to the registry after success,
		    // so that a previous success in the Pushgateway isn't overwritten on failure.
		    Gauge lastSuccess = Gauge.build()
		       .name("wangml_java").help("2018-11-1 test").labelNames("wangml_java_test").register(registry);
//		    lastSuccess.setToCurrentTime();
		    lastSuccess.labels("jar").set(123);
		  } finally {
//		    durationTimer.setDuration();
		    PushGateway pg = new PushGateway("172.16.22.233:9091");
		    try {
				pg.pushAdd(registry, "wangml_java");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
	}
	
}

