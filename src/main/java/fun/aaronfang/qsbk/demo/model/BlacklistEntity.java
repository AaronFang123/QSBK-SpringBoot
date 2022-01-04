package fun.aaronfang.qsbk.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "blacklist", schema = "info")
public class BlacklistEntity {
    private int id;
    private int blackId;
    private int userId;
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
    @Column(name = "black_id")
    public int getBlackId() {
        return blackId;
    }

    public void setBlackId(int blackId) {
        this.blackId = blackId;
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
        BlacklistEntity that = (BlacklistEntity) o;
        return id == that.id && blackId == that.blackId && userId == that.userId && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, blackId, userId, createTime);
    }

    @PrePersist
    void createAt() {
        this.createTime = Math.toIntExact(System.currentTimeMillis() / 1000);
    }
}
