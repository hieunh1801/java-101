package org.hieunh1801.springboot101.CAMEL_0003_QUARTZ.quartz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quartz/jobs")
public class QuartzController {
    @Autowired
    private QuartzService quartzService;

    @GetMapping("/{id}/add")
    public String post(@PathVariable String id) throws Exception {
        quartzService.addJob(id, "0/10+*+*+*+*+?");
        return "SUCCESS";
    }

    @GetMapping("/{id}/pause")
    public ResponseEntity<String> pause(@PathVariable String id) throws Exception {
        quartzService.pauseJob(id);
        return ResponseEntity.ok("Paused " + id);
    }

    @GetMapping("/{id}/resume")
    public ResponseEntity<String> resume(@PathVariable String id) throws Exception {
        quartzService.resumeJob(id);
        return ResponseEntity.ok("Resumed " + id);
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> status(@PathVariable String id) throws Exception {
        return ResponseEntity.ok(quartzService.getStatus(id));
    }
}
