package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProfessorMapper extends EntityMapper<ProfessorDTO, Professor> {
}
