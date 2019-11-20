package com.eternitywars.api.DAL.Contexts;

import com.eternitywars.api.Database.DatabaseConnection;
import com.eternitywars.api.Interfaces.IRelationshipContext;
import com.eternitywars.api.Models.Enums.FriendStatus;
import com.eternitywars.api.Models.Relationship;
import com.eternitywars.api.Models.RelationshipCollection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class RelationshipContext implements IRelationshipContext
{
    private DatabaseConnection dbc;

    public RelationshipContext(){
        dbc = new DatabaseConnection();
    }

    public RelationshipCollection GetRelationships(int userId)
    {
        RelationshipCollection rc = new RelationshipCollection();

        try (Connection conn = dbc.getDatabaseConnection())
        {
            String query = "{call GetRelationships(?)}";

            try (CallableStatement cst = conn.prepareCall(query))
            {
                cst.setInt(1, userId);
                try (ResultSet rs = cst.executeQuery())
                {
                    while (rs.next())
                    {
                        Relationship relationship = new Relationship();
                        relationship.setFriendOneId(rs.getInt("user_one_id"));
                        relationship.setFriendTwoId(rs.getInt("user_two_id"));
                        relationship.setFriendStatus(FriendStatus.valueOf(rs.getString("status")));
                        rc.getRelationships().add(relationship);
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return rc;
    }
}
