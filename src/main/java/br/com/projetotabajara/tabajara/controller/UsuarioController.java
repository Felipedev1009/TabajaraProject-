package br.com.projetotabajara.tabajara.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // LISTAR TODOS
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Usuario> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "usuarios/listar";
    }

    // FORMULÁRIO DE CRIAÇÃO
    @GetMapping("/criar")
    public String criarForm(Model model){
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "usuario/formularioUsuario";
    }

    // SALVAR USUÁRIO
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("usuario") Usuario usuario) {
        usuarioService.save(usuario);
        return "redirect:/usuarios/listar"; // consistent route
    }

    // EXCLUIR USUÁRIO
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id) {
        usuarioService.excluir(id);
        return "redirect:/usuarios/listar";
    }

}
