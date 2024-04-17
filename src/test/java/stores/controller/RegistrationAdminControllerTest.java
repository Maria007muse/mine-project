package stores.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import stores.controller.RegistrationAdminController;
import stores.dto.AdminRegistrationDto;
import stores.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationAdminController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegistrationAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void showAdminRegistrationForm_ShouldReturnAdminRegistrationPage() throws Exception {
        mockMvc.perform(get("/registration/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration_admin"))
                .andExpect(model().attributeExists("admin"));
    }

    @Test
    void saveAdmin_ValidInput_RedirectsToLoginPage() throws Exception {
        AdminRegistrationDto adminDto = new AdminRegistrationDto();
        adminDto.setUserName("admin");
        adminDto.setFullName("Admin Name");
        adminDto.setStreet("Admin Street");
        adminDto.setCity("Admin City");
        adminDto.setZip("12345");
        adminDto.setPhoneNumber("1234567890");
        adminDto.setPassword("adminpassword");

        mockMvc.perform(post("/registration/admin")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .flashAttr("admin", adminDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService, times(1)).saveAdmin(any(AdminRegistrationDto.class));
    }
}
