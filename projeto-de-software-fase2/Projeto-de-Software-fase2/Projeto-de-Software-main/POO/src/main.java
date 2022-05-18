import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class main {
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
	
	public static void main(String[] args) {
		List<User> accounts = new ArrayList<User>();
		List<Comunity> comunities = new ArrayList<Comunity>();
		Scanner scanner = new Scanner(System.in).useDelimiter("\n");
		User user = new User();
		Comunity comunity = new Comunity();
		
		List<Message> feed_friends = new ArrayList<Message>();
		List<Message> feed_general = new ArrayList<Message>();
		
		String op = "1";
		boolean logado = false;
		
		IllegalArgumentException illegalEntrance = new IllegalArgumentException();
		
		while (!op.equals("3")) {
			System.out.print("\n1-Logar\n2-criar conta\n3-Sair\n");
			op = scanner.nextLine();
			if(op.equals("1")) {
		        System.out.print("email: ");
		        String login = scanner.nextLine();
		        
		        
		        
		        System.out.print("Senha: ");
		        String password = scanner.nextLine();
		        
		        user = login(accounts,login,password);
		        if(!Objects.isNull(user)) {
		        	logado=true;
		        }
			}else if(op.equals("2")) {
		        try {
		  
			        
		        	System.out.print("Digite seu email: ");
			        String login = scanner.nextLine();
			        if(!validEmail(login)) {
			        	throw illegalEntrance;
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
		        }catch(IllegalArgumentException e ) {
		        	System.out.println("Formato Invalido ");
		        }
		        catch (Exception e) {
		        	System.out.println("Algo deu errado na criação de conta, tente novamente");
		        }

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

			        
				}else if(op.equals("2")) {
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
					}catch(IllegalArgumentException e ) {
			        	System.out.println("Você já faz parte dessa comunidade");
			        }catch(Exception e) {
			        	System.out.println("Algo deu errado ao entrar na comunidade, tente novamente");
			        }

			        
				}else if(op.equals("3")) {
					for( Comunity comuniti : comunities) {
						System.out.println("Lista de membros da comunidade "+comuniti.getName());
						for (User usr : comuniti.getMembers()) {
							System.out.print(usr.getAttributes().get("nome"));
							System.out.print("\n");
						}
					}
				}else if(op.equals("4")) {
					if(accounts.size()>1) {
						System.out.println("Lista de usuarios: ");
						for( User usr : accounts) {
							if(!usr.getAttributes().get("login").equals(user.getAttributes().get("login")))
								System.out.println(usr.getId_user() + "-"+usr.getAttributes().get("nome"));
						}
				        System.out.print("Digite o id do usuario que você quer ser amigo: ");
				        String id = scanner.nextLine();
				        User u = accounts.get(Integer.parseInt(id)-2);
				        u.newNotfication(user);
					}else {
						System.out.println("Infelizmente só você cricou conta até o momento");
					}

				}else if(op.equals("5")) {
					user.my_info();
				}else if(op.equals("6")) {
			        System.out.print("Digite o nome do atributo ");
			        String key = scanner.nextLine();
			        
			        System.out.print("Digite as informações do atributo: ");
			        String value = scanner.nextLine();
			        try {
			        	user.addAttribute(key, value);
			        }catch (IllegalFormatException e ) {
			        	System.out.println("Os atributos precisam ter mais de 2 caracteres ");
			        } catch (Exception e) {
			        	System.out.println("Algo deu errado na criação de um novo atributo , tente novamente");
			        }
			        
				}
				else if(op.equals("7")) {
					user.my_attributes();
			        System.out.print("Digite o nome do atributo para ser editado ");
			        String key = scanner.nextLine();
			        
			        System.out.print("Digite o novo valor do atributo: ");
			        String value = scanner.nextLine();
			        try {
			        	user.editAttribute(key, value);
			        }catch (IllegalFormatException e ) {
			        	System.out.println("Os atributos precisam ter mais de 2 caracteres ");
			        }catch (IllegalArgumentException e) {
			        	System.out.println("O atributo digitado não existe");
			        }
			        catch (Exception e) {
			        	System.out.println("Algo deu errado na alteração de um atributo, tente novamente");
			        }
			        
				}else if(op.equals("8")) {
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
				}else if(op.equals("9")) {
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
							user.newMessage(message);
							accounts.get(Integer.parseInt(id)-2).newMessage(message);
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
				}else if(op.equals("10")) {
					if(user.getMy_comunities().size()>0) {
						user.my_comunities();
						System.out.print("Digite o Id da comunidade que deseja ver o feed: ");
						String id = scanner.nextLine();		
						System.out.print("Mensagens na "+comunities.get(Integer.parseInt(id)-2).getName());
						comunities.get(Integer.parseInt(id)-2).my_messages();
					}else {
						System.out.print("Você não faz parte de nenhuma comunidade :/ ");
					}
				}else if(op.equals("11")) {
					System.out.print("Digite o conteudo da mensagem: ");
					String content = scanner.nextLine();	
					
					Message message = new Message(user,content);
					
					System.out.print("Quem pode ver essa mensagem?\n1-Geral\n2-Só amigos ");
					String choice = scanner.nextLine();	
					if(choice.equals("1")) {
						feed_general.add(message);
					}else {
						feed_friends.add(message);
					}
				}else if(op.equals("12")) {
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
				}else if(op.equals("13")) {
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
					}
				}
		}
	
		}
	System.out.print("Obrigado por ter usado nosso sistems <3");
	}
}


	

