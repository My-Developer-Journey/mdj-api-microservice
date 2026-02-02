package com.diemyolo.media_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {

  @GetMapping("/hello")
  public String hello() {
    return "Hello World";
  }
}
