package stores.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import stores.DealType;
import stores.Deals;
import stores.data.DealTypeRepository;
import stores.data.DealsRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/type")
public class DealTypeController {
    private final DealTypeRepository dealTypeRepository;
    private final DealsRepository dealRepository;

    public DealTypeController(DealTypeRepository dealTypeRepository, DealsRepository dealRepository) {
        this.dealTypeRepository = dealTypeRepository;
        this.dealRepository = dealRepository;
    }

    @GetMapping("/all")
    public String showAllDealTypes(Model model) {
        model.addAttribute("dealTypes", dealTypeRepository.findAll());
        model.addAttribute("error", null);
        return "allTypes";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/add")
    public String showAddDealTypeForm(Model model) {
        model.addAttribute("dealType", new DealType());
        return "addType";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public String addType(@ModelAttribute("dealType") @Valid DealType dealType, BindingResult result) {
        if (result.hasErrors()) {
            return "addType";
        }
        dealTypeRepository.save(dealType);
        return "redirect:/type/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditDealTypeForm(@PathVariable Long id, Model model) {
        DealType dealType = dealTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal type Id:" + id));
        model.addAttribute("dealType", dealType);
        return "editType";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String editDealType(@PathVariable Long id, @ModelAttribute("dealType") @Valid DealType updatedDealType, BindingResult result) {
        if (result.hasErrors()) {
            return "editType";
        }
        dealTypeRepository.save(updatedDealType);
        return "redirect:/type/all";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteDealType(@PathVariable Long id, Model model) {
        if (dealRepository.existsByTypeId(id)) {
            model.addAttribute("error", "Сделка с этим типом существует. Нельзя удалить тип сделки.");
            model.addAttribute("dealTypes", dealTypeRepository.findAll());
            return "allTypes";
        }
        dealTypeRepository.deleteById(id);
        return "redirect:/type/all";
    }
}



