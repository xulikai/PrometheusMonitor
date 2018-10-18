package monitor.Prometheus;

import java.io.IOException;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;


/**
 * 测试Prometheus监控
 * created by wangml
 * 2018-10-18
 *
 */
public class App {
	public  static void main(String args[]){

		try {
			//默认jmx监控信息
			DefaultExports.initialize();
			
			//自定义监控信息 Counter类型
			Counter requests = Counter.build()
				     .name("my_javatest_counter").help("Total counts.")
				     .labelNames("category", "fruit_type" ).register();
			requests.labels("food", "apple").inc();
			
			//自定义监控信息 Gauge类型
			//FIXME Gauge方法好像不能用label，因为Gauge中的set方法写着"Set the gauge with no labels to the current unixtime."
//			Gauge inprogressRequests = Gauge.build()
//				     .name("inprogress_requests").help("Inprogress requests.")
//				     .labelNames(new String[]{"mytest","testmy"}).register();
//			 inprogressRequests.labels(new String[]{"mytest1","testmy1"});
//			 inprogressRequests.inc();
//		    inprogressRequests.dec();
//		    inprogressRequests.set();
			Gauge inprogressRequests = Gauge.build()
				     .name("inprogress_requests").help("Inprogress requests.").register();
			 inprogressRequests.set(142); 
			
			HTTPServer hs = new HTTPServer("0.0.0.0", 9001);
			System.out.println("start...");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

