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

    public Usuario save(Usuario usuario){
        //Criptografar a senha antes de salvar
        usuario.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

   // ...existing code...
    public void excluir(Long id){
        usuarioRepository.deleteById(id);
    }

    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElse(null);
    }
// ...existing code...
}