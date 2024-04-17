package stores.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import stores.dto.*;
import stores.entity.Role;
import stores.entity.User;
import stores.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

   private UserRepository userRepository;
   private BCryptPasswordEncoder passwordEncoder;

   public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
      super();
      this.userRepository = userRepository;
      this.passwordEncoder = passwordEncoder;
   }

   @Override
   public User save(UserRegistrationDto registrationDto) {
      var user = new User(
              registrationDto.getUserName(),
                 registrationDto.getFullName(),
                  registrationDto.getStreet(),
                   registrationDto.getCity(),
                   registrationDto.getZip(),
                   registrationDto.getPhoneNumber(),
                   passwordEncoder.encode(registrationDto
                          .getPassword()),
                   Arrays.asList(new Role("ROLE_USER")));

      return userRepository.save(user);
   }

    @Override
    public User saveAdmin(AdminRegistrationDto adminDto) {
        User admin = new User(
                adminDto.getUserName(),
                adminDto.getFullName(),
                adminDto.getStreet(),
                adminDto.getCity(),
                adminDto.getZip(),
                adminDto.getPhoneNumber(),
                passwordEncoder.encode(adminDto.getPassword()),
                Arrays.asList(new Role("ROLE_ADMIN")));
        return userRepository.save(admin);
    }


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        var user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException
                    ("Invalid username or password.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority>
          mapRolesToAuthorities(Collection<Role> roles) {
      return roles.stream()
            .map(role -> new SimpleGrantedAuthority
                  (role.getName()))
            .collect(Collectors.toList());
   }
   @Override
   public List<User> getAll() {
      return userRepository.findAll();
   }
}
