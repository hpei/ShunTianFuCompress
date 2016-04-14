package model;

/**
 * Created by haopei on 2016/4/5.
 */
public class AlarmData {
    private String content;
    private String status;
    private String createTime;
    private String id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AlarmData{" +
                "content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
