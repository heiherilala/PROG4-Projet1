package com.hei.project2p1.controller;

import com.hei.project2p1.service.SpringSessionService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthentificationController {
    private final SpringSessionService service;

    @PostMapping(value = "/quitSession")
    public String logOut(HttpSession session) {

        service.shutUpSessionById(session.getId());
        return "index";
    }

}
