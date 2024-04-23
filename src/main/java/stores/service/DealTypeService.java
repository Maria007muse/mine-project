package stores.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import stores.entity.DealType;
import stores.repository.DealTypeRepository;
import stores.repository.DealsRepository;

import java.util.List;

@Service
public class DealTypeService {
    private final DealTypeRepository dealTypeRepository;
    private final DealsRepository dealsRepository;

    public DealTypeService(DealTypeRepository dealTypeRepository, DealsRepository dealsRepository) {
        this.dealTypeRepository = dealTypeRepository;
        this.dealsRepository = dealsRepository;
    }

    public List<DealType> getAllDealTypes() {
        return (List<DealType>) dealTypeRepository.findAll();
    }

    public DealType getDealTypeById(Long id) {
        return dealTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency Id:" + id));
    }
    public String addDealType(DealType dealType, BindingResult result) {
        if (result.hasErrors()) {
            return "addType";
        }
        dealTypeRepository.save(dealType);
        return "redirect:/type/all";
    }

    public String editDealTypeForm(Long id, Model model) {
        DealType dealType = dealTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal type Id:" + id));
        model.addAttribute("dealType", dealType);
        return "editType";
    }

    public String updateDealType(Long id, DealType updatedDealType, BindingResult result) {
        if (result.hasErrors()) {
            return "editType";
        }
        dealTypeRepository.save(updatedDealType);
        return "redirect:/type/all";
    }

    public String deleteDealType(Long id, Model model) {
        if (dealsRepository.existsByTypeId(id)) {
            model.addAttribute("error", "Сделка с этим типом существует. Нельзя удалить тип сделки.");
            model.addAttribute("dealTypes", dealTypeRepository.findAll());
            return "allTypes";
        }
        dealTypeRepository.deleteById(id);
        return "redirect:/type/all";
    }
}


