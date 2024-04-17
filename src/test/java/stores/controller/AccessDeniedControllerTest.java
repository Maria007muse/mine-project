package stores.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AccessDeniedController.class)
class AccessDeniedControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void accessDenied_ShouldReturnAccessDeniedPage() throws Exception {
        mockMvc.perform(get("/accessDenied"))
                .andExpect(status().isOk())
                .andExpect(view().name("accessDenied"));
    }
}


