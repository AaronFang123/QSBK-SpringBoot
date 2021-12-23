package fun.aaronfang.qsbk.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "topic_class", schema = "info")
public class TopicClassEntity {
    private int id;
    private String classname;
    @JsonIgnore
    private byte status;
    @JsonIgnore
    private Timestamp createTime;

    private List<TopicEntity> topics = new ArrayList<>();

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "classname")
    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Basic
    @Column(name = "status")
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicClassEntity that = (TopicClassEntity) o;
        return id == that.id && status == that.status && Objects.equals(classname, that.classname) && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classname, status, createTime);
    }

    @PrePersist
    void createAt() {
        this.createTime = new Timestamp(System.currentTimeMillis());
    }

    @OneToMany(targetEntity = TopicEntity.class)
    @JoinColumn(name = "topic_class_id", referencedColumnName= "id")
    public List<TopicEntity> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicEntity> topics) {
        this.topics = topics;
    }
}
