package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoListagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Mapper(componentModel = "spring",uses = {})
public interface AlunoListagemMapper extends EntityMapper<AlunoListagemDTO, Aluno> { //criado um mapper para AlunoListagem

    @Mapping(target = "idade", expression = "java(java.time.Period.between(aluno.getDataNascimento(), java.time.LocalDate.now()).getYears())" )
    AlunoListagemDTO toDto(Aluno aluno);

}
