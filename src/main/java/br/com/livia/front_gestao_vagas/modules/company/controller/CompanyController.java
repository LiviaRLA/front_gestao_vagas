package br.com.livia.front_gestao_vagas.modules.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.livia.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;
import br.com.livia.front_gestao_vagas.modules.company.dto.CreateJobDTO;
import br.com.livia.front_gestao_vagas.modules.company.services.CreateCompanyService;
import br.com.livia.front_gestao_vagas.modules.company.services.CreateJobService;
import br.com.livia.front_gestao_vagas.modules.company.services.ListJobsByCompanyService;
import br.com.livia.front_gestao_vagas.modules.company.services.LoginCompanyService;
import br.com.livia.front_gestao_vagas.utils.FormatErrorMessage;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyService createCompanyService;

    @Autowired
    private LoginCompanyService loginCompanyService;

    @Autowired
    private CreateJobService createJobService;

    @Autowired
    private ListJobsByCompanyService listJobsByCompanyService;

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("company", new CreateCompanyDTO());
        return "company/create";
    }

    @PostMapping("/create")
    public String save (Model model, CreateCompanyDTO company) {

        try {
            this.createCompanyService.execute(company);
            model.addAttribute("company", new CreateCompanyDTO());

        } catch (HttpClientErrorException e) {
            model.addAttribute("error_message", FormatErrorMessage.formatErrorMessage(e.getResponseBodyAsString())); 
            model.addAttribute("company", company); 
        }
        
        return "company/create";
    }    

    @GetMapping("/login")
    public String login() {

        return "company/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {
        
        try {
            var token = this.loginCompanyService.execute(username, password);
            var grants = token.getRoles().stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);

            auth.setDetails(token.getAccessToken());

            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("token", token);

            return "redirect:/company/jobs";
            
        } catch (HttpClientErrorException e) {
            redirectAttributes.addFlashAttribute("error_message", "Incorrect Username or Password");
            
            return "redirect:/company/login";
        }
    }
    
    @GetMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String jobs(Model model) {
        model.addAttribute("job", new CreateJobDTO());
        return "company/jobs";
    }

    @PostMapping("/jobs")
    @PreAuthorize("hasRole('COMPANY')")
    public String createJob (Model model, CreateJobDTO job) {

        try {
            var result = this.createJobService.execute(getToken(), job);
            System.out.println(result);
            return "redirect:/company/jobs/list";

        } catch (HttpClientErrorException e) {
            model.addAttribute("error_message", FormatErrorMessage.formatErrorMessage(e.getResponseBodyAsString()));
            return "redirect:/company/jobs";
        }
    } 

    @GetMapping("/jobs/list")
    @PreAuthorize("hasRole('COMPANY')")
    public String list(Model model) {

        var result = this.listJobsByCompanyService.execute(getToken());
        model.addAttribute("job", result);
        System.out.println(result);
        return "company/list";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        session.setAttribute("token", null);
        return "redirect:/company/login";
    }
    

    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }
}