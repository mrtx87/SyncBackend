package Services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.haihuynh.springbootwebsocketchatapplication.configuration.WebSocketConfiguration;

import messages.ChatMessage;
import messages.Message;
import messages.Video;

@Service
public class SyncService {

	public static int maxUpdateJoinClients = 3;
	public static Boolean publicRaum = false;
	public static Boolean privateRaum = true;

	public  Video defaultVideo = new Video("WBG7TFLj4YQ", 0.0f,"Unter Wasser: Megacitys in Gefahr | Doku | ARTE\r\n");

	
	HashMap<Long, Raum> rooms = new HashMap<>();
	public List<Message> generateToPublicRoomMessages;

	static String[] randomNames = { "Sir Anthony Absolute", "Nick Adams", "Parson Adams", "Frankie Addams",
			"Anthony Adverse", "Captain Ahab", "Albertine", "Alceste", "Algernon", "Ali Baba", "Squire Allworthy",
			"Count Almaviva", "The Alvings", "Amaryllis", "Ananse", "Pamela Andrews", "Angelica", "Harry Angstrom",
			"Mr. and Mrs. Antrobus", "Aramis", "Isabel Archer", "Enoch Arden", "The Artful Dodger",
			"Gustave von Aschenbach", "Lady Brett Ashley", "Athos", "Ayesha", "Dr. Aziz", "Baba-Yaga", "Babar",
			"Bilbo Baggins", "Frodo Baggins", "Harry Bailly", "David Balfour", "Mr. Barkis", "Jake Barnes", "Lily Bart",
			"Yevgeny Bazarov", "Adam Bede", "Belial", "Bennet family", "Bertram family", "Pierre Bezukhov",
			"Big Brother", "Big Daddy", "Rupert Birkin", "Anthony Blanche", "Leopold Bloom", "Molly Bloom", "Bluebeard",
			"Bobbsey Twins", "William Boldwood", "Bolkonsky family", "James Bond", "Boojum", "The Borrowers",
			"Josiah Bounderby", "Emma Bovary", "Sally Bowles", "Lady Augusta Bracknell", "Bradamante",
			"Matthew Bramble", "Colonel Brandon", "Dave Brandstetter", "Gudrun Brangwen", "Ursula Brangwen",
			"Brer Rabbit", "Brighella", "Hans Brinker", "Lily Briscoe", "Dorothea Brooke", "Father Brown", "Bruin",
			"Tom and Daisy Buchanan", "Inspector Bucket", "Buendía family", "Mr. Bumble", "Natty Bumppo", "Bunter",
			"Billy Bunter", "Rhett Butler", "Camille", "Don Camillo", "Albert Campion", "Capitano", "Philip Carey",
			"Nick Carraway", "Richard Carstone", "Nick Carter", "Sydney Carton", "Edward Casaubon", "Hans Castorp",
			"Cathbad", "Holden Caulfield", "Olive Chancellor", "Cheshire Cat", "Roger Chillingworth", "Chingachgook",
			"Mr. Chips", "Anna Christie", "Martin Chuzzlewit", "John Claggart", "Angel Clare", "Claudine",
			"Arthur Clennam", "Humphry Clinker", "Columbine", "Conan the Barbarian", "Conlaí", "David Copperfield",
			"Richard Cory", "Corydon", "Mother Courage", "Ichabod Crane", "Cratchit family", "Janie Crawford",
			"Gerald Crich", "Guy Crouchback", "Robinson Crusoe", "Cú Chulainn", "Sergeant Cuff", "Cunégonde",
			"D’Artagnan", "Edmond Dantès", "Fitzwilliam Darcy", "Charles Darnay", "Dashwood family", "Stephen Dedalus",
			"Lady Honoria Dedlock", "Madame Defarge", "Deirdre", "Jean Des Esseintes", "Mr. Dick", "Digenis Akritas",
			"Arthur Dimmesdale", "Dick and Nicole Diver", "Don Quixote", "Eliza Doolittle", "Lorna Doone", "Dorothy",
			"Dottore", "Nancy Drew", "Edwin Drood", "Bulldog Drummond", "Blanche DuBois", "The Duchess",
			"The Duke and the Dauphin", "Dulcinea", "C. Auguste Dupin", "Humphrey Chimpden Earwicker", "Eeyore",
			"Elliot family", "Lord Emsworth", "Enmerkar", "Henry Esmond", "Till Eulenspiegel", "Bathsheba Everdene",
			"Fagin", "Lord Fauntleroy", "Jude Fawley", "Richard Feverel", "Fezziwig", "Flora Finching", "Finn",
			"Huckleberry Finn", "Jeremiah Flintwinch", "Phileas Fogg", "Forsyte family", "Count Fosco", "Frankenstein",
			"Ethan Frome", "Fu Manchu", "Hedda Gabler", "Sairey Gamp", "Gandalf", "Eugene Gant", "Joe Gargery",
			"Jay Gatsby", "Gellert", "Beau Geste", "Gilgamesh", "Glass family", "Boris Godunov", "Golias", "Gollum",
			"Père Goriot", "Gradgrind", "Eugénie Grandet", "Emmeline Grangerford", "Dorian Gray", "Grendel",
			"Clyde Griffiths", "Grisette", "Titus Groan", "Mrs. Grundy", "Guermantes family", "Martin Guerre",
			"Guildenstern", "Prince Hal", "Mike Hammer", "Hardy Boys", "Jonathan Harker", "Harlequin",
			"Clarissa Harlowe", "Miss Havisham", "Jim Hawkins", "Asa and Sabbath Lily Hawks", "Headless horseman",
			"Heathcliff", "Uriah Heep", "Matt Helm", "Nora Helmer", "Michael Henchard", "Herne the Hunter",
			"Henry Higgins", "Fanny Hill", "Roy Hobbs", "Sherlock Holmes", "Horatio Hornblower", "Roderick Hudson",
			"Humbert Humbert", "Humpty Dumpty", "Mr. Hyde", "Iago", "Ilya of Murom", "Infant Phenomenon", "Isolde",
			"Jabberwock", "Mr. Jaggers", "Jarndyce family", "Dr. Jekyll", "Mrs. Jellyby", "Jim", "Gulley Jimson",
			"Joad family", "Joseph K.", "Juliet", "Karamazov brothers", "Anna Karenina", "Katharina", "Carol Kennicott",
			"Diedrich Knickerbocker", "George Knightley", "Stanley Kowalski", "Tonio Kröger", "Mr. Kurtz",
			"Will Ladislaw", "Lydia Languish", "Silas Lapham", "Wolf Larsen", "Lazarillo de Tormes", "Lear",
			"Simon Legree", "Lemminkäinen", "Inspector Lestrade", "Konstantine Levin", "Linton family", "Little Em’ly",
			"Little Eva", "Little Nell", "Lochinvar", "Lohengrin", "Willy Loman", "Studs Lonigan", "Lothario",
			"Robert Lovelace", "Sut Lovingood", "Lugalbanda", "Lulu", "Arsène Lupin", "Tertius Lydgate", "Barry Lyndon",
			"Macbeth", "Lady Macbeth", "Macheath", "Mad Hatter", "Madog ab Owain Gwynedd", "Maeldúin", "Abel Magwitch",
			"Jules Maigret", "Major Major", "Malcolm", "Alexander and Lucie Manette", "Marcel", "March Hare",
			"March family", "Augie March", "Marchmain family", "Jacob Marley", "Philip Marlowe", "Miss Marple",
			"Bertha Mason", "Perry Mason", "Travis McGee", "Wilhelm Meister", "Oliver Mellors", "Mr. Merdle",
			"Wilkins Micawber", "Daisy Miller", "Miranda", "Walter Mitty", "Mock Turtle", "Sara Monday", "Moomintroll",
			"Hank Morgan", "Professor Moriarty", "Catherine Morland", "Hazel Motes", "Mr. Moto", "Mowgli", "Mugridge",
			"Baron Munchausen", "Edward Murdstone", "Captain Nemo", "Nicholas Nickleby", "Scarlett O’Hara",
			"Gabriel Oak", "Oberon", "Odette", "Duke of Omnium", "Eugene Onegin", "Ophelia", "Orlando", "Orlando",
			"Gilbert Osmond", "Othello", "Palliser family", "Pancks", "Pangloss", "Pantaloon", "Panurge",
			"Peachum family", "Pearl", "Seth Pecksniff", "Pedrolino", "Peer Gynt", "Clara Peggotty", "Peter Pan",
			"Petruchio", "Samuel Pickwick", "Piglet", "Billy Pilgrim", "Pinocchio", "Pip", "Sir Fretful Plagiary",
			"Anna Livia Plurabelle", "Hercule Poirot", "Ross Poldark", "Aunt Polly", "Pollyanna", "Polonius",
			"Pontifex family", "Mary Poppins", "Porthos", "Portia", "Harry Potter", "Fanny Price", "Miss Prism",
			"Prospero", "J. Alfred Prufrock", "Hester Prynne", "Puck", "Puss in Boots", "Quasimodo", "Allan Quatermain",
			"Captain Queeg", "Queen of Hearts", "Queequeg", "Adela Quested", "Daniel Quilp", "A.J. Raffles",
			"Ramsay family", "Basil Ransom", "Rodion Raskolnikov", "Red Cross Knight", "Regan", "Reynard the Fox",
			"Richard III", "Tom Ripley", "Howard Roark", "Robin Hood", "Christopher Robin", "Mr. Rochester",
			"Rocinante", "Romeo", "Rosalind", "Rosencrantz", "Rostov family", "Roxane", "Barnaby Rudge", "Ruggiero",
			"Marmaduke Ruggles", "Horace Rumpole", "Charles Ryder", "The Saint", "Gregor Samsa", "Sancho Panza",
			"Tom Sawyer", "Scapin", "Scaramouche", "Ebenezer Scrooge", "Amelia Sedley", "The Shadow", "Shakuntala",
			"Becky Sharp", "Ántonia Shimerda", "Anne Shirley", "Shrek", "Shylock", "Bill Sikes", "Long John Silver",
			"Tyrone Slothrop", "Smike", "George Smiley", "Winston Smith", "Snark", "Snopes family", "Snowball",
			"Lucy Snowe", "Halvard Solness", "Hetty Sorrel", "Sam Spade", "Dora Spenlow", "Wackford Squeers",
			"Stage Manager", "Monroe Stahr", "Starbuck", "Willie Stark", "James Steerforth", "Lambert Strether",
			"Esther Summerson", "Charles and Joseph Surface", "Sutpen family", "Svengali", "Charles Swann",
			"Verena Tarrant", "Tarzan", "Tattycoram", "Suky Tawdry", "Lady Teazle", "William Tell", "Becky Thatcher",
			"Thomas Bigger", "Thomas the Tank Engine", "Sadie Thompson", "Christopher Tietjens", "Tinker Bell",
			"Titania", "Tituba", "Topsy", "Tristan", "Betsey Trotwood", "Sergeant Francis Troy",
			"Tweedledum and Tweedledee", "Oliver Twist", "Uncle Tom", "Urizen", "Jean Valjean", "Valmont",
			"Philo Vance", "Dolly Varden", "Varner family", "Diggory Venn", "Captain Vere", "Rosamond Vincy", "Viola",
			"Count Aleksey Vronsky", "Eustacia Vye", "Dr. Watson", "Weird Sisters", "Sam Weller", "Frederick Wentworth",
			"Werther", "Sophia Western", "Simon Wheeler", "White Rabbit", "Agnes Wickfield", "Tom Wilcher",
			"Damon Wildeve", "Pudd’nhead Wilson", "Lord Peter Wimsey", "Wingfield family", "Wise Men of Gotham",
			"Wolfdietrich", "Nero Wolfe", "Emma Woodhouse", "Bertie Wooster", "Clym Yeobright",
			"Captain John Yossarian", "Zorro" };

	public HashMap<Long, Raum> getRooms() {
		return rooms;
	}

	public void setRooms(HashMap<Long, Raum> rooms) {
		this.rooms = rooms;
	}

	public static Long generateRaumId() {

		Long time = System.currentTimeMillis();
		return time;
	}

	public String getCurrentTime() {
		return LocalTime.now().toString().substring(0, 8);
	}

	public void saveRoom(Raum raum) {
		rooms.put(raum.getRaumId(), raum);

	}

	public Message createRaum(Message message) {
		try {
			Long userID = message.getUserId();
			Raum raum = new Raum();
			raum.setCreatedAt(getCurrentTime());
			raum.setVideo(defaultVideo.clone());
			raum.setRaumStatus(message.getRaumStatus());
			raum.setPlayerState(2);
			saveRoom(raum);

			String name = randomName();
			User user = new User();
			user.setUserName(name);
			user.setUserId(message.getUserId());
			user.setAdmin(true);
			raum.addUser(user);

			WebSocketConfiguration.registryInstance.enableSimpleBroker("/" + userID);

			Message createRaumMessage = new Message();
			createRaumMessage.setType("create-room");
			createRaumMessage.setUser(user);
			createRaumMessage.setRaumId(raum.raumId);
			createRaumMessage.setUsers(raum.getUserList());
			createRaumMessage.setVideo(raum.getVideo());
			createRaumMessage.setRaumStatus(raum.getRaumStatus());
			createRaumMessage.setPlayerState(raum.getPlayerState());
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setMessageText("has created the Room");
			chatMessage.setTimestamp(getCurrentTime());
			chatMessage.setRaumId(raum.getRaumId());
			chatMessage.setUser(user);

			saveChatMessage(chatMessage);
			createRaumMessage.setChatMessages(raum.getChatMessages());

			return createRaumMessage;
		} catch (Exception e) {
			return null;
		}

	}

	public List<Message> joinRaum(Message message) {

		if (rooms.containsKey(message.getRaumId())) {

			Raum raum = rooms.get(message.getRaumId());
			String name = randomName();
			User user = new User();
			user.setUserName(name);
			user.setUserId(message.getUserId());

			if (raum.getRaumStatus() == privateRaum) {
				user.setAdmin(true);
			} else {
				user.setAdmin(false);
			}

			raum.addUser(user);
			raum.addJoiningUser(user);
			List<Message> messages = new ArrayList<>();

			Message joinMessage = new Message();
			joinMessage.setType("join-room");
			joinMessage.setUser(user);
			joinMessage.setUsers(raum.getUserList());
			joinMessage.setRaumId(raum.getRaumId());
			joinMessage.setVideo(raum.getVideo());
			joinMessage.setRaumStatus(raum.getRaumStatus());
			joinMessage.setPlayerState(raum.getPlayerState());
			joinMessage.setPlaylist(raum.getPlaylist());

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setMessageText("has joined the Room");
			chatMessage.setTimestamp(getCurrentTime());
			chatMessage.setUser(user);
			chatMessage.setRaumId(raum.getRaumId());

			saveChatMessage(chatMessage);

			joinMessage.setChatMessages(raum.getChatMessages());

			WebSocketConfiguration.registryInstance.enableSimpleBroker("/" + message.getUserId());

			for (Long id : raum.getUserIds()) {
				if (user.userId != id) {

					Message responseMessage = new Message();
					responseMessage.setType("update-client");
					responseMessage.setRaumId(raum.raumId);
					responseMessage.setUserId(id);
					responseMessage.setChatMessage(chatMessage);
					responseMessage.setUsers(raum.getUserList());

					messages.add(responseMessage);

				} else {
					messages.add(joinMessage);
				}
			}

			return messages;
		}

		return null;
	}

	/*
	 * public Long getTimeStamp(Long raumId) { return
	 * getRaum(raumId).getVideo().getTimestamp(); }
	 */

	public Message generatePublicRaeumeMessage(Message message) {

		Message responseMessage = new Message();
		responseMessage.setUserId(message.getUserId());
		responseMessage.setPublicRaeume(getPublicRooms());
		responseMessage.setType("request-public-raeume");

		return responseMessage;
	}

	public ArrayList<RaumDTO> getPublicRooms() {

		return (ArrayList<RaumDTO>) rooms.values().stream().filter(raum -> raum.getRaumStatus() == publicRaum)
				.map(raum -> RaumMapper.createRaumDTO(raum)).collect(Collectors.toList());
	}

	public List<Message> addAndShareNewVideo(Message message) {
		if (rooms.containsKey(message.getRaumId()) && message.getVideo() != null) {
			Raum raum = rooms.get(message.getRaumId());
			Video video = message.getVideo();
			raum.setVideo(video);
			raum.setPlayerState(1);
			List<Message> messages = new ArrayList<>();

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setType("insert-video");
			chatMessage.setVideo(raum.getVideo());
			chatMessage.setRaumId(raum.getRaumId());
			chatMessage.setUser(message.getUser());
			chatMessage.setTimestamp(getCurrentTime());
			saveChatMessage(chatMessage);
			for (Long id : raum.getUserIds()) {

				Message responseMessage = new Message();
				responseMessage.setType("insert-new-video");
				responseMessage.setRaumId(raum.raumId);
				responseMessage.setUserId(id);
				responseMessage.setVideo(video);
				responseMessage.setChatMessage(chatMessage);
				responseMessage.setPlayerState(raum.getPlayerState());
				messages.add(responseMessage);
			}

			return messages;
		}
		return new ArrayList<>();

	}

	public List<Message> disconnectClient(Message message) {
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			Long userID = message.getUserId();
			raum.remove(userID);

			List<Message> messages = new ArrayList<>();

			ChatMessage chatMessage = new ChatMessage();

			chatMessage.setMessageText("has left the Room");
			chatMessage.setUser(message.getUser());
			chatMessage.setRaumId(raum.getRaumId());
			chatMessage.setTimestamp(getCurrentTime());
			saveChatMessage(chatMessage);

			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType("update-client");
				responseMessage.setRaumId(raum.raumId);
				responseMessage.setUser(user);
				responseMessage.setChatMessage(chatMessage);
				messages.add(responseMessage);
			}

			return messages;
		}
		return new ArrayList<>();
	}

	public List<Long> saveChatMessage(Message message) {
		if (rooms.containsKey(message.getRaumId())) {
			saveChatMessage(message.getChatMessage());
			return rooms.get(message.getRaumId()).getUserIds();
		}
		return null;
	}

	// TODO raum.saveChatMessage(chatmessage)
	public void saveChatMessage(ChatMessage message) {
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			if (raum.getRaumStatus() == publicRaum) {
				message.setPrivate(publicRaum);
			} else {
				message.setPrivate(privateRaum);
			}
			raum.addChatMessage(message);

		}
	}

	public String randomName() {
		return randomNames[(int) Math.round((randomNames.length - 1) * Math.random())];
	}

	public Raum getRaum(Long raumId) {
		return rooms.get(raumId);
	}

	public List<Message> generateSyncSeekToMessages(Message message) {
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			Video video = message.getVideo();
			List<Message> messages = new ArrayList<>();
			raum.updateTimestamp(video);
			for (Long userId : raum.getUserIds()) {
				Message responseMessage = new Message();
				responseMessage.setUserId(userId);
				responseMessage.setType("seekto-timestamp");
				responseMessage.setVideo(raum.getVideo());
				responseMessage.setRaumId(raum.getRaumId());
				messages.add(responseMessage);
			}

			return messages;

		}

		return null;
	}

	public List<Message> generateSyncPlayToggleMessages(Message message) {

		if (rooms.containsKey(message.getRaumId()) && message.getVideo() != null) {
			Raum raum = rooms.get(message.getRaumId());
			raum.setPlayerState(message.getPlayerState());
			raum.updateTimestamp(message.getVideo());
			List<Message> messages = new ArrayList<>();
			for (Long userId : raum.getUserIds()) {
				Message responseMessage = new Message();
				responseMessage.setType("toggle-play");
				responseMessage.setUserId(userId);
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setPlayerState(raum.getPlayerState());
				responseMessage.setVideo(raum.getVideo());
				messages.add(responseMessage);
			}
			return messages;

		}

		return null;
	}

	public List<Message> generateSyncRoomStateMessages(Message message) {

		if (rooms.containsKey(message.getRaumId())) {

			Raum raum = rooms.get(message.getRaumId());
			raum.setRaumStatus(message.getRaumStatus());
			List<Message> messages = new ArrayList<>();
			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setUser(message.getUser());
			chatMessage.setRaumId(message.getRaumId());
			chatMessage.setTimestamp(getCurrentTime());
			if (raum.getRaumStatus()) {
				chatMessage.setMessageText("Room is now public");
			} else {
				chatMessage.setMessageText("Room is now private");
			}

			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType("sync-roomstate");
				responseMessage.setUser(user);
				responseMessage.setRaumStatus(raum.getRaumStatus());
				responseMessage.setChatMessage(chatMessage);
				messages.add(responseMessage);
			}

			return messages;
		}

		return null;
	}

	public List<Message> generateAssignedAdminMessages(Message message) {
		// type="assigned-as-admin"
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())
				&& exists(message.getRaumId(), message.getAssignedUser().userId)) {
			Raum raum = getRaum(message.getRaumId());
			raum.assignUserAsAdmin(message.getAssignedUser());

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setUser(raum.getUser(message.getUserId()));
			chatMessage.setTimestamp(getCurrentTime());
			chatMessage.setMessageText(
					"assigned " + raum.getUser(message.getAssignedUser().getUserId()).getUserName() + " as Admin");

			Message assignAdminMessage = new Message();
			assignAdminMessage.setType("assigned-as-admin");
			assignAdminMessage.setUser(raum.getUser(message.getAssignedUser().userId));
			assignAdminMessage.setUsers(raum.getUserList());
			assignAdminMessage.setRaumId(raum.getRaumId());
			assignAdminMessage.setChatMessage(chatMessage);

			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {
				if (user.getUserId() != message.getAssignedUser().getUserId()) {
					Message responseMessage = new Message();
					responseMessage.setType("update-client");
					responseMessage.setUser(user);
					responseMessage.setUsers(raum.getUserList());
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setChatMessage(chatMessage);

					responseMessages.add(responseMessage);
				} else {
					responseMessages.add(assignAdminMessage);
				}

			}
			return responseMessages;

		}
		return null;
	}

	public boolean isAdmin(Long raumId, Long userId) {
		Raum raum = getRaum(raumId);
		User user = raum.getUser(userId);
		return user.isAdmin();

	}

	public boolean exists(Long raumId, Long userId) {
		Raum raum = rooms.get(raumId);
		return raum.exists(userId);
	}

	public List<Message> generateToPublicRoomMessages(Message message) {

		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			raum.setRaumStatus(publicRaum);

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setUser(raum.getUser(message.getUserId()));
			chatMessage.setTimestamp(getCurrentTime());
			chatMessage.setMessageText("has changed the Room to Public");

			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType("to-public-room");
				responseMessage.setUser(user);
				responseMessage.setUsers(raum.getUserList());
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setChatMessage(chatMessage);
				responseMessage.setRaumStatus(raum.getRaumStatus());

				responseMessages.add(responseMessage);
			}
			return responseMessages;
		}
		return null;
	}

	public List<Message> generateToPrivateRoomMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			raum.setRaumStatus(privateRaum);
			raum.setAllUsersToAdmins();

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setUser(raum.getUser(message.getUserId()));
			chatMessage.setTimestamp(getCurrentTime());
			chatMessage.setMessageText("has changed the Room to Private");

			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType("to-private-room");
				responseMessage.setUser(user);
				responseMessage.setUsers(raum.getUserList());
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setChatMessage(chatMessage);
				responseMessage.setRaumStatus(raum.getRaumStatus());

				responseMessages.add(responseMessage);
			}
			return responseMessages;
		}

		return null;
	}

	public List<Message> generateKickMessages(Message message) {

		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())
				&& exists(message.getRaumId(), message.getAssignedUser().userId)) {
			Raum raum = getRaum(message.getRaumId());

			refreshRaumId(raum.getRaumId());

			User kickedUser = raum.deleteUser(message.getAssignedUser().getUserId());
			if (kickedUser != null) {
				ChatMessage chatMessage = new ChatMessage();
				chatMessage.setUser(raum.getUser(message.getUserId()));
				chatMessage.setTimestamp(getCurrentTime());
				chatMessage.setMessageText("has kicked :" + kickedUser.getUserName());

				Message kickedUserMessage = new Message();
				kickedUserMessage.setType("kicked-user");
				kickedUserMessage.setUser(kickedUser);
				kickedUserMessage.setChatMessage(chatMessage);

				ArrayList<Message> responseMessages = new ArrayList<>();
				responseMessages.add(kickedUserMessage);

				for (User user : raum.getUserList()) {

					Message responseMesage = new Message();
					responseMesage.setType("update-kick-client");
					responseMesage.setUser(user);
					responseMesage.setRaumId(raum.getRaumId());
					responseMesage.setChatMessage(chatMessage);
					responseMesage.setUsers(raum.getUserList());

					responseMessages.add(responseMesage);
				}
				return responseMessages;
			}
		}

		return null;
	}

	public List<Message> generateRefreshRaumIdMessages(Message message) {

		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());

			refreshRaumId(raum.getRaumId());

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setUser(raum.getUser(message.getUserId()));
			chatMessage.setTimestamp(getCurrentTime());
			chatMessage.setMessageText("has refreshed RoomId to :" + raum.getRaumId());

			ArrayList<Message> responseMessages = new ArrayList<>();
			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType("refresh-raumid");
				responseMessage.setUser(user);
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setChatMessage(chatMessage);

				responseMessages.add(responseMessage);
			}
			return responseMessages;
		}

		return null;
	}

	public void refreshRaumId(Long raumId) {

		Raum raum = rooms.get(raumId);
		rooms.remove(raumId);
		raum.setRaumId(generateRaumId());
		rooms.put(raum.getRaumId(), raum);
	}

	public Message addUserTimeStamp(Message message) {
		if (rooms.containsKey(message.getRaumId())) {

			Raum raum = getRaum(message.getRaumId());
			raum.updateTimestamp(message.getVideo());
			return message;
		}

		return null;
	}

	public List<Message> addVideoToPlaylistMessages(Message message) {

		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			raum.addVideoToPlaylist(message.getVideo());

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setUser(raum.getUser(message.getUserId()));
			chatMessage.setTimestamp(getCurrentTime());
			chatMessage.setMessageText(raum.getVideo().getVideoId());

			ArrayList<Message> responseMessages = new ArrayList<>();
			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType("add-video-to-playlist");
				responseMessage.setUser(user);
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setChatMessage(chatMessage);
				responseMessage.setPlaylist(raum.getPlaylist());
				responseMessages.add(responseMessage);
			}
			return responseMessages;
		}

		return null;
	}

	public ArrayList<Message> processJoinTimeStamp(Message message) {

		if (rooms.containsKey(message.getRaumId()) && message.getVideo() != null) {
			Raum raum = getRaum(message.getRaumId());
				Float ts = message.getVideo().getTimestamp();
				System.out.println("[UPDATE TIMESTAMP " + raum.getVideo().getTimestamp() +" -> " + ts +"]");
				raum.updateTimestamp(raum.getVideo().getVideoId() , ts);
				
				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User joiningUser : raum.getJoiningUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType("seekto-timestamp");
					responseMessage.setUser(joiningUser);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setVideo(raum.getVideo());
					responseMessage.setPlayerState(raum.getPlayerState());
					responseMessages.add(responseMessage);
				}
				return responseMessages;
			}
		return null;
	}
	

	public Message generateRequestSyncTimestampMessages(Message message) {
		
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = getRaum(message.getRaumId());

			ArrayList<User> onlyJoinedUsers = (ArrayList<User>) raum
					.getUserList()
					.stream()
					.filter(anyUser -> !raum.isJoiningUser(anyUser)) //if (raum.getTimeStampsCount() >= Math.min(maxUpdateJoinClients, raum.getUsersInRoomCount() - raum.getJoiningUserList().size())) {
					.collect(Collectors.toList());
			System.out.println("[JOINED USERS: " + onlyJoinedUsers + " - SIZE: " + onlyJoinedUsers.size() +"]");
			if(onlyJoinedUsers.size() > 0) {
				Message responseMessage = new Message();
				responseMessage.setType("request-sync-timestamp");
				responseMessage.setUser(onlyJoinedUsers.get(0));
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setPlaylist(raum.getPlaylist());
							
				return responseMessage;
			}else {
				raum.clearJoiningUsers();
				System.out.println("[no joined users found - no sync - clearing joining users]");
				return null;
			}
			
		}
				
		return null;
	}
	
	public void clearJoiningUsers(Long raumId) {
		if(rooms.containsKey(raumId)) {
			Raum raum = getRaum(raumId);
			raum.clearJoiningUsers();
		}
	}
	
	public void clearTimestamps(Long raumId) {
		if(rooms.containsKey(raumId)) {
			Raum raum = getRaum(raumId);
			raum.cleartimestamps();
		}
	}
}
