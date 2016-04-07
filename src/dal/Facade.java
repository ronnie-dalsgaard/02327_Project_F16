package dal;

import java.util.List;

import dal.dao_impl.MySQLOperatoerDAO;
import dal.dao_impl.MySQLProduktBatchDAO;
import dal.dao_impl.MySQLRaavareBatchDAO;
import dal.dao_impl.MySQLRaavareDAO;
import dal.dao_impl.MySQLReceptDAO;
import dal.dao_interfaces.*;
import dal.dto.OperatoerDTO;
import dal.dto.ProduktBatchDTO;
import dal.dto.RaavareBatchDTO;
import dal.dto.RaavareDTO;
import dal.dto.ReceptDTO;

public class Facade implements OperatoerDAO, ProduktBatchDAO, RaavareBatchDAO, RaavareDAO, ReceptDAO {
	private OperatoerDAO o = new MySQLOperatoerDAO();
	private ProduktBatchDAO pb = new MySQLProduktBatchDAO();
	private RaavareBatchDAO rb = new MySQLRaavareBatchDAO();
	private RaavareDAO raa = new MySQLRaavareDAO();
	private ReceptDAO rec = new MySQLReceptDAO();
	
	@Override public ReceptDTO getRecept(int receptId) throws DALException { return rec.getRecept(receptId); }
	@Override public List<ReceptDTO> getReceptList() throws DALException { return rec.getReceptList();  }
	@Override public void createRecept(ReceptDTO recept) throws DALException { rec.createRecept(recept); }
	@Override public void updateRecept(ReceptDTO recept) throws DALException { rec.updateRecept(recept); }
	@Override public RaavareDTO getRaavare(int raavareId) throws DALException { return raa.getRaavare(raavareId); }
	@Override public List<RaavareDTO> getRaavareList() throws DALException { return raa.getRaavareList(); }
	@Override public void createRaavare(RaavareDTO raavare) throws DALException { raa.createRaavare(raavare); }
	@Override public void updateRaavare(RaavareDTO raavare) throws DALException { raa.updateRaavare(raavare); }
	@Override public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException { return rb.getRaavareBatch(rbId); }
	@Override public List<RaavareBatchDTO> getRaavareBatchList() throws DALException { return rb.getRaavareBatchList(); }
	@Override public List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException { return rb.getRaavareBatchList(raavareId); }
	@Override public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException { rb.createRaavareBatch(raavarebatch); }
	@Override public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException { rb.updateRaavareBatch(raavarebatch); }
	@Override public ProduktBatchDTO getProduktBatch(int pbId) throws DALException { return pb.getProduktBatch(pbId); }
	@Override public List<ProduktBatchDTO> getProduktBatchList() throws DALException { return pb.getProduktBatchList(); }
	@Override public void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException { pb.createProduktBatch(produktbatch); }
	@Override public void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException { pb.updateProduktBatch(produktbatch); }
	@Override public OperatoerDTO getOperatoer(int oprId) throws DALException { return o.getOperatoer(oprId); }
	@Override public List<OperatoerDTO> getOperatoerList() throws DALException { return o.getOperatoerList(); }
	@Override public void createOperatoer(OperatoerDTO opr) throws DALException { o.createOperatoer(opr); }
	@Override public void updateOperatoer(OperatoerDTO opr) throws DALException { o.updateOperatoer(opr); }
}
