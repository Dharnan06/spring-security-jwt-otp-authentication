package com.nammakuzhu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/protected")
    public String protectedApi() {
        return "You accessed protected API successfully";
    }

    @GetMapping("/test/member")
    public String memberOnly() {
        return "Only MEMBER can access this";
    }

    @GetMapping("/test/bank")
    public String bankOfficerOnly() {
        return "Only BANK_OFFICER can access this";
    }

    @GetMapping("/test/admin")
    public String adminOnly() {
        return "Only ADMIN can access this";
    }

}