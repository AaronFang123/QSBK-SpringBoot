package fun.aaronfang.qsbk.demo.controller;


import fun.aaronfang.qsbk.demo.common.ApiValidationException;
import fun.aaronfang.qsbk.demo.common.Result;
import fun.aaronfang.qsbk.demo.config.QsbkProps;
import fun.aaronfang.qsbk.demo.constants.ApiUserAuth;
import fun.aaronfang.qsbk.demo.model.UserEntity;
import fun.aaronfang.qsbk.demo.model.UserinfoEntity;
import fun.aaronfang.qsbk.demo.repo.UserRepo;
import fun.aaronfang.qsbk.demo.repo.UserinfoRepo;
import fun.aaronfang.qsbk.demo.util.PhoneCacheUtils;
import fun.aaronfang.qsbk.demo.util.RedisUtils;
import fun.aaronfang.qsbk.demo.validation.phone.PhoneValidation;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.Jpa21Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Validated
@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {

    @Resource
    RedisUtils redisUtils;
    final QsbkProps qsbkProps;
    final UserRepo userRepo;
    final UserinfoRepo userinfoRepo;
    final BCryptPasswordEncoder passwordEncoder;

    public UserController(QsbkProps qsbkProps, UserRepo userRepo, BCryptPasswordEncoder passwordEncoder, UserinfoRepo userinfoRepo) {
        this.qsbkProps = qsbkProps;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.userinfoRepo = userinfoRepo;
    }

    @PostMapping("sendcode")
    public ResponseEntity<Result> sendCode(@PhoneValidation @RequestParam("phone") String phone) {
        // 判断是否开启验证码功能
        if (qsbkProps.isEnableSms()) {
            // 判断是否已经发送过验证码
            String phoneCachedKey = PhoneCacheUtils.getPhoneCachedKey(phone);
            if (redisUtils.hasKey(phoneCachedKey)) {
                return new ResponseEntity<>(Result.buildResult(30001, "你操作得太快啦", null), HttpStatus.OK);
            }
            // 生成4位验证码
            String valiCode = PhoneCacheUtils.genarateValiCode(phone);
            // 写入缓存
            boolean setResult = redisUtils.set(phoneCachedKey, valiCode, qsbkProps.getLoginExpireIn());
            if (setResult) {
                return new ResponseEntity<>(Result.buildResult("发送成功", "验证码：" + valiCode), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(Result.buildResult(30001, "发送失败，请重试", null), HttpStatus.OK);
            }
        }
        return null;
    }

    @PostMapping("phonelogin")
    public ResponseEntity<Result> phoneLogin(
            @PhoneValidation @RequestParam("phone") String phone,
            @RequestParam("code") String code) {
        validatePhoneCode(phone, code);
        HashMap<String, String> map = new HashMap<>();
        map.put("phone", phone);
        Pair<UserEntity, String> pair = isUserExist(map);
        Map<String, Object> resultMap = new HashMap<>();

        if (pair == null) {
            // 用户主表
            UserEntity newUser = new UserEntity();
            newUser.setUsername(phone);
            newUser.setPhone(phone);
            // 加密后的密码
            newUser.setPassword(passwordEncoder.encode(phone));
            log.info("newUser:" + newUser.toString());
            // 在用户信息表创建对应的记录（用户存放用户其他信息）
            UserinfoEntity userinfoEntity = new UserinfoEntity();
//            userinfoEntity.setUserId(newUser.getId());
            // 建立关联
            newUser.setUserinfoEntity(userinfoEntity);
            userinfoEntity.setUserEntity(newUser);
            // 先保存主表，再保存附表
            userinfoRepo.save(userinfoEntity);
            userRepo.save(newUser);

            resultMap.put("username", newUser.getUsername());
            resultMap.put("phone", newUser.getPhone());
            resultMap.put("password", false);
            resultMap.put("create_time", newUser.getCreateTime());
            resultMap.put("update_time", newUser.getCreateTime());
            resultMap.put("id", newUser.getId());
            resultMap.put("logintype", "phone");
            resultMap.put("token", UserEntity.getToken(newUser, (int) qsbkProps.getTokenExpireIn()));
            resultMap.put("userinfo", userinfoEntity);

            return new ResponseEntity<>(Result.buildResult(map), HttpStatus.OK);

        }
        UserEntity pairKey = pair.getKey();
        if (pairKey.getStatus() == 0) {
            // 处于禁用状态
            throw new ApiValidationException("该用户已被禁用", 20001);
        }
        resultMap.put("username", pairKey.getUsername());
        resultMap.put("phone", pairKey.getPhone());
        resultMap.put("password", false);
        resultMap.put("create_time", pairKey.getCreateTime());
        resultMap.put("update_time", pairKey.getCreateTime());
        resultMap.put("id", pairKey.getId());
        resultMap.put("logintype", "phone");
        resultMap.put("token", UserEntity.getToken(pairKey, (int) qsbkProps.getTokenExpireIn()));
        resultMap.put("userinfo", pairKey.getUserinfoEntity());
        return new ResponseEntity<>(Result.buildResult(resultMap), HttpStatus.OK);
    }

    @GetMapping("test")
    public ResponseEntity<Result> test() {
        Optional<UserEntity> byUsername = userRepo.findById(29);
        return new ResponseEntity<>(Result.buildResult(byUsername), HttpStatus.OK);
    }

    @GetMapping("json")
    public ResponseEntity<Result> json() {
        Map<String, Object> map = new HashMap<>();
        map.put("123", false);
        map.put("234", "phone");
        map.put("345", 345);
        map.put("456", new UserEntity());
        return new ResponseEntity<>(Result.buildResult("ok" ,map), HttpStatus.OK);

    }

    /**
     * 验证码验证，验证不通过抛出异常
     * @param phone phone
     * @param code code
     */
    private void validatePhoneCode(String phone, String code) {
        String toValidateKey = PhoneCacheUtils.getPhoneCachedKey(phone);
        if (!redisUtils.hasKey(toValidateKey)) {
            throw new ApiValidationException("请重新获取验证码", 10001);
        }
        String codeInRedis = (String) redisUtils.get(toValidateKey);
        if (!codeInRedis.equals(code)) {
            throw new ApiValidationException("验证码错误", 10001);
        }
    }

    /**
     * @param map 查找用户的键值对，支持
     *            - 手机号 phone
     *            - 用户名id id
     *            - 邮箱 email
     *            - 用户名 username
     * @return 查找到的用户 Pair 实体 Entity, 类型 String
     */
    private Pair<UserEntity, String> isUserExist(Map<String, String> map) {
        if(map == null || map.isEmpty()) {
            return null;
        }
        if (map.containsKey("phone")) {
            UserEntity entity = userRepo.findUserEntityByPhone(map.get("phone"));
            if (entity != null) return new Pair<>(entity, "phone");
        }
        if (map.containsKey("id")) {
            Optional<UserEntity> entity = userRepo.findById(Integer.valueOf(map.get("id")));
            if (entity.isPresent()) {
                return new Pair<>(entity.get(), "id");
            }
        }
        if (map.containsKey("email")) {
            UserEntity userEntityByEmail = userRepo.findUserEntityByEmail(map.get("email"));
            if (userEntityByEmail != null) return new Pair<>(userEntityByEmail, "phone");
        }
        if (map.containsKey("username")) {
            UserEntity userEntityByEmail = userRepo.findUserEntityByUsername(map.get("username"));
            if (userEntityByEmail != null) return new Pair<>(userEntityByEmail, "username");
        }
        return null;
    }
}
