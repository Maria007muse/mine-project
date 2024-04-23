package stores.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import stores.entity.Deals;
import stores.service.DealService;

import java.security.Principal;

@Controller
@RequestMapping("/deal")
public class DealController {
    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping("/create")
    public String dealForm(Model model) {
        return dealService.createDealForm(model);
    }

    @GetMapping("/all")
    public String allDeals(Model model) {
        model.addAttribute("allDeals", dealService.getAllDeals());
        model.addAttribute("allDealTypes", dealService.getAllDealTypes());
        model.addAttribute("allDealPlaces", dealService.getAllDealPlaces());
        model.addAttribute("allCurrencies", dealService.getAllCurrencies());
        return "allDeals";
    }

    @PostMapping("/submit")
    public String processDeal(@Valid @ModelAttribute("deal") Deals deal, Errors errors,
                              SessionStatus sessionStatus, Model model, Principal principal) {
        return dealService.processDeal(deal, errors, sessionStatus, model, principal);
    }

    @GetMapping("/edit/{id}")
    public String editDeal(@PathVariable Long id, Model model, Principal principal) {
        return dealService.editDeal(id, model, principal);
    }

    @PostMapping("/update/{id}")
    public String updateDeal(@PathVariable Long id, @Valid @ModelAttribute("deal") Deals deal,
                             BindingResult result, Model model, Principal principal) {
        return dealService.updateDeal(id, deal, result, model, principal);
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteDeal(@PathVariable Long id, Principal principal) {
        return dealService.deleteDeal(id, principal);
    }
}
