package com.vaha1st.sberCredit.controller;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.entity.Credit;
import com.vaha1st.sberCredit.service.ClientService;
import com.vaha1st.sberCredit.service.CreditService;
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
    private CreditService creditService;


    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClientWithoutNew(client);
        model.addAttribute("client", client);
        model.addAttribute("credit", credit);
        return "client/profile";
    }
}
