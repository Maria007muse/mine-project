package stores.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import stores.entity.Currency;
import stores.repository.CurrencyRepository;
import stores.repository.DealsRepository;

import java.util.List;

@Service
public class CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final DealsRepository dealsRepository;

    public CurrencyService(CurrencyRepository currencyRepository, DealsRepository dealsRepository) {
        this.currencyRepository = currencyRepository;
        this.dealsRepository = dealsRepository;
    }

    public List<Currency> getAllCurrencies() {
        return (List<Currency>) currencyRepository.findAll();
    }

    public String addCurrency(Currency currency, Errors errors) {
        if (errors.hasErrors()) {
            return "addCurrency";
        }
        currencyRepository.save(currency);
        return "redirect:/currency/all";
    }

    public Currency getCurrencyById(Long id) {
        return currencyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency Id:" + id));
    }

    public String updateCurrency(Long id, Currency currency, Errors errors) {
        if (errors.hasErrors()) {
            return "editCurrency";
        }
        currencyRepository.save(currency);
        return "redirect:/currency/all";
    }

    public String deleteCurrency(Long id, Model model) {
        if (dealsRepository.existsByCurrencyId(id)) {
            model.addAttribute("error", "Невозможно удалить валюту, так как с ней связана сделка.");
            model.addAttribute("allCurrencies", currencyRepository.findAll());
            return "allCurrencies";
        }
        currencyRepository.deleteById(id);
        return "redirect:/currency/all";
    }
}

