package fun.aaronfang.qsbk.demo.controller;


import fun.aaronfang.qsbk.demo.common.Result;
import fun.aaronfang.qsbk.demo.model.PostClassEntity;
import fun.aaronfang.qsbk.demo.repo.PostClassRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/postclass", produces = "application/json")
@CrossOrigin(origins = "*")
public class PostClassController {

    final PostClassRepo postClassRepo;

    public PostClassController(PostClassRepo postClassRepo) {
        this.postClassRepo = postClassRepo;
    }


    @GetMapping
    public ResponseEntity<Result> getTopicClassAvailable() {
        List<PostClassEntity> postClassEntities = postClassRepo.findPostClassEntitiesByStatus(Byte.parseByte("1"));
        return new ResponseEntity<>(Result.buildResult("获取成功", postClassEntities), HttpStatus.OK);
    }


}
