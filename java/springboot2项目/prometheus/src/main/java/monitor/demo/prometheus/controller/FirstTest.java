package monitor.demo.prometheus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import io.prometheus.client.Gauge;
//import io.prometheus.client.Counter;
//import java.util.Random;

@RestController
public class FirstTest {

//    private static Random random = new Random();
//
//    private static final Counter requestTotal = Counter.build()
//            .name("my_sample_counter")
//            .labelNames("status")
//            .help("A simple Counter to illustrate custom Counters in Spring Boot and Prometheus").register();


    @GetMapping("/test")
    public String test(){
//        inprogressRequests.labels("myjavatest","myjavatest1").set(31);
        return "test succ";
    }

    @GetMapping("/main")
    public String testmain(){
        return "main test succ";
    }

//    @RequestMapping("/endpoint")
//    public void endpoint() {
//        if (random.nextInt(2) > 0) {
//            requestTotal.labels("success").inc();
//        } else {
//            requestTotal.labels("error").inc();
//        }
//    }


//    @RequestMapping(value ="/hello")
//    @ResponseBody
//    @PrometheusMetrics
//    @Prome
//    @PrometheusTimeMethod
//    public String index() {
//        return "Hello World";
//    }
}
