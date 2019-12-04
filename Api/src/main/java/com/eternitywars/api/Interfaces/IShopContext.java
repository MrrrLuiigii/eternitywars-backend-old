package com.eternitywars.api.Interfaces;

import com.eternitywars.api.Models.Pack;
import com.eternitywars.api.Models.User;

public interface IShopContext
{
    Pack OpenPack(User user);
    void BuyPack(User user);
}