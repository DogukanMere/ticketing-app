package com.ticketing.service.impl;

import com.ticketing.entity.User;
import com.ticketing.entity.common.UserPrincipal;
import com.ticketing.repository.UserRepository;
import com.ticketing.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);

        if(user == null){
            throw new UsernameNotFoundException("This user does not exist");
        }
        return new UserPrincipal(user);
    }
}
