package prometheus.messages.entity;

public class Pending_messages {
    private String id;      //待发表告警id
    private String application;     //应用
    private String content;     //告警内容
    private String job;     //业务组
    private String extend;      //详细信息
    private String key_word;        //关键词
    private String monitortype;     //应用类型
    private String platform;        //平台
    private int flag;       //告警待发状态
    private String create_time;     //告警产生时间
    private int status;     //告警状态
    private String value;       //告警值
    private String level;       //告警等级
    private String instance;    //实例

    public void setId(String id) {
        this.id = id;
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

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLevel(String level) { this.level = level; }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getId() {
        return id;
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

    public int getFlag() {
        return flag;
    }

    public String getCreate_time() {
        return create_time;
    }

    public int getStatus() {
        return status;
    }

    public String getValue() {
        return value;
    }

    public String getLevel() { return level; }

    public String getInstance() {
        return instance;
    }

    public String toString(){
        return getId() + "," + getApplication() + "," + getContent() + "," + getJob() + "," + getExtend() + "," + getKey_word()
                + "," + getMonitortype() + "," + getPlatform() + "," + getFlag() + "," + getCreate_time() + "," + getStatus()
                + "," + getValue() + "," + getLevel()+ "," + getInstance();
    }
}