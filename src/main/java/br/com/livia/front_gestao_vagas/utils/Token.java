package br.com.livia.front_gestao_vagas.utils;

import java.util.List;

import lombok.Data;

@Data
public class Token {

    private String accessToken;
    private List<String> roles;
    private Long expiresIn;

}
