package dal.dto;

public class ReceptKompDTO
{
	int receptId;                  // auto genereres fra 1..n   
	RaavareDTO raavare;             // i omraadet 1-99999999
	double nomNetto;            // skal vaere positiv og passende stor
	double tolerance;           // skal vaere positiv og passende stor
	
	public ReceptKompDTO(int receptId, RaavareDTO raavare, double nomNetto, double tolerance)
	{
		this.receptId = receptId;
		this.raavare = raavare;
		this.nomNetto = nomNetto;
		this.tolerance = tolerance;
	}

	public int getReceptId() { return receptId; }
	public void setReceptId(int receptId) { this.receptId = receptId; }
	public RaavareDTO getRaavare() { return raavare; }
	public void setRaavare(RaavareDTO raavare) { this.raavare = raavare; }
	public double getNomNetto() { return nomNetto; }
	public void setNomNetto(double nomNetto) { this.nomNetto = nomNetto; }
	public double getTolerance() { return tolerance; }
	public void setTolerance(double tolerance) { this.tolerance = tolerance; }
	public String toString() { 
		return receptId + "\t" + raavare.getRaavareId() + "\t" + raavare.getRaavareNavn() + 
				"\t" + raavare.getLeverandoer() + "\t" + nomNetto + "\t" + tolerance; 
	}
}
