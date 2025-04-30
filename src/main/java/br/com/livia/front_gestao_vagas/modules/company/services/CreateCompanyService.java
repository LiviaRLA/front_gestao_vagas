package br.com.livia.front_gestao_vagas.modules.company.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import br.com.livia.front_gestao_vagas.modules.company.dto.CreateCompanyDTO;

@Service
public class CreateCompanyService {

    @Value("${host.api.gestao.vagas}")
    private String hostAPI;

    public void execute(CreateCompanyDTO createCompanyDTO) {

        try {

            RestTemplate rt = new RestTemplate();

            HttpHeaders hearders = new HttpHeaders();
            hearders.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<CreateCompanyDTO> request = new HttpEntity<>(createCompanyDTO, hearders);

            String url = hostAPI.concat("/company/");

            var result = rt.postForObject(url, request, String.class);
            System.out.println(result);

        } catch (HttpClientErrorException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getMessage());

        }
    }
    
}
