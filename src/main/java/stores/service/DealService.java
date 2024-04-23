package stores.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;
import stores.entity.*;
import stores.repository.*;

import java.security.Principal;
import java.util.List;

@Service
public class DealService {
    private final DealsRepository dealRepository;
    private final DealTypeRepository dealTypeRepository;
    private final DealPlaceRepository dealPlaceRepository;
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;
    private final UserDealRepository userDealRepository;

    public DealService(DealsRepository dealRepository, DealTypeRepository dealTypeRepository,
                       DealPlaceRepository dealPlaceRepository, CurrencyRepository currencyRepository,
                       UserRepository userRepository, UserDealRepository userDealRepository) {
        this.dealRepository = dealRepository;
        this.dealTypeRepository = dealTypeRepository;
        this.dealPlaceRepository = dealPlaceRepository;
        this.currencyRepository = currencyRepository;
        this.userRepository = userRepository;
        this.userDealRepository = userDealRepository;
    }

    public List<Deals> getAllDeals() {
        return (List<Deals>) dealRepository.findAll();
    }
    public List<DealType> getAllDealTypes() {
        return (List<DealType>) dealTypeRepository.findAll();
    }
    public List<DealPlace> getAllDealPlaces() {
        return (List<DealPlace>) dealPlaceRepository.findAll();
    }
    public List<Currency> getAllCurrencies() {
        return (List<Currency>) currencyRepository.findAll();
    }
    public String createDealForm(Model model) {
        model.addAttribute("deal", new Deals());
        model.addAttribute("allDealTypes", dealTypeRepository.findAll());
        model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
        model.addAttribute("allCurrencies", currencyRepository.findAll());
        return "Deal";
    }

    public String processDeal(Deals deal, Errors errors, SessionStatus sessionStatus, Model model, Principal principal) {
        if (errors.hasErrors()) {
            model.addAttribute("allDealTypes", dealTypeRepository.findAll());
            model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
            model.addAttribute("allCurrencies", currencyRepository.findAll());
            return "Deal";
        }

        String username = principal.getName();
        User currentUser = userRepository.findByUserName(username);
        if (currentUser == null) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        dealRepository.save(deal);
        saveUserDeal(currentUser, deal);
        sessionStatus.setComplete();
        return "redirect:/deal/all";
    }

    public String editDeal(Long id, Model model, Principal principal) {
        Deals deal = findDealById(id);
        if (!hasAccess(principal, deal)) {
            return "accessDenied";
        }

        model.addAttribute("deal", deal);
        model.addAttribute("allDealTypes", dealTypeRepository.findAll());
        model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
        model.addAttribute("allCurrencies", currencyRepository.findAll());
        return "DealEdit";
    }

    public String updateDeal(Long id, Deals deal, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("allDealTypes", dealTypeRepository.findAll());
            model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
            model.addAttribute("allCurrencies", currencyRepository.findAll());
            return "DealEdit";
        }
        dealRepository.save(deal);
        return "redirect:/deal/all";
    }

    public String deleteDeal(Long id, Principal principal) {
        Deals deal = findDealById(id);
        if (!hasAccess(principal, deal)) {
            return "accessDenied";
        }
        userDealRepository.deleteByDealId(id);
        dealRepository.deleteById(id);
        return "redirect:/deal/all";
    }

    private Deals findDealById(Long id) {
        return dealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal Id:" + id));
    }

    private void saveUserDeal(User user, Deals deal) {
        UserDeal userDeal = new UserDeal();
        userDeal.setUserId(user.getId());
        userDeal.setDealId(deal.getId());
        userDealRepository.save(userDeal);
    }

    private boolean hasAccess(Principal principal, Deals deal) {
        String username = principal.getName();
        User currentUser = userRepository.findByUserName(username);
        boolean isAdmin = currentUser.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return true;
        }

        UserDeal userDeal = userDealRepository.findByUserIdAndDealId(currentUser.getId(), deal.getId());
        return userDeal != null;
    }
}
