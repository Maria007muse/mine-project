package stores.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import stores.entity.Deals;
import stores.entity.User;
import stores.entity.UserDeal;
import stores.repository.*;

import java.security.Principal;

@Controller
@RequestMapping("/deal")
public class DealController {
    private final DealsRepository dealRepository;
    private final DealTypeRepository dealTypeRepository;
    private final DealPlaceRepository dealPlaceRepository;
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;
    private final UserDealRepository userDealRepository;

    public DealController(DealsRepository dealRepository, DealTypeRepository dealTypeRepository,
                          DealPlaceRepository dealPlaceRepository, CurrencyRepository currencyRepository,
                          UserRepository userRepository, UserDealRepository userDealRepository) {
        this.dealRepository = dealRepository;
        this.dealTypeRepository = dealTypeRepository;
        this.dealPlaceRepository = dealPlaceRepository;
        this.currencyRepository = currencyRepository;
        this.userRepository = userRepository;
        this.userDealRepository = userDealRepository;
    }

    @GetMapping("/create")
    public String dealForm(Model model) {
        model.addAttribute("deal", new Deals());
        model.addAttribute("allDealTypes", dealTypeRepository.findAll());
        model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
        model.addAttribute("allCurrencies", currencyRepository.findAll());
        return "Deal";
    }

    @GetMapping("/all")
    public String allDeals(Model model) {
        model.addAttribute("allDeals", dealRepository.findAll());
        model.addAttribute("allDealTypes", dealTypeRepository.findAll());
        model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
        model.addAttribute("allCurrencies", currencyRepository.findAll());
        return "allDeals";
    }

    @PostMapping("/submit")
    public String processDeal(@Valid @ModelAttribute("deal") Deals deal, Errors errors,
                              SessionStatus sessionStatus, Model model, Principal principal) {
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

    private void saveUserDeal(User user, Deals deal) {
        UserDeal userDeal = new UserDeal();
        userDeal.setUserId(user.getId());
        userDeal.setDealId(deal.getId());
        userDealRepository.save(userDeal);
    }


    @GetMapping("/edit/{id}")
    public String editDeal(@PathVariable Long id, Model model, Principal principal) {
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

    private Deals findDealById(Long id) {
        return dealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deal Id:" + id));
    }

    @PostMapping("/update/{id}")
    public String updateDeal(@PathVariable Long id, @Valid @ModelAttribute("deal") Deals deal,
                             BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            model.addAttribute("allDealTypes", dealTypeRepository.findAll());
            model.addAttribute("allDealPlaces", dealPlaceRepository.findAll());
            model.addAttribute("allCurrencies", currencyRepository.findAll());
            return "DealEdit";
        }
        dealRepository.save(deal);
        return "redirect:/deal/all";
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteDeal(@PathVariable Long id, Principal principal) {
        Deals deal = findDealById(id);
        if (!hasAccess(principal, deal)) {
            return "accessDenied";
        }
        userDealRepository.deleteByDealId(id);
        dealRepository.deleteById(id);
        return "redirect:/deal/all";
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










