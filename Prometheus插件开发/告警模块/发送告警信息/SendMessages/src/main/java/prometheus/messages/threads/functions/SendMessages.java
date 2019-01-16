package prometheus.messages.threads.functions;

public interface SendMessages {
    //发送告警 钉钉或者电话
    public void send(String status, String keyword, String time, String instance, String platform, String message,
                     String value, String extend, String application, String job, String monitortype, String id, int state);

    //记录日志
    public Boolean save();
}
