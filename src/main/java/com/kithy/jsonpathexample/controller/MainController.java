package com.kithy.jsonpathexample.controller;

import com.kithy.jsonpathexample.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class MainController {

    private final ResourceService resourceService;

    public MainController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/resource")
    public Flux<Object> postAccount(@RequestBody String json) {
        return resourceService.saveResource(json);
    }

    @GetMapping("/resource")
    public Flux<String> getAll(){
        return resourceService.getResources();
    }

}
