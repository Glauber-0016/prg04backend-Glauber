package br.com.ifba.usuario.service;

import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.usuario.dto.UsuarioPostRequestDto;
import br.com.ifba.usuario.entity.Usuario;
import br.com.ifba.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    @Transactional
    public Usuario save(UsuarioPostRequestDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Long id, UsuarioPostRequestDto dto) {
        Usuario usuarioExistente = this.findById(id);
        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setSenha(dto.getSenha());
        return usuarioRepository.save(usuarioExistente);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuarioExistente = this.findById(id);
        usuarioRepository.delete(usuarioExistente);
    }

    @Transactional(readOnly = true)
    public void login(String nome, String senha) {
        Usuario usuario = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Nome de usuário ou senha inválidos."));

        if (!usuario.getSenha().equals(senha)) {
            throw new BusinessException("Nome de usuário ou senha inválidos.");
        }

    }
}