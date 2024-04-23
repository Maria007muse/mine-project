package stores.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.Model;
import stores.entity.Currency;
import stores.service.CurrencyService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyService currencyService;

    @Test
    @WithMockUser
    public void testAllCurrencies() throws Exception {
        List<Currency> currencies = Arrays.asList(new Currency(), new Currency());
        when(currencyService.getAllCurrencies()).thenReturn(currencies);

        mockMvc.perform(MockMvcRequestBuilders.get("/currency/all"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allCurrencies", currencies))
                .andExpect(view().name("allCurrencies"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAddCurrencyForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCurrency"))
                .andExpect(model().attributeExists("currency"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testEditCurrencyForm() throws Exception {
        Long currencyId = 1L;
        Currency currency = new Currency();
        when(currencyService.getCurrencyById(currencyId)).thenReturn(currency);

        mockMvc.perform(MockMvcRequestBuilders.get("/currency/edit/{id}", currencyId))
                .andExpect(status().isOk())
                .andExpect(view().name("editCurrency"))
                .andExpect(model().attribute("currency", currency));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteCurrency() {
        Long currencyId = 1L;
        Model model = mock(Model.class);
        when(currencyService.deleteCurrency(currencyId, model)).thenReturn("allCurrencies");
        CurrencyController controller = new CurrencyController(currencyService);
        String viewName = controller.deleteCurrency(currencyId, model);
        assertEquals("allCurrencies", viewName);
        verify(currencyService, times(1)).deleteCurrency(currencyId, model);
    }
}
