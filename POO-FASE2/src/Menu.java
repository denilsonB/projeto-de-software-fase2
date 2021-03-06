import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
	List<User> accounts = new ArrayList<User>();
	List<String> used_emails = new ArrayList<String>();
	List<Comunity> comunities = new ArrayList<Comunity>();
	Scanner scanner = new Scanner(System.in).useDelimiter("\n");
	User user = new User();
	Comunity comunity = new Comunity();
	
	List<Message> feed_friends = new ArrayList<Message>();
	List<Message> feed_general = new ArrayList<Message>();
	
	String op = "1";
	boolean logado = false;
	
	IllegalArgumentException illegalEntrance = new IllegalArgumentException();
	
	public void start() {
		while (!op.equals("3")) {
			System.out.print("\n1-Logar\n2-criar conta\n3-Sair\n");
			op = scanner.nextLine();
			if(op.equals("1")) {
				user_login();
			}else if(op.equals("2")) {
				create_account();
			}
			while (logado){
				System.out.println("\n0-Sair\n1-Criar comunidade\n2-Entrar numa comunidade\n3-listar membros das comunidades"
						+ "\n4-Adicionar amigo\n5-Minhas informações\n6-Adicionar novo atributo\n7-Editar perfil\n"
						+ "8-Notificações\n9-Mandar mensagem\n10-Ver feed da comunidade\n"
						+ "11-Postar no feed de noticias\n12-vizualisar feed\n13-Apagar conta\n");
				op = scanner.nextLine();
				
				if(op.equals("0")) {
					logado=false;
					System.out.print("Até a proxima =p");
					break;
				}
				else if(op.equals("1")) {
					create_comunity();
				}else if(op.equals("2")) {
					enter_comunity();
				}else if(op.equals("3")) {
					list_comunities_members();
				}else if(op.equals("4")) {
					add_friend();
				}else if(op.equals("5")) {
					user.my_info();
				}else if(op.equals("6")) {
			        create_attribute();
				}
				else if(op.equals("7")) {
					update_attribute();
				}else if(op.equals("8")) {
					notifications();
				}else if(op.equals("9")) {
					send_message();
				}else if(op.equals("10")) {
					comunity_feed();
				}else if(op.equals("11")) {
					post_on_feed();
				}else if(op.equals("12")) {
					see_feed();
				}else if(op.equals("13")) {
					delete_account();
				}
		}
	
		}
		System.out.print("Obrigado por ter usado nosso sistems <3");
	}
	
	public static User login(List<User> accounts, String login, String senha) {
        for(User usr : accounts) {
        	if(usr.getAttributes().get("login").equals(login)) {
        		if(usr.getAttributes().get("senha").equals(senha)) {
        			System.out.print("Seja bem vindo!! ");
        			return usr;
 
        		}else {
        			System.out.print("Login ou senha incorreto");
        			return null;		        			
        		}
        	}
        }
        System.out.print("Usuario não encontrado");
        return null;
	}
	
	public static boolean validEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
                  
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}
	
	public void user_login() {
        System.out.print("email: ");
        String login = scanner.nextLine();
        
        
        
        System.out.print("Senha: ");
        String password = scanner.nextLine();
        
        user = login(accounts,login,password);
        if(!Objects.isNull(user)) {
        	logado=true;
        }
	}
	public void create_account() {
        try {
        	user = new User();
        	System.out.print("Digite seu email: ");
	        String login = scanner.nextLine();
	        if(!validEmail(login)) {
	        	InvalidEmailException e = new InvalidEmailException();
	        	throw e;
	        }else if(used_emails.contains(login)) {
		    	UserAlreadyExistsException ex = new UserAlreadyExistsException();
				throw ex;
			}
	        user.addAttribute("login",login);
	        
	        System.out.print("Digite sua senha: ");
	        String password = scanner.nextLine();
	        if(password.contains(" ")) {
	        	throw illegalEntrance;
	        }
		    user.addAttribute("senha", password);
		    
	        System.out.print("Digite seu nome: ");
	        String name = scanner.nextLine();
	        
		    user.addAttribute("nome",name);		
		    
		    accounts.add(user);
		    System.out.println("Conta criada com sucesso!!: ");
		    used_emails.add(login);
        }catch(InvalidEmailException e ) {
        	System.out.println("Email Invalido ");
        }
        catch(IllegalArgumentException e ) {
        	System.out.println("Formato Invalido ");
        }catch(UserAlreadyExistsException e ) {
        	System.out.println("Email já cadastrado");
        }
        catch (Exception e) {
        	System.out.println("Algo deu errado na criação de conta, tente novamente");
        }
	}
	public void create_comunity() {
		
		try {
	        System.out.print("Digite o nome da comunidade: ");
	        String comunity_name = scanner.nextLine();
	        if(comunity_name.length()<3) {
	        	throw illegalEntrance;
	        }
	        System.out.print("Digite a descrição da comunidade: ");
	        String comunity_description = scanner.nextLine();
	        if(comunity_description.length()<3) {
	        	throw illegalEntrance;
	        }
	        
	        comunity = new Comunity(user.getId_user(),comunity_name,comunity_description);
	        
	        comunity.new_member(user);
	        
	        comunities.add(comunity);

	        user.enter_comunity(comunity);
	        
	        
	        System.out.println("Comunidade criada com sucesso!!: ");						
		}catch (IllegalArgumentException e ) {
			System.out.println("Tamanho da entrada Invalido, é precisso ter mais de 2 caracteres");	
		}catch (Exception e) {
        	System.out.println("Algo deu errado na criação de comunidade, tente novamente");
        }
	}
	public void enter_comunity() {
		System.out.println("Lista de comunidades: ");
		for(Comunity comunit : comunities) {
			System.out.println(comunit.getId_comunity() +" "+comunit.getName() );
		}
		try {
	        System.out.print("Digite o numero da comunidade que deseja entrar: ");
	        String choice = scanner.nextLine();
	        int com = Integer.parseInt(choice);
	        
	        comunities.get(com-2).new_member(user);
	        user.enter_comunity(comunities.get(com-2));						
		}catch(NumberFormatException e ) {
			System.out.println("Por favor digite um numero");	
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Por favor digite um id valido");
		}catch(UserAlreadyExistsException e ) {
        	System.out.println("Você já faz parte dessa comunidade");
        }catch(Exception e) {
        	System.out.println("Algo deu errado ao entrar na comunidade, tente novamente");
        }
	}
	public void list_comunities_members() {
		for( Comunity comuniti : comunities) {
			System.out.println("Lista de membros da comunidade "+comuniti.getName());
			for (User usr : comuniti.getMembers()) {
				System.out.print(usr.getAttributes().get("nome"));
				System.out.print("\n");
			}
		}
	}
	public void add_friend(){
		if(accounts.size()>1) {
			System.out.println("Lista de usuarios: ");
			for( User usr : accounts) {
				if(!usr.getAttributes().get("login").equals(user.getAttributes().get("login")))
					System.out.println(usr.getId_user() + "-"+usr.getAttributes().get("nome"));
			}
			try {
		        System.out.print("Digite o id do usuario que você quer ser amigo: ");
		        String id = scanner.nextLine();
		        User u = accounts.get(Integer.parseInt(id)-2);
		        if(u.equals(user)) {
		        	throw illegalEntrance;
		        }
		        u.newNotfication(user);
		        System.out.println("Solicitação enviada com sucesso !");
			}catch(NumberFormatException e ) {
				System.out.println("Por favor digite um numero");	
			}catch(IndexOutOfBoundsException | IllegalArgumentException ex) {
				System.out.println("Por favor digite um id valido");
			}catch(UserAlreadyExistsException e ) {
	        	System.out.println("Você e está pessoa já estão relacionadas");
	        }catch(Exception e) {
	        	System.out.println("Algo deu errado ao adicionar amigo, tente novamente");
	        }

		}else {
			System.out.println("Infelizmente só você cricou conta até o momento");
		}
	}
	public void create_attribute() {
		try {
			System.out.print("Digite o nome do atributo ");
	        String key = scanner.nextLine();
	        
	        System.out.print("Digite as informações do atributo: ");
	        String value = scanner.nextLine();
        
        	user.addAttribute(key, value);
        }catch (IllegalFormatException e ) {
        	System.out.println("O atributo digitado é invalido");
        } catch (Exception e) {
        	System.out.println("Algo deu errado na criação de um novo atributo , tente novamente");
        }
	}
	public void update_attribute() {
		try {
			user.my_attributes();
	        System.out.print("Digite o nome do atributo para ser editado ");
	        String key = scanner.nextLine();
	        
	        System.out.print("Digite o novo valor do atributo: ");
	        String value = scanner.nextLine();
        
        	if(key.equals("login")) {
        		if(!validEmail(key)) {
        			InvalidEmailException e = new InvalidEmailException();
		        	throw e;
		        }
        	}
        	user.editAttribute(key, value);
        }catch(InvalidEmailException e ) {
        	System.out.println("Digite um email valido");
        }
        catch (IllegalFormatException e ) {
        	System.out.println("Os atributos precisam ter mais de 2 caracteres ");
        }catch (IllegalArgumentException e) {
        	System.out.println("O atributo digitado não existe");
        }
        catch (Exception e) {
        	System.out.println("Algo deu errado na alteração de um atributo, tente novamente");
        }   
	}
	public void notifications() {
		try {
			System.out.println("Solicitações de amizade:");
			user.listNotifications();
			System.out.print("1-Aceitar\n2-Recusar\n3-Voltar:");
			String choice = scanner.nextLine();
			if(choice.equals("1")) {
				System.out.print("Digite o Id da notificação que você quer aceitar: ");
				String id = scanner.nextLine();
				User usr = user.getNotifications().remove(Integer.parseInt(id)-1);
				user.addFriend(usr);
				accounts.set(accounts.indexOf(usr), usr).addFriend(user);
			}else if(choice.equals("2")) {
				System.out.print("Digite o Id da notificação que você quer rejeitar: ");
				String id = scanner.nextLine();
				user.getNotifications().remove(Integer.parseInt(id)-1);
			}
		}catch(NumberFormatException e ) {
			System.out.println("Por favor digite um numero");	
		}catch(IndexOutOfBoundsException | IllegalArgumentException ex) {
			System.out.println("Por favor digite um id valido");
		}catch(Exception e) {
        	System.out.println("Algo deu errado nas notificações, tente novamente");
        }
	}
	public void send_message() {
		try {
			System.out.print("1-Amigo\n2-Comunidade\n3-voltar:");
			String choice = scanner.nextLine();
			if(choice.equals("1")) {
				if(user.getFriends().size()>0) {
					user.my_friends();
					System.out.print("Digite o Id do amigo que deseja mandar mensagem: ");
					String id = scanner.nextLine();		
					System.out.print("Digite o conteudo da mensagem: ");
					String content = scanner.nextLine();	
					
					Message message = new Message(user,content);
					if(user.equals(accounts.get(Integer.parseInt(id)-2))) {
						UserAlreadyExistsException e = new UserAlreadyExistsException();
						throw e;
					}
					accounts.get(Integer.parseInt(id)-2).newMessage(message);
					user.newMessage(message);
					System.out.println("Mensagem enviada com sucesso");
				}else {
					System.out.println("Você não tem nenhum amigo :/ ");
				}
			}else if(choice.equals("2")) {
				if(user.getMy_comunities().size()>0) {
					user.my_comunities();
					System.out.print("Digite o Id da comunidade que deseja mandar mensagem: ");
					String id = scanner.nextLine();		
					System.out.print("Digite o conteudo da mensagem: ");
					String content = scanner.nextLine();	
					
					Message message = new Message(user,content);
					comunities.get(Integer.parseInt(id)-2).newMessage(message);
				}else {
					System.out.println("Você não faz parte de nenhuma comunidade :/ ");
				}
				
			}
		}catch(NumberFormatException e ) {
			System.out.println("Por favor digite um numero no id");	
		}catch(IndexOutOfBoundsException  | UserAlreadyExistsException e) {
			System.out.println("Por favor digite um id valido");
		}catch(IllegalArgumentException e ) {
        	System.out.println("O conteudo possui tamanho invalido(precisa ser entre 3 e 50) ");
        }
		catch(Exception e) {
        	System.out.println("Algo deu errado ao enviar mensagem, tente novamente");
        }
	}
	public void comunity_feed() {
		try {
			if(user.getMy_comunities().size()>0) {
				user.my_comunities();
				System.out.print("Digite o Id da comunidade que deseja ver o feed: ");
				String id = scanner.nextLine();		
				System.out.println("Mensagens na "+comunities.get(Integer.parseInt(id)-2).getName());
				comunities.get(Integer.parseInt(id)-2).my_messages();
			}else {
				System.out.print("Você não faz parte de nenhuma comunidade :/ ");
			}						
		}catch(NumberFormatException e ) {
			System.out.println("Por favor digite um numero no id");	
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Por favor digite um id valido");
		}
		catch(Exception e) {
        	System.out.println("Algo deu a ver fedd da comunidade, tente novamente");
        }
	}
	public void post_on_feed() {
		try {
			System.out.print("Digite o conteudo da mensagem: ");
			String content = scanner.nextLine();	
			
			Message message = new Message(user,content);
			
			System.out.print("Quem pode ver essa mensagem?\n1-Geral\n2-Só amigos ");
			String choice = scanner.nextLine();	
			if(choice.equals("1")) {
				feed_general.add(message);
			}else if(choice.equals("2")){
				feed_friends.add(message);
			}else {
				System.out.println("Digite um numero valido ");
			}
		}catch(IllegalArgumentException e ) {
        	System.out.println("O conteudo possui tamanho invalido(precisa ser entre 3 e 50)  ");
        }catch(Exception e) {
        	System.out.println("Algo deu errado ao enviar mensagem, tente novamente");
        }
	}
	public void see_feed() {
		System.out.println("Feed Geral: ");
		for(int i=0;i<feed_general.size();i++) {
			System.out.println(feed_general.get(i).getSender().getAttributes().get("nome")+": "+feed_general.get(i).getContent());
		}
		System.out.println("Feed Amigos: ");
		for(Message m : feed_friends) {
			if(user.getFriends().contains(m.getSender()) || m.getSender().equals(user)) {
				System.out.println(m.getSender().getAttributes().get("nome")+": "+m.getContent());
			}
		}
	}
	public void delete_account() {
		try {
			System.out.print("Tem certeza que seja apagar a conta?\n1-Sim\n2-Não ");
			String choice = scanner.nextLine();
			if(choice.equals("1")) {
				for(int i=0;i<feed_general.size();i++) {
					if(feed_general.get(i).getSender().equals(user)){
						feed_general.remove(i);
					}
				}
				for(int i=0;i<feed_friends.size();i++) {
					if(feed_friends.get(i).getSender().equals(user)){
						feed_friends.remove(i);
					}
				}
				for(User friend : user.getFriends()) {
					for(int i=0;i<friend.getMy_messages().size();i++) {
						if(friend.getMy_messages().get(i).getSender().equals(user)) {
							friend.getMy_messages().remove(i);
						}
					}
				}	
				for(User friend : user.getFriends()) {
					friend.getFriends().remove(user);
				}
				for(Comunity comun : user.getMy_comunities()) {
					for(int i=0;i<comun.getMy_messages().size();i++) {
						if(comun.getMy_messages().get(i).getSender().equals(user)) {
							comun.getMy_messages().remove(i);
						}
					}
				}
				accounts.remove(user);
				user = null;
				logado=false;
			}
		}catch(Exception e) {
			System.out.print("Algo deu errado na remoção de conta, tente novamente");
		}
	}
}

