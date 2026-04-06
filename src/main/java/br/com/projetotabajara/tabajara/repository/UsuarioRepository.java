package br.com.projetotabajara.tabajara.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.projetotabajara.tabajara.entity.Usuario;


public interface UsuarioRepository extends JpaRepository <Usuario, Integer>{
    Optional<Usuario> findByLoginUsuario(String loginUsuario);

    //Buscar usuário pelo E-mail (p/ recuperar a senha)
    Optional<Usuario> findByEmailUsuario(String emailUsuario);

    //Buscar usuário pelo token
    Usuario findByResetToken(String resetToken);
}
