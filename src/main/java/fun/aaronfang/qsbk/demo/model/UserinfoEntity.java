package fun.aaronfang.qsbk.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "userinfo", schema = "info")
public class UserinfoEntity {
    private int id;
    private int age;
    private byte sex;
    private byte qg;
    private String job;
    private String path;
    private String birthday;
    private UserEntity userEntity;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Basic
    @Column(name = "sex")
    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "qg")
    public byte getQg() {
        return qg;
    }

    public void setQg(byte qg) {
        this.qg = qg;
    }

    @Basic
    @Column(name = "job")
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Basic
    @Column(name = "path")
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Basic
    @Column(name = "birthday")
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @OneToOne(cascade = CascadeType.ALL, targetEntity = UserEntity.class) //JPA注释： 一对一 关系
    @JoinColumn(name = "user_id", referencedColumnName="id")
    @JsonIgnore
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserinfoEntity that = (UserinfoEntity) o;
        return id == that.id && age == that.age && sex == that.sex && qg == that.qg && Objects.equals(job, that.job) && Objects.equals(path, that.path) && Objects.equals(birthday, that.birthday) && Objects.equals(userEntity, that.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, sex, qg, job, path, birthday, userEntity);
    }
}
