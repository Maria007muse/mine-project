package stores.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import stores.entity.DealPlace;
import stores.repository.DealPlaceRepository;
import stores.repository.DealsRepository;
import stores.service.DealPlaceService;

@Controller
@RequestMapping("/place")
public class DealPlaceController {
    private final DealPlaceService dealPlaceService;

    public DealPlaceController(DealPlaceService dealPlaceService) {
        this.dealPlaceService = dealPlaceService;
    }

    @GetMapping("/all")
    public String allPlaces(Model model) {
        model.addAttribute("allPlaces", dealPlaceService.getAllPlaces());
        return "allPlaces";
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addPlaceForm(Model model) {
        model.addAttribute("place", new DealPlace());
        return "addPlace";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addPlace(@Valid @ModelAttribute("place") DealPlace place, Errors errors) {
        return dealPlaceService.addPlace(place, errors);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editPlaceForm(@PathVariable Long id, Model model) {
        return dealPlaceService.editPlaceForm(id, model);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updatePlace(@PathVariable Long id, @Valid @ModelAttribute("place") DealPlace place, Errors errors) {
        return dealPlaceService.updatePlace(id, place, errors);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePlace(@PathVariable Long id, Model model) {
        return dealPlaceService.deletePlace(id, model);
    }
}

