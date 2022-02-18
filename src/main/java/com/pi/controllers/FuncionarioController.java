package com.pi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pi.models.Funcionario;
import com.pi.repository.FuncionarioRepository;


@Controller
public class FuncionarioController {
    
    @Autowired
    private FuncionarioRepository fr;
    
    //Chama o form que cadastra o funcionario
    
    @RequestMapping("/cadastrarFuncionario")
    public String form() {
        return "funcionario/form-funcionario";
    }
    
    //Post que cadastra o funcionario
    @RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.POST)
    public String form(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {
        
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos...");
            return "redirect:/cadastrarFuncionario";
        }
        
        fr.save(funcionario);
        attributes.addFlashAttribute("mensagem", "Funcionario cadastrado com sucesso!");
        return "redirect:/cadastrarFuncionario";
    }
    
    // GET que lista os funcionarios
    @RequestMapping("/funcionarios")
    public ModelAndView listaFuncionarios() {
        ModelAndView mv = new ModelAndView("funcionario/lista-funcionario");
        Iterable<Funcionario> funcionarios = fr.findAll();
        mv.addObject("funcionarios", funcionarios);
        return mv;
    }
    
    // GET que mostra os detalhes dos funcionarios
    @RequestMapping("/funcionario/{codigo}")
    public ModelAndView detalhesFuncionario(@PathVariable("codigo") long codigo) {
        Funcionario funcionario = fr.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("funcionario/detalhes-funcionario");
        mv.addObject("funcionario", funcionario);

        return mv;
    }
    
    // GET que deleta o funcionario
    @RequestMapping("/deletarFuncionario")
    public String deletarFuncionario(long codigo) {
        Funcionario funcionario = fr.findByCodigo(codigo);
        fr.delete(funcionario);
        return "redirect:/funcionarios";
    }
    
	// Métodos que atualiza o funcionario
	// GET que chama o formulário de edição do funcionario
	@RequestMapping("/editar-funcionario")
	public ModelAndView editarFuncionario(long codigo) {
		Funcionario funcionario = fr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("funcionario", funcionario);
		return mv;
	}

	// POST do FORM que atualiza o funcionario
	@RequestMapping(value = "/editar-funcionario", method = RequestMethod.POST)
	public String updateFuncionario(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attributes) {
		fr.save(funcionario);
		attributes.addFlashAttribute("success", "Funcionario alterado com sucesso!");
		return "redirect:/funcionarios";
	}
}
