package org.learning.java.springlamiapizzeriarelazioni.controller;

import jakarta.validation.Valid;
import org.learning.java.springlamiapizzeriarelazioni.model.Pizza;
import org.learning.java.springlamiapizzeriarelazioni.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizzaList = pizzaRepository.findAll();
        model.addAttribute("pizza", pizzaList);
        return "pizzas/index";
    }

    //-------------Detail------------------
    @GetMapping("/pizza/{id}")
    public String detail(@PathVariable Integer id,Model model){
        Optional<Pizza> pizzaResult = pizzaRepository.findById(id);
        if (pizzaResult.isPresent()){
            Pizza pizzaDB = pizzaResult.get();
            model.addAttribute("pizza", pizzaDB);
            return "pizzas/detail";
        }else {
            // se l'opional è vuoto sollevo un'eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //------------Create--------------

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        return "pizzas/form";
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute("pizza") Pizza pizzaForm) {
        pizzaRepository.save(pizzaForm);
        return "redirect:/";
    }


    //-----------------Edit-----------------

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Pizza> pizzaResult = pizzaRepository.findById(id);
        if (pizzaResult.isPresent()) {
            model.addAttribute("pizza", pizzaResult.get());
            return "pizzas/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza with id " + id + " not found");
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
    @ModelAttribute("pizza") Pizza pizzaForm){
        pizzaRepository.save(pizzaForm);
        return "redirect:/";
    }

    //--------------Delete-----------------
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        pizzaRepository.deleteById(id);
        return "redirect:/";
    }

}
