package stores.data;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
   
   User save(UserRegistrationDto registrationDto);

   User saveAdmin(AdminRegistrationDto registrationDto);

   List<User> getAll();
}