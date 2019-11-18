package com.xincan.transaction.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @description: TODO
 * @className: GateWayController
 * @date: 2019/11/15 10:16
 * @author: Xincan Jiang
 * @version: 1.0
 */
@RestController
@RequestMapping("gateway")
public class GateWayController {

    @GetMapping("info")
    public Mono info(){
        return Mono.just("gateway");
    }

}
