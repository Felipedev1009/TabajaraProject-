package br.com.projetotabajara.tabajara.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.repository.UsuarioRepository;



@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

      // Método para salvar um usuario
    public Usuario save(Usuario usuario) {
        usuario.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario()));
        return usuarioRepository.save(usuario);
    }
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    }
