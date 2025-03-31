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
        return messageDAO.getMessageById(message_id);
    }

    public Message deleteByMessageId(int message_id) {
        return messageDAO.deleteMessage(message_id);
    }

    public Message updateMessage(int message_id, String message_text) {
        return messageDAO.updateMessageByMessageId(message_id, message_text);
    }

    public List<Message> getMessagesByUserId(int posted_by) {
        return messageDAO.getAllMessagesByUserId(posted_by);
    }
}
