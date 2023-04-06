package emsi.chakir_kaffou.devoir.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import emsi.chakir_kaffou.devoir.models.User;
import emsi.chakir_kaffou.devoir.repo.UserRepo;

@Service
public class UserServiceImpl<T extends User> implements UserService<T>, UserDetailsService{ 
    
    @Autowired
    private UserRepo<T> userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public T add(T t) {
        t.setPassword(passwordEncoder.encode(t.getPassword()));
        return userRepo.save(t);
    }

    @Override
    public T update(T t) {
        if (userRepo.existsById(t.getId())) {
            return userRepo.save(t);
        }
        return null;
    }

    @Override
    public T delete(T t) {
        if (userRepo.existsById(t.getId())) {
            userRepo.delete(t);
            return t;
        }
        return null;
    }

    @Override
    public T findById(int id) {
        return userRepo.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return userRepo.findAll();
    }

    @Override
    public List<T> findByRole(String role) {
        return userRepo.findByRole(role).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email).get();
        if (user == null)
            throw new UsernameNotFoundException("User with email " + email + " notfound");

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public T findByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}

