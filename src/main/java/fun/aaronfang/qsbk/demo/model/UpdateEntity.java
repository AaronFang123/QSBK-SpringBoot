package fun.aaronfang.qsbk.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "update", schema = "info")
public class UpdateEntity {
    private int id;
    private String url;
    private String version;
    private Byte status;
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
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
        UpdateEntity that = (UpdateEntity) o;
        return id == that.id && Objects.equals(url, that.url) && Objects.equals(version, that.version) && Objects.equals(status, that.status) && Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, version, status, createTime);
    }

    @PrePersist
    void createAt() {
        this.createTime = Math.toIntExact(System.currentTimeMillis() / 1000);
    }
}
