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
	
	static String INFO = "toast-info";
	static String WARNING = "toast-warning";
	static String ERROR = "toast-error";
	static String SUCCESS = "toast-success";
	
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

	static String[] randomNames = { "Indiana Jones",
			"Forrest Gump",
			"James Bond",
			"Han Solo",
			"Captain Jack Sparrow",
			"Yoda",
			"Gandalf",
			"The Joker",
			"Anakin Skywalker",
			"Luke Skywalker",
			"Bruce Wayne / Batman",
			"Hannibal Lecter",
			"Vito Corleone",
			"Harry Potter",
			"Obi-Wan Kenobi",
			"T-800 / The Terminator",
			"Rocky Balboa",
			"Marty McFly",
			"Man with No Name",
			"Dr. Emmett Brown",
			"Professor Albus Dumbledore",
			"R2-D2",
			"Legolas",
			"Wolverine",
			"E.T",
			"Aragorn",
			"Professor Severus Snape",
			"John McClane",
			"Chewbacca",
			"Atticus Finch",
			"Leia Organa",
			"Michael Corleone",
			"Woody",
			"Iron Man",
			"Jack Torrance",
			"Hermione Granger",
			"Neo",
			"Tyler Durden",
			"Norman Bates",
			"The Dude",
			"Gollum",
			"Ferris Bueller",
			"Kevin McCallister",
			"Jason Bourne",
			"John Rambo",
			"Ellen Ripley",
			"Scarlett O'Hara",
			"Shrek",
			"Spock",
			"Inspector Clouseau",
			"Spider-Man",
			"Edward Scissorhands",
			"The Bride",
			"Count Dracula",
			"Tony Montana",
			"Sarah Connor",
			"Professor X",
			"James T. Kirk",
			"Maximus Decimus Meridius",
			"Captain America",
			"King Leonidas",
			"King Kong",
			"Mr. Bean",
			"Harry Callahan",
			"Andy Dufresne",
			"Martin Riggs",
			"Vincent Vega",
			"C-3PO",
			"Thor",
			"Willy Wonka",
			"Rick Blaine",
			"Austin Powers",
			"Buzz Lightyear",
			"Freddy Krueger",
			"Bob the Minion",
			"Captain John H. Miller",
			"William Wallace",
			"Frodo Baggins",
			"Randle Patrick McMurphy",
			"Samwise Gamgee",
			"Superman",
			"Jules Winnfield",
			"Gny. Sgt. Hartman",
			"Katniss Everdeen",
			"Lieutenant Dan Taylor",
			"Chuck Noland",
			"Paul Edgecombe",
			"Inigo Montoya",
			"Loki",
			"Sherlock Holmes",
			"Rocket Raccoon",
			"Rubeus Hagrid",
			"Crocodile Dundee",
			"Professor Minerva McGonagall",
			"Mufasa",
			"Mike Wazowski",
			"Groot",
			"Black Widow",
			"Gimli",
			"Sirius Black",
			"Star-Lord",
			"Donkey",
			"Genie",
			"Alfred Pennyworth",
			"John Coffey",
			"Hans Gruber",
			"Dorothy Gale",
			"Lord Voldemort",
			"Wonder Woman",
			"Palpatine",
			"Dory",
			"Morpheus",
			"Barbossa",
			"Hans Landa",
			"Clarice Starling",
			"Agent Smith",
			"Lt. Frank Drebin",
			"Clark Griswold",
			"Ronald Weasley",
			"T-1000",
			"Phil Connors",
			"Bilbo Baggins",
			"Minions",
			"Ron Burgundy",
			"Boromir",
			"Oskar Schindler",
			"Peter Venkman",
			"Travis Bickle",
			"Lee",
			"Scar",
			"Robin Hood",
			"Luna Lovegood",
			"George Bailey",
			"Doc Holliday - Tombstone",
			"Hulk",
			"Jean-Luc Picard",
			"Bane",
			"Aslan",
			"Mr. Incredible",
			"Magneto",
			"Boba Fett",
			"Wicked Witch of the West",
			"Scarecrow",
			"Beetlejuice",
			"Doctor Evil",
			"Mr. Kesuke Miyagi",
			"Catwoman",
			"James P. Sullivan",
			"HAL 9000",
			"V",
			"Lt. Aldo Raine",
			"Tommy DeVito",
			"Holly Golightly",
			"Arwen",
			"Rhett Butler",
			"Conan the Barbarian",
			"Marge Gunderson",
			"Mrs. Gump",
			"Galadriel",
			"Godzilla",
			"Elizabeth Swann",
			"Ace Ventura",
			"Darth Maul",
			"Maleficent",
			"Sauron",
			"Leonard McCoy",
			"Qui-Gon Jinn",
			"Rey",
			"Josh Baskin",
			"Spartacus",
			"Wednesday Addams",
			"Jack Skellington",
			"Data",
			"Peregrin Took",
			"Michael Myers",
			"Roger &quot;Verbal&quot; Kint",
			"Andrew Beckett",
			"Gamora",
			"Buddy the Elf",
			"Pumbaa",
			"Frankenstein's Monster",
			"Mr. Potato Head",
			"Wyatt Earp",
			"Carl Hanratty",
			"Annie Wilkes",
			"Kyle Reese",
			"Westley",
			"Mulan",
			"Ian Malcolm",
			"Elrond",
			"Timon",
			"Patrick Bateman",
			"Nick Fury",
			"Axel Foley",
			"Anton Chigurh",
			"Rooster Cogburn",
			"Will Turner",
			"Captain Richard Phillips",
			"Blade",
			"Saruman",
			"Ebenezer Scrooge",
			"Lara Croft",
			"Bruce Banner",
			"James Gordon",
			"Tin Man",
			"Morticia Addams",
			"Richard Kimble",
			"Olaf",
			"Simba",
			"Carl Fredricksen",
			"Elsa",
			"Jason Voorhees",
			"Jabba the Hutt",
			"Drax the Destroyer",
			"Apollo Creed",
			"RoboCop",
			"Davy Jones",
			"Smaug",
			"'Joliet' Jake Blues",
			"Jessica Rabbit",
			"Danny Zuko",
			"Zorro",
			"Montgomery Scott",
			"Optimus Prime",
			"Max Rockatansky",
			"Buddy",
			"The Tramp",
			"Dr. King Schultz",
			"Elwood Blues",
			"Raymond Stantz",
			"Cruella de Vil",
			"Fred Weasley",
			"John Connor",
			"Dr. Strangelove",
			"Jim Lovell",
			"Django",
			"Cowardly Lion",
			"Nurse Ratched",
			"Theodore Roosevelt",
			"Rogue",
			"Happy Gilmore",
			"Mia Wallace",
			"Agent Phil Coulson",
			"Short Round",
			"Q",
			"M",
			"Marion Ravenwood",
			"Henry Jones, Sr.",
			"Butch Cassidy",
			"Lt. Col. Bill Kilgore",
			"Sonny Corleone",
			"Truman Burbank",
			"Stanley Ipkiss",
			"The Tramp",
			"Tallahassee",
			"Belle",
			"Donnie Darko",
			"Miss Piggy",
			"Matilda",
			"Molly Weasley",
			"Sandy Olsson",
			"Frank N. Furter",
			"Alita",
			"Silent Bob",
			"Beast",
			"Pavel Chekov",
			"Raymond Babbitt",
			"Tarzan",
			"Trinity",
			"Roger Murtaugh",
			"George McFly",
			"Selene",
			"Alex",
			"Amon Goeth",
			"Walter Sobchak",
			"Carrie White",
			"Napoleon Dynamite",
			"Colonel Walter E. Kurtz",
			"Wreck-It Ralph",
			"Jeff Spicoli",
			"Colonel Nathan Jessup",
			"Lex Luthor",
			"John &quot;Bluto&quot; Blutarsky",
			"John Doe",
			"Lieutenant Colonel Frank Slade",
			"Wayne Campbell",
			"Haymitch Abernathy",
			"Ant-Man (Scott Lang)",
			"Blanche DuBois",
			"Treebeard",
			"Remy",
			"Elle Woods",
			"John Nash",
			"Mrs. Robinson",
			"John Hammond",
			"Princess Fiona",
			"Borat",
			"Meriadoc Brandybuck",
			"Draco Malfoy",
			"Jarvis",
			"Bambi",
			"Alastor Moody",
			"Agent Kay",
			"Dewey Finn",
			"Agent J",
			"Vizzini",
			"Quint",
			"Mathilda",
			"Tuco",
			"The Winter Soldier",
			"Mickey O'Neil",
			"Leonardo",
			"Norman Stansfield",
			"Samuel Gerard",
			"Joshamee Gibbs",
			"Brick Tamland",
			"Catherine Tramell",
			"Steve Stifler",
			"Scarlet Witch",
			"Yondu",
			"Babe",
			"Daniel Larusso",
			"Lloyd Christmas",
			"Sweeney Todd",
			"Barf",
			"Regina George",
			"Mr. Blonde",
			"Alice",
			"Moses",
			"Mr. Pink",
			"Wedge Antilles",
			"Bill 'The Butcher' Cutting",
			"Henry Hill",
			"Lancelot",
			"Harmonica",
			"Cyclops",
			"Hawkeye",
			"James 'Jimmy' Conway",
			"Judah Ben-Hur",
			"Riddick",
			"Euphegenia Doubtfire",
			"Scooby Doo",
			"Stitch",
			"Frank Abagnale Jr.",
			"Mad Hatter",
			"Worf",
			"George Weasley",
			"Baloo",
			"Dr. Malcolm Crowe",
			"Smeagol",
			"Caesar",
			"Thing",
			"Sheriff Bart",
			"Frank Martin",
			"Charlie Bucket",
			"Will Hunting",
			"John Bender",
			"Kirk Lazarus",
			"William H. 'Billy the Kid' Bonney",
			"Russell",
			"Mater",
			"Rocket Raccoon",
			"Buck",
			"Hit-Girl",
			"Ted",
			"Jessie",
			"Frozone",
			"Bryan Mills",
			"Rose DeWitt Bukater",
			"Alice",
			"Jiminy Cricket",
			"Bagheera",
			"Mystique",
			"Ellen Griswold",
			"Erin Brockovich",
			"Hamm",
			"Uhura",
			"Shere Khan",
			"King Louie",
			"Odin",
			"Joseph D. 'Joe' Pistone",
			"Jackie Brown",
			"Daniel Plainview",
			"John Constantine",
			"Faramir",
			"Mace Windu",
			"Dark Helmet",
			"Mini-Me",
			"Penguin",
			"Dr. Frederick Frankenstein",
			"Dr. John Dolittle",
			"Billy Elliot",
			"Raphael",
			"Jaws",
			"Derek Vinyard",
			"Anton Ego",
			"Kaa",
			"Eli",
			"Frollo",
			"Kristoff",
			"John Keating",
			"Flynn Rider",
			"Jack Byrnes",
			"Ricky Bobby",
			"Jake LaMotta",
			"Donatello",
			"William Munny",
			"Buffalo Bill",
			"Michelangelo",
			"Nick Wilde",
			"Alonzo",
			"Antonio Salieri",
			"Harley Quinn",
			"Elastigirl",
			"Wolfgang Amadeus Mozart",
			"Regan MacNeil",
			"J. Jonah Jameson",
			"Scout Finch",
			"Hedley Lamarr",
			"Ivan Drago",
			"Willow Ufgood",
			"Khan Noonien Singh",
			"Alan Grant",
			"Baymax",
			"Dr. Otto Octavius",
			"Walt Kowalski",
			"Kronk",
			"Mary Poppins",
			"Ardeth Bay",
			"Alan Garner",
			"James Norrington",
			"Nearly Headless Nick",
			"Leeloo",
			"Danny Ocean",
			"Great Prince of the Forest",
			"Dr. Henry Jekyll",
			"Abe Sapien",
			"Luke Zoolander",
			"Glinda the Good Witch",
			"Bard the Bowman",
			"Dirk Diggler",
			"Thing",
			"Violet Parr",
			"Harry Dunne",
			"Elliot Taylor",
			"Hiccup Horrendous Haddock III",
			"Edna Mode",
			"Cole Sear",
			"Wilson",
			"Tia Dalma",
			"Hilts 'The Cooler King'",
			"Sheriff John T. Chance",
			"Prince John",
			"Azeem",
			"Crow",
			"Bumblebee",
			"Claire Standish",
			"Judy Benjamin",
			"Evil Queen",
			"Marsellus Wallace",
			"Rue",
			"Simon Phoenix",
			"Black Panther",
			"Lord Farquaad",
			"Sam Wheat",
			"Lucius Fox",
			"Jane Foster",
			"Benjamin Martin",
			"Billy Costigan",
			"Juno MacGuff",
			"Rosemary Woodhouse",
			"Chunk",
			"Gomez Addams",
			"Thelma Yvonne Dickinson",
			"Dutch",
			"Martin Brody",
			"Karl Childers",
			"Ash Williams",
			"Harry Lime",
			"Lily Evans Potter",
			"Connor MacLeod",
			"Charles Foster Kane",
			"Jordan Belfort",
			"Jim Gordon",
			"Sid",
			"Ichabod Crane",
			"Peter Quill/starlord",
			"Chief Bromden",
			"Bedivere",
			"Scrappy-Doo",
			"Gordie Lachance",
			"Detective James Carter",
			"Captain Benjamin L. Willard",
			"Dash",
			"Sallah",
			"Mr. Chow",
			"Dr. Robert Neville",
			"Inspector Javert",
			"Samuel J. Loomis",
			"Ralphie",
			"Neytiri",
			"Ted &quot;Theodore&quot; Logan",
			"Alabama Whitman",
			"Grand Moff Tarkin",
			"Snoopy",
			"Grumpy",
			"Black Knight",
			"Rex",
			"Vitruvius",
			"Randal Graves",
			"Lt. Col. Bill Cage",
			"Winston 'The Wolf' Wolfe",
			"Punisher",
			"Thorin Oakenshield",
			"D'Artagnan",
			"Elena Montero",
			"EVE",
			"Erik, the Phantom of the Opera",
			"Josey Wales",
			"Carl Spackler",
			"Mister Peabody",
			"Jack the Monkey",
			"Buford T. Justice",
			"Nicky Santoro",
			"Max Cady",
			"Vesper Lynd",
			"Lady",
			"Matt Hooper",
			"David",
			"Dr. Nefario",
			"Ragetti",
			"David Levinson",
			"Riff Raff - A Handyman",
			"William Riker",
			"Nathan Algren",
			"Narrator",
			"Rupert Pupkin",
			"Bad Cop",
			"Casey Jones",
			"Allison Reynolds",
			"Finnick Odair",
			"Lando Calrissian",
			"Lori",
			"Toothless",
			"Karen Hill",
			"Biff Tannen",
			"Ty Webb",
			"John Ferguson",
			"Gamling",
			"Terry Malloy",
			"Long John Silver",
			"Roy Waller",
			"Lorraine Baines McFly",
			"Deanna Troi",
			"Bonnie Parker",
			"Kumar Patel",
			"Commodus",
			"Louise Elizabeth Sawyer",
			"Paul Cicero",
			"Garth Algar",
			"President Snow",
			"Marv",
			"Bill Lumbergh",
			"Charlie Chaplin",
			"President James Marshall",
			"Ashitaka",
			"Cobb",
			"Snake Plissken",
			"Greedo",
			"Charles 'Charley' Bowdre",
			"Yzma",
			"Thranduil",
			"Frank Costello",
			"Chris Taylor",
			"Billy Beane",
			"Tony Stark Usher",
			"Johnny 5",
			"Remus Lupin",
			"Lois Lane",
			"Heimdall",
			"Cameron Frye",
			"Tina Carlyle",
			"Jango Fett",
			"Guido Orefice",
			"L. B. Jeffries",
			"Pongo",
			"Reuben",
			"Edgar Frog",
			"Peter Gibbons",
			"Audrey II",
			"Arthur Weasley",
			"Sheriff of Nottingham",
			"John",
			"Killer Rabbit",
			"Bellatrix Lestrange",
			"Frank",
			"Leo Getz",
			"Alita",
			"The Iron Giant",
			"Irene Adler",
			"Damien Karras",
			"Doctor Watson",
			"Mickey Goldmill",
			"Ming the Merciless",
			"Jay",
			"The Dude",
			"Robbie Hart",
			"Charlie Brown",
			"Perseus",
			"Virgil Tibbs",
			"The Sundance Kid",
			"John Matrix",
			"Brooks Hatlen",
			"David Mills",
			"Brian Johnson",
			"John Patrick Mason",
			"Angel Eyes",
			"Dr. Julia Harris",
			"Wyldstyle",
			"Lucy Pevensie",
			"Fezzik",
			"Mongo",
			"Melvin Udall",
			"Taunting French Guard",
			"Madmartigan",
			"Nina Sayers",
			"Cleopatra",
			"Clubber Lang",
			"Gordon Gekko",
			"Wade Garrett",
			"Ash",
			"Gertie Taylor",
			"Elliott",
			"David Wooderson",
			"Green Goblin",
			"Hawkeye",
			"Harvey Dent",
			"Bandit",
			"The Wizard of Oz",
			"Fat Bastard",
			"Maria Elena",
			"Jack Burton",
			"Sam Emerson",
			"Megan",
			"Mugatu",
			"Ilsa Lund Laszlo",
			"Clarence Odbody",
			"Frank Serpico",
			"Butch Coolidge",
			"Frankenstein",
			"Judge Dredd",
			"Jenny Curran",
			"Dug",
			"Jim Stark",
			"Johnny Lawrence",
			"Fogell",
			"John Winger",
			"Ramona Flowers",
			"Beast",
			"Lucy Emerson",
			"Carl Showalter",
			"Lassie",
			"T. E. Lawrence",
			"Dr. Rumack",
			"Sergeant Taggart",
			"Captain Hook",
			"Samara Morgan",
			"Jerry Lundegaard",
			"Ricky 'Wild Thing' Vaughn",
			"Percy Jackson",
			"Annie Hall",
			"&quot;Rusty&quot; Griswold",
			"Sam 'Ace' Rothstein",
			"George Caldwell",
			"Brick Top",
			"Alita Battle Angel",
			"Harry Tasker",
			"Hamish",
			"Stephen",
			"Moana",
			"Eric Draven",
			"Mr. Levenstein",
			"Leonard",
			"Korben Dallas",
			"Naomi Lapaglia",
			"Invisible Woman",
			"Bill Murray",
			"Boris 'The Blade' Yurinov",
			"Susan Pevensie",
			"Major Arnold Toht",
			"Cujo",
			"Deadpool",
			"Jack Ryan",
			"Imhotep",
			"Patrick Gates",
			"Jim the Waco Kid",
			"Inspector Todd",
			"The Bride",
			"Nancy Thompson",
			"Professor Filius Flitwick",
			"Queen Elizabeth I",
			"May Parker",
			"Lt. Raymond Tango",
			"Doralee Rhodes",
			"James Dalton",
			"Chris Chambers",
			"Lestat de Lioncourt",
			"Pennywise the Dancing Clown",
			"Gromit",
			"Cha-Cha DiGregorio",
			"Danny Torrance",
			"Machete",
			"Prince Akeem",
			"Katrina Van Tassel",
			"Aramis",
			"David Lightman",
			"Ronnie Neary",
			"Clark Wilhelm Griswold, Sr.",
			"Ra's Al Ghul",
			"Wolfman",
			"Quicksilver",
			"Cinderella",
			"Ruby Rhod",
			"Bill S. Preston, Esq.",
			"The Lone Ranger",
			"Navin Johnson",
			"Audrey Griswold",
			"Sentenza",
			"Jo March",
			"Rachael",
			"Ed Rooney",
			"Sonny",
			"Aunt Bethany",
			"Colette",
			"Les Grossman",
			"Sloth",
			"Shaun",
			"Mycroft Holmes",
			"Mary Jane Watson",
			"Uncle Lewis",
			"Kitty Pryde",
			"Ben Hanscom",
			"Angel Eyes",
			"Sir Galahad",
			"Jim Malone",
			"Jeffrey Goines",
			"J.R. MacReady",
			"Sean Dignam",
			"Ethan Hunt",
			"River Tam",
			"Wicket W. Warrick",
			"Elise Elliot",
			"Col. Hathi",
			"Barbara Maitland",
			"Chong Li",
			"Howard Payne",
			"Scott Howard",
			"Pussy Galore" };
	
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
	
	
	public ToastrMessage createAndSaveToastrMessage(String type, String messageText, Raum raum, String toastrType) {
		return createAndSaveToastrMessage(type, messageText, raum, false, toastrType);
	}
	
	public ToastrMessage createAndSaveToastrMessage(String type, String messageText, Raum raum, boolean onlyLogging, String toastrType) {
		ToastrMessage toastrMsg = new ToastrMessage(type, messageText, raum.getRaumId(), LocalDateTime.now(), onlyLogging, toastrType);
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
			createRaumMessage.setType(MessageTypes.CREATE_ROOM);
			createRaumMessage.setUser(user);
			createRaumMessage.setRaumId(raum.raumId);
			createRaumMessage.setUsers(raum.getUserList());
			createRaumMessage.setCurrentPlaybackRate(raum.getCurrentPlaybackRate());			
			createRaumMessage.setRaumTitle(raum.getTitle());
			createRaumMessage.setRaumDescription(raum.getDescription());
			createRaumMessage.setRaumStatus(raum.getRaumStatus());
			createRaumMessage.setPlayerState(raum.getPlayerState());
			//createAndSaveChatMessage(user, raum.getRaumId(), "has created the Room", null, false);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.CREATE_ROOM, user.userName + " has created the Room", raum, SUCCESS);
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
				joinMessage.setType(MessageTypes.JOIN_ROOM);
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
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.JOIN_ROOM, user.userName + " has joined the Room", raum, INFO);
				joinMessage.setToastrMessage(toastrMessage);
	
				WebSocketConfiguration.registryInstance.enableSimpleBroker("/" + message.getUserId());
	
				for (String id : raum.getUserIds()) {
					if (!user.userId.equals(id)) {
	
						Message responseMessage = new Message();
						responseMessage.setType(MessageTypes.UPDATE_CLIENT);
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
	

	public List<Message> disconnectClient(Message message) {
		if (rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			String userId = message.getUserId();
			User removedUser = raum.remove(userId);

			List<Message> messages = new ArrayList<>();
			//ChatMessage chatMessage = createAndSaveChatMessage(removedUser, raum.getRaumId(), "has left the Room", null, false);
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.DISCONNECT, removedUser.userName + " has left the Room", raum, INFO);
			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType(MessageTypes.UPDATE_CLIENT);
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
					ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ONLY_LOGGING, message.getUserName() + " send ChatMessge " + chatMessage.getMessageText(), raum, true, INFO);

					ArrayList<Message> responseMessages = new ArrayList<Message>();
					for(User user : raum.getUserList()){
						Message responseMessage = new Message();
						responseMessage.setUser(user);
						responseMessage.setType(MessageTypes.CHAT_MESSAGE);
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
				responseMessage.setType(MessageTypes.SEEK_TO_TIMESTAMP);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ONLY_LOGGING, message.getUserName() + " toggled play", raum, true, INFO);
			List<Message> messages = new ArrayList<>();
			for (String userId : raum.getUserIds()) {
				Message responseMessage = new Message();
				responseMessage.setType(MessageTypes.TOGGLE_PLAY);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ASSIGNED_AS_ADMIN,"assigned " + raum.getUser(message.getAssignedUser().getUserId()).getUserName() + " as Admin", raum, SUCCESS);
			Message assignAdminMessage = new Message();
			assignAdminMessage.setType(MessageTypes.ASSIGNED_AS_ADMIN);
			assignAdminMessage.setUser(raum.getUser(message.getAssignedUser().userId));
			assignAdminMessage.setUsers(raum.getUserList());
			assignAdminMessage.setRaumId(raum.getRaumId());
			assignAdminMessage.setToastrMessage(toastrMessage);
			
			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {
				if (user.getUserId() != message.getAssignedUser().getUserId()) {
					Message responseMessage = new Message();
					responseMessage.setType(MessageTypes.UPDATE_CLIENT);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.TO_PUBLIC_ROOM, message.getUserName() + " has changed the Room to Public", raum, INFO);

			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType(MessageTypes.TO_PUBLIC_ROOM);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.TO_PRIVATE_ROOM, message.getUserName() + " has changed the Room to Private", raum, INFO);
			ArrayList<Message> responseMessages = new ArrayList<>();

			for (User user : raum.getUserList()) {

				Message responseMessage = new Message();
				responseMessage.setType(MessageTypes.TO_PRIVATE_ROOM);
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
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.KICKED_USER, message.getUserName() + " has kicked " + kickedUser.getUserName()  , raum, ERROR);

				Message kickedUserMessage = new Message();
				kickedUserMessage.setType(MessageTypes.KICKED_USER);
				kickedUserMessage.setUser(kickedUser);
				kickedUserMessage.setToastrMessage(toastrMessage);

				ArrayList<Message> responseMessages = new ArrayList<>();
				responseMessages.add(kickedUserMessage);

				for (User user : raum.getUserList()) {

					Message responseMessage = new Message();
					responseMessage.setType(MessageTypes.UPDATE_KICK_CLIENT);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.REFRESH_ROOM_ID, message.getUserName() + " has refreshed RoomId: " + raum.getRaumId(), raum, INFO);

			ArrayList<Message> responseMessages = new ArrayList<>();
			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType(MessageTypes.REFRESH_ROOM_ID);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ADDED_VIDEO_TO_PLAYLIST, message.getUserName() + " has added Video: " + playlistVideo.getTitle(), raum, INFO);

			ArrayList<Message> responseMessages = new ArrayList<>();
			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType(MessageTypes.UPDATE_PLAYLIST);
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
					responseMessage.setType(MessageTypes.SEEK_TO_TIMESTAMP);
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
				responseMessage.setType(MessageTypes.REQUEST_SYNC_TIMESTAMP);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.UPDATE_TITLE_AND_DESCRIPTION, message.getUserName() + "changed room title & description: \n " + message.getRaumTitle() +"\n" + message.getRaumDescription(), raum, INFO);

			ArrayList<Message> responseMessages = new ArrayList<>();
			for (User user : raum.getUserList()) {
				Message responseMessage = new Message();
				responseMessage.setType(MessageTypes.UPDATE_TITLE_AND_DESCRIPTION);
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
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.REMOVE_VIDEO_PLAYLIST, message.getUserName() + " has removed Video: " + removedVideo.getTitle(), raum, INFO);

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
			responseMessage.setType(MessageTypes.REMOVE_VIDEO_PLAYLIST);
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
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.IMPORTED_PLAYLIST, getRaum(raumId).getUser(userId).getUserName()+  ((importedPlaylist.mode == 0) ?  " has imported " : " has integrated ")+  importedPlaylist.title, raum, SUCCESS);

				Video playlistTitleTransferVideo = new Video();
				playlistTitleTransferVideo.setTitle(importedPlaylist.getTitle());
				//ChatMessage chatMessage = createAndSaveChatMessage(raum.getUser(userId), raumId, null, playlistTitleTransferVideo, true);
				raum.setCurrentVideo(raum.getPlaylistVideo(0));
				
				Message responseMessage = new Message();
				responseMessage.setType(MessageTypes.UPDATE_PLAYLIST);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.ONLY_LOGGING, message.getUserName() + " switched Video to " + video.getTitle(), raum, true, INFO);

			raum.setCurrentPlaybackRate(1);
			if(video != null)	{	
				video.setTimestamp(0f);
				raum.addToHistory(video);
				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType(MessageTypes.SWITCH_VIDEO);
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
					responseMessage.setType(MessageTypes.TOGGLE_PLAYLIST_LOOP);
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
					responseMessage.setType(MessageTypes.TOGGLE_PLAYLIST_RUNNING_ORDER);
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
			ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.CHANGED_PLAYBACK_RATE, message.getUserName() + "changed Playbackrate to " + message.getCurrentPlaybackRate(), raum, INFO);
				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType(MessageTypes.CHANGED_PLAYBACK_RATE);
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
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.MUTE_USER, message.getUserName() + "has " + ((toggleMuteUser.isMute()) ? "muted " : "unmuted ") + toggleMuteUser.getUserName(), raum, WARNING);

				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					if(toggleMuteUser.getUserId() == user.getUserId()) {
						responseMessage.setType(MessageTypes.REFRESH_USER_AND_LIST);

					}else {
						responseMessage.setType(MessageTypes.REFRESH_USERLIST);

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
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.CHANGED_USER_NAME, message.getUserName()  + " has changed Name to " + changedNameUser.getUserName() , raum, INFO);

				ArrayList<Message> responseMessages = new ArrayList<>();
				for (User user : raum.getUserList()) {
					Message responseMessage = new Message();
					responseMessage.setType(MessageTypes.UPDATE_CLIENT);
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
				ToastrMessage toastrMessage = createAndSaveToastrMessage(ToastrMessageTypes.PARDONED_KICKED_USER, message.getUserName()  + "kicked " + message.getAssignedUser().getUserName(), raum, INFO);
				User kickedUser = raum.removeFromKickedUserList(message.getAssignedUser().getUserId());
				if (kickedUser != null) {
					ArrayList<Message> responseMessages = new ArrayList<>();
					for (User user : raum.getAdminList()) {
						Message responseMessage = new Message();
						responseMessage.setType(MessageTypes.UPDATE_KICKED_USERS);
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
