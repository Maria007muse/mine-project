package stores.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stores.entity.DealPlace;
import stores.repository.DealPlaceRepository;
import stores.repository.DealsRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DealPlaceController.class)
public class DealPlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealPlaceRepository dealPlaceRepository;

    @MockBean
    private DealsRepository dealsRepository;

    @Test
    @WithMockUser
    public void testShowAllPlaces() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/place/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("allPlaces"))
                .andExpect(model().attributeExists("allPlaces"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowAddPlaceForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/place/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addPlace"))
                .andExpect(model().attributeExists("place"));
    }



    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowEditPlaceForm() throws Exception {
        Long id = 1L;
        DealPlace place = new DealPlace();
        place.setId(id);
        when(dealPlaceRepository.findById(id)).thenReturn(Optional.of(place));

        mockMvc.perform(MockMvcRequestBuilders.get("/place/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("editPlace"))
                .andExpect(model().attributeExists("place"));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeletePlace() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/place/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/place/all"));
        verify(dealPlaceRepository, times(1)).deleteById(id);
    }
}
