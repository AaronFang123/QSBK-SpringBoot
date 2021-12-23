package fun.aaronfang.qsbk.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_bind", schema = "info", catalog = "")
public class UserBindEntity {
    private int id;
    private String type;
    private String openid;
    private Integer userId;
    private String nickname;
    private String avatarurl;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "openid")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Basic
    @Column(name = "avatarurl")
    public String getAvatarurl() {
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBindEntity that = (UserBindEntity) o;
        return id == that.id && Objects.equals(type, that.type) && Objects.equals(openid, that.openid) && Objects.equals(userId, that.userId) && Objects.equals(nickname, that.nickname) && Objects.equals(avatarurl, that.avatarurl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, openid, userId, nickname, avatarurl);
    }


}
