package stores.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import stores.dto.AdminRegistrationDto;
import stores.entity.User;
import stores.dto.UserRegistrationDto;

import java.util.List;

public interface UserService extends UserDetailsService {
   
   User save(UserRegistrationDto registrationDto);

    User saveAdmin(AdminRegistrationDto registrationDto);

   List<User> getAll();
}