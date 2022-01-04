package fun.aaronfang.qsbk.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "feedback", schema = "info")
public class FeedbackEntity {
    private int id;
    private int toId;
    private int fromId;
    private String data;
    private Integer createTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "to_id")
    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    @Basic
    @Column(name = "from_id")
    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    @Basic
    @Column(name = "data")
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Basic
    @Column(name = "create_time")
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeedbackEntity that = (FeedbackEntity) o;
        return id == that.id && toId == that.toId && fromId == that.fromId && Objects.equals(data, that.data) && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, toId, fromId, data, createTime);
    }

    @PrePersist
    void createAt() {
        this.createTime = Math.toIntExact(System.currentTimeMillis() / 1000);
    }
}
