package stores.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import stores.entity.DealPlace;
import stores.repository.DealPlaceRepository;
import stores.repository.DealsRepository;

import java.util.List;

@Service
public class DealPlaceService {
    private final DealPlaceRepository dealPlaceRepository;
    private final DealsRepository dealsRepository;

    public DealPlaceService(DealPlaceRepository dealPlaceRepository, DealsRepository dealsRepository) {
        this.dealPlaceRepository = dealPlaceRepository;
        this.dealsRepository = dealsRepository;
    }

    public List<DealPlace> getAllPlaces() {
        return (List<DealPlace>) dealPlaceRepository.findAll();
    }

    public String addPlace(DealPlace place, Errors errors) {
        if (errors.hasErrors()) {
            return "addPlace";
        }
        dealPlaceRepository.save(place);
        return "redirect:/place/all";
    }
    public DealPlace getPlaceById(Long id) {
        return dealPlaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency Id:" + id));
    }

    public String editPlaceForm(Long id, Model model) {
        DealPlace place = dealPlaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid place Id:" + id));
        model.addAttribute("place", place);
        return "editPlace";
    }

    public String updatePlace(Long id, DealPlace place, Errors errors) {
        if (errors.hasErrors()) {
            return "editPlace";
        }
        dealPlaceRepository.save(place);
        return "redirect:/place/all";
    }

    public String deletePlace(Long id, Model model) {
        if (dealsRepository.existsByPlaceId(id)) {
            model.addAttribute("error", "Невозможно удалить место, так как с ним связана сделка.");
            model.addAttribute("allPlaces", dealPlaceRepository.findAll());
            return "allPlaces";
        }
        dealPlaceRepository.deleteById(id);
        return "redirect:/place/all";
    }
}

