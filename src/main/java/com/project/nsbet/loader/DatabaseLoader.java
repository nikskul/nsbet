package com.project.nsbet.loader;

import com.project.nsbet.configuration.EncoderConfiguration;
import com.project.nsbet.model.*;
import com.project.nsbet.repository.*;
import com.project.nsbet.service.AvatarService;
import com.project.nsbet.service.MatchService;
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
    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    private final MatchService matchService;

    private final EncoderConfiguration passwordEncoder;

    public DatabaseLoader(RoleRepository roleRepository,
                          ResultRepository resultRepository,
                          UserRepository userRepository,
                          AvatarRepository avatarRepository,
                          AvatarService avatarService,
                          TeamRepository teamRepository,
                          MatchRepository matchRepository,
                          MatchService matchService,
                          EncoderConfiguration passwordEncoder) {
        this.roleRepository = roleRepository;
        this.resultRepository = resultRepository;
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
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

        if (teamRepository.count() == 0) {
            teamRepository.save(new Team("Virtus.pro"));
            teamRepository.save(new Team("Navi"));
            teamRepository.save(new Team("BlackNinjas"));
            teamRepository.save(new Team("TeamSpirit"));
            teamRepository.save(new Team("Спартак"));
            teamRepository.save(new Team("Зенит"));
            teamRepository.save(new Team("Факел"));
            teamRepository.save(new Team("Барселона"));
        }

        if (matchRepository.count() == 0) {
            var teams = teamRepository.findAll();
            for (int i = 0; i < teams.size() - 1; i += 2) {
                matchService.registerMatch(
                        LocalDateTime.now().plusMinutes(20L + (i * 5L)),
                        teams.get(i),
                        teams.get(i + 1)
                );
            }
        }
    }
}
