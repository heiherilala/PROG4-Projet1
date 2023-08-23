package com.hei.project2p1.controller;

import com.hei.project2p1.service.SpringSessionService;
import jakarta.servlet.http.HttpSession;
import java.rmi.ServerException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;

@Controller
@AllArgsConstructor
public class ErrorController {

//    @ExceptionHandler(ServerException.class)
//    public String handleBadRequestException(ServerException e, Model model) {
//        model.addAttribute("exception", e);
//        return "error";
//    }

}
