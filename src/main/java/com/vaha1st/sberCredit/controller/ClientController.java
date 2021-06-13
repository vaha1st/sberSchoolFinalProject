package com.vaha1st.sberCredit.controller;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private ClientService clientService;


    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        model.addAttribute("client", client);
        return "client/profile";
    }
}
