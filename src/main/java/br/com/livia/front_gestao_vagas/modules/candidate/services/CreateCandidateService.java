package br.com.livia.front_gestao_vagas.modules.candidate.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.livia.front_gestao_vagas.modules.candidate.dto.CreateCandidateDTO;

@Service
public class CreateCandidateService {

    public void execute(CreateCandidateDTO createCandidateDTO) {

        RestTemplate rt = new RestTemplate();

        HttpHeaders hearders = new HttpHeaders();
        hearders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateCandidateDTO> request = new HttpEntity<>(createCandidateDTO, hearders);

        var result = rt.postForObject("http:/localhost:8080/candidate/", request, String.class);
        System.out.println(result);


    }
    
}
