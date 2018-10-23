package monitor.demo.prometheus;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Map;
import java.util.HashMap;

@SpringBootApplication
public class PrometheusApplication implements MeterBinder {

    private Counter job1Counter;

    private Counter job2Counter;

    private Counter job3Counter;

    private Gauge ge;

    private Map<String, Double> map = new HashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(PrometheusApplication.class, args);

    }

    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        this.job1Counter = Counter.builder("my_job")
                .tags(new String[]{"name", "job1"})
                .description("Job 1 execute count").register(meterRegistry);
        this.job1Counter.increment();


        this.job2Counter = Counter.builder("my_job")
                .tags(new String[]{"name", "job2"})
                .description("Job 2 execute count").register(meterRegistry);
        this.job2Counter.increment();

        this.job3Counter = Counter.builder("my_job_3")
                .tags(new String[]{"label", "job3"})
                .description("Job 3 execute count").register(meterRegistry);
        this.job3Counter.increment();

        this.ge = Gauge.builder("my_job_gauge", map, x -> x.get("value"))
                .tags("name", "java_label")
                .description("")
                .register(meterRegistry);
        this.map.put("value",  Double.valueOf(302));

    }
}
