package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;


public class MessageService {
    MessageDAO messageDAO;

    public MessageService() {
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message addNewMessage(Message msg) {
        if (msg.getMessage_text().isEmpty()) {
            return null;
        }
        else {
            return messageDAO.newMessage(msg);
        }
    }

    public List<Message> returnAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message returnMessageByMessageId(int message_id) {
        Message message = messageDAO.getMessageById(message_id);
        if (message==null) {
            return null;
        }
        else {
            return message;
        }
    }

    public Message deleteByMessageId(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

    public Message updateMessage(int message_id, String message_text) {
        if (message_text == null || message_text.isBlank()|| message_text.length() > 255) {
            return null;
        }
        else {
            return messageDAO.updateMessageByMessageId(message_id, message_text);
        }
    }

    public List<Message> getMessagesByUserId(int posted_by) {
        List<Message> messages = messageDAO.getAllMessagesByUserId(posted_by);
        if (messages==null) {
            return null;
        }
        else {
            return messages;
        }
    }
}
