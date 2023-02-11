package com.example.WhatsappCoppy;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashMap<String, User> userData;
    private int customGroupCount;
    private HashSet<User> userMobile ;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userData = new HashMap<>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public boolean isNewUser(String mobile) {
        if(userData.containsKey(mobile)) return false;
        return true;
    }

    public void createUser(String name, String mobile) {
        userData.put(mobile, new User(name, mobile));
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        if(!adminMap.get(group).equals(approver)) throw new Exception("Approver does not have rights");
        if(!this.userExistsInGroup(group, user)) throw  new Exception("User is not a participant");

        adminMap.put(group, user);
        return "SUCCESS";
    }

    public Group createGroup(List<User> users) {
        if(users.size() == 2) return this.createPersonalChat(users);

        this.customGroupCount++;
        String groupName = "Group " + this.customGroupCount;
        Group group = new Group(groupName, users.size());
        groupUserMap.put(group, users);
        adminMap.put(group, users.get(0));
        return group;
    }

    public Group createPersonalChat(List<User> users) {
        String groupName = users.get(1).getName();
        Group personalGroup = new Group(groupName, 2);
        groupUserMap.put(personalGroup, users);
        return personalGroup;
    }

    public int createMessage(String content){
        this.messageId++;
        Message message = new Message(messageId, content, new Date());
        return this.messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        if(!this.userExistsInGroup(group, sender)) throw  new Exception("You are not allowed to send message");

        List<Message> messages = new ArrayList<>();
        if(groupMessageMap.containsKey(group)) messages = groupMessageMap.get(group);

        messages.add(message);
        groupMessageMap.put(group, messages);
        senderMap.put(message ,sender) ;
        return messages.size();
    }

    public boolean userExistsInGroup(Group group, User sender) {
        List<User> users = groupUserMap.get(group);
        for(User user: users) {
            if(user.equals(sender)) return true;
        }
        //if(users.contains(sender)) return true?false

        return false;
    }
    // Remove user
    public int removeUser(User user) throws Exception{
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
        boolean userFound = false ;
        Group userGroup = null ;
        for(Group group : groupUserMap.keySet())
        {
            if(groupUserMap.get(group).contains(user))
            {
                userGroup = group ;
                if( adminMap.get(group).equals(user))
                {
                    throw new Exception("Cannot remove admin") ;
                }
                userFound = true ;
            }
        }
        if(!userFound) throw  new Exception("User not found") ;
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)
        List<User> users = groupUserMap.get(userGroup) ;
        users.remove(user) ;
        groupUserMap.put(userGroup , users) ;
        // removed
        List<Message> messageList = groupMessageMap.get(userGroup) ;
        for(Message message: messageList)
        {
            if(senderMap.get(message).equals(user))
            {
                messageList.remove(message) ;
            }
        }
        groupMessageMap.put(userGroup , messageList) ;
        // removed from groupmas
        for(Message message: senderMap.keySet())
        {
            if(senderMap.get(message).equals(user))
            {
                senderMap.remove(message) ;
            }
        }
        return groupUserMap.get(userGroup).size() + groupMessageMap.get(userGroup).size()+ senderMap.size() ;
    }

    public String findMessage(Date start, Date end, int K) throws Exception{
        // collect all msg
        List<Message> messageList = new ArrayList<>() ;
        for(Group group : groupUserMap.keySet())
        {
            messageList.addAll(groupMessageMap.get(group)) ;
        }
        // filter out msg
        List<Message> filterMessage = new ArrayList<>() ;
        for(Message message : messageList)
        {
            if(message.getTimestamp().after(start) && message.getTimestamp().before(end))
            {
                filterMessage.add(message) ;
            }
        }
        return filterMessage.get(K-1).getContent() ;
    }
    public String deleteGroup(Group group , User user )
    {
        if(!groupUserMap.containsKey(group)) return "Not possible" ;
        if(!adminMap.get(group).equals(user)) return "Only admin can delete" ;
        groupUserMap.remove(group)  ;
        groupMessageMap.remove(group) ;
        adminMap.remove(group) ;
        return "Success" ;
    }}
