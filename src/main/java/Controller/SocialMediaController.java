package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController() {
        messageService = new MessageService();
        accountService = new AccountService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::createAccountHandler);
        app.post("/login", this::loginAccountHandler);
        app.post("/messages", this::newMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/message_id", this::getMessageByMessageIdHandler);
        app.delete("/messages/message_id", this::deleteByMessageIdHandler);
        app.patch("/messages/message_id", this::updateMessageHandler);
        app.get("/accounts/account_id/messages", this::getMessagesByUserIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void createAccountHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account createdAccount = accountService.addAccount(account);
        if (createdAccount == null) {
            context.status(400);
        }

        else {
            context.status(200);
            context.json(createdAccount);
            
        }

    }

    private void loginAccountHandler(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account accountLogin = accountService.LoginToAccount(account.getUsername(), account.getPassword());
        if (accountLogin == null) {
            context.status(401);
        }

        else {
            context.status(200);
            context.json(accountLogin);
        }

    }

    private void newMessageHandler(Context context) {
        Message message = context.bodyAsClass(Message.class);
        Message newMessage = messageService.addNewMessage(message);
        if (newMessage == null) {
            context.status(400);
        }

        else {
            context.status(200);
            context.json(newMessage);
        }
        
    }

    private void getAllMessagesHandler (Context context) {
        List<Message> messages = messageService.returnAllMessages();
        context.status(200);
        context.json(messages);
    }

    private void getMessageByMessageIdHandler (Context context) {
        String messageId = context.pathParam("message_id");
        int convertedId = Integer.parseInt(messageId);
        Message message = messageService.returnMessageByMessageId(convertedId);
        context.status(200);
        context.json(message);
    }

    private void deleteByMessageIdHandler (Context context) {
        String messageId = context.pathParam("message_id");
        int convertedId = Integer.parseInt(messageId);
        Message message = messageService.deleteByMessageId(convertedId);
        if (message == null) {
            context.status(200);
        }
        else {
            context.status(200);
            context.json(message);
        }
        
    }

    private void updateMessageHandler (Context context) {
        String messageId = context.pathParam("message_id");
        int convertedId = Integer.parseInt(messageId);
        String text = context.body();
        Message updatedMessage = messageService.updateMessage(convertedId, text);
        if (updatedMessage == null) {
            context.status(400);
        }
        else {
            context.status(200);
            context.json(updatedMessage);
        }
    }

    private void getMessagesByUserIdHandler (Context context) {
        String messageId = context.pathParam("message_id");
        int convertedId = Integer.parseInt(messageId);
        List<Message> messages = messageService.getMessagesByUserId(convertedId);
        context.status(200);
        context.json(messages);
    }

}
