package org.learning.java.springlamiapizzeriarelazioni.controller;

import jakarta.persistence.GeneratedValue;
import org.learning.java.springlamiapizzeriarelazioni.model.Pizza;
import org.learning.java.springlamiapizzeriarelazioni.model.SpecialOffer;
import org.learning.java.springlamiapizzeriarelazioni.repository.PizzaRepository;
import org.learning.java.springlamiapizzeriarelazioni.repository.SpecialOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class SpecialOfferController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model){
        Optional<Pizza> pizzaResult = pizzaRepository.findById(pizzaId);
        if (pizzaResult.isPresent()){
            Pizza pizza = pizzaResult.get();
            SpecialOffer specialOffer = new SpecialOffer();
            model.addAttribute("specialoffer", specialOffer);
            return "offers/form";
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "pizza with id " + pizzaId + " not found");
        }
    }

    @PostMapping("/create")
    public String doCreate(@ModelAttribute("specialoffer") SpecialOffer specialOfferForm,
        Model model){
        specialOfferRepository.save(specialOfferForm);
        return "redirect:/pizza/" + specialOfferForm.getPizza().getId();
    }

    //------------------Edit-------------------

    @GetMapping("/edit/{specialofferId}")
    public String edit(@PathVariable("specialofferId") Integer id, Model model){
       Optional<SpecialOffer> specialOfferResult = specialOfferRepository.findById(id);
       if (specialOfferResult.isPresent()){
           model.addAttribute("specialoffer",specialOfferResult);
           return "/offers/edit";
       }else {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping("/edit/{specialofferId}")
    public String doEdit(@PathVariable("specialofferId") Integer specialofferId,
        @ModelAttribute("specialoffer") SpecialOffer specialOfferForm, Model model){
        specialOfferForm.setId(specialofferId);
        specialOfferRepository.save(specialOfferForm);
        return "redirect:/pizza/" + specialOfferForm.getPizza().getId();
    }

    //-----------------Delete----------------------

    @PostMapping("/delete/{specialofferId}")
    public String delete(@PathVariable("specialofferId") Integer id){
        Optional<SpecialOffer> specialOfferResult = specialOfferRepository.findById(id);
        if (specialOfferResult.isPresent()){
            Integer pizzaId = specialOfferResult.get().getPizza().getId();
            specialOfferRepository.deleteById(id);
            return "redirect:/pizza/" + pizzaId;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}
