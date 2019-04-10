package com.foo.bar;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VeryImportantOperationController {


    @PostMapping(path = "/very/important/operation")
    public void theOperationIsVeryImportant() {


    }
}
