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
        return msgDAO.add(msg);
    }

    public List<Message> getMessages() {
        return msgDAO.getAllMessages();
    }

    public Message getMessage(int id) {
        return msgDAO.getMessageByID(id);
    }

    public void deleteMessage(int id) {
        msgDAO.deleteMessageByID(id);
    }

    public void updateMessage(Message msg, int id) {
        msgDAO.updateMessageByID(msg, id);
    }

    public List<Message> getAllUserMessages(int accId) {
        return msgDAO.getAllMessagesByAccID(accId);
    }
}
