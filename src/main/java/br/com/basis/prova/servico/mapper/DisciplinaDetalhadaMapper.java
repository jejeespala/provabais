package br.com.basis.prova.servico.mapper;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface DisciplinaDetalhadaMapper extends EntityMapper<DisciplinaDetalhadaDTO, Disciplina> {

    @Mapping(target = "professor.disciplinas",ignore = true)
    DisciplinaDetalhadaDTO toDto(Disciplina disciplina);
}
