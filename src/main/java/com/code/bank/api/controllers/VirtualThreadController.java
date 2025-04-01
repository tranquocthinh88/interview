package com.code.bank.api.controllers;

import com.code.bank.services.interfaces.VirtualThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/virtual-thread")
@RequiredArgsConstructor
public class VirtualThreadController {
    private final VirtualThreadService virtualThreadService;


    @GetMapping()
    public String VirtualThreads() {
        virtualThreadService.runVirtualThreadsTest();
        return "Running Virtual Threads test! Check the console logs.";
    }
}
