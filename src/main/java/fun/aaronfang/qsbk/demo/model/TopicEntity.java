package fun.aaronfang.qsbk.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "topic", schema = "info")
public class TopicEntity {
    private int id;
    private String title;
    private String titlepic;
    private String desc;
    private byte type;
    private Timestamp createTime;
    private Integer topicClassId;
//    private TopicClassEntity topicClassEntity;


    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "titlepic")
    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    @Basic
    @Column(name = "desc")
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Basic
    @Column(name = "type")
    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "topic_class_id")
    public Integer getTopicClassId() {
        return topicClassId;
    }

    public void setTopicClassId(Integer topicClassId) {
        this.topicClassId = topicClassId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TopicEntity that = (TopicEntity) o;
        return id == that.id && type == that.type && Objects.equals(title, that.title) && Objects.equals(titlepic, that.titlepic) && Objects.equals(desc, that.desc) && Objects.equals(createTime, that.createTime) && Objects.equals(topicClassId, that.topicClassId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, titlepic, desc, type, createTime, topicClassId);
    }

    @PrePersist
    void createAt() {
        this.createTime = new Timestamp(System.currentTimeMillis());
    }


//    @ManyToOne(targetEntity = TopicClassEntity.class)
//    @JoinColumn(name = "topic_class_id", referencedColumnName = "id", insertable = false, updatable = false
//    )
//    public TopicClassEntity getTopicClassEntity() {
//        return topicClassEntity;
//    }

//    public void setTopicClassEntity(TopicClassEntity topicClassEntity) {
//        this.topicClassEntity = topicClassEntity;
//    }
}
