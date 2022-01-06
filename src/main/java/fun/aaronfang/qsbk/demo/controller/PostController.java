package fun.aaronfang.qsbk.demo.controller;


import fun.aaronfang.qsbk.demo.common.ApiValidationException;
import fun.aaronfang.qsbk.demo.common.Result;
import fun.aaronfang.qsbk.demo.model.*;
import fun.aaronfang.qsbk.demo.repo.PostRepo;
import fun.aaronfang.qsbk.demo.util.ObjectToMapUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/post", produces = "application/json")
@CrossOrigin(origins = "*")
public class PostController {

    final PostRepo postRepo;

    public PostController(PostRepo postRepo) {
        this.postRepo = postRepo;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Result> getPostDetails(@PathVariable int id) {
        Optional<PostEntity> byId = postRepo.findById(id);
        if (!byId.isPresent()) {
            throw new ApiValidationException("帖子不存在", 20004);
        }

        // 查询用户信息
        PostEntity postEntity = byId.get();
        UserEntity userEntity = postEntity.getUserEntityWithPost();
        UserinfoEntity userinfoEntity = userEntity.getUserinfoEntity();

        // 查询图片信息 多对多
        List<ImageEntity> imageEntityList = postEntity.getImageEntityList();

        Map<String, Object> resultMap = ObjectToMapUtils.toMap(postEntity);
        resultMap.put("images", imageEntityList);
        resultMap.put("share", null);

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("id", userEntity.getId());
        userMap.put("username", userEntity.getUsername());
        userMap.put("userpic", userEntity.getUserpic());
        userMap.put("userinfo", userinfoEntity);

        resultMap.put("user", userMap);
        return new ResponseEntity<>(Result.buildResult("获取成功", resultMap), HttpStatus.OK);
    }


}
