package br.com.projetotabajara.tabajara.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    // Método para salvar um usuário
    public Usuario save(Usuario usuario) {
        if (usuario.getSenhaUsuario() != null && !usuario.getSenhaUsuario().isBlank()) {
            String senha = usuario.getSenhaUsuario();
            if (!senha.startsWith("$2a$") && !senha.startsWith("$2b$") && !senha.startsWith("$2y$")) {
                usuario.setSenhaUsuario(passwordEncoder.encode(senha));
            }
        }
        return usuarioRepository.save(usuario);
    }
    public List<Usuario> findAll(){ 
        return usuarioRepository.findAll();
    }
    public Usuario findById(Integer id){
        return usuarioRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }

    // Método para gerar um token de recuperação de senha
    public String gerarTokenRecuperacao(String email){
        Usuario usuario = usuarioRepository.findByEmailUsuario(email).orElse(null);
        if(usuario == null){
            return null;
        }
        // Gera um token
        String token = UUID.randomUUID().toString();
        // Define token e expiração(30 minutos)
        usuario.setResetToken(token);
        usuario.setTokenExpiracao(LocalDateTime.now().plusMinutes(30));
        usuarioRepository.save(usuario);
        return token;
    }

    // Método para redefinir a senha
    public boolean redefinirSenha(String token, String novaSenha){
        Usuario usuario = usuarioRepository.findByResetToken(token);
        // Valida o token
        if(usuario == null || usuario.getTokenExpiracao()
            .isBefore(LocalDateTime.now())){
                return false;
            }
        // Criptografa a nova senha
        usuario.setSenhaUsuario(passwordEncoder.encode(novaSenha));
        // Limpa token após o uso
        usuario.setResetToken(null);
        usuario.setTokenExpiracao(null);
        usuarioRepository.save(usuario);
        return true;
    }
}