package com.project.nsbet.loader;

import com.project.nsbet.configuration.EncoderConfiguration;
import com.project.nsbet.model.*;
import com.project.nsbet.repository.*;
import com.project.nsbet.service.MatchService;
import com.project.nsbet.service.TeamService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Set;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TeamService teamService;
    private final MatchService matchService;

    private final EncoderConfiguration passwordEncoder;

    public DatabaseLoader(RoleRepository roleRepository,
                          UserRepository userRepository,
                          TeamService teamService,
                          MatchService matchService,
                          EncoderConfiguration passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.teamService = teamService;
        this.matchService = matchService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (roleRepository.findAll().size() == 0) {
            roleRepository.save(new Role(1L, "ROLE_USER"));
            roleRepository.save(new Role(2L, "ROLE_ADMIN"));
        }

        if (userRepository.findAll().size() == 0) {
            User admin = new User(
                    "Никита",
                    "Скулыбердин",
                    "nikita",
                    passwordEncoder.getPasswordEncoder().encode("nikita"),
                    LocalDate.of(2001, Month.FEBRUARY, 6)
            );
            var roles = Set.of(
                    roleRepository.findByName("ROLE_ADMIN"),
                    roleRepository.findByName("ROLE_USER")
            );
            admin.setRoles(roles);
            admin.setActive(true);
            Wallet wallet = new Wallet();
            wallet.setBalance(BigDecimal.valueOf(1_000_000));
            admin.setWallet(wallet);

            userRepository.save(admin);
        }

        if (teamService.findAll().size() == 0) {
            teamService.registerTeam("Virtus.pro");
            teamService.registerTeam("Navi");
            teamService.registerTeam("BlackNinjas");
            teamService.registerTeam("TeamSpirit");
            teamService.registerTeam("Спартак");
            teamService.registerTeam("Зенит");
            teamService.registerTeam("Факел");
            teamService.registerTeam("Барселона");
        }

        if (matchService.findAll().size() == 0) {
            var teams = teamService.findAll();
            for (int i = 0; i < teams.size() - 1; i += 2) {
                matchService.registerMatch(
                        LocalDateTime.now().plusMinutes(20L + (i * 5L)),
                        teams.get(i).getName(),
                        teams.get(i + 1).getName()
                );
            }
        }
    }
}
