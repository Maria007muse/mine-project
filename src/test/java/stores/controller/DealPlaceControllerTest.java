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
import stores.entity.DealPlace;
import stores.repository.DealPlaceRepository;
import stores.repository.DealsRepository;
import stores.service.CurrencyService;
import stores.service.DealPlaceService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DealPlaceController.class)
public class DealPlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealPlaceService dealPlaceService;

    @Test
    @WithMockUser
    public void testAllPlaces() throws Exception {
        List<DealPlace> places = Arrays.asList(new DealPlace(), new DealPlace());
        when(dealPlaceService.getAllPlaces()).thenReturn(places);

        mockMvc.perform(MockMvcRequestBuilders.get("/place/all"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("allPlaces", places))
                .andExpect(view().name("allPlaces"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testAddPlaceForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/place/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPlace"))
                .andExpect(model().attributeExists("place"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeletePlace() {

        Long placeId = 1L;
        Model model = mock(Model.class);
        when(dealPlaceService.deletePlace(placeId, model)).thenReturn("allPlaces");
        DealPlaceController controller = new DealPlaceController(dealPlaceService);

        String viewName = controller.deletePlace(placeId, model);
        assertEquals("allPlaces", viewName);
        verify(dealPlaceService, times(1)).deletePlace(placeId, model);
    }

}
