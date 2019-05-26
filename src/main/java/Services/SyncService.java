package Services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.haihuynh.springbootwebsocketchatapplication.configuration.WebSocketConfiguration;

import messages.ChatMessage;
import messages.Message;

@Service
public class SyncService {

	public static Boolean publicRaum = false;
	public static Boolean privateRaum = true;

	public static String defaultVideo = "OmEn2Iclb8U";

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

	public String getCurrenTime() {
		return LocalTime.now().toString().substring(0, 8);
	}

	public void saveRoom(Raum raum) {
		rooms.put(raum.getRaumId(), raum);

	}

	public Message createRaum(Message message) {
		try {
			Long userID = message.getUserId();
			Raum raum = new Raum();
			//DEBUG ESTMAL
			raum.setVideoLink(defaultVideo);
			//DEBUG
			raum.setRaumStatus(message.getRaumStatus());
			saveRoom(raum);

			
			String name = randomName();
			User user = new User();
			user.setUserName(name);
			user.setUserId(message.getUserId());
			user.setAdmin(true);			
			raum.addUser(message.getUserId(), user);
			raum.addTimeStamp(userID, 0L);
			
			WebSocketConfiguration
			.registryInstance
			.enableSimpleBroker("/"+userID);
			
			Message createRaumMessage = new Message();
			createRaumMessage.setType("create-room");
			createRaumMessage.setUser(user);
			createRaumMessage.setRaumId(raum.raumId);
			createRaumMessage.setUsers(raum.getUserList());
			createRaumMessage.setVideoLink(raum.getVideoLink());
			createRaumMessage.setRaumStatus(raum.getRaumStatus());
			ChatMessage chatMessage = new ChatMessage();
			if(raum.getRaumStatus() == publicRaum) {
				chatMessage.setPrivate(publicRaum);
			}else {
				chatMessage.setPrivate(privateRaum);
			}
			chatMessage.setMessageText("has created the Room");
			chatMessage.setTimestamp(getCurrenTime());
			chatMessage.setRaumId(raum.getRaumId());
			chatMessage.setUser(user);
			
			saveChatMessage(chatMessage);
			createRaumMessage.setChatMessages(raum.getChatMessages());
			
			return createRaumMessage;
		}catch(Exception e) {
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

			raum.addUser(message.getUserId(), user);
			List<Message> messages = new ArrayList<>();

			Message joinMessage = new Message();
			joinMessage.setType("join-room");
			joinMessage.setUser(user);
			joinMessage.setUsers(raum.getUserList());
			joinMessage.setRaumId(raum.getRaumId());
			joinMessage.setVideoLink(raum.getVideoLink());
			joinMessage.setTimeStamp(getTimeStamp(raum.getRaumId()));
			joinMessage.setRaumStatus(raum.getRaumStatus());
			joinMessage.setPlayerState(raum.getPlayerState());

			ChatMessage chatMessage = new ChatMessage();
			if(raum.getRaumStatus() == publicRaum) {
				chatMessage.setPrivate(publicRaum);
			}else {
				chatMessage.setPrivate(privateRaum);
			}
			chatMessage.setMessageText("has joined the Room");
			chatMessage.setTimestamp(getCurrenTime());
			chatMessage.setUser(user);
			chatMessage.setRaumId(raum.getRaumId());

			saveChatMessage(chatMessage);
			if(raum.getRaumStatus() == publicRaum) {
				joinMessage.setChatMessages(raum.getPublicChatMessages());
			}else {
				joinMessage.setChatMessages(raum.getChatMessages());
			}
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

	public Long getTimeStamp(Long raumId) {
		return getRaum(raumId).getTimeStamps().values().stream().collect(Collectors.toList()).get(0);
	}

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
		if (rooms.containsKey(message.getRaumId()) && message.getVideoLink() != null) {
			Raum raum = rooms.get(message.getRaumId());
			String videoLink = message.getVideoLink();
			raum.setVideoLink(videoLink);
			List<Message> messages = new ArrayList<>();

			ChatMessage chatMessage = new ChatMessage();
			if(raum.getRaumStatus() == publicRaum) {
				chatMessage.setPrivate(publicRaum);
			}else {
				chatMessage.setPrivate(privateRaum);
			}
			chatMessage.setMessageText(videoLink);
			chatMessage.setRaumId(raum.getRaumId());
			chatMessage.setUser(message.getUser());
			chatMessage.setTimestamp(getCurrenTime());
			saveChatMessage(chatMessage);
			for (Long id : raum.getUserIds()) {

				Message responseMessage = new Message();
				responseMessage.setType("insert-new-video");
				responseMessage.setRaumId(raum.raumId);
				responseMessage.setUserId(id);
				responseMessage.setVideoLink(videoLink);
				responseMessage.setChatMessage(chatMessage);
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
			chatMessage.setTimestamp(getCurrenTime());
			if(raum.getRaumStatus() == publicRaum) {
				chatMessage.setPrivate(publicRaum);
			}else {
				chatMessage.setPrivate(privateRaum);
			}
			saveChatMessage(chatMessage);

			for (User user: raum.getUserList()) {

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

	public Message addUserTimeStamp(Message message) {
		Raum raum = rooms.get(message.getRaumId());
		Long userID = message.getUserId();
		Long time = message.getTimeStamp();
		raum.addTimeStamp(userID, time);

		Message responseMessage = new Message();
		// responseMessage.getType("video-timestamp");
		return null;
	}

	public List<Long> saveChatMessage(Message message) {
		if (rooms.containsKey(message.getRaumId())) {
			saveChatMessage(message.getChatMessage());
			return rooms.get(message.getRaumId()).getUserIds();
		}
		return null;
	}

	public void saveChatMessage(ChatMessage message) {
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			if(raum.getRaumStatus() == publicRaum) {
				message.setPrivate(publicRaum);
			}else {
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
			Long timeStamp = message.getTimeStamp();
			List<Message> messages = new ArrayList<>();
			raum.addTimeStamp(message.getUserId(), timeStamp);
			for (Long userId : raum.getUserIds()) {
				raum.addTimeStamp(userId, timeStamp);
				Message responseMessage = new Message();
				responseMessage.setUserId(userId);
				responseMessage.setType("seekto-timestamp");
				responseMessage.setTimeStamp(timeStamp);
				responseMessage.setRaumId(raum.getRaumId());
				messages.add(responseMessage);
			}

			return messages;

		}

		return null;
	}

	public List<Message> generateSyncPlayToggleMessages(Message message) {

		if (rooms.containsKey(message.getRaumId()) && message.getTimeStamp() >= 0) {
			Raum raum = rooms.get(message.getRaumId());
			raum.setPlayerState(message.getPlayerState());
			List<Message> messages = new ArrayList<>();

			for (Long userId : raum.getUserIds()) {
				Message responseMessage = new Message();
				responseMessage.setType("toggle-play");
				responseMessage.setUserId(userId);
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setPlayerState(raum.getPlayerState());
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
			if(raum.getRaumStatus() == publicRaum) {
				chatMessage.setPrivate(publicRaum);
			}else {
				chatMessage.setPrivate(privateRaum);
			}
			chatMessage.setUser(message.getUser());
			chatMessage.setRaumId(message.getRaumId());
			chatMessage.setTimestamp(getCurrenTime());
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
				&& exists(message.getRaumId(), message.getAssignedAdmin().userId)) {
			Raum raum = getRaum(message.getRaumId());
			raum.assignUserAsAdmin(message.getAssignedAdmin());

			ChatMessage chatMessage = new ChatMessage();
			if(raum.getRaumStatus() == publicRaum) {
				chatMessage.setPrivate(publicRaum);
			}else {
				chatMessage.setPrivate(privateRaum);
			}
			chatMessage.setUser(raum.getUser(message.getUserId()));
			chatMessage.setTimestamp(getCurrenTime());
			chatMessage.setMessageText("assigned " + raum.getUser(message.getUserId()).getUserName() + " as Admin");

			Message assignAdminMessage = new Message();
			assignAdminMessage.setType("assigned-as-admin");
			assignAdminMessage.setUser(raum.getUser(message.getAssignedAdmin().userId));
			assignAdminMessage.setUsers(raum.getUserList());
			assignAdminMessage.setRaumId(raum.getRaumId());
			assignAdminMessage.setChatMessage(chatMessage);

			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {
				if (user.getUserId() != message.getAssignedAdmin().getUserId()) {
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
			if(raum.getRaumStatus() == publicRaum) {
				chatMessage.setPrivate(publicRaum);
			}else {
				chatMessage.setPrivate(privateRaum);
			}
			chatMessage.setUser(raum.getUser(message.getUserId()));
			chatMessage.setTimestamp(getCurrenTime());
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
			if(raum.getRaumStatus() == publicRaum) {
				chatMessage.setPrivate(publicRaum);
			}else {
				chatMessage.setPrivate(privateRaum);
			}
			chatMessage.setUser(raum.getUser(message.getUserId()));
			chatMessage.setTimestamp(getCurrenTime());
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

}
