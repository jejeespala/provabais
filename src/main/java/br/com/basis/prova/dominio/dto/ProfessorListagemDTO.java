package br.com.basis.prova.dominio.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class ProfessorListagemDTO { //Implementado um DTO para consulta simples de um professor

    private Integer id;
    private String nome;
    private String matricula;
    private String area;
    private Integer idade;
    @JsonIgnore
    private LocalDate dataNascimento;

}
