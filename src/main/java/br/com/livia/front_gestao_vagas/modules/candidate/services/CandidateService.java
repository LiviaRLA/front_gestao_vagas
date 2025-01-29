package br.com.livia.front_gestao_vagas.modules.candidate.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.livia.front_gestao_vagas.modules.candidate.dto.Token;

@Service
public class CandidateService {

    public Token login(String username, String password) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data);

        var result = rt.postForObject("http://localhost:8080/candidate/auth", request, Token.class);
        System.out.println("Requisition returned:");
        System.out.println(result);
        System.out.println(result.getAccessToken());

        return result;
    }
}
