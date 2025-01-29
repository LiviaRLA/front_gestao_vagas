package br.com.livia.front_gestao_vagas.modules.candidate.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateDTO {

    private UUID id;
    private String name;
    private String username;
    private String email;
    private String description;
    
}
