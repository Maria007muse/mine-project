package stores.security;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigurationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void accessAdminPageAsAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/type/add"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void accessAdminPageAsUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/type/add"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    public void loginRedirectsToLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    public void accessHomePageAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")
                        .with(request -> {
                            request.setRemoteUser("username");
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Добро пожаловать")));
    }
}
