package fre.shown.security.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Radon Freedom
 * created at 2019.04.01 上午11:36
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new User(username, "radon", AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
