package com.project.nsbet.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.nsbet.configuration.EncoderConfiguration;
import com.project.nsbet.exception.AlreadyExistException;
import com.project.nsbet.exception.CredentialVerificationException;
import com.project.nsbet.repository.RoleRepository;
import com.project.nsbet.repository.UserRepository;
import com.project.nsbet.model.Avatar;
import com.project.nsbet.model.Bet;
import com.project.nsbet.model.Role;
import com.project.nsbet.model.User;
import com.project.nsbet.model.Wallet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class, EncoderConfiguration.class})
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @MockBean
    private AvatarService avatarService;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    void testSaveUser() throws AlreadyExistException, CredentialVerificationException, IOException {
        Avatar avatar = new Avatar();
        avatar.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar.setContentType("text/plain");
        avatar.setId("42");
        avatar.setName("Name");
        avatar.setSize(3L);

        Avatar avatar1 = new Avatar();
        avatar1.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar1.setContentType("text/plain");
        avatar1.setId("42");
        avatar1.setName("Name");
        avatar1.setSize(3L);

        Wallet wallet = new Wallet();
        wallet.setBalance(null);
        wallet.setId(123L);
        wallet.setUser(new User());

        User user = new User();
        user.setActive(true);
        user.setAvatar(avatar1);
        user.setBets(new ArrayList<>());
        user.setBirthday(LocalDate.ofEpochDay(1L));
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        user.setWallet(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setId(123L);
        wallet1.setUser(user);

        User user1 = new User();
        user1.setActive(true);
        user1.setAvatar(avatar);
        user1.setBets(new ArrayList<>());
        user1.setBirthday(LocalDate.ofEpochDay(1L));
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");
        user1.setWallet(wallet1);
        assertThrows(CredentialVerificationException.class, () -> this.userService.saveUser(user1, "Password Verification",
                new MockMultipartFile("Name", "AAAAAAAA".getBytes("UTF-8"))));
    }

    @Test
    void testSaveUser2() throws AlreadyExistException, CredentialVerificationException, IOException {
        Avatar avatar = new Avatar();
        avatar.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar.setContentType("text/plain");
        avatar.setId("42");
        avatar.setName("Name");
        avatar.setSize(3L);

        Avatar avatar1 = new Avatar();
        avatar1.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar1.setContentType("text/plain");
        avatar1.setId("42");
        avatar1.setName("Name");
        avatar1.setSize(3L);

        Wallet wallet = new Wallet();
        wallet.setBalance(null);
        wallet.setId(123L);
        wallet.setUser(new User());

        User user = new User();
        user.setActive(true);
        user.setAvatar(avatar1);
        user.setBets(new ArrayList<>());
        user.setBirthday(LocalDate.ofEpochDay(1L));
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        user.setWallet(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setId(123L);
        wallet1.setUser(user);
        User user1 = mock(User.class);
        when(user1.getPassword()).thenReturn("iloveyou");
        doNothing().when(user1).setActive(anyBoolean());
        doNothing().when(user1).setAvatar((Avatar) any());
        doNothing().when(user1).setBets((java.util.List<Bet>) any());
        doNothing().when(user1).setBirthday((LocalDate) any());
        doNothing().when(user1).setFirstName((String) any());
        doNothing().when(user1).setId((Long) any());
        doNothing().when(user1).setLastName((String) any());
        doNothing().when(user1).setPassword((String) any());
        doNothing().when(user1).setRoles((java.util.Set<Role>) any());
        doNothing().when(user1).setUsername((String) any());
        doNothing().when(user1).setWallet((Wallet) any());
        user1.setActive(true);
        user1.setAvatar(avatar);
        user1.setBets(new ArrayList<>());
        user1.setBirthday(LocalDate.ofEpochDay(1L));
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");
        user1.setWallet(wallet1);
        assertThrows(CredentialVerificationException.class, () -> this.userService.saveUser(user1, "Password Verification",
                new MockMultipartFile("Name", "AAAAAAAA".getBytes("UTF-8"))));
        verify(user1).getPassword();
        verify(user1).setActive(anyBoolean());
        verify(user1).setAvatar((Avatar) any());
        verify(user1).setBets((java.util.List<Bet>) any());
        verify(user1).setBirthday((LocalDate) any());
        verify(user1).setFirstName((String) any());
        verify(user1).setId((Long) any());
        verify(user1).setLastName((String) any());
        verify(user1).setPassword((String) any());
        verify(user1).setRoles((java.util.Set<Role>) any());
        verify(user1).setUsername((String) any());
        verify(user1).setWallet((Wallet) any());
    }

    @Test
    void testSaveUser3() throws AlreadyExistException, CredentialVerificationException, IOException {
        Avatar avatar = new Avatar();
        avatar.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar.setContentType("text/plain");
        avatar.setId("42");
        avatar.setName("Name");
        avatar.setSize(3L);

        Avatar avatar1 = new Avatar();
        avatar1.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar1.setContentType("text/plain");
        avatar1.setId("42");
        avatar1.setName("Name");
        avatar1.setSize(3L);

        User user = new User();
        user.setActive(true);
        user.setAvatar(new Avatar());
        user.setBets(new ArrayList<>());
        user.setBirthday(null);
        user.setFirstName("Jane");
        user.setId(123L);
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setRoles(new HashSet<>());
        user.setUsername("janedoe");
        user.setWallet(new Wallet());

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.valueOf(42L));
        wallet.setId(123L);
        wallet.setUser(user);

        User user1 = new User();
        user1.setActive(true);
        user1.setAvatar(avatar1);
        user1.setBets(new ArrayList<>());
        user1.setBirthday(LocalDate.ofEpochDay(1L));
        user1.setFirstName("Jane");
        user1.setId(123L);
        user1.setLastName("Doe");
        user1.setPassword("iloveyou");
        user1.setRoles(new HashSet<>());
        user1.setUsername("janedoe");
        user1.setWallet(wallet);

        Wallet wallet1 = new Wallet();
        wallet1.setBalance(BigDecimal.valueOf(42L));
        wallet1.setId(123L);
        wallet1.setUser(user1);

        User user2 = new User();
        user2.setActive(true);
        user2.setAvatar(avatar);
        user2.setBets(new ArrayList<>());
        user2.setBirthday(LocalDate.ofEpochDay(1L));
        user2.setFirstName("Jane");
        user2.setId(123L);
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setRoles(new HashSet<>());
        user2.setUsername("janedoe");
        user2.setWallet(wallet1);

        Avatar avatar2 = new Avatar();
        avatar2.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar2.setContentType("text/plain");
        avatar2.setId("42");
        avatar2.setName("Name");
        avatar2.setSize(3L);

        Avatar avatar3 = new Avatar();
        avatar3.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar3.setContentType("text/plain");
        avatar3.setId("42");
        avatar3.setName("Name");
        avatar3.setSize(3L);

        Wallet wallet2 = new Wallet();
        wallet2.setBalance(null);
        wallet2.setId(123L);
        wallet2.setUser(new User());

        User user3 = new User();
        user3.setActive(true);
        user3.setAvatar(avatar3);
        user3.setBets(new ArrayList<>());
        user3.setBirthday(LocalDate.ofEpochDay(1L));
        user3.setFirstName("Jane");
        user3.setId(123L);
        user3.setLastName("Doe");
        user3.setPassword("iloveyou");
        user3.setRoles(new HashSet<>());
        user3.setUsername("janedoe");
        user3.setWallet(wallet2);

        Wallet wallet3 = new Wallet();
        wallet3.setBalance(BigDecimal.valueOf(42L));
        wallet3.setId(123L);
        wallet3.setUser(user3);

        User user4 = new User();
        user4.setActive(true);
        user4.setAvatar(avatar2);
        user4.setBets(new ArrayList<>());
        user4.setBirthday(LocalDate.ofEpochDay(1L));
        user4.setFirstName("Jane");
        user4.setId(123L);
        user4.setLastName("Doe");
        user4.setPassword("iloveyou");
        user4.setRoles(new HashSet<>());
        user4.setUsername("janedoe");
        user4.setWallet(wallet3);
        Optional<User> ofResult = Optional.of(user4);
        when(this.userRepository.save((User) any())).thenReturn(user2);
        when(this.userRepository.findByUsername((String) any())).thenReturn(ofResult);

        Avatar avatar4 = new Avatar();
        avatar4.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar4.setContentType("text/plain");
        avatar4.setId("42");
        avatar4.setName("Name");
        avatar4.setSize(3L);

        Avatar avatar5 = new Avatar();
        avatar5.setBytes("AAAAAAAA".getBytes("UTF-8"));
        avatar5.setContentType("text/plain");
        avatar5.setId("42");
        avatar5.setName("Name");
        avatar5.setSize(3L);

        Wallet wallet4 = new Wallet();
        wallet4.setBalance(null);
        wallet4.setId(123L);
        wallet4.setUser(new User());

        User user5 = new User();
        user5.setActive(true);
        user5.setAvatar(avatar5);
        user5.setBets(new ArrayList<>());
        user5.setBirthday(LocalDate.ofEpochDay(1L));
        user5.setFirstName("Jane");
        user5.setId(123L);
        user5.setLastName("Doe");
        user5.setPassword("iloveyou");
        user5.setRoles(new HashSet<>());
        user5.setUsername("janedoe");
        user5.setWallet(wallet4);

        Wallet wallet5 = new Wallet();
        wallet5.setBalance(BigDecimal.valueOf(42L));
        wallet5.setId(123L);
        wallet5.setUser(user5);
        User user6 = mock(User.class);
        when(user6.getUsername()).thenReturn("janedoe");
        when(user6.getPassword()).thenReturn("Password Verification");
        doNothing().when(user6).setActive(anyBoolean());
        doNothing().when(user6).setAvatar((Avatar) any());
        doNothing().when(user6).setBets((java.util.List<Bet>) any());
        doNothing().when(user6).setBirthday((LocalDate) any());
        doNothing().when(user6).setFirstName((String) any());
        doNothing().when(user6).setId((Long) any());
        doNothing().when(user6).setLastName((String) any());
        doNothing().when(user6).setPassword((String) any());
        doNothing().when(user6).setRoles((java.util.Set<Role>) any());
        doNothing().when(user6).setUsername((String) any());
        doNothing().when(user6).setWallet((Wallet) any());
        user6.setActive(true);
        user6.setAvatar(avatar4);
        user6.setBets(new ArrayList<>());
        user6.setBirthday(LocalDate.ofEpochDay(1L));
        user6.setFirstName("Jane");
        user6.setId(123L);
        user6.setLastName("Doe");
        user6.setPassword("iloveyou");
        user6.setRoles(new HashSet<>());
        user6.setUsername("janedoe");
        user6.setWallet(wallet5);
        assertThrows(AlreadyExistException.class, () -> this.userService.saveUser(user6, "Password Verification",
                new MockMultipartFile("Name", "AAAAAAAA".getBytes("UTF-8"))));
        verify(this.userRepository).findByUsername((String) any());
        verify(user6).getPassword();
        verify(user6, atLeast(1)).getUsername();
        verify(user6).setActive(anyBoolean());
        verify(user6).setAvatar((Avatar) any());
        verify(user6).setBets((java.util.List<Bet>) any());
        verify(user6).setBirthday((LocalDate) any());
        verify(user6).setFirstName((String) any());
        verify(user6).setId((Long) any());
        verify(user6).setLastName((String) any());
        verify(user6).setPassword((String) any());
        verify(user6).setRoles((java.util.Set<Role>) any());
        verify(user6).setUsername((String) any());
        verify(user6).setWallet((Wallet) any());
    }
}

