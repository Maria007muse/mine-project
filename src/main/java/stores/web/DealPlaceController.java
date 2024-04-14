package stores.web;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import stores.DealPlace;
import stores.data.DealPlaceRepository;
import stores.data.DealsRepository;

@Controller
@RequestMapping("/place")
public class DealPlaceController {
    private final DealPlaceRepository dealPlaceRepository;
    private final DealsRepository dealsRepository;

    public DealPlaceController(DealPlaceRepository dealPlaceRepository, DealsRepository dealsRepository) {
        this.dealPlaceRepository = dealPlaceRepository;
        this.dealsRepository = dealsRepository;
    }

    @GetMapping("/all")
    public String allPlaces(Model model) {
        model.addAttribute("allPlaces", dealPlaceRepository.findAll());
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
        if (errors.hasErrors()) {
            return "addPlace";
        }
        dealPlaceRepository.save(place);
        return "redirect:/place/all";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editPlaceForm(@PathVariable Long id, Model model) {
        DealPlace place = dealPlaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid place Id:" + id));
        model.addAttribute("place", place);
        return "editPlace";
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updatePlace(@PathVariable Long id, @Valid @ModelAttribute("place") DealPlace place, Errors errors) {
        if (errors.hasErrors()) {
            return "editPlace";
        }
        dealPlaceRepository.save(place);
        return "redirect:/place/all";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deletePlace(@PathVariable Long id, Model model) {
        if (dealsRepository.existsByPlaceId(id)) {
            model.addAttribute("error", "Невозможно удалить место, так как с ним связана сделка.");
            model.addAttribute("allPlaces", dealPlaceRepository.findAll());
            return "allPlaces";
        }
        dealPlaceRepository.deleteById(id);
        return "redirect:/place/all";
    }
}
