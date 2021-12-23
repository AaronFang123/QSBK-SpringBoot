package fun.aaronfang.qsbk.demo.controller;


import fun.aaronfang.qsbk.demo.common.Result;
import fun.aaronfang.qsbk.demo.model.TopicClassEntity;
import fun.aaronfang.qsbk.demo.model.TopicEntity;
import fun.aaronfang.qsbk.demo.repo.TopicClassRepo;
import fun.aaronfang.qsbk.demo.repo.TopicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/topicclass", produces = "application/json")
@CrossOrigin(origins = "*")
public class TopicClassController {

    private final TopicClassRepo topicClassRepo;
    private final TopicRepo topicRepo;

    @Autowired
    public TopicClassController(TopicClassRepo topicClassRepo, TopicRepo topicRepo) {
        this.topicClassRepo = topicClassRepo;
        this.topicRepo = topicRepo;
    }

    @GetMapping
    public ResponseEntity<Result> getAvailableTopicClass() {
        List<TopicClassEntity> availableTopicClass = topicClassRepo.getAvailableTopicClass();
        if (availableTopicClass == null) {
            return new ResponseEntity<>(Result.buildResult("No Available Data", null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(Result.buildResult(availableTopicClass), HttpStatus.OK);
    }

    @GetMapping("/{id}/topic/{page}")
    public ResponseEntity<Result> getTopic(@PathVariable int id, @PathVariable int page) {
        PageRequest pageRequest = PageRequest.of(page - 1, 10);
        List<TopicEntity> topicEntities = topicRepo.findTopicEntityByTopicClassId(id, pageRequest);
        return new ResponseEntity<>(Result.buildResult(topicEntities), HttpStatus.OK);
    }
}
