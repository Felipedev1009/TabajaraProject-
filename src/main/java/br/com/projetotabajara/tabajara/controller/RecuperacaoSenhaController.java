package br.com.projetotabajara.tabajara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.projetotabajara.tabajara.service.UsuarioService;

@Controller
public class RecuperacaoSenhaController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/esqueci-senha")
    public String esqueciSenha(Model model) {
        return "esqueciSenha/esquecisenha";
    }

    @PostMapping("/esqueci-senha")
    public String processarEsqueciSenha(@RequestParam String email, Model model) {
        String token = usuarioService.gerarTokenRecuperacao(email);
        if (token == null) {
            model.addAttribute("erro", "Email não encontrado.");
            return "esqueciSenha/esquecisenha";
        }
        String link = "http://localhost:8080/redefinir-senha?token=" + token;
        model.addAttribute("mensagem", "Um link de recuperação (simulação): " + link);
        return "esqueciSenha/esquecisenha";
    }

    @GetMapping("/redefinir-senha")
    public String redefinirSenha(@RequestParam(required = false) String token, Model model) {
        if (token == null || token.isBlank()) {
            model.addAttribute("erro", "Token inválido. Solicite a recuperação de senha novamente.");
            return "esqueciSenha/esquecisenha";
        }
        model.addAttribute("token", token);
        return "esqueciSenha/redefinirsenha";
    }

    @PostMapping("/redefinir-senha")
    public String salvarNovaSenha(@RequestParam String token,
                                 @RequestParam String senha,
                                 @RequestParam String confirmar,
                                 Model model) {
        if (senha == null || confirmar == null || !senha.equals(confirmar)) {
            model.addAttribute("erro", "As senhas não conferem. Tente novamente.");
            model.addAttribute("token", token);
            return "esqueciSenha/redefinirsenha";
        }

        boolean sucesso = usuarioService.redefinirSenha(token, senha);
        if (!sucesso) {
            model.addAttribute("erro", "Falha ao redefinir senha. O token é inválido ou expirou.");
            model.addAttribute("token", token);
            return "esqueciSenha/redefinirsenha";
        }
        return "redirect:/login?resetSucesso";
    }
}