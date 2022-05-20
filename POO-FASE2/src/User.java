import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


import java.util.Set;
import java.util.Map;

public class User implements Messages{
	private static final AtomicInteger count = new AtomicInteger(0);

	private static final Exception IllegalFormatException = null; 
	
	private int id_user;

	private List<Comunity> my_comunities = new ArrayList<Comunity>();
	private List<User> notifications = new ArrayList<User>();
	private List<User> friends = new ArrayList<User>();
	private List<Message> my_messages = new ArrayList<Message>();
	private LinkedHashMap<String,String> attributes = new LinkedHashMap<String, String>();
	
	public User() {
		this.id_user = count.incrementAndGet();
	}
	

	public void enter_comunity(Comunity comunity_name) {
		this.my_comunities.add(comunity_name);
	}
	
	
	public void my_attributes() {		
		Set<Map.Entry<String,String>> new_attributes = this.attributes.entrySet();
		for (Map.Entry<String, String> it: new_attributes) {
			System.out.println(it.getKey() + ":   " +  it.getValue());
		}		
	}
	
	public void my_comunities() {
		for(Comunity comun : my_comunities) {
			System.out.println(comun.getId_comunity()+"-"+comun.getName());
		}	
	}
	
	public void my_friends() {
		for(User friend : this.getFriends()) {
			System.out.println(friend.getId_user()+"-"+friend.attributes.get("nome"));
		}		
	}
	
	public void my_messages() {
		for(Message message : this.getMy_messages()) {
			System.out.println(message.getSender().attributes.get("nome")+": "+message.getContent());
		}			
	}
	
	public void newMessage(Message message) {
		this.my_messages.add(message);
	}
	
	public void my_info() {
		//retona as informações do usuario
		my_attributes();
		
		
		System.out.println("Minhas comunidades:");
		this.my_comunities();
		System.out.println("Amigos:");
		this.my_friends();
		System.out.println("Messagens:");
		this.my_messages();
	}
	
	public void addAttribute(String key,String value) throws Exception{
		if(key.length()<3 || value.length()<3 || attributes.containsKey(key)) {
			//IllegalFormatException formatInvalid = new IllegalFormatException();
			throw IllegalFormatException;
		}else {
			this.attributes.put(key, value);
		}
	}
	public void editAttribute(String key,String value) throws Exception {
		if(key.length()<3 || value.length()<3) {
			//InvalidFormatException formatInvalid = new InvalidFormatException();
			throw IllegalFormatException;
		}else if(!attributes.containsKey(key)) {
			IllegalArgumentException illegalEntrance = new IllegalArgumentException();
			throw illegalEntrance;
		}
		else {
			this.attributes.replace(key, value);
		}
	}
	public void newNotfication(User notification) throws UserAlreadyExistsException{
		if(notifications.contains(notification) | friends.contains(notification)) {
			UserAlreadyExistsException ex = new UserAlreadyExistsException();
			throw ex;
		}
		this.notifications.add(notification);
	}
	
	public void listNotifications() {
		for(int i=0;i<this.notifications.size();i++) {
			System.out.println(i+1+"-"+this.notifications.get(i).attributes.get("nome"));
		}
	}
	
	public void addFriend(User user) {
		this.friends.add(user);
	}
	
	
	public int getId_user() {
		return id_user;
	}

	public List<Comunity> getMy_comunities() {
		return my_comunities;
	}

	public List<User> getFriends() {
		return friends;
	}

	public List<Message> getMy_messages() {
		return my_messages;
	}


	public List<User> getNotifications() {
		return notifications;
	}


	public void setNotifications(List<User> notifications) {
		this.notifications = notifications;
	}


	public LinkedHashMap<String, String> getAttributes() {
		return attributes;
	}


	public void setAttributes(LinkedHashMap<String, String> attributes) {
		this.attributes = attributes;
	}


	public static AtomicInteger getCount() {
		return count;
	}


	public void setId_user(int id_user) {
		this.id_user = id_user;
	}


	public void setMy_comunities(List<Comunity> my_comunities) {
		this.my_comunities = my_comunities;
	}


	public void setFriends(List<User> friends) {
		this.friends = friends;
	}


	public void setMy_messages(List<Message> my_messages) {
		this.my_messages = my_messages;
	}




	
}
