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
import stores.entity.Deals;
import stores.service.DealService;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DealController.class)
public class DealControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealService dealService;

    @Test
    @WithMockUser
    public void testAllDeals() throws Exception {
        List<Deals> deals = Arrays.asList(new Deals(), new Deals());
        when(dealService.getAllDeals()).thenReturn(deals);

        mockMvc.perform(MockMvcRequestBuilders.get("/deal/all"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allDeals", deals))
                .andExpect(view().name("allDeals"));
    }

    @Test
    @WithMockUser
    public void testDeleteDeal() throws Exception {
        Long dealId = 1L;
        when(dealService.deleteDeal(eq(dealId), any(Principal.class)))
                .thenReturn("redirect:/deal/all");

        mockMvc.perform(MockMvcRequestBuilders.get("/deal/delete/{id}", dealId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/deal/all"));
    }
}
