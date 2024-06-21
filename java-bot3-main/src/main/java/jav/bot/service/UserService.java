package jav.bot.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jav.bot.entity.Role;
import jav.bot.entity.User;
import jav.bot.entity.UserRole;
import jav.bot.repository.UserRepository;
import jav.bot.repository.UserRoleRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> {
            throw new UsernameNotFoundException(username);
        });
    }

    @Transactional
    public User registerNewUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Transactional
    public void assignRoleToUser(Long userId, String roleName) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(Role.valueOf(roleName));
        this.userRoleRepository.save(userRole);
    }

    @Transactional
    public void removeRoleFromUser(Long userId, String roleName) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new IllegalArgumentException("User not found");
        });
        Optional<UserRole> userRole = user.getUserRoles().stream().filter(role -> role.getRole().name().equals(roleName)).findFirst();
        userRole.ifPresent(this.userRoleRepository::delete);
    }
}
