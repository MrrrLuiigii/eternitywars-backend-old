package com.eternitywars.api.Interfaces.User;

import com.eternitywars.api.Models.Enums.AccountStatus;
import com.eternitywars.api.Models.User;

public interface IUserContext
{
    void ChangeUserName( int userId, String username);
    void ChangeUserStatus(User user);
    int GetPackAmount(int userId);
    void UpdatePackAmount(User user);
}