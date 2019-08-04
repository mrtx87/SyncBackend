package Services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.haihuynh.springbootwebsocketchatapplication.configuration.WebSocketConfiguration;

import Apis.SupportedApi;
import messages.ChatMessage;
import messages.Message;
import messages.MessageTypes;
import messages.MessageTypesObject;
import messages.ToastrMessage;
import messages.ToastrMessageTypes;
import messages.ToastrMessageTypesObject;
import messages.Video;

@Service
public class SyncService {

	public static int maxUpdateJoinClients = 3;
	public static Boolean publicRaum = false;
	public static Boolean privateRaum = true;
	
	public static ArrayList<SupportedApi> supportedApis;
	
	public static enum AddVideoMode {
		NEXT,
		CURRENT,
		LAST
	}
	
	public SyncService() {
		
		supportedApis = new ArrayList<SupportedApi>();
		final SupportedApi youtubeApi = new SupportedApi();
		youtubeApi.setId(1);
		youtubeApi.setName("youtube");
		youtubeApi.setIconUrl("assets/yt_icon_rgb.png");
		//youtubeApi.setApiKey("AIzaSyBJKPvOKMDqPzaR-06o1-Mfixvq2CRlS5M");
		youtubeApi.setApiKey("AIzaSyAYkdQEFOAjHZHRSQiREI4qO28FxpOUtZM");
		youtubeApi.setScript("https://www.youtube.com/iframe_api");
		
		final SupportedApi dailymotionApi = new SupportedApi();
		dailymotionApi.setId(2);
		dailymotionApi.setName("dailymotion");
		dailymotionApi.setIconUrl("assets/logo_dailymotion.png");
		dailymotionApi.setApiKey("a8f15ba0bbd9f9552be9");
		dailymotionApi.setScript("https://api.dmcdn.net/all.js");

		
		final SupportedApi vimeoApi = new SupportedApi();
		vimeoApi.setId(3);
		vimeoApi.setName("vimeo");
		vimeoApi.setIconUrl("assets/vimeo_icon.png");
		vimeoApi.setApiKey("be6fcf0320704697342bd26a54557395");
		vimeoApi.setScript("https://player.vimeo.com/api/player.js.");

		
		supportedApis.add(youtubeApi);
		supportedApis.add(dailymotionApi);
		supportedApis.add(vimeoApi);
	}

	public  Video defaultVideo = new Video(generateVideoObjectId(), "WBG7TFLj4YQ", 0.0f,"Unter Wasser: Megacitys in Gefahr | Doku | ARTE\r\n", "", new Date(), "nothumbail", 1);

	
	HashMap<String, Raum> rooms = new HashMap<>();
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
	
	@Autowired
	private SimpMessagingTemplate messageService;


	public HashMap<String, Raum> getRooms() {
		return rooms;
	}

	public void setRooms(HashMap<String, Raum> rooms) {
		this.rooms = rooms;
	}

	public static String generateRaumId() {


		Long nanoseconds = System.nanoTime();
		String shorted_nano_string = (nanoseconds + "").substring(3); 
		StringBuilder sb = new StringBuilder();
		sb.append(getRandomChar()).append(getRandomChar()).append(getRandomChar()).append(shorted_nano_string).append(getRandomChar()).append(getRandomChar()).append(getRandomChar());
		//System.out.println(sb.toString());
		
		
		
		return sb.toString();
	}
	
	private static char getRandomChar() {
		// 65-90, 97-122
		return (Math.random() > 0.5d) ? (char) (Math.random()*26+65) : (char) (Math.random()*26+97);
	}

	public String getCurrentTime() {
		return LocalTime.now().toString().substring(0, 8);
	}

	public void saveRoom(Raum raum) {
		rooms.put(raum.getRaumId(), raum);

	}
	
	public ChatMessage createAndSaveChatMessage(User user, String raumId, String messageText, Video video, boolean isPlaylist) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMessageText(messageText);
		chatMessage.setTimestamp(getCurrentTime());
		chatMessage.setRaumId(raumId);
		chatMessage.setUser(user);
		if(video != null) {
			if(isPlaylist) {
				chatMessage.setType("insert-playlist");
			}else {
				chatMessage.setType("insert-video");
			}
			chatMessage.setVideo(video);
		}else {
			chatMessage.setType("insert-user-message");
		}
		saveChatMessage(chatMessage);
		return chatMessage;
	}
	
	
	public ToastrMessage createAndSaveToastrMessage(String type, String messageText, Raum raum) {
		return createAndSaveToastrMessage(type, messageText, raum, false);
	}
	
	public ToastrMessage createAndSaveToastrMessage(String type, String messageText, Raum raum, boolean onlyLogging) {
		ToastrMessage toastrMsg = new ToastrMessage(raum.getSizeOfTaostrMessages(), type, messageText, raum.getRaumId(), LocalDateTime.now(), onlyLogging);
		raum.addToastrMessage(toastrMsg);
		return toastrMsg;
	}

	public Message createRaum(Message message) {
		try {
			String userId = message.getUserId();
			Raum raum = new Raum();
			raum.setCreatedAt(getCurrentTime());
			//raum.setVideo(defaultVideo.clone());
			raum.setRaumStatus(message.getRaumStatus());
			raum.setPlayerState(2);
			saveRoom(raum);
			raum.setCurrentPlaybackRate(1);
			raum.setTitle("Raum#" + raum.getRaumId());
			raum.setDescription("new created room");
			String name = randomName();
			User user = new User();
			user.setUserName(name);
			user.setUserId(message.getUserId());
			user.setAdmin(true);
			user.setMute(false);
			raum.addUser(user);
			raum.addToAllTimeUsers(user);

			WebSocketConfiguration.registryInstance.enableSimpleBroker("/" + userId);

			Message createRaumMessage = new Message();
			createRaumMessage.setType("create-room");
			createRaumMessage.setUser(user);
			createRaumMessage.setRaumId(raum.raumId);
			createRaumMessage.setUsers(raum.getUserList());
			createRaumMessage.setCurrentPlaybackRate(raum.getCurrentPlaybackRate());			
			createRaumMessage.setRaumTitle(raum.getTitle());
			createRaumMessage.setRaumDescription(raum.getDescription());
			createRaumMessage.setRaumStatus(raum.getRaumStatus());
			createRaumMessage.setPlayerState(raum.getPlayerState());
			//createAndSaveChatMessage(user, raum.getRaumId(), "has created the Room", null, false);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.CREATE_ROOM, user.userName + " has created the Room", raum);
			createRaumMessage.setToastrMessage(toastrMessage);

			return createRaumMessage;
		} catch (Exception e) {
			return null;
		}

	}

	public List<Message> joinRaum(Message message) {

		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			
			if(!raum.existsOnKickedUsersList(message.getUserId())) {
				User user;	
				if(raum.hasBeenToRaum(message.getUserId())) {
					user = raum.getUserFromAllTimeUsers(message.getUserId());
				}else {
					String name = randomName();
					user = new User();
					user.setUserName(name);
					user.setUserId(message.getUserId());
					user.setMute(false);
					user.setAdmin(false);

					raum.addToAllTimeUsers(user);
	
				}
				raum.addUser(user);
				raum.addJoiningUser(user);
					
				List<Message> messages = new ArrayList<>();
				Message joinMessage = new Message();
				joinMessage.setType("join-room");
				joinMessage.setUser(user);
				joinMessage.setUsers(raum.getUserList());
				joinMessage.setRaumTitle(raum.getTitle());
				joinMessage.setRaumDescription(raum.getDescription());
				joinMessage.setRaumId(raum.getRaumId());
				joinMessage.setVideo(raum.getCurrentVideo());
				joinMessage.setRaumStatus(raum.getRaumStatus());
				joinMessage.setPlayerState(raum.getPlayerState());
				joinMessage.setCurrentPlaybackRate(raum.getCurrentPlaybackRate());
				//ChatMessage chatMessage = createAndSaveChatMessage(user, raum.getRaumId(), "has joined the Room", null, false);
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.JOIN_ROOM, user.userName + " has joined the Room", raum);
				joinMessage.setToastrMessage(toastrMessage);
	
				WebSocketConfiguration.registryInstance.enableSimpleBroker("/" + message.getUserId());
	
				for (String id : raum.getUserIds()) {
					if (!user.userId.equals(id)) {
	
						Message responseMessage = new Message();
						responseMessage.setType("update-client");
						responseMessage.setRaumId(raum.raumId);
						responseMessage.setUserId(id);
						responseMessage.setToastrMessage(toastrMessage);
						responseMessage.setUsers(raum.getUserList());
	
						messages.add(responseMessage);
	
					} else {
						messages.add(joinMessage);
					}
				}
	
				return messages;
			}
		}
		return null;
	}

	public ArrayList<RaumDTO> getPublicRooms(String userId) {

		return (ArrayList<RaumDTO>) rooms
				.values()
				.stream()
				.filter(raum -> raum.getRaumStatus() == publicRaum)
				.filter(raum -> !raum.existsOnKickedUsersList(userId))
				.map(raum -> RaumMapper.createRaumDTO(raum))
				.collect(Collectors.toList());
	}
	

	public List<Message> addAndShareNewVideo(Message message) {
		if (rooms.containsKey(message.getRaumId()) && message.getVideo() != null) {
			Raum raum = rooms.get(message.getRaumId());
			Video video = message.getVideo();
			raum.setCurrentVideo(video);
			raum.setPlayerState(1);
			List<Message> messages = new ArrayList<>();

			ChatMessage chatMessage = new ChatMessage();
			chatMessage.setType("insert-video");
			chatMessage.setVideo(raum.getCurrentVideo());
			chatMessage.setRaumId(raum.getRaumId());
			chatMessage.setUser(message.getUser());
			chatMessage.setTimestamp(getCurrentTime());
			saveChatMessage(chatMessage);
			for (String id : raum.getUserIds()) {

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
			String userId = message.getUserId();
			User removedUser = raum.remove(userId);

			List<Message> messages = new ArrayList<>();
			//ChatMessage chatMessage = createAndSaveChatMessage(removedUser, raum.getRaumId(), "has left the Room", null, false);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.DISCONNECT, removedUser.userName + " has left the Room", raum);
			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType("update-client");
				responseMessage.setRaumId(raum.raumId);
				responseMessage.setUser(user);
				responseMessage.setToastrMessage(toastrMessage);
				responseMessage.setUsers(raum.getUserList());
				messages.add(responseMessage);
			}

			return messages;
		}
		return new ArrayList<>();
	}
	
	public ArrayList<Message> generateSaveChatMessageResponse(Message message) {
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = getRaum(message.getRaumId());
			
			if(raum.exists(message.getUserId())) {
				User sendingUser = raum.getUser(message.getUserId());
				if(!sendingUser.isMute()) {					
					ChatMessage chatMessage = createAndSaveChatMessage(sendingUser, raum.getRaumId(), message.getChatMessage().getMessageText(), null, false);
					ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ONLY_LOGGING, message.getUserName() + " send ChatMessge " + chatMessage.getMessageText(), raum, true);

					ArrayList<Message> responseMessages = new ArrayList<Message>();
					for(User user : raum.getUserList()){
						Message responseMessage = new Message();
						responseMessage.setUser(user);
						responseMessage.setType("chat-message");
						responseMessage.setRaumId(raum.getRaumId());
						responseMessage.setChatMessage(chatMessage);
						responseMessage.setToastrMessage(toastrMessage);
						
						responseMessages.add(responseMessage);
					}
					return responseMessages;
					}				
			}
		}
		return null;
	}

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

	public Raum getRaum(String raumId) {
		return rooms.get(raumId);
	}

	public List<Message> generateSyncSeekToMessages(Message message) {
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			Video video = message.getVideo();
			List<Message> messages = new ArrayList<>();
			raum.updateTimestamp(video);
			for (String userId : raum.getUserIds()) {
				Message responseMessage = new Message();
				responseMessage.setUserId(userId);
				responseMessage.setType("seekto-timestamp");
				responseMessage.setVideo(raum.getCurrentVideo());
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ONLY_LOGGING, message.getUserName() + " toggled play", raum, true);
			List<Message> messages = new ArrayList<>();
			for (String userId : raum.getUserIds()) {
				Message responseMessage = new Message();
				responseMessage.setType("toggle-play");
				responseMessage.setUserId(userId);
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setPlayerState(raum.getPlayerState());
				responseMessage.setVideo(raum.getCurrentVideo());
				responseMessage.setToastrMessage(toastrMessage);
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

			//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(message.getUserId()), raum.getRaumId(),"assigned " + raum.getUser(message.getAssignedUser().getUserId()).getUserName() + " as Admin", null, falsetz);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ASSIGNED_AS_ADMIN,"assigned " + raum.getUser(message.getAssignedUser().getUserId()).getUserName() + " as Admin", raum);
			Message assignAdminMessage = new Message();
			assignAdminMessage.setType("assigned-as-admin");
			assignAdminMessage.setUser(raum.getUser(message.getAssignedUser().userId));
			assignAdminMessage.setUsers(raum.getUserList());
			assignAdminMessage.setRaumId(raum.getRaumId());
			assignAdminMessage.setToastrMessage(toastrMessage);
			
			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {
				if (user.getUserId() != message.getAssignedUser().getUserId()) {
					Message responseMessage = new Message();
					responseMessage.setType("update-client");
					responseMessage.setUser(user);
					responseMessage.setUsers(raum.getUserList());
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setToastrMessage(toastrMessage);

					responseMessages.add(responseMessage);
				} else {
					responseMessages.add(assignAdminMessage);
				}

			}
			return responseMessages;

		}
		return null;
	}

	public boolean isAdmin(String raumId, String userId) {
		Raum raum = getRaum(raumId);
		if(exists(raumId, userId)) {
		User user = raum.getUser(userId);
			return user.isAdmin();
		}
		
		return false;

	}

	public boolean exists(String raumId, String userId) {
		Raum raum = rooms.get(raumId);
		return raum.exists(userId);
	}

	public List<Message> generateToPublicRoomMessages(Message message) {

		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			raum.setRaumStatus(publicRaum);

			//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(message.getUserId()), raum.getRaumId(),"has changed the Room to Public", null, falsetz);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.TO_PUBLIC_ROOM, message.getUserName() + " has changed the Room to Public", raum);

			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType("to-public-room");
				responseMessage.setUser(user);
				responseMessage.setUsers(raum.getUserList());
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setToastrMessage(toastrMessage);
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
			//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(message.getUserId()), raum.getRaumId(), "has changed the Room to Private", null, falsetzt);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.TO_PRIVATE_ROOM, message.getUserName() + " has changed the Room to Private", raum);
			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType("to-private-room");
				responseMessage.setUser(user);
				responseMessage.setUsers(raum.getUserList());
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setToastrMessage(toastrMessage);
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
				raum.addKickedUser(kickedUser);
				
				//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(message.getUserId()), raum.getRaumId(), "has kicked " + kickedUser.getUserName(), null, falzse);
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.KICKED_USER, message.getUserName() + " has kicked " + kickedUser.getUserName()  , raum);

				Message kickedUserMessage = new Message();
				kickedUserMessage.setType("kicked-user");
				kickedUserMessage.setUser(kickedUser);
				kickedUserMessage.setToastrMessage(toastrMessage);

				ArrayList<Message> responseMessages = new ArrayList<>();
				responseMessages.add(kickedUserMessage);

				for (User user : raum.getUserList()) {

					Message responseMessage = new Message();
					responseMessage.setType("update-kick-client");
					responseMessage.setUser(user);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setToastrMessage(toastrMessage);
					responseMessage.setUsers(raum.getUserList());
					responseMessage.setKickedUsers(raum.getKickedUsersList());

					responseMessages.add(responseMessage);
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
			//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(message.getUserId()), raum.getRaumId(), "has refreshed RoomId to :" + raum.getRaumId(), null, fztzalse);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.REFRESH_ROOM_ID, message.getUserName() + " has refreshed RoomId: " + raum.getRaumId(), raum);

			ArrayList<Message> responseMessages = new ArrayList<>();
			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType("refresh-raumid");
				responseMessage.setUser(user);
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setToastrMessage(toastrMessage);

				responseMessages.add(responseMessage);
			}
			return responseMessages;
		}

		return null;
	}

	public void refreshRaumId(String raumId) {

		Raum raum = rooms.get(raumId);
		rooms.remove(raumId);
		raum.setRaumId(generateRaumId());
		rooms.put(raum.getRaumId(), raum);
	}
	
	public static String generateVideoObjectId() {
		return "v_" + System.nanoTime() + "r_" + ((char) (97 + (int) (Math.random() * 24))) + (int) (Math.random() * 10000) + ((char) (97 + (int) (Math.random() * 24)));
	}

	public Message addUserTimeStamp(Message message) {
		if (rooms.containsKey(message.getRaumId())) {

			Raum raum = getRaum(message.getRaumId());
			raum.updateTimestamp(message.getVideo());
			return message;
		}

		return null;
	}

	public List<Message> addVideoToPlaylistMessages(Message message, AddVideoMode mode) {

		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			Video newCurrentVideo = null;
			Video playlistVideo = message.getPlaylistVideo();
			playlistVideo.setId(generateVideoObjectId());
			switch(mode) {
				case CURRENT : {
					newCurrentVideo = raum.addVideoToPlaylistAsCurrent(playlistVideo);
				}break;
				case NEXT: {
					newCurrentVideo = raum.addVideoToPlaylistAsNext(playlistVideo);
				}break;
				case LAST : {
					newCurrentVideo = raum.addVideoToPlaylist(playlistVideo);
				}break;
			}
			
			//raum.addVideoToPlaylist(message.getPlaylistVideo());
			//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(message.getUserId()), raum.getRaumId(), null, message.getPlaylistVideo(), faltzse);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ADDED_VIDEO_TO_PLAYLIST, message.getUserName() + " has added Video: " + playlistVideo.getTitle(), raum);

			ArrayList<Message> responseMessages = new ArrayList<>();
			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType("update-playlist");
				responseMessage.setUser(user);
				if(newCurrentVideo != null) {
					raum.addToHistory(newCurrentVideo);
					responseMessage.setVideo(newCurrentVideo);
					responseMessage.setPlayerState(1);
					responseMessage.setCurrentPlaybackRate(1);
					raum.setCurrentPlaybackRate(1);
				}else {
					responseMessage.setCurrentPlaybackRate(raum.getCurrentPlaybackRate());
				}
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setToastrMessage(toastrMessage);
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
				System.out.println("[UPDATE TIMESTAMP " + raum.getCurrentVideo().getTimestamp() +" -> " + ts +"]");
				raum.updateTimestamp(raum.getCurrentVideo().getVideoId() , ts);
				
				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User joiningUser : raum.getJoiningUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType("seekto-timestamp");
					responseMessage.setUser(joiningUser);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setVideo(raum.getCurrentVideo());
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
				return responseMessage;
			}else {
				raum.clearJoiningUsers();
				System.out.println("[no joined users found - no sync - clearing joining users]");
				return null;
			}
			
		}
				
		return null;
	}
	
	public void clearJoiningUsers(String raumId) {
		if(rooms.containsKey(raumId)) {
			Raum raum = getRaum(raumId);
			raum.clearJoiningUsers();
		}
	}
	

	public List<Message> generateUpdateTitleAndDescriptionMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());

			raum.setDescription(message.getRaumDescription());
			raum.setTitle(message.getRaumTitle());
			//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(message.getUserId()), raum.getRaumId(), "has refreshed RoomId to :" + raum.getRaumId(), null);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.UPDATE_TITLE_AND_DESCRIPTION, message.getUserName() + "changed room title & description: \n " + message.getRaumTitle() +"\n" + message.getRaumDescription(), raum);

			ArrayList<Message> responseMessages = new ArrayList<>();
			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType("update-title-and-description");
				responseMessage.setUser(user);
				responseMessage.setRaumId(raum.getRaumId());
				responseMessage.setRaumTitle(raum.getTitle());
				responseMessage.setRaumDescription(raum.getDescription());
				//responseMessage.setChatMessage(chatMessage);
				responseMessage.setToastrMessage(toastrMessage);
				responseMessages.add(responseMessage);
			}
			return responseMessages;
		}

		return null;
	}


	public List<Message> generateRemoveVideoFromPlaylistMessages(Message message) {

		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			
			Video removedVideo = raum.removeVideoFromPlaylist(message.getPlaylistVideo());
			if(removedVideo != null) {
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.REMOVE_VIDEO_PLAYLIST, message.getUserName() + " has removed Video: " + removedVideo.getTitle(), raum);

				if(raum.currentVideo.equalsTo(removedVideo)) {
					if(!raum.playlist.isEmpty()) {
						if(raum.getCurrentVideoIndex() >= raum.getPlaylist().size()) {
							raum.setCurrentVideo(raum.playlist.get(raum.getCurrentVideoIndex()-1));
						}else {
							raum.setCurrentVideo(raum.playlist.get(raum.getCurrentVideoIndex()));
						}
						
						return createRemoveVideoFromPlaylistMessages(raum, removedVideo, raum.getCurrentVideo(), toastrMessage);

					}else {
						raum.setCurrentVideo(null);
						return createRemoveVideoFromPlaylistMessages(raum, removedVideo, new Video(), toastrMessage);

					}
				}else { // REMOVED AND CURRENT VIDEO ARE NOT EQUAL
					raum.setCurrentVideo(raum.getCurrentVideo());
					return createRemoveVideoFromPlaylistMessages(raum, removedVideo, null, toastrMessage);
				}
							
			}
						
		}

		return null;
	}
	
	private List<Message> createRemoveVideoFromPlaylistMessages(Raum raum, Video removedVideo, Video currentVideo, ToastrMessage toastrMessage) {
		ArrayList<Message> responseMessages = new ArrayList<>();
		for (User user : raum.getUserList()) {
			Message responseMessage = new Message();
			responseMessage.setType("remove-video-playlist");
			responseMessage.setUser(user);
			responseMessage.setRaumId(raum.getRaumId());
			responseMessage.setPlaylistVideo(removedVideo);
			responseMessage.setToastrMessage(toastrMessage);
			if(currentVideo != null) {
				responseMessage.setVideo(currentVideo);
			}else {
				responseMessage.setVideo(null);
			}
			responseMessages.add(responseMessage);
		}
		return responseMessages;
	}
	
	public boolean raumExists(String raumId) {
		return rooms.containsKey(raumId);
	}
	
	public ArrayList<Video> getCopyOfRaumPlaylist(String raumId) {
		if(raumExists(raumId)) {
			return (ArrayList<Video>) getRaum(raumId).getPlaylist().stream().map(vid -> vid.clone()).collect(Collectors.toList());
		}
		return null;
	}
	
	
	public ArrayList<Video> getRaumPlaylist(String raumId) {
		if(raumExists(raumId)) {
			return getRaum(raumId).getPlaylist();
		}
		return null;
	}

	public boolean importPlaylist(String raumId, ImportedPlaylist importedPlaylist, String userId) {
		if(raumExists(raumId)){ 
			Raum raum = getRaum(raumId);
			
			if(raum.importPlaylist(importedPlaylist)) {
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.IMPORTED_PLAYLIST, getRaum(raumId).getUser(userId).getUserName()+  ((importedPlaylist.mode == 0) ?  " has imported " : " has integrated ")+  importedPlaylist.title, raum);

				Video playlistTitleTransferVideo = new Video();
				playlistTitleTransferVideo.setTitle(importedPlaylist.getTitle());
				//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(userId), raumId, null, playlistTitleTransferVideo, true);
				raum.setCurrentVideo(raum.getPlaylistVideo(0));
				
				Message responseMessage = new Message();
				responseMessage.setType("update-playlist");
				responseMessage.setRaumId(raumId);
				raum.setPlayerState(1);
				responseMessage.setPlayerState(1);		
				responseMessage.setVideo(raum.getCurrentVideo());
				raum.setCurrentPlaybackRate(1);
				responseMessage.setCurrentPlaybackRate(1);
				//responseMessage.setChatMessage(chatMessage);
				responseMessage.setToastrMessage(toastrMessage);
				
			for(User user: getRaum(raumId).getUserList()) {
					responseMessage.setUser(user);
					this.messageService.convertAndSend("/chat/" + user.getUserId(), responseMessage);
				}
				return true;
			}
		}
		return false;
	}

	public List<Message> generateSwitchPlaylistVideoMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			Video video = raum.switchCurrentPlaylistVideo(message.getPlaylistVideo());
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ONLY_LOGGING, message.getUserName() + " switched Video to " + video.getTitle(), raum, true);

			raum.setCurrentPlaybackRate(1);
			if(video != null)	{	
				video.setTimestamp(0f);
				raum.addToHistory(video);
				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType("switch-video");
					responseMessage.setUser(user);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setVideo(raum.getCurrentVideo());
					raum.setPlayerState(1);
					responseMessage.setPlayerState(1);
					responseMessage.setCurrentPlaybackRate(raum.getCurrentPlaybackRate());
					responseMessage.setToastrMessage(toastrMessage);
					responseMessages.add(responseMessage);
				}
				return responseMessages;
			}
		}
		return null;
	}

	static int NO_LOOP = 0;
	static int LOOP_ALL = 1;
	static int LOOP_SINGLE = 2;
	
	public List<Message> processAutoNextPlaylistVideo(Message message) {
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = getRaum(message.getRaumId());
			int requests = raum.countNextVidRequest();
			Video newCurrentVid = null;
			if(requests >= raum.getSize()/3) {
				raum.setCountingNextVidRequests(0);

				if(raum.getLoop() == NO_LOOP) { //KEINE LOOP
					if(raum.isRandomOrder()) { //ZUFÄLLIG?
						newCurrentVid = raum.getRandomPlaylistVideo();
					}else { // NICHT ZUFÄLLIG
						if(raum.getIndexOfVideo(raum.getCurrentVideo()) < raum.getPlaylist().size()) { //nicht letztes video
							newCurrentVid = raum.getPlaylistVideo(raum.getIndexOfVideo(raum.getCurrentVideo()) + 1);
						}
						//wenn letztes video -> nichts machen
					}
				}else if(raum.getLoop() == LOOP_ALL) { // ALLES LOOPEN
					if(raum.getIndexOfVideo(raum.getCurrentVideo()) < raum.getPlaylist().size()-1) { //nicht letztes video
						newCurrentVid = raum.getPlaylistVideo(raum.getIndexOfVideo(raum.getCurrentVideo()) + 1);
					}else { //letztes video
						newCurrentVid = raum.getPlaylistVideo(0);
					}
				}else if(raum.getLoop() == LOOP_SINGLE) {
					newCurrentVid = raum.getCurrentVideo(); // ANY RANDOM
				}
				
				newCurrentVid.setTimestamp(0f);
				message.setPlaylistVideo(newCurrentVid);
				return generateSwitchPlaylistVideoMessages(message);
			}
			
		}
		return null;
	}

	public List<Message> generateTogglePlaylistLoopMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			int loop  = raum.toggleLoop();
				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType("toggle-playlist-loop");
					responseMessage.setUser(user);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setVideo(raum.getCurrentVideo());
					responseMessage.setLoop(loop);
					responseMessages.add(responseMessage);
				}
				return responseMessages;
		}
		return null;
	}

	public List<Message> generateTogglePlaylistRunningOrderMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			boolean randomOrder  = raum.toggleRandomOrder();
				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType("toggle-playlist-running-order");
					responseMessage.setUser(user);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setVideo(raum.getCurrentVideo());
					responseMessage.setRandomOrder(randomOrder);
					responseMessages.add(responseMessage);
				}
				return responseMessages;
		}
		return null;
	}

	public List<Message> generateChangePlaybackRateMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			raum.setCurrentPlaybackRate(message.getCurrentPlaybackRate());
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.CHANGED_PLAYBACK_RATE, message.getUserName() + "changed Playbackrate to " + message.getCurrentPlaybackRate(), raum);
				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType("change-playback-rate");
					responseMessage.setUser(user);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setVideo(raum.getCurrentVideo());
					responseMessage.setCurrentPlaybackRate(raum.getCurrentPlaybackRate());
					responseMessage.setToastrMessage(toastrMessage);
					responseMessages.add(responseMessage);
				}
				return responseMessages;
		}
		return null;
	}

	public List<Message> generateToggleMuteUserMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());
			User toggleMuteUser = raum.toggleMuteUserById(message.getAssignedUser().getUserId());
			
			if(toggleMuteUser != null) {
				
				//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(message.getUserId()), raum.getRaumId(), "has " + ((toggleMuteUser.isMute) ? "muted " : "unmuted ") + toggleMuteUser.getUserName() , null, falstze);
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.MUTE_USER, message.getUserName() + "has " + ((toggleMuteUser.isMute()) ? "muted " : "unmuted ") + toggleMuteUser.getUserName(), raum);

				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					if(toggleMuteUser.getUserId() == user.getUserId()) {
						responseMessage.setType("refresh-user-and-list");

					}else {
						responseMessage.setType("refresh-userlist");

					}
					responseMessage.setUser(user);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setToastrMessage(toastrMessage);
					responseMessage.setUsers(raum.getUserList());

					responseMessages.add(responseMessage);
				}
				return responseMessages;
							
			}
			
		}
		return null;
	}

	public List<Message> generateChangeUserNameMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && message.getUser() != null) {
			Raum raum = getRaum(message.getRaumId());
			if(raum.exists(message.getUserId())) {
				
				User changedNameUser = raum.changeUserName(message.getUser());
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.CHANGED_USER_NAME, message.getUserName()  + " has changed Name to " + changedNameUser.getUserName() , raum);

				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType("update-client");
					responseMessage.setUser(user);
					responseMessage.setRaumId(raum.getRaumId());
					responseMessage.setUsers(raum.getUserList());
					responseMessage.setToastrMessage(toastrMessage);
					
					responseMessages.add(responseMessage);
				}
				return responseMessages;
			}
		}
		return null;
	}

	public List<Message> generatePardonKickedUserMessages(Message message) {
		if (rooms.containsKey(message.getRaumId()) && isAdmin(message.getRaumId(), message.getUserId())) {
			Raum raum = getRaum(message.getRaumId());

			if (raum.existsOnKickedUsersList(message.getAssignedUser().getUserId())) {
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.PARDONED_KICKED_USER, message.getUserName()  + "kicked " + message.getAssignedUser().getUserName(), raum);
				User kickedUser = raum.removeFromKickedUserList(message.getAssignedUser().getUserId());
				if (kickedUser != null) {
					ArrayList<Message> responseMessages = new ArrayList<>();
					for (User user : raum.getAdminList()) {
						Message responseMessage = new Message();
						responseMessage.setType("update-kicked-users");
						responseMessage.setUser(user);
						responseMessage.setRaumId(raum.getRaumId());
						responseMessage.setKickedUsers(raum.getKickedUsersList());
						responseMessage.setToastrMessage(toastrMessage);

						responseMessages.add(responseMessage);
					}
					return responseMessages;
				}
			}
		}
		return null;
	}
	
	
	public List<User> getCurrentUsersInRaum(String raumId) {
		if(rooms.containsKey(raumId)) {
			Raum raum = getRaum(raumId);
			return raum.getUserList();
		}
		
		return null;
	}

	public ArrayList<SupportedApi> getSupportedApis() {
		return supportedApis;
	}

	public ArrayList<Video> getHistory(String raumId, String userId) {
		if(raumExists(raumId) && exists(raumId, userId)) {
			return getRaum(raumId).getHistory();
		}
		return null;
	}

	public ToastrMessageTypesObject getToastrMessageTypes() {
		
		return ToastrMessageTypes.cloneInstance();
	}

	public MessageTypesObject getMessageTypes() {
		return MessageTypes.cloneInstance();
	}

	public ArrayList<ToastrMessage> getToastrMessages(String raumId, String userId) {
		if(raumExists(raumId) && exists(raumId, userId)) {
			return getRaum(raumId).getToastrMessages();
		}
		return null;	}

	public ArrayList<ChatMessage> getChatMessages(String raumId, String userId) {
		if(raumExists(raumId) && exists(raumId, userId)) {
			return getRaum(raumId).getChatMessages();
		}
		return null;	
	}
	
	
}
