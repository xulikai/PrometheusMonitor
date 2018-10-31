package test.jmx;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@ManagedResource(objectName = "devops:type=DevOpsMonitor,app=AutOperation", description = "操作日志队列长度")
@Service
public class Infomation {
    private static List<String> logList = new ArrayList<String>();

    public List<String> getLogList() {
        return logList;
    }

    public void setLog(String log){
        logList.add(log);
        System.out.println(logList.size());
    }

    @ManagedAttribute(description = "操作日志队列长度")
    public long getLogLength() {
        return logList.size();
    }

    /**
     * 队列中取数据的具体方法
     *
     * @return string
     * 		当队列为空，返回null，否则返回队列中日志
     * */
    public static synchronized String getLog(){

        if(logList.isEmpty()){
            return null;
        }else{
            return logList.remove(logList.size() -1);
        }
    }

}
