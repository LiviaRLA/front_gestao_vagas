package br.com.livia.front_gestao_vagas.modules.candidate.services;



import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import br.com.livia.front_gestao_vagas.modules.candidate.dto.ProfileCandidateDTO;

public class ProfileCandidateService {
    

    public ProfileCandidateDTO execute(String token){

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        try {
            var result = rt.exchange("http://localhost:8080/candidate/", HttpMethod.GET, request, ProfileCandidateDTO.class);
        
            System.out.println(result);
            return result.getBody();
        } catch (Unauthorized e) {

            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);

        }
    }
}
