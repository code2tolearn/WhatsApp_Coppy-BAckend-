package com.example.WhatsappCoppy;

import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {
    WhatsappRepository whatsappRepository = new WhatsappRepository() ;
    public boolean isNewUser(String mobile) {
        return whatsappRepository.isNewUser(mobile);
    }

    public String createUser(String name, String mobile) {
        whatsappRepository.createUser(name, mobile);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        return whatsappRepository.createGroup(users);
    }

    public int createMessage(String content) {
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception {
        return whatsappRepository.sendMessage(message, sender, group);
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception {
        return whatsappRepository.changeAdmin(approver, user, group);
    }
    public int removeUser(User user) throws Exception{
        return whatsappRepository.removeUser(user) ;
    }
    public String findMessage(Date start, Date end, int K) throws Exception{
        return whatsappRepository.findMessage(start, end, K) ;
    }
    public String deleteGroup(Group group , User user )
    {
        return whatsappRepository.deleteGroup(group , user);
    }
}