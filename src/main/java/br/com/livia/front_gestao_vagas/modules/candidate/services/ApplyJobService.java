package br.com.livia.front_gestao_vagas.modules.candidate.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



@Service
public class ApplyJobService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPI;

    public String execute(String token, UUID jobId){

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<UUID> request = new HttpEntity<>(jobId, headers);

        String url = hostAPI.concat("/candidate/job/apply");

        var result =  rt.postForObject(url, request, String.class);

        System.out.println(result);

        return result;

    }
    
}
