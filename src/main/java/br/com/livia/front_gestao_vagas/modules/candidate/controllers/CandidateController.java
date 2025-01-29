package br.com.livia.front_gestao_vagas.modules.candidate.controllers;


import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.livia.front_gestao_vagas.modules.candidate.services.CandidateService;
import br.com.livia.front_gestao_vagas.modules.candidate.services.FindJobsService;
import br.com.livia.front_gestao_vagas.modules.candidate.services.ProfileCandidateService;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ProfileCandidateService profileCandidateService;

    @Autowired
    private FindJobsService findJobsService;


    @GetMapping("/login")
    public String login(){

        return "candidate/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {
        
        try {
            var token = this.candidateService.login(username, password);
            var grants = token.getRoles().stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);

            auth.setDetails(token.getAccessToken());

            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("token", token);

            return "redirect:/candidate/profile";
            
        } catch (HttpClientErrorException e) {
            redirectAttributes.addFlashAttribute("error_message", "Incorrect Username or Password");
            
            return "redirect:/candidate/login";
        }
    } 
    
    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String profile(Model model){

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            var candidate = this.profileCandidateService.execute(authentication.getDetails().toString());

            model.addAttribute("candidate", candidate);

            return "candidate/profile";
        } catch (HttpClientErrorException e) {
            return "redirect:/candidate/login";
        }
    }


    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String jobs(Model model, String filter) {
        
        System.out.println("Filter: " + filter);

        if (filter != null) {
            //model.addAttribute("jobs", filter);
            this.findJobsService.execute(getToken(), filter);
        }

        return "candidate/jobs";
    }

    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();

    }
    
}
