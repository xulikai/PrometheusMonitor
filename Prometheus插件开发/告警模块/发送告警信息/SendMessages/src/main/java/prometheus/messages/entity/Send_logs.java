package prometheus.messages.entity;

public class Send_logs {
    private String id;
    private String create_time;
    private String send_time;
    private String application;
    private String content;
    private String job;
    private String extend;
    private String key_word;
    private String monitortype;
    private String platform;
    private int ecode;
    private int rcode;
    private String receiver;
    private int status;
    private String value;
    private String explanation;
    private String pending_id;

    public void setId(String id) {
        this.id = id;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public void setKey_word(String key_word) {
        this.key_word = key_word;
    }

    public void setMonitortype(String monitortype) {
        this.monitortype = monitortype;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setEcode(int ecode) {
        this.ecode = ecode;
    }

    public void setRcode(int rcode) {
        this.rcode = rcode;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setPending_id(String pending_id) {
        this.pending_id = pending_id;
    }

    public String getId() {
        return id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public String getApplication() {
        return application;
    }

    public String getContent() {
        return content;
    }

    public String getJob() {
        return job;
    }

    public String getExtend() {
        return extend;
    }

    public String getKey_word() {
        return key_word;
    }

    public String getMonitortype() {
        return monitortype;
    }

    public String getPlatform() {
        return platform;
    }

    public int getEcode() {
        return ecode;
    }

    public int getRcode() {
        return rcode;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getPending_id() {
        return pending_id;
    }
}
