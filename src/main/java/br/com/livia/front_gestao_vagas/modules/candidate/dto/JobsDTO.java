package br.com.livia.front_gestao_vagas.modules.candidate.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobsDTO {


    private UUID id;
    private UUID companyId;
    private String name;
    private String description;
    private String level;
    private String benefits;
    private Date createdAt;
    
    
}
