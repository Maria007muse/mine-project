package stores.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import stores.entity.DealType;
import stores.repository.DealTypeRepository;
import stores.repository.DealsRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DealTypeController.class)
public class DealTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealTypeRepository dealTypeRepository;

    @MockBean
    private DealsRepository dealRepository;

    @InjectMocks
    private DealTypeController dealTypeController;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testShowAllDealTypes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/allTypes"))
                .andExpect(status().isOk())
                .andExpect(view().name("allTypes"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowAddDealTypeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/addType"))
                .andExpect(status().isOk())
                .andExpect(view().name("addType"))
                .andExpect(model().attributeExists("dealType"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testAddType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/addType"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowEditDealTypeForm() throws Exception {
        Long id = 1L;
        DealType dealType = new DealType();
        dealType.setId(id);
        when(dealTypeRepository.findById(id)).thenReturn(Optional.of(dealType));

        mockMvc.perform(MockMvcRequestBuilders.get("/editType/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("editType"))
                .andExpect(model().attributeExists("dealType"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void testEditDealType() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/editType/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteDealType() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/deleteType/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/allTypes"));
        verify(dealTypeRepository, times(1)).deleteById(id);
    }
}




