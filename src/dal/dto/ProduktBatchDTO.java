package dal.dto;

import java.util.ArrayList;
import java.util.List;

public class ProduktBatchDTO 
{
	int pbId;                     // i omraadet 1-99999999
	int status;					// 0: ikke paabegyndt, 1: under produktion, 2: afsluttet
	int receptId;
	List<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
	
	
	public ProduktBatchDTO(int pbId, int status, int receptId, List<ProduktBatchKompDTO> list)
	{
		this.pbId = pbId;
		this.status = status;
		this.receptId = receptId;
		this.list = list;
	}
	
	public int getPbId() { return pbId; }
	public void setPbId(int pbId) { this.pbId = pbId; }
	public int getStatus() { return status; }
	public void setStatus(int status) { this.status = status; }
	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public List<ProduktBatchKompDTO> getList() { return list; }
	public void setList(List<ProduktBatchKompDTO> list) { this.list = list; }

	public String toString() { return pbId + "\t" + status + "\t" + receptId; }
}

