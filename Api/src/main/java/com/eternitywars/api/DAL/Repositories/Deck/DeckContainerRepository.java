package com.eternitywars.api.DAL.Repositories.Deck;

import com.eternitywars.api.Interfaces.Deck.IDeckContainerContext;
import com.eternitywars.api.Models.Deck;
import com.eternitywars.api.Models.DeckCollection;

public class DeckContainerRepository implements IDeckContainerContext
{
    public Deck AddDeck(Deck deck)
    {
        return null;
    }

    public boolean DeleteDeck(Deck deck)
    {
        return false;
    }

    public DeckCollection GetAllDecksByUserId(int userId)
    {
        return null;
    }

    public Deck GetDeckById(int deckId)
    {
        return null;
    }
}
