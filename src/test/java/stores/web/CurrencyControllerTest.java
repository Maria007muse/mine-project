package stores.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stores.Currency;
import stores.data.CurrencyRepository;
import stores.data.DealsRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CurrencyController.class)
@AutoConfigureMockMvc
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyRepository currencyRepository;

    @MockBean
    private DealsRepository dealsRepository;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowAllCurrencies() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("allCurrencies"))
                .andExpect(model().attributeExists("allCurrencies"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowAddCurrencyForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addCurrency"))
                .andExpect(model().attributeExists("currency"));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowEditCurrencyForm() throws Exception {
        Long id = 1L;
        Currency currency = new Currency();
        currency.setId(id);
        when(currencyRepository.findById(id)).thenReturn(Optional.of(currency));

        mockMvc.perform(MockMvcRequestBuilders.get("/currency/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("editCurrency"))
                .andExpect(model().attributeExists("currency"));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteCurrency() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/currency/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/currency/all"));
        verify(currencyRepository, times(1)).deleteById(id);
    }
}
