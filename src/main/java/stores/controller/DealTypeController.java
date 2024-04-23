package stores.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import stores.entity.DealType;
import stores.repository.DealTypeRepository;
import stores.repository.DealsRepository;
import stores.service.DealTypeService;

@Controller
@RequestMapping("/type")
public class DealTypeController {
    private final DealTypeService dealTypeService;

    public DealTypeController(DealTypeService dealTypeService) {
        this.dealTypeService = dealTypeService;
    }

    @GetMapping("/all")
    public String showAllDealTypes(Model model) {
        model.addAttribute("dealTypes", dealTypeService.getAllDealTypes());
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
        return dealTypeService.addDealType(dealType, result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/edit/{id}")
    public String showEditDealTypeForm(@PathVariable Long id, Model model) {
        return dealTypeService.editDealTypeForm(id, model);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/edit/{id}")
    public String editDealType(@PathVariable Long id, @ModelAttribute("dealType") @Valid DealType updatedDealType, BindingResult result) {
        return dealTypeService.updateDealType(id, updatedDealType, result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteDealType(@PathVariable Long id, Model model) {
        return dealTypeService.deleteDealType(id, model);
    }
}
