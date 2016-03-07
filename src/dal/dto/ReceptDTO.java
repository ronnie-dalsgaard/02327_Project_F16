package dal.dto;

import java.util.List;
import java.util.ArrayList;

/**
 * Recept Data Objekt
 * 
 * @author mn/tb
 * @version 1.2
 */

public class ReceptDTO 
{
	/** Recept nr i omraadet 1-99999999 */
	int receptId;
	/** Receptnavn min. 2 max. 20 karakterer */
	String receptNavn;
	/** liste af kompenenter i recepten */
	List<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
    
	public ReceptDTO(int receptId, String receptNavn, List<ReceptKompDTO> list)
	{
        this.receptId = receptId;
        this.receptNavn = receptNavn;
        this.list = list;
    }

    public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public String getReceptNavn() { return receptNavn; }
	public void setReceptNavn(String receptNavn) { this.receptNavn = receptNavn; }
	public List<ReceptKompDTO> getList() { return list; }
	public void setList(List<ReceptKompDTO> list) { this.list = list; }

	public String toString() { 
		String out = receptId + "\t" + receptNavn;
		for(ReceptKompDTO k : list){
			out += "\n    " + k.getRaavare().toString() + "\t" + k.getNomNetto() + "\t" + k.getTolerance() + "%";
		}
		return out;
	}
}
