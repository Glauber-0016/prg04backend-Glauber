package br.com.ifba.user.service;

import br.com.ifba.infrastructure.exception.BusinessException;
import br.com.ifba.infrastructure.exception.ResourceNotFoundException;
import br.com.ifba.user.dto.UsuarioPostRequestDto;
import br.com.ifba.user.entity.User;
import br.com.ifba.user.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    @Transactional
    public User save(UsuarioPostRequestDto dto) {
        User user = new User();
        user.setNome(dto.getNome());
        user.setEmail(dto.getEmail());
        user.setSenha(dto.getSenha());
        return usuarioRepository.save(user);
    }

    @Transactional
    public User update(Long id, UsuarioPostRequestDto dto) {
        User userExistente = this.findById(id);
        userExistente.setNome(dto.getNome());
        userExistente.setEmail(dto.getEmail());
        userExistente.setSenha(dto.getSenha());
        return usuarioRepository.save(userExistente);
    }

    @Transactional
    public void delete(Long id) {
        User userExistente = this.findById(id);
        usuarioRepository.delete(userExistente);
    }

    @Transactional(readOnly = true)
    public void login(String nome, String senha) {
        User user = usuarioRepository.findByNome(nome)
                .orElseThrow(() -> new BusinessException("Nome de usuário ou senha inválidos."));

        if (!user.getSenha().equals(senha)) {
            throw new BusinessException("Nome de usuário ou senha inválidos.");
        }

    }
}