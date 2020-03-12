package br.com.basis.prova.servico;

import br.com.basis.prova.dominio.Aluno;
import br.com.basis.prova.dominio.dto.AlunoDTO;
import br.com.basis.prova.dominio.dto.AlunoDetalhadoDTO;
import br.com.basis.prova.dominio.dto.AlunoListagemDTO;
import br.com.basis.prova.repositorio.AlunoRepositorio;
import br.com.basis.prova.servico.exception.RegraNegocioException;
import br.com.basis.prova.servico.mapper.AlunoDetalhadoMapper;
import br.com.basis.prova.servico.mapper.AlunoListagemMapper;
import br.com.basis.prova.servico.mapper.AlunoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AlunoServico {

    @Autowired
    private AlunoMapper alunoMapper;
    private AlunoListagemMapper alunoListagemMapper;
    private AlunoDetalhadoMapper alunoDetalhadoMapper;
    private AlunoRepositorio alunoRepositorio;

    public AlunoServico(AlunoMapper alunoMapper, AlunoListagemMapper alunoListagemMapper, AlunoDetalhadoMapper alunoDetalhadoMapper, AlunoRepositorio alunoRepositorio) {
        this.alunoMapper = alunoMapper;
        this.alunoListagemMapper = alunoListagemMapper;
        this.alunoDetalhadoMapper = alunoDetalhadoMapper;
        this.alunoRepositorio = alunoRepositorio;
    }

    public AlunoDTO salvar(AlunoDTO alunoDTO) {
        Aluno aluno = alunoMapper.toEntity(alunoDTO);

        if (verificarCPF(aluno)) {
            throw new RegraNegocioException("CPF já existe");
        }

        alunoRepositorio.save(aluno); //Implementado o metodo save do JPA repository

        return alunoMapper.toDto(aluno);
    }

    private boolean verificarCPF(Aluno aluno) {
        Aluno alunoCpf = alunoRepositorio.findByCpf(aluno.getCpf());
        return !(alunoCpf == null || alunoCpf.getId().equals(aluno.getId()));
    }

    private boolean verificaVinculacao(Aluno aluno) {

        if(aluno.getDisciplinas().size()>0){
            return true;
        }else{
            return false;
        }

    }

    public void excluir(String matricula) { //Implementado a exclusao com verificação da regra de negócio

        Aluno aluno = alunoRepositorio.findByMatricula(matricula);

        if (verificaVinculacao(aluno)) {
            System.out.println("Não foi possivel");

        } else {
            alunoRepositorio.deleteByMatricula(matricula);
        }

    }

    public List<AlunoListagemDTO> consultar() { //Implementado a consulta


        return alunoListagemMapper.toDto(alunoRepositorio.findAll());
    }

    public AlunoDetalhadoDTO detalhar(Integer id) { //Implementado a consulta detalhada
        Aluno aluno = alunoRepositorio.findById(id).orElseThrow(() -> new RegraNegocioException("Registro não encontrado"));
        return alunoDetalhadoMapper.toDto(aluno);
    }

}
