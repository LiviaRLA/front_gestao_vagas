package br.com.livia.front_gestao_vagas.modules.candidate.services;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.RestTemplate;
import br.com.livia.front_gestao_vagas.modules.candidate.dto.JobsDTO;



@Service
public class FindJobsService {


    public List<JobsDTO> execute(String token, String filter) {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);
        
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8080/candidate/job").queryParam("filter", filter);

        ParameterizedTypeReference<List<JobsDTO>> responseType = new ParameterizedTypeReference<List<JobsDTO>>() {
            
        };


        try {
            var result = rt.exchange(builder.toUriString(), HttpMethod.GET, request, responseType);

            return result.getBody();
            
        } catch (Unauthorized e) {

            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);

        }

    }
    
}
