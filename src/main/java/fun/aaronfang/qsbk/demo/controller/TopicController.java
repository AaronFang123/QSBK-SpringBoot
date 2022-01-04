package fun.aaronfang.qsbk.demo.controller;

import fun.aaronfang.qsbk.demo.common.Result;
import fun.aaronfang.qsbk.demo.repo.TopicRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/topic", produces = "application/json")
public class TopicController {

    final TopicRepo topicRepo;

    public TopicController(TopicRepo topicRepo) {
        this.topicRepo = topicRepo;
    }

    @GetMapping("hottopic")
    public ResponseEntity<Result> getHotTopic() {
        int nowTime = (int) System.currentTimeMillis() / 1000;
        int oneDayBefore = nowTime - 24 * 60 * 60;
        List<Map<String, Object>> topicCount = topicRepo.getHottestTopic(oneDayBefore, nowTime);
        return new ResponseEntity<>(Result.buildResult("发送成功", topicCount), HttpStatus.OK);
    }

}
