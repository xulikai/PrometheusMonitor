package prometheus.messages.entity;

public class Alarm_url {

    private String type;
    private String url;
    private String name;

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getName() { return name; }

    public void setType(String type) { this.type = type; }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) { this.name = name; }
}
