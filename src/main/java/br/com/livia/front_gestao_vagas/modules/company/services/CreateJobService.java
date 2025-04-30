package br.com.livia.front_gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.livia.front_gestao_vagas.modules.company.dto.CreateJobDTO;


@Service
public class CreateJobService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPI;

    public String execute(String token, CreateJobDTO createJobDTO) {

            RestTemplate rt = new RestTemplate();

            HttpHeaders hearders = new HttpHeaders();
            hearders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateJobDTO> request = new HttpEntity<>(createJobDTO, hearders);

            String url = hostAPI.concat("/company/job/");

            var result = rt.postForObject(url, request, String.class);
            System.out.println(result);
            return result;
    }
    
}
