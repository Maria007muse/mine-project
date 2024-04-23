package stores.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import stores.entity.Currency;
import stores.repository.CurrencyRepository;
import stores.repository.DealsRepository;
import stores.service.CurrencyService;

@Controller
@RequestMapping("/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/all")
    public String allCurrencies(Model model) {
        model.addAttribute("allCurrencies", currencyService.getAllCurrencies());
        return "allCurrencies";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCurrencyForm(Model model) {
        model.addAttribute("currency", new Currency());
        return "addCurrency";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCurrency(@Valid @ModelAttribute("currency") Currency currency, Errors errors) {
        return currencyService.addCurrency(currency, errors);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCurrencyForm(@PathVariable Long id, Model model) {
        Currency currency = currencyService.getCurrencyById(id);
        model.addAttribute("currency", currency);
        return "editCurrency";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCurrency(@PathVariable Long id, @Valid @ModelAttribute("currency") Currency currency, Errors errors) {
        return currencyService.updateCurrency(id, currency, errors);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCurrency(@PathVariable Long id, Model model) {
        return currencyService.deleteCurrency(id, model);
    }

}

