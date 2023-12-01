package Controller;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accService;
    MessageService msgService;
    
    public SocialMediaController() {
        accService = new AccountService();
        msgService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        // app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message.id}", this::getMessageHandler);
        app.delete("/messages/{message.id}", this::deleteMessageHandler);
        app.patch("/messages/{message.id}", this::updateMessageHandler);
        app.get("/accounts/{account.id}/messages", this::getAllUserMessagesHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    // private void exampleHandler(Context context) {
    //     context.json("sample text");
    // }

    private void registerHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account addedAcc = accService.registerAcc(acc);
        if (addedAcc == null) {
            ctx.status(400);
        } else {
            ctx.json(addedAcc).status(200);
        }
    }

    private void loginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account acc = mapper.readValue(ctx.body(), Account.class);
        Account loginAcc = accService.loginAcc(acc);
        if (loginAcc == null) {
            ctx.status(401);
        } else {
            ctx.json(loginAcc).status(200);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message msg = mapper.readValue(ctx.body(), Message.class);
        Message postedMessage = msgService.addMessage(msg);
        if (postedMessage == null) {
            ctx.status(400);
        } else {
            ctx.json(postedMessage).status(200);
        }
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = msgService.getMessages();
        ctx.json(messages).status(200);        
    }

    private void getMessageHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message.id"));
        Message msg = msgService.getMessage(id);
        if (msg == null) {
            ctx.status(200);
        } else {
            ctx.json(msg).status(200);
        }
    }

    private void deleteMessageHandler(Context ctx) {
        int id = Integer.parseInt(ctx.pathParam("message.id"));
        Message msg = msgService.getMessage(id);
        if (msg == null) {
            ctx.status(200);
        } else {
            msgService.deleteMessage(id);
            ctx.json(msg).status(200);
        }
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException {
        int id = Integer.parseInt(ctx.pathParam("message.id"));
        Message msg = msgService.getMessage(id);
        ObjectMapper mapper = new ObjectMapper();
        Message newMsg = mapper.readValue(ctx.body(), Message.class);
        newMsg = msgService.updateMessage(newMsg, id);
        if (msg == null || newMsg == null) {
            ctx.status(400);
        } else {
            msg = msgService.getMessage(id);
            ctx.json(msg).status(200);
        }

    }

    private void getAllUserMessagesHandler(Context ctx) {
        int accId = Integer.parseInt(ctx.pathParam("account.id"));
        List<Message> userMsgs = msgService.getAllUserMessages(accId);
        ctx.json(userMsgs).status(200); 
    }
}