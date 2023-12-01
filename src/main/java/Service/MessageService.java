package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    MessageDAO msgDAO;

    public MessageService() {
        msgDAO = new MessageDAO();
    }

    public MessageService(MessageDAO m) {
        this.msgDAO = m;
    }

    public Message addMessage(Message msg) {
        if (msg.getMessage_text().isEmpty()) {
            System.out.println("Message cannot be blank");
            return null;
        } else if (msg.getMessage_text().length() > 254) {
            System.out.println("Message has too many characters");
            return null;
        } 
        return msgDAO.add(msg);
    }

    public List<Message> getMessages() {
        return msgDAO.getAllMessages();
    }

    public Message getMessage(int id) {
        if (msgDAO.getMessageByID(id) == null) {
            System.out.println("Message does not exist");
        } 
        return msgDAO.getMessageByID(id);
    }

    public void deleteMessage(int id) {
        msgDAO.deleteMessageByID(id);
    }

    public Message updateMessage(Message msg, int id) {
        if (msg.getMessage_text().isEmpty()) {
            System.out.println("Message cannot be empty");
            return null;
        } else if (msg.getMessage_text().length() > 254) {
            System.out.println("Message has too many characters");
            return null;
        }
        return msgDAO.updateMessageByID(msg, id);
    }

    public List<Message> getAllUserMessages(int accId) {
        return msgDAO.getAllMessagesByAccID(accId);
    }
}
