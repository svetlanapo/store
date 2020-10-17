package ru.spo.store.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.spo.store.dto.UserDto;

public interface UserService extends UserDetailsService {
    boolean save(UserDto userDto);
}
