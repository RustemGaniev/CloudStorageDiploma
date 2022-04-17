package ru.netology.cloudstorage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/cloud")
public class UserController {

    @GetMapping("/list")
    public String hello() {
        return "hello world";
    }
}