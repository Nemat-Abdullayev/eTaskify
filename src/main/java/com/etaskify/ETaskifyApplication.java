package com.etaskify;

import com.etaskify.enums.RoleName;
import com.etaskify.model.Role;
import com.etaskify.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class ETaskifyApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(ETaskifyApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = Role.builder().name(RoleName.ADMIN).active(true).build();

        Role roleManager = Role.builder().name(RoleName.MANAGER).active(true).build();

        Role roleUser = Role.builder().name(RoleName.USER).active(true).build();

        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) roleRepository.saveAll(List.of(roleAdmin, roleManager, roleUser));
    }
}
