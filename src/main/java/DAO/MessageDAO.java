package DAO;

import java.sql.*;
import Model.Message;
import Util.ConnectionUtil;
import java.util.List;
import java.util.ArrayList;

public class MessageDAO {

    public Message newMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sqlStatement = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                int messageId = (int) result.getInt(1);
                Message messageCreation = new Message(messageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
                return messageCreation;
            }
        }
        catch(SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();

        try{

        String sqlStatement = "SELECT * FROM Message";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
            messages.add(message);
            
        }
        return messages;


        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public Message getMessageById(int message_id) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sqlStatement = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, message_id);

            ResultSet result = preparedStatement.executeQuery();

            if (result.next()) {
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                return message;
            }
        
        }
        catch (SQLException exception) {
            
            exception.printStackTrace();
        }
        return null;
    }


    public Message deleteMessage(int message_id) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sqlQuery = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedSelectStatement = connection.prepareStatement(sqlQuery);

            preparedSelectStatement.setInt(1, message_id);
            ResultSet result = preparedSelectStatement.executeQuery();

            if (result.next()) {
                Message message = new Message(result.getInt("message_id"),result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                return message;
            }

            String sqlStatement = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, message_id);

            preparedStatement.executeUpdate();

        }
        catch (SQLException exception) {
            exception.printStackTrace();

        }
        return null;
    }


    public Message updateMessageByMessageId(int message_id, String message_text) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sqlStatement = "UPDATE Message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);


            preparedStatement.setString(1, message_text);
            preparedStatement.setInt(2, message_id);
            

            int updates = preparedStatement.executeUpdate();

            if(updates == 0) {
                return null;
            }

            String sqlQuery = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery);

            preparedStatement2.setInt(1, message_id);
            ResultSet result = preparedStatement2.executeQuery();

            if (result.next()) {
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                return message;
                
            }
        
        }
        catch (SQLException exception) {
            exception.printStackTrace();

        }
        return null;
    }

    public List<Message> getAllMessagesByUserId(int posted_by) {
        Connection connection = ConnectionUtil.getConnection();
        

        try {
            String sqlStatement = "SELECT * FROM Message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

            preparedStatement.setInt(1, posted_by);

            ResultSet result = preparedStatement.executeQuery();

            List <Message> messagesByUser = new ArrayList<>();
            while (result.next()) {
                Message message = new Message(result.getInt("message_id"), result.getInt("posted_by"), result.getString("message_text"), result.getLong("time_posted_epoch"));
                messagesByUser.add(message);
            }
            return messagesByUser; 
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
