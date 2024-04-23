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
import stores.entity.DealType;
import stores.service.DealTypeService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DealTypeController.class)
public class DealTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DealTypeService dealTypeService;

    @Test
    @WithMockUser
    public void testShowAllDealTypes() throws Exception {
        List<DealType> dealTypes = Arrays.asList(new DealType(), new DealType());
        when(dealTypeService.getAllDealTypes()).thenReturn(dealTypes);

        mockMvc.perform(MockMvcRequestBuilders.get("/type/all"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("dealTypes", dealTypes))
                .andExpect(view().name("allTypes"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testShowAddDealTypeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/type/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("addType"))
                .andExpect(model().attributeExists("dealType"));
    }


    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void testDeleteDealType() throws Exception {
        Long typeId = 1L;
        Model model = mock(Model.class);
        when(dealTypeService.deleteDealType(typeId, model)).thenReturn("allTypes");
        DealTypeController controller = new DealTypeController(dealTypeService);

        String viewName = controller.deleteDealType(typeId, model);

        assertEquals("allTypes", viewName);
        verify(dealTypeService, times(1)).deleteDealType(typeId, model);
    }
}

