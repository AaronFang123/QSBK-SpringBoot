package fun.aaronfang.qsbk.demo.controller;


import fun.aaronfang.qsbk.demo.common.Result;
import fun.aaronfang.qsbk.demo.config.QsbkProps;
import fun.aaronfang.qsbk.demo.constants.ApiUserAuth;
import fun.aaronfang.qsbk.demo.util.PhoneCacheUtils;
import fun.aaronfang.qsbk.demo.util.RedisUtils;
import fun.aaronfang.qsbk.demo.validation.phone.PhoneValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Validated
@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {

    @Resource
    RedisUtils redisUtils;

    final QsbkProps qsbkProps;

    public UserController(QsbkProps qsbkProps) {
        this.qsbkProps = qsbkProps;
    }

    @ApiUserAuth
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
}
