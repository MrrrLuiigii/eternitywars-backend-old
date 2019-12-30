package com.eternitywars.Logic.User;

import com.eternitywars.Models.Enums.AccountStatus;
import com.eternitywars.Models.Relationship;
import com.eternitywars.Models.User;
import com.eternitywars.Models.UserCollection;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class UserContainerLogic
{
    private RestTemplate restTemplate = new RestTemplate();

    public UserCollection GetUsers(JSONObject jsonObject)
    {
        String token = jsonObject.getString("Token");
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange("localhost:8083/api/private/user/get", HttpMethod.GET, request , String.class);
        return restTemplate.getForObject("localhost:8083/api/private/user/get" , UserCollection.class);
    }


    public User AddUserByUsernameAndEmail(User user)
    {
        //todo add token in headers
        String token = "";

        UserCollection userCollection = GetUserCollectionFromAPI(token);

        if (CheckUserTaken(userCollection, user))
        {
            return null;
        }

        return AddUserByUsernameAndEmailAPI(user, token);
    }

    private User AddUserByUsernameAndEmailAPI(User user, String token)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userJson = new JSONObject(user);
        HttpEntity<String> request = new HttpEntity<>(userJson.toString(), headers);

        return restTemplate.postForObject("http://eternity-wars-api/api/private/user/add", request, User.class);
    }

    private UserCollection GetUserCollectionFromAPI(String token)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.postForObject("http://eternity-wars-api/api/private/user/get", request, UserCollection.class);
    }

    public User GetUserByEmail(JSONObject json)
    {
        User user = new User();
        String email = json.getString("Content");
        String token = json.getString("Token");
        System.out.println(token);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<User> response = restTemplate.exchange("http://localhost:8083/api/private/user/getByEmail/{email}", HttpMethod.GET, request , User.class, email);

        return response.getBody();

    }

    public User GetUserById(User user)
    {

        //String token = json.getString("Token");
        //System.out.println(token);
        JSONObject output = new JSONObject(user);

        HttpHeaders headers = new HttpHeaders();
        //headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(output.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange("localhost:8083/api/public/user/getByEmail/", HttpMethod.GET, request , String.class);
        //todo how does response work
        JSONObject api_output = new JSONObject(response.getBody());

        user.setId(api_output.getInt("userId"));
        user.setEmail(api_output.getString("email"));
        user.setGold(api_output.getInt("gold"));
        user.setPackAmount(api_output.getInt("packAmount"));
        user.setUsername(api_output.getString("username"));
        if(api_output.getString("accountStatus") == "online")
        {
            user.setAccountStatus(AccountStatus.Online);
        }
        else if(api_output.getString("accountStatus") == "offline")
        {
            user.setAccountStatus(AccountStatus.Offline);
        }
        else if(api_output.getString("accountStatus") == "inGame")
        {
            user.setAccountStatus(AccountStatus.InGame);
        }
        else if(api_output.getString("accountStatus") == "inLobby")
        {
            user.setAccountStatus(AccountStatus.InLobby);
        }
        return user;
    }



    private boolean CheckUserTaken(UserCollection userCollection, User user)
    {
        for (User u : userCollection.getUsers())
        {
            if (u.getUsername() == user.getUsername() || u.getEmail() == user.getEmail())
            {
                return true;
            }
        }

        return false;
    }

}
