package com.vaha1st.sberCredit.controller;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/administration")
@AllArgsConstructor
public class ProfileController {

    private ClientService clientService;

    @GetMapping("/clients")
    public String clients(Model model) {
        List<Client> clients = clientService.getClients();
        model.addAttribute("theClients", clients);
        return "admin/clients";
    }

    @GetMapping("/createClient")
    public String createClient(Model model) {
        Client client = new Client();
        model.addAttribute("theClient", client);
        return "admin/createClient";
    }

    @GetMapping("/updateClient")
    public String updateClient(@RequestParam("clientId") int id, Model model) {
        Client client = clientService.getSingleClient(id);
        model.addAttribute("theClient", client);
        return "admin/createClient";
    }

    @GetMapping("/deleteClient")
    public String deleteClient(@RequestParam("clientId") int id) {
        clientService.deleteClient(id);
        return "redirect:/administration/clients";
    }

    @PostMapping("/saveClient")
    public String save(@ModelAttribute("client") Client client) {
        clientService.saveOrUpdateClient(client);
        return "redirect:/administration/clients";
    }
}
