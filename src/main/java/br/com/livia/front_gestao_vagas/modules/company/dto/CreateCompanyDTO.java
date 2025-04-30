package br.com.livia.front_gestao_vagas.modules.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCompanyDTO {

    private String name;
    private String username;
    private String email;
    private String password;
    private String website;
    private String description;  
}
