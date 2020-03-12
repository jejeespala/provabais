package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.ProfessorDTO;
import br.com.basis.prova.dominio.dto.ProfessorDetalhadoDTO;
import br.com.basis.prova.dominio.dto.ProfessorListagemDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.ProfessorDetalhadoMapper;
import br.com.basis.prova.servico.mapper.ProfessorListagemMapper;
import br.com.basis.prova.servico.mapper.ProfessorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProfessorServico {

    @Autowired
    private ProfessorListagemMapper professorListagemMapper;
    private ProfessorDetalhadoMapper professorDetalhadoMapper;
    private ProfessorMapper professorMapper;
    private ProfessorRepositorio professorRepositorio;

    public ProfessorServico(ProfessorMapper professorMapper, ProfessorRepositorio professorRepositorio, ProfessorListagemMapper professorListagemMapper,
                            ProfessorDetalhadoMapper professorDetalhadoMapper) {
        this.professorMapper = professorMapper;
        this.professorRepositorio = professorRepositorio;
        this.professorListagemMapper = professorListagemMapper;
        this.professorDetalhadoMapper = professorDetalhadoMapper;
    }

    public ProfessorDTO salvar(ProfessorDTO professorDTO) {

        Professor professor = professorMapper.toEntity(professorDTO);
        professorRepositorio.save(professor);

        return professorMapper.toDto(professor);
    }

    private boolean verificaVinculacaoProfessor(Professor professor){


        if(professor.getDisciplinas().size() > 0){
            return true;
        }else{
            return false;
        }

    }

    public void excluir(String matricula) {


        Professor professor = professorRepositorio.findByMatricula(matricula);
        if(verificaVinculacaoProfessor(professor)){
            new RegraNegocioException("Está vinculado a uma disciplina");
            System.out.println("Não foi possível");

        }else{
            professorRepositorio.deleteByMatricula(matricula);
        }

    }

    public List<ProfessorListagemDTO> consultar() {

        return professorListagemMapper.toDto(professorRepositorio.findAll());
    }

    public ProfessorDetalhadoDTO detalhar(Integer id) {

        Professor professor = professorRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Registro não encontrado"));
        return professorDetalhadoMapper.toDto(professor);
    }

}
