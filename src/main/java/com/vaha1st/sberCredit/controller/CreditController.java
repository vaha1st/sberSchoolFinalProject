package com.vaha1st.sberCredit.controller;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.entity.Credit;
import com.vaha1st.sberCredit.entity.Form;
import com.vaha1st.sberCredit.service.ClientService;
import com.vaha1st.sberCredit.service.CreditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/credit")
@AllArgsConstructor
public class CreditController {

    ClientService clientService;
    CreditService creditService;

    private final String STATUS_FORM = "form";
    private final String STATUS_APP = "application";
    private final String STATUS_PAY = "payment";

    @GetMapping("/navigate")
    public String navigate(Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        switch (credit.getStatus()) {
            case STATUS_APP:
                return "redirect:/credit/application";
            case STATUS_PAY:
                return "redirect:/credit/payment";
            default:
                return "redirect:/credit/form";
        }
    }

    @GetMapping("/form")
    public String form(Model model, Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        Form form;
        credit.setStatus(STATUS_FORM);
        creditService.saveOrUpdateCredit(credit);
        if (credit.getForm() == null) {
            form = new Form();
        } else {
            form = credit.getForm();
        }
        transferClientToForm(credit, form);
        model.addAttribute("form", form);
        return "credit/form";
    }

    @PostMapping("/saveForm")
    public String saveForm(@ModelAttribute("form") Form form, Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        form.setClient(client);
        form.setPersonalDataAccept(true);
        credit.setForm(form);
        transferFormToClient(credit);
        creditService.saveOrUpdateCredit(credit);
        return "redirect:/credit/application";
    }

    private void transferClientToForm(Credit credit, Form form) {
        form.setFirstName(credit.getClient().getFirstName());
        form.setLastName(credit.getClient().getLastName());
        form.setPatronymic(credit.getClient().getPatronymic());
        form.setDateOfBirth(credit.getClient().getDateOfBirth());
        form.setPassportSer(credit.getClient().getPassportSer());
        form.setPassportNum(credit.getClient().getPassportNum());
        form.setAddress(credit.getClient().getAddress());
    }

    private void transferFormToClient(Credit credit) {
        credit.getClient().setFirstName(credit.getForm().getFirstName());
        credit.getClient().setLastName(credit.getForm().getLastName());
        credit.getClient().setPatronymic(credit.getForm().getPatronymic());
        credit.getClient().setDateOfBirth(credit.getForm().getDateOfBirth());
        credit.getClient().setPassportSer(credit.getForm().getPassportSer());
        credit.getClient().setPassportNum(credit.getForm().getPassportNum());
        credit.getClient().setAddress(credit.getForm().getAddress());
    }
}
