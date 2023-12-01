package DAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {
    Connection connection = ConnectionUtil.getConnection();

    public Message add(Message msg) {
        try {
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());
            ps.executeUpdate();
            ResultSet pKeyResultSet = ps.getGeneratedKeys();

            if (pKeyResultSet.next()) {
                return new Message((int) pKeyResultSet.getLong(1), msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages() {
        List<Message> msgList = new ArrayList<Message>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();

            while(rs.next()) {
                msgList.add(new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return msgList;
    }

    public Message getMessageByID(int id) {
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                return new Message(id, rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteMessageByID(int id) {
        try {
            String sql = "DELETE FROM message WHERE message_id = ? RETURNING *";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Message updateMessageByID(Message msg, int id) {
        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, msg.getMessage_text());
            ps.setInt(2, id);
            ps.executeUpdate();
            return msg;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessagesByAccID(int accId) {
        List<Message> userMsgs = new ArrayList<Message>();
        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, accId);
            ps.executeQuery();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Message msg = new Message(rs.getInt("message_id"), accId, rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                userMsgs.add(msg);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userMsgs;
    }
}
