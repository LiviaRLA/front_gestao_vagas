package br.com.livia.front_gestao_vagas.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobsDTO {

    private String name;
    private String company;
    private String description;
    private String level;
    private String benefits;
    
    
}
