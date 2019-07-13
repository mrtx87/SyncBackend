package Services;

public class RaumMapper {

	public static RaumDTO createRaumDTO(Raum raum) {
		RaumDTO raumDTO = new RaumDTO();
		raumDTO.setRaumId(raum.getRaumId());
		raumDTO.setSize(raum.getSize());
		raumDTO.setVideo(raum.getCurrentVideo());
		raumDTO.setCreatedAt(raum.getCreatedAt());
		raumDTO.setDescription(raum.getDescription());
		raumDTO.setTitle(raum.getTitle());
		raumDTO.setRaumStatus(raum.getRaumStatus());
		return raumDTO;
	}
	
}
