package Services;

public class RaumMapper {

	public static RaumDTO createRaumDTO(Raum raum) {
		RaumDTO raumDTO = new RaumDTO();
		raumDTO.setRaumId(raum.getRaumId());
		raumDTO.setSize(raum.getSize());
		raumDTO.setVideo(raum.getVideo());
		return raumDTO;
	}
	
}
