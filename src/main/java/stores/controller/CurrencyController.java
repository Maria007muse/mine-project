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

@Controller
@RequestMapping("/currency")
public class CurrencyController {
    private final CurrencyRepository currencyRepository;
    private final DealsRepository dealsRepository;

    public CurrencyController(CurrencyRepository currencyRepository, DealsRepository dealsRepository) {
        this.currencyRepository = currencyRepository;
        this.dealsRepository = dealsRepository;
    }

    @GetMapping("/all")
    public String allCurrencies(Model model) {
        model.addAttribute("allCurrencies", currencyRepository.findAll());
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
        if (errors.hasErrors()) {
            return "addCurrency";
        }
        currencyRepository.save(currency);
        return "redirect:/currency/all";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCurrencyForm(@PathVariable Long id, Model model) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency Id:" + id));
        model.addAttribute("currency", currency);
        return "editCurrency";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateCurrency(@PathVariable Long id, @Valid @ModelAttribute("currency") Currency currency, Errors errors) {
        if (errors.hasErrors()) {
            return "editCurrency";
        }
        currencyRepository.save(currency);
        return "redirect:/currency/all";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCurrency(@PathVariable Long id, Model model) {
        if (dealsRepository.existsByCurrencyId(id)) {
            model.addAttribute("error", "Невозможно удалить валюту, так как с ней связана сделка.");
            model.addAttribute("allCurrencies", currencyRepository.findAll());
            return "allCurrencies";
        }
        currencyRepository.deleteById(id);
        return "redirect:/currency/all";
    }
}

