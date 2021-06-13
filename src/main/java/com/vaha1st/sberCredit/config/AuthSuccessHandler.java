package com.vaha1st.sberCredit.config;

import com.vaha1st.sberCredit.entity.Client;
import com.vaha1st.sberCredit.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ClientService clientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);

        String userName = authentication.getName();
        Client client = clientService.getSingleClientByUserName(userName);

        HttpSession session = request.getSession();
        session.setAttribute("user", client);
    }

    @Override
    public String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();
        String targetUrl = "";
        if (role.contains("ADMIN")) {
            targetUrl = "/administration/clients";
        } else if (role.contains("CLIENT")) {
            targetUrl = "/client/profile";
        }
        return targetUrl;
    }
}
