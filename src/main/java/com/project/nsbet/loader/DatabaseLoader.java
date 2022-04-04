package com.project.nsbet.loader;

import com.project.nsbet.model.Role;
import com.project.nsbet.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DatabaseLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().size() == 0) {
            roleRepository.save(new Role(1L, "ROLE_USER"));
            roleRepository.save(new Role(2L, "ROLE_ADMIN"));
        }
    }
}
