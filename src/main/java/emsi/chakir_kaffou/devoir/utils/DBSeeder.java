package emsi.chakir_kaffou.devoir.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import emsi.chakir_kaffou.devoir.models.Admin;
import emsi.chakir_kaffou.devoir.models.Client;
import emsi.chakir_kaffou.devoir.models.Dev;
import emsi.chakir_kaffou.devoir.repo.UserRepo;

@Component
public class DBSeeder implements CommandLineRunner {

    @Autowired
    private UserRepo<Admin> aRepo;
    @Autowired
    private UserRepo<Dev> dRepo;
    @Autowired
    private UserRepo<Client> cRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (aRepo.findAll().isEmpty()) {
            Admin admin = new Admin();
            admin.setDisplayName("Kaffou");
            admin.setEmail("kaffou@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(Constants.getRole("admin"));
            aRepo.save(admin);

            Client client = new Client();
            client.setDisplayName("Chakir");
            client.setEmail("chakir@gmail.com");
            client.setPassword(passwordEncoder.encode("123456"));
            client.setRole(Constants.getRole("client"));
            cRepo.save(client);
        
            Dev dev = new Dev();
            dev.setDisplayName("Dev");
            dev.setEmail("dev@gmail.com");
            dev.setPassword(passwordEncoder.encode("123456"));
            dev.setRole(Constants.getRole("dev"));
            dRepo.save(dev);
        }
    }

}

