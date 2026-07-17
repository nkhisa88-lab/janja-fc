package com.team.janja_fc.security.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.team.janja_fc.user.User;
import com.team.janja_fc.user.UserRepository;

@Service
public class JanjaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JanjaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByPhoneNumber(username);

        if (user == null) {
            throw new UsernameNotFoundException(
                    "User not found: " + username);
        }

        return new JanjaUserDetails(user);

    }

}