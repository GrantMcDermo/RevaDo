package com.revature.RevaDo.controller;

import com.revature.RevaDo.service.SubtaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SubtaskController {

    private final SubtaskService service;
}
