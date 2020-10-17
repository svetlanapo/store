package ru.spo.store.service;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spo.store.dao.UserRepository;
import ru.spo.store.domain.Role;
import ru.spo.store.domain.User;
import ru.spo.store.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Builder
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean save(UserDto userDto) {
        if(!Objects.equals(userDto.getPassword(), userDto.getMatchingPassword())){
            throw new RuntimeException("Password is not equal");
        }
        User user = User.builder()
                .name(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if(user == null){
            throw new UsernameNotFoundException("User not found with name: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                roles);
    }
}
