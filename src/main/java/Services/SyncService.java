package Services;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.haihuynh.springbootwebsocketchatapplication.configuration.WebSocketConfiguration;

import messages.ChatMessageStub;
import messages.Message;

@Service
public class SyncService {
	
	HashMap<Long, Raum> rooms = new HashMap<>();
	Long roomIdCounter = new Long(1000);
	
	static String[] randomNames = {"Sir Anthony Absolute","Nick Adams","Parson Adams","Frankie Addams","Anthony Adverse","Captain Ahab","Albertine","Alceste","Algernon","Ali Baba","Squire Allworthy","Count Almaviva","The Alvings","Amaryllis","Ananse","Pamela Andrews","Angelica","Harry Angstrom","Mr. and Mrs. Antrobus","Aramis","Isabel Archer","Enoch Arden","The Artful Dodger","Gustave von Aschenbach","Lady Brett Ashley","Athos","Ayesha","Dr. Aziz","Baba-Yaga","Babar","Bilbo Baggins","Frodo Baggins","Harry Bailly","David Balfour","Mr. Barkis","Jake Barnes","Lily Bart","Yevgeny Bazarov","Adam Bede","Belial","Bennet family","Bertram family","Pierre Bezukhov","Big Brother","Big Daddy","Rupert Birkin","Anthony Blanche","Leopold Bloom","Molly Bloom","Bluebeard","Bobbsey Twins","William Boldwood","Bolkonsky family","James Bond","Boojum","The Borrowers","Josiah Bounderby","Emma Bovary","Sally Bowles","Lady Augusta Bracknell","Bradamante","Matthew Bramble","Colonel Brandon","Dave Brandstetter","Gudrun Brangwen","Ursula Brangwen","Brer Rabbit","Brighella","Hans Brinker","Lily Briscoe","Dorothea Brooke","Father Brown","Bruin","Tom and Daisy Buchanan","Inspector Bucket","Buendía family","Mr. Bumble","Natty Bumppo","Bunter","Billy Bunter","Rhett Butler","Camille","Don Camillo","Albert Campion","Capitano","Philip Carey","Nick Carraway","Richard Carstone","Nick Carter","Sydney Carton","Edward Casaubon","Hans Castorp","Cathbad","Holden Caulfield","Olive Chancellor","Cheshire Cat","Roger Chillingworth","Chingachgook","Mr. Chips","Anna Christie","Martin Chuzzlewit","John Claggart","Angel Clare","Claudine","Arthur Clennam","Humphry Clinker","Columbine","Conan the Barbarian","Conlaí","David Copperfield","Richard Cory","Corydon","Mother Courage","Ichabod Crane","Cratchit family","Janie Crawford","Gerald Crich","Guy Crouchback","Robinson Crusoe","Cú Chulainn","Sergeant Cuff","Cunégonde","D’Artagnan","Edmond Dantès","Fitzwilliam Darcy","Charles Darnay","Dashwood family","Stephen Dedalus","Lady Honoria Dedlock","Madame Defarge","Deirdre","Jean Des Esseintes","Mr. Dick","Digenis Akritas","Arthur Dimmesdale","Dick and Nicole Diver","Don Quixote","Eliza Doolittle","Lorna Doone","Dorothy","Dottore","Nancy Drew","Edwin Drood","Bulldog Drummond","Blanche DuBois","The Duchess","The Duke and the Dauphin","Dulcinea","C. Auguste Dupin","Humphrey Chimpden Earwicker","Eeyore","Elliot family","Lord Emsworth","Enmerkar","Henry Esmond","Till Eulenspiegel","Bathsheba Everdene","Fagin","Lord Fauntleroy","Jude Fawley","Richard Feverel","Fezziwig","Flora Finching","Finn","Huckleberry Finn","Jeremiah Flintwinch","Phileas Fogg","Forsyte family","Count Fosco","Frankenstein","Ethan Frome","Fu Manchu","Hedda Gabler","Sairey Gamp","Gandalf","Eugene Gant","Joe Gargery","Jay Gatsby","Gellert","Beau Geste","Gilgamesh","Glass family","Boris Godunov","Golias","Gollum","Père Goriot","Gradgrind","Eugénie Grandet","Emmeline Grangerford","Dorian Gray","Grendel","Clyde Griffiths","Grisette","Titus Groan","Mrs. Grundy","Guermantes family","Martin Guerre","Guildenstern","Prince Hal","Mike Hammer","Hardy Boys","Jonathan Harker","Harlequin","Clarissa Harlowe","Miss Havisham","Jim Hawkins","Asa and Sabbath Lily Hawks","Headless horseman","Heathcliff","Uriah Heep","Matt Helm","Nora Helmer","Michael Henchard","Herne the Hunter","Henry Higgins","Fanny Hill","Roy Hobbs","Sherlock Holmes","Horatio Hornblower","Roderick Hudson","Humbert Humbert","Humpty Dumpty","Mr. Hyde","Iago","Ilya of Murom","Infant Phenomenon","Isolde","Jabberwock","Mr. Jaggers","Jarndyce family","Dr. Jekyll","Mrs. Jellyby","Jim","Gulley Jimson","Joad family","Joseph K.","Juliet","Karamazov brothers","Anna Karenina","Katharina","Carol Kennicott","Diedrich Knickerbocker","George Knightley","Stanley Kowalski","Tonio Kröger","Mr. Kurtz","Will Ladislaw","Lydia Languish","Silas Lapham","Wolf Larsen","Lazarillo de Tormes","Lear","Simon Legree","Lemminkäinen","Inspector Lestrade","Konstantine Levin","Linton family","Little Em’ly","Little Eva","Little Nell","Lochinvar","Lohengrin","Willy Loman","Studs Lonigan","Lothario","Robert Lovelace","Sut Lovingood","Lugalbanda","Lulu","Arsène Lupin","Tertius Lydgate","Barry Lyndon","Macbeth","Lady Macbeth","Macheath","Mad Hatter","Madog ab Owain Gwynedd","Maeldúin","Abel Magwitch","Jules Maigret","Major Major","Malcolm","Alexander and Lucie Manette","Marcel","March Hare","March family","Augie March","Marchmain family","Jacob Marley","Philip Marlowe","Miss Marple","Bertha Mason","Perry Mason","Travis McGee","Wilhelm Meister","Oliver Mellors","Mr. Merdle","Wilkins Micawber","Daisy Miller","Miranda","Walter Mitty","Mock Turtle","Sara Monday","Moomintroll","Hank Morgan","Professor Moriarty","Catherine Morland","Hazel Motes","Mr. Moto","Mowgli","Mugridge","Baron Munchausen","Edward Murdstone","Captain Nemo","Nicholas Nickleby","Scarlett O’Hara","Gabriel Oak","Oberon","Odette","Duke of Omnium","Eugene Onegin","Ophelia","Orlando","Orlando","Gilbert Osmond","Othello","Palliser family","Pancks","Pangloss","Pantaloon","Panurge","Peachum family","Pearl","Seth Pecksniff","Pedrolino","Peer Gynt","Clara Peggotty","Peter Pan","Petruchio","Samuel Pickwick","Piglet","Billy Pilgrim","Pinocchio","Pip","Sir Fretful Plagiary","Anna Livia Plurabelle","Hercule Poirot","Ross Poldark","Aunt Polly","Pollyanna","Polonius","Pontifex family","Mary Poppins","Porthos","Portia","Harry Potter","Fanny Price","Miss Prism","Prospero","J. Alfred Prufrock","Hester Prynne","Puck","Puss in Boots","Quasimodo","Allan Quatermain","Captain Queeg","Queen of Hearts","Queequeg","Adela Quested","Daniel Quilp","A.J. Raffles","Ramsay family","Basil Ransom","Rodion Raskolnikov","Red Cross Knight","Regan","Reynard the Fox","Richard III","Tom Ripley","Howard Roark","Robin Hood","Christopher Robin","Mr. Rochester","Rocinante","Romeo","Rosalind","Rosencrantz","Rostov family","Roxane","Barnaby Rudge","Ruggiero","Marmaduke Ruggles","Horace Rumpole","Charles Ryder","The Saint","Gregor Samsa","Sancho Panza","Tom Sawyer","Scapin","Scaramouche","Ebenezer Scrooge","Amelia Sedley","The Shadow","Shakuntala","Becky Sharp","Ántonia Shimerda","Anne Shirley","Shrek","Shylock","Bill Sikes","Long John Silver","Tyrone Slothrop","Smike","George Smiley","Winston Smith","Snark","Snopes family","Snowball","Lucy Snowe","Halvard Solness","Hetty Sorrel","Sam Spade","Dora Spenlow","Wackford Squeers","Stage Manager","Monroe Stahr","Starbuck","Willie Stark","James Steerforth","Lambert Strether","Esther Summerson","Charles and Joseph Surface","Sutpen family","Svengali","Charles Swann","Verena Tarrant","Tarzan","Tattycoram","Suky Tawdry","Lady Teazle","William Tell","Becky Thatcher","Thomas Bigger","Thomas the Tank Engine","Sadie Thompson","Christopher Tietjens","Tinker Bell","Titania","Tituba","Topsy","Tristan","Betsey Trotwood","Sergeant Francis Troy","Tweedledum and Tweedledee","Oliver Twist","Uncle Tom","Urizen","Jean Valjean","Valmont","Philo Vance","Dolly Varden","Varner family","Diggory Venn","Captain Vere","Rosamond Vincy","Viola","Count Aleksey Vronsky","Eustacia Vye","Dr. Watson","Weird Sisters","Sam Weller","Frederick Wentworth","Werther","Sophia Western","Simon Wheeler","White Rabbit","Agnes Wickfield","Tom Wilcher","Damon Wildeve","Pudd’nhead Wilson","Lord Peter Wimsey","Wingfield family","Wise Men of Gotham","Wolfdietrich","Nero Wolfe","Emma Woodhouse","Bertie Wooster","Clym Yeobright","Captain John Yossarian","Zorro"};
	
	public HashMap<Long, Raum> getRooms() {
		return rooms;
	}

	public void setRooms(HashMap<Long, Raum> rooms) {
		this.rooms = rooms;
	}
	
	public Long generateNewRaumId() {
		roomIdCounter +=1;
		return roomIdCounter;
	}
	
	public String getCurrenTime() {
		return LocalTime.now().toString();
	}
	
	public Message createRaum(Message message) {
		try {
			Long userID = message.getUserId();
			Raum raum = new Raum();
			String name = randomName();
			raum.setRaumId(generateNewRaumId());
			raum.addUser(message.getUserId());
			raum.setVideoLink("https://www.youtube.com/embed/9ClYy0MxsU0");

			rooms.put(roomIdCounter, raum);
			
			WebSocketConfiguration
			.registryInstance
			.enableSimpleBroker("/"+userID);
			
			Message responseMessage = new Message();
			responseMessage.setType("create-room");
			responseMessage.setContent(raum);
			responseMessage.setRaumId(raum.raumId);
			responseMessage.setUserId(userID);
			responseMessage.setUserName(name);
			
			Message joinChatMessage = new Message();
			joinChatMessage.setType("chat-message");
			joinChatMessage.setUserId(userID);
			joinChatMessage.setUserName(name);
			ChatMessageStub cms = new ChatMessageStub();
			cms.setChatMessage("has joined the Room");
			cms.setTimestamp(getCurrenTime());
			joinChatMessage.setContent(cms);
			raum.addChatMessage(joinChatMessage);
			
			return responseMessage;
		}catch(Exception e) {
			return null;
		}
		
	}
	
	public List<Message> joinRaum(Message message) {
		
		if(rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			raum.addUser(message.getUserId());
			
			Long userID = message.getUserId();
			String name = randomName();
			List<Message> messages = new ArrayList<>();

			
			Message joinChatMessage = new Message();
			joinChatMessage.setType("chat-message");
			joinChatMessage.setUserId(userID);
			joinChatMessage.setUserName(name);
			ChatMessageStub cms = new ChatMessageStub();
			cms.setChatMessage("has joined the Room");
			cms.setTimestamp(getCurrenTime());
			joinChatMessage.setContent(cms);
			raum.addChatMessage(joinChatMessage);

			WebSocketConfiguration
			.registryInstance
			.enableSimpleBroker("/"+userID);
			
			for (Long id : raum.getUserIds()) {
					if(userID != id) {
					Message responseMessage = new Message();
					responseMessage.setType("update-client");
					responseMessage.setContent(raum);
					responseMessage.setRaumId(raum.raumId);
					responseMessage.setUserId(id);
					messages.add(responseMessage);
				}else {
					Message responseMessage = new Message();
					responseMessage.setType("join-room");
					responseMessage.setContent(raum);
					responseMessage.setRaumId(raum.raumId);
					responseMessage.setUserId(id);
					responseMessage.setUserName(name);
					messages.add(responseMessage);
					
				}
			}
			
			return messages;
		
		}
		
		return null;		
	}

	public List<Message> addAndShareNewVideo(Message message) {
		if(rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			Long userID = message.getUserId();
			String videoLink = message.getVideoLink();
			raum.setVideoLink(videoLink);
			List<Message> messages = new ArrayList<>();
			
			for (Long id : raum.getUserIds()) {
				
				Message responseMessage = new Message();
				responseMessage.setType("insert-new-video");
				responseMessage.setRaumId(raum.raumId);
				responseMessage.setUserId(id);
				responseMessage.setVideoLink(videoLink);
				messages.add(responseMessage);
			}
			
		
			return messages;	
		}
		return new ArrayList<>();
	
	}
	
	public List<Message> disconnectClient(Message message) {
		if(rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			Long userID = message.getUserId();
			raum.remove(userID);

			List<Message> messages = new ArrayList<>();
		
			for (Long id : raum.getUserIds()) {
				
				Message responseMessage = new Message();
				responseMessage.setType("update-clients");
				responseMessage.setRaumId(raum.raumId);
				responseMessage.setUserId(id);
				responseMessage.setContent(raum);
				
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
		//responseMessage.getType("video-timestamp");
		return null;
	}
	
	public List<Long>  saveMessage(Message message) {
		if(rooms.containsKey(message.getRaumId())) {
			Raum raum = rooms.get(message.getRaumId());
			raum.addChatMessage(message);
			
			return raum.getUserIds();
		}
			return null;
	}
	
	public String randomName(){
		return randomNames[ (int) Math.round((randomNames.length-1) * Math.random())];
	}
	
	public Raum getRaum(Long raumId) {
		return rooms.get(raumId);
	}
	

}
