package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Disciplina;
import br.com.basis.prova.dominio.Professor;
import br.com.basis.prova.dominio.dto.DisciplinaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaDetalhadaDTO;
import br.com.basis.prova.dominio.dto.DisciplinaListagemDTO;
import br.com.basis.prova.repositorio.DisciplinaRepositorio;
import br.com.basis.prova.repositorio.ProfessorRepositorio;
import br.com.basis.prova.servico.exception.RegistroNaoEncontradoException;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.DisciplinaDetalhadaMapper;
import br.com.basis.prova.servico.mapper.DisciplinaListagemMapper;
import br.com.basis.prova.servico.mapper.DisciplinaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Service
@Transactional
public class DisciplinaServico {



    @Autowired
    private DisciplinaListagemMapper disciplinaListagemMapper;
    private DisciplinaRepositorio disciplinaRepositorio;
    private DisciplinaMapper disciplinaMapper;
    private DisciplinaDetalhadaMapper disciplinaDetalhadaMapper;

    @Autowired
    private ProfessorRepositorio professorRepositorio;

    public DisciplinaServico(DisciplinaMapper disciplinaMapper, DisciplinaRepositorio disciplinaRepositorio,DisciplinaListagemMapper disciplinaListagemMapper,
                             DisciplinaDetalhadaMapper disciplinaDetalhadaMapper) {
        this.disciplinaMapper = disciplinaMapper;
        this.disciplinaRepositorio = disciplinaRepositorio;
        this.disciplinaListagemMapper = disciplinaListagemMapper;
        this.disciplinaDetalhadaMapper = disciplinaDetalhadaMapper;
    }

    public DisciplinaDTO salvar(DisciplinaDTO disciplinaDTO) {
        Disciplina disciplina = disciplinaMapper.toEntity(disciplinaDTO);
        disciplinaRepositorio.save(disciplina);

        return disciplinaMapper.toDto(disciplina);
    }

    private boolean verificaVinculacao(Disciplina disciplina){

        if(disciplina.getAluno().size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public void excluir(Integer id) {

        Disciplina disciplina = disciplinaRepositorio.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Registro não encontrado"));

        if(verificaVinculacao(disciplina)){
            new RegraNegocioException("Um aluno esta vinculado a disciplina");
            System.out.println("Não foi possível");

        }else{
            disciplinaRepositorio.deleteById(id);
        }
    }

    public List<DisciplinaListagemDTO> consultar() {
        return disciplinaListagemMapper.toDto(disciplinaRepositorio.findAll());
    }

    public DisciplinaDetalhadaDTO detalhar(Integer id) {

        Disciplina disciplina = disciplinaRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Registro não encontrado"));
        return disciplinaDetalhadaMapper.toDto(disciplina);
    }

}
