package br.com.projetotabajara.tabajara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.service.UsuarioService;


@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    //MÉTODO PARA CRIAR UM NOVO USUÁRIO E ENCAMINHAR PARA O FORMULÁRIO DE CADASTRO

    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/formularioUsuario";
    }

    // Listar usuários
    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuario/listarUsuario";
    }

    // Abrir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioService.findById(id);
        model.addAttribute("usuario", usuario);
        return "usuario/formularioUsuario";
    }

    // Excluir usuário
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        usuarioService.deleteById(id);
        return "redirect:/usuarios/listar";
    }

    // Salvar Usuário
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario) {
        boolean novoUsuario = usuario.getIdUsuario() == null;
        usuarioService.save(usuario);
        if (novoUsuario) {
            return "redirect:/login?registered";
        }
        return "redirect:/usuarios/listar";
    }

}

