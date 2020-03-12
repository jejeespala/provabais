package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.ProfessorListagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface ProfessorListagemMapper extends EntityMapper<ProfessorListagemDTO, Professor> {

    @Mapping(target = "idade", expression = "java(java.time.Period.between(professor.getDataNascimento(), java.time.LocalDate.now()).getYears())" )
    ProfessorListagemDTO toDto(Professor professor);

}
