package com.eternitywars.api.DAL.Repositories.User;

import com.eternitywars.api.Factories.User.UserContainerFactory;
import com.eternitywars.api.Factories.User.UserFactory;
import com.eternitywars.api.Models.Enums.AccountStatus;
import com.eternitywars.api.Models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository = new UserRepository(new UserFactory());
    private UserContainerRepository userContainerRepository = new UserContainerRepository(new UserContainerFactory());

    private User SetupExpectedUser()
    {
        User expectedUser = new User();
        expectedUser.setUserId(3);
        expectedUser.setEmail("getUser@byEmail.nl");
        expectedUser.setUsername("expectedUser");
        expectedUser.setAccountStatus(AccountStatus.Online);
        expectedUser.setGold(250);
        expectedUser.setPackAmount(3);
        return expectedUser;
    }

    @Test
    void updateUsername() {
        User user = SetupExpectedUser();
        user.setUsername("Test2");
        userRepository.UpdateUsername(user);

        User updatedUser = userContainerRepository.GetUserById(user.getUserId());
        assertEquals(user.getUsername(), updatedUser.getUsername());
    }

    @Test
    void updateAccountStatus() {
        User user = SetupExpectedUser();
        user.setAccountStatus(AccountStatus.Offline);
        userRepository.UpdateAccountStatus(user);

        User updatedUser = userContainerRepository.GetUserById(user.getUserId());
        assertEquals(user.getAccountStatus(), updatedUser.getAccountStatus());
    }

    @Test
    void updatePackAmount() {
        User user = SetupExpectedUser();
        user.setPackAmount(100);
        userRepository.UpdatePackAmount(user);

        User updatedUser = userContainerRepository.GetUserById(user.getUserId());
        assertEquals(user.getPackAmount(), updatedUser.getPackAmount());
    }

    @Test
    void updateGold()
    {
        User user = SetupExpectedUser();
        user.setGold(100);
        userRepository.UpdateGold(user);

        User updatedUser = userContainerRepository.GetUserById(user.getUserId());
        assertEquals(user.getGold(), updatedUser.getGold());
    }
}