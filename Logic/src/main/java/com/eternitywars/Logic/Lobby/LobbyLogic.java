package com.eternitywars.Logic.Lobby;

import com.eternitywars.Logic.Game.GameLogic;
import com.eternitywars.Models.Enums.LobbyPlayerStatus;
import com.eternitywars.Models.Enums.LobbyStatus;
import com.eternitywars.Models.Lobby;
import com.eternitywars.Models.Player;
import com.eternitywars.Models.RelationshipCollection;
import com.eternitywars.Models.User;
import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class LobbyLogic
{
    private RestTemplate restTemplate = new RestTemplate();
    LobbyContainerLogic lobbyContainerLogic = new LobbyContainerLogic();
    GameLogic gameLogic = new GameLogic();

    public Lobby JoinLobby(Lobby lobby, Player player, String token)
    {
        Lobby lobby1 = lobbyContainerLogic.GetLobbyById(lobby, token);

       if(lobby1.getPlayerOne() != null)
       {
           lobby1.setStatus(LobbyStatus.Full);
           HttpHeaders headers = new HttpHeaders();
           headers.setBearerAuth(token);
           headers.setContentType(MediaType.APPLICATION_JSON);
           Lobby sendlobby = new Lobby();
           sendlobby.setLobbyId(lobby.getLobbyId());
           sendlobby.setPlayerOne(player);

           JSONObject json = new JSONObject(lobby);

           HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
           //send lobby object with the user that wants to join
           if(restTemplate.postForObject("http://localhost:8083/api/private/lobby/join", request , boolean.class))
           {
               lobby.setPLayerTwo(player);
               lobby.setStatus(LobbyStatus.Full);
           }
       }
        return lobby;
    }

    public Lobby LeaveLobby(Lobby lobby, Player player, String token)
    {
        if(lobby.getPLayerTwo() != null)
        {
            if(lobby.getPLayerTwo().getUserId() != 0)
            {
                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                headers.setContentType(MediaType.APPLICATION_JSON);
                Lobby sendlobby = new Lobby();
                sendlobby.setLobbyId(lobby.getLobbyId());
                lobby.setPlayerOne(player);

                JSONObject json = new JSONObject(lobby);

                HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
                if(restTemplate.postForObject("http://localhost:8083/api/private/lobby/leave", request , boolean.class))
                {
                    lobby.setPLayerTwo(null);
                    return lobby;
                }
            }
        }
        lobbyContainerLogic.DeleteLobby(lobby, token);
        return null;
    }

    public Lobby PlayerReady(Lobby lobby, Player player)
    {
       if(lobby.getPlayerOne() == player)
       {
           lobby.getPlayerOne().setLobbyPlayerStatus(LobbyPlayerStatus.Ready);
       }
       else
       {
           lobby.getPLayerTwo().setLobbyPlayerStatus(LobbyPlayerStatus.Ready);
       }
       if(lobby.getPlayerOne().getLobbyPlayerStatus() == LobbyPlayerStatus.Ready && lobby.getPlayerOne().getLobbyPlayerStatus() == LobbyPlayerStatus.Ready)
       {
           gameLogic.LaunchGame(lobby);
       }
        return lobby;
    }

    public Lobby PlayerNotReady(Lobby lobby, Player player)
    {
        if(lobby.getPlayerOne() == player)
        {
            lobby.getPlayerOne().setLobbyPlayerStatus(LobbyPlayerStatus.NotReady);
        }
        else
        {
            lobby.getPLayerTwo().setLobbyPlayerStatus(LobbyPlayerStatus.NotReady);
        }
        return lobby;
    }



    public Lobby SetDeck(Lobby lobby, Player player, String token)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Lobby sendlobby = new Lobby();
        sendlobby.setLobbyId(lobby.getLobbyId());
        sendlobby.setPlayerOne(player);

        JSONObject json = new JSONObject(lobby);

        HttpEntity<String> request = new HttpEntity<>(json.toString(), headers);
        //send lobby object with the user that wants to leave
        restTemplate.postForObject("http://localhost:8083/api/private/lobby/setDeck", request , Lobby.class);
        return lobby;
    }
}
