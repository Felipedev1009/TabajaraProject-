package br.com.projetotabajara.tabajara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.projetotabajara.tabajara.entity.Usuario;
import br.com.projetotabajara.tabajara.service.UsuarioService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/criar")
     public String criarForm(Model model) {
         // Adiciona um novo fornecedor ao modelo
         model.addAttribute("usuario", new Usuario());
         // Retorna a página do formulário de fornecedores
         return "usuario/formularioUsuario";
     }

       @PostMapping("/salvar")
    public String salvar(@ModelAttribute Usuario usuario) {
        // Salva o fornecedor
        usuarioService.save(usuario);
        // Redireciona para a lista de fornecedores
        return "redirect:/login";
    }

    
}
