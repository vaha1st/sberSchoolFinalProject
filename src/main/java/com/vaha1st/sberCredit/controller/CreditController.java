package com.vaha1st.sberCredit.controller;

import com.vaha1st.sberCredit.entity.*;
import com.vaha1st.sberCredit.service.ClientService;
import com.vaha1st.sberCredit.service.CreditSchemeService;
import com.vaha1st.sberCredit.service.CreditService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/credit")
@AllArgsConstructor
public class CreditController {

    ClientService clientService;
    CreditService creditService;
    CreditSchemeService schemeService;


    private final String STATUS_FORM = "form";
    private final String STATUS_APP = "application";
    private final String STATUS_RES = "result";

    @GetMapping("/privacy")
    public String privacy() {
        return "/credit/privacy";
    }

    @GetMapping("/navigate")
    public String navigate(Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        switch (credit.getStatus()) {
            case STATUS_APP:
                return "redirect:/credit/application";
            case STATUS_RES:
                return "redirect:/credit/result";
            default:
                return "redirect:/credit/form";
        }
    }

    @GetMapping("/form")
    public String fillupForm(Model model, Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        Form form;
        credit.setStatus(STATUS_FORM);
        if (credit.getApp() != null) {
            credit.getApp().setCreditScheme(null);
        }
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

    @GetMapping("/application")
    public String fillupApplication(
            @ModelAttribute("app") Application incomingApp,
            @ModelAttribute("schemes") ArrayList<CreditScheme> incomingSchemes,
            Model model, Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        Application app;
        List<CreditScheme> schemes = model.containsAttribute("schemes") ? incomingSchemes : new ArrayList<>();
        credit.setStatus(STATUS_APP);
        creditService.saveOrUpdateCredit(credit);
        if (incomingApp.getSum() == null) {
            if (credit.getApp() == null) {
                app = new Application();
            } else {
                app = credit.getApp();
            }
        } else {
            app = incomingApp;
            credit.setApp(app);
            creditService.saveOrUpdateCredit(credit);
        }
        model.addAttribute("app", app);
        model.addAttribute("schemes", schemes);
        return "credit/application";
    }

    @PostMapping("/application/availableSchemes")
    public String importAvailableSchemes(
            @ModelAttribute("app") Application app,
            RedirectAttributes redirectAttributes) {
        List<CreditScheme> schemes = schemeService.getSchemesBySumAndPeriod(app.getSum(), app.getPeriod());
        redirectAttributes.addAttribute("schemes", schemes);
        redirectAttributes.addFlashAttribute("app", app);
        return "redirect:/credit/application";
    }

    @PostMapping("/application/chooseScheme")
    public String choose(
                    @RequestParam("scheme") int schemeId,
                    Principal principal,
                    RedirectAttributes redirectAttributes) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        credit.getApp().setCreditScheme(schemeService.getSchemeById(schemeId));
        creditService.saveOrUpdateCredit(credit);
        List<CreditScheme> schemes = schemeService.getSchemesBySumAndPeriod(credit.getApp().getSum(), credit.getApp().getPeriod());
        redirectAttributes.addAttribute("schemes", schemes);
        return "redirect:/credit/application";
    }

    @GetMapping("/calculate")
    public String calculate(Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        credit.setStatus(STATUS_RES);
        calculatePayment(credit.getApp());
        creditService.saveOrUpdateCredit(credit);
        return "redirect:/credit/result";
    }

    @GetMapping("/result")
    public String showResult(Model model, Principal principal) {
        Client client = clientService.getSingleClientByUserName(principal.getName());
        Credit credit = creditService.getCreditByClient(client);
        model.addAttribute("credit", credit);
        if (credit.getApp().isPreApproved()) {
            return "result/preapproved";
        }
        return "result/denied";
    }

    @PostMapping("/finalize")
    public String finalizeCredit(@RequestParam("credit") int id) {
        creditService.finalizeCredit(id);
        return "redirect:/client/profile";
    }

    private void calculatePayment(Application app) {
        int creditSum = app.getSum();
        float ratePerMonth = app.getCreditScheme().getRate() / 12 / 100;
        int paysQuantity = app.getPeriod();

        float monthlyPayment = (float) (creditSum * ((ratePerMonth * Math.pow(1 + ratePerMonth, paysQuantity))
                / (Math.pow(1 + ratePerMonth, paysQuantity) - 1)));

        app.setPayment((Math.round(monthlyPayment * 100f) / 100f));
        app.setPreApproved(app.getSalary() * 0.7 >= monthlyPayment);
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
