package com.eternitywars.Logic.WebsocketServer.Executors;

import com.eternitywars.Logic.DeckBuilder.DeckBuilderContainerLogic;
import com.eternitywars.Logic.DeckBuilder.DeckBuilderLogic;
import com.eternitywars.Models.Account;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

public class DeckBuilderExecutor implements IExecutor  {

    private DeckBuilderLogic deckBuilderLogic = new DeckBuilderLogic();
    private DeckBuilderContainerLogic deckBuilderContainerLogic = new DeckBuilderContainerLogic();

    private JSONObject message;
    private Session session;

    @Override
    public void Execute(JSONObject message, Session session) {
        switch (message.getString("Action")) {
            case "ADDDECK":
                Gson gson = new Gson();
                String json = message.getJSONObject("Content").toString();
                Account account = gson.fromJson(json, Account.class);
                System.out.println(account);
                break;
            case "GETALLDECKNAMES":
                break;
            case "GETDECKBYID":
                break;
            case "DELETEDECK":
                break;
            case "SAVEDECK":
                break;
            case "ADDCARD":
                break;
            case "REMOVECARD":
                break;
        }
    }

    public DeckBuilderExecutor(JSONObject message, Session session) {
        this.message = message;
        this.session = session;
    }

    @Override
    public void run() {
        Execute(message, session);
    }
}