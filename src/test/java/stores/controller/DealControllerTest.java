package stores.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.support.SessionStatus;
import stores.entity.Deals;
import stores.entity.Role;
import stores.entity.User;
import stores.repository.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DealControllerTest {

    @InjectMocks
    private DealController dealController;

    @Mock
    private DealsRepository dealRepository;

    @Mock
    private DealTypeRepository dealTypeRepository;

    @Mock
    private DealPlaceRepository dealPlaceRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDealRepository userDealRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAllDeals() {
        Model model = mock(Model.class);
        List<Deals> deals = new ArrayList<>();
        when(dealRepository.findAll()).thenReturn(deals);

        String viewName = dealController.allDeals(model);

        assertEquals("allDeals", viewName);
        verify(model).addAttribute("allDeals", deals);
    }

    @Test
    public void testProcessDeal() {
        Model model = mock(Model.class);
        Errors errors = mock(Errors.class);
        SessionStatus sessionStatus = mock(SessionStatus.class);
        Principal principal = mock(Principal.class);
        Deals deal = new Deals();

        when(errors.hasErrors()).thenReturn(false);
        when(principal.getName()).thenReturn("testUser");
        when(userRepository.findByUserName("testUser")).thenReturn(new User());

        String viewName = dealController.processDeal(deal, errors, sessionStatus, model, principal);

        assertEquals("redirect:/deal/all", viewName);
        verify(dealRepository).save(deal);
        verify(sessionStatus).setComplete();
    }

    @Test
    public void testEditDeal() {
        Model model = mock(Model.class);
        Principal principal = mock(Principal.class);
        Deals deal = new Deals();
        User user = new User();

        user.setRoles(Collections.singletonList(new Role("ROLE_ADMIN")));
        when(dealRepository.findById(1L)).thenReturn(java.util.Optional.of(deal));
        when(dealTypeRepository.findAll()).thenReturn(new ArrayList<>());
        when(dealPlaceRepository.findAll()).thenReturn(new ArrayList<>());
        when(currencyRepository.findAll()).thenReturn(new ArrayList<>());
        when(principal.getName()).thenReturn("testUser");
        when(userRepository.findByUserName("testUser")).thenReturn(user);

        String viewName = dealController.editDeal(1L, model, principal);

        assertEquals("DealEdit", viewName);
        verify(model).addAttribute("deal", deal);
        verify(dealTypeRepository).findAll();
        verify(dealPlaceRepository).findAll();
        verify(currencyRepository).findAll();
    }

    @Test
    public void testDeleteDeal() {
        Principal principal = mock(Principal.class);
        Deals deal = new Deals();
        User user = new User();
        user.setRoles(Collections.singletonList(new Role("ROLE_ADMIN")));
        when(dealRepository.findById(1L)).thenReturn(java.util.Optional.of(deal));
        when(principal.getName()).thenReturn("testUser");
        when(userRepository.findByUserName("testUser")).thenReturn(user);

        String viewName = dealController.deleteDeal(1L, principal);

        assertEquals("redirect:/deal/all", viewName);
        verify(userDealRepository).deleteByDealId(1L);
        verify(dealRepository).deleteById(1L);
    }

}
