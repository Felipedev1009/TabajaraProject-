package br.com.projetotabajara.tabajara.service;

import java.time.LocalDateTime;
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

    // MÉTODO PARA SALVAR UM USUÁRIO

    public Usuario save(Usuario usuario) {
        // Criptografar a senha anstes de salvar!!!
        usuario.setSenhaUsuario(passwordEncoder.encode(usuario.getSenhaUsuario()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    // Metodo para gerar um token de recuperação de senha
    public String gerarTokenRecupercao(String email) {
        Usuario usuario = usuarioRepository.findByEmailUsuario(email).orElse(null);
        if (usuario == null) {
            return null;
        }
        String token = java.util.UUID.randomUUID().toString();
        // Define token e expiração (30 minutos)
        usuario.setResetToken(token);
        usuario.setTokenExpiracao(LocalDateTime.now().plusMinutes(30));
        usuarioRepository.save(usuario);
        return token;
    }

    public boolean redefinirSenha(String token, String novaSenha) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'redefinirSenha'");
    }

   
   
    }
