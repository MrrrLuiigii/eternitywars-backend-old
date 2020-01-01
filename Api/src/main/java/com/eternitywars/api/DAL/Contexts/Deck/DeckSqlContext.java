package com.eternitywars.api.DAL.Contexts.Deck;

import com.eternitywars.api.Database.DatabaseConnection;
import com.eternitywars.api.Interfaces.Deck.IDeckContext;
import com.eternitywars.api.Models.Card;
import com.eternitywars.api.Models.Deck;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeckSqlContext implements IDeckContext
{
    private DatabaseConnection dbc = new DatabaseConnection();



    public boolean AddCard(Deck deck, Card card)
    {
        try (Connection conn = dbc.getDatabaseConnection())
        {
            String query = "insert into `deck_card`(`deck_id`, `card_id`) values(?, ?);";

            try (PreparedStatement pst = conn.prepareStatement(query))
            {
                pst.setInt(1, deck.getDeckId());
                pst.setInt(2, card.getCardId());
                pst.executeUpdate();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public boolean DeleteCard(Deck deck, Card card)
    {
        try (Connection conn = dbc.getDatabaseConnection())
        {
            String query = "delete from `deck_card` where `card_id` = ?;";

            try (PreparedStatement pst = conn.prepareStatement(query))
            {
                pst.setInt(1, card.getCardId());
                pst.executeUpdate();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }

        return true;
    }

    public boolean UpdateDeckName(Deck deck)
    {
        try (Connection conn = dbc.getDatabaseConnection())
        {
            String query = "update `deck` set `name` = ? where `id` = ?;";

            try (PreparedStatement pst = conn.prepareStatement(query))
            {
                pst.setString(1, deck.getName());
                pst.setInt(2, deck.getDeckId());
                pst.executeUpdate();
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }

        return true;
    }
}
