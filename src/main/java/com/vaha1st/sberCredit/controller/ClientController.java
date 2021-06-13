package com.vaha1st.sberCredit.controller;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientController {

    private ClientService clientService;


    @GetMapping("/profile")
    public String profile(Model model) {
        return "client/profile";
    }
}
