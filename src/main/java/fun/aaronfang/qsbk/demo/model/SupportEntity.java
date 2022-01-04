package fun.aaronfang.qsbk.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "support", schema = "info")
public class SupportEntity {
    private int id;
    private int userId;
    private int postId;
    private byte type;
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
    @Column(name = "user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "post_id")
    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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
        SupportEntity that = (SupportEntity) o;
        return id == that.id && userId == that.userId && postId == that.postId && type == that.type && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, postId, type, createTime);
    }

    @PrePersist
    void createAt() {
        this.createTime = Math.toIntExact(System.currentTimeMillis() / 1000);
    }
}
