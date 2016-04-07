package dal.dao_impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dal.DALException;
import dal.dao_interfaces.ProduktBatchDAO;
import dal.dto.ProduktBatchDTO;
import dal.dto.ProduktBatchKompDTO;

public class MySQLProduktBatchDAO implements ProduktBatchDAO {

	@Override
	public ProduktBatchDTO getProduktBatch(int pbId) throws DALException {
		ArrayList<ProduktBatchKompDTO> list = new ArrayList<ProduktBatchKompDTO>();
		Connector connector = new Connector();
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id = '"+pbId+"';");
			while(rs.next()){
				int rbId = rs.getInt("rb_id");
				double tara = rs.getDouble("tara");
				double netto = rs.getDouble("netto");
				int oprId = rs.getInt("opr_id");
				ProduktBatchKompDTO k = new ProduktBatchKompDTO(pbId, rbId, tara, netto, oprId);
				list.add(k);
			}
			rs = connector.doQuery("SELECT * FROM produktbatch WHERE pb_id = '"+pbId+"';");
			if(!rs.next()) 
				throw new DALException("Produktbatchen med id " + pbId + " findes ikke");
			return new ProduktBatchDTO(pbId, rs.getInt("status"), rs.getInt("recept_id"), list);
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<ProduktBatchDTO> getProduktBatchList() throws DALException {
		ArrayList<ProduktBatchDTO> pbLists = new ArrayList<ProduktBatchDTO>();
		Connector connector = new Connector();
		try {
			ResultSet rs1 = connector.doQuery("SELECT * FROM produktbatch;");
			while(rs1.next()){
				int pbId = rs1.getInt("pb_id");
				int status = rs1.getInt("status");
				int receptId = rs1.getInt("recept_id");

				ArrayList<ProduktBatchKompDTO> kompList = new ArrayList<ProduktBatchKompDTO>();
				ResultSet rs2 = connector.doQuery("SELECT * FROM produktbatch WHERE pb_id = '"+pbId+"';");
				while(rs2.next()){
					int rbId = rs2.getInt("rb_id");
					double tara = rs2.getDouble("tara");
					double netto = rs2.getDouble("netto");
					int oprId = rs2.getInt("opr_id");
					ProduktBatchKompDTO k = new ProduktBatchKompDTO(pbId, rbId, tara, netto, oprId);
					kompList.add(k);
				}
				pbLists.add(new ProduktBatchDTO(pbId, status, receptId, kompList));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return pbLists;
	}

	@Override
	public void createProduktBatch(ProduktBatchDTO pb) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("START TRANSACTION");
			connector.doUpdate("INSERT INTO produktbatch(pb_id, recept_id, status) "
					+ "VALUES ('"+pb.getPbId()+"', '"+pb.getReceptId()+"', '"+pb.getStatus()+"');");
			for(ProduktBatchKompDTO k : pb.getList()){
				connector.doUpdate("INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, opr_id) "
						+ "VALUES ('"+pb.getPbId()+"', '"+k.getRbId()+"', '"+k.getTara()+"', '"+k.getNetto()+"', '"+k.getOprId()+"'),");

				// Update amount
				connector.doUpdate("UPDATE raavarebatch SET maengde = maengde -"+k.getNetto()+" WHERE rb_id = '"+k.getRbId()+"';");
				ResultSet rs = connector.doQuery("SELECT maengde FROM raavarebatch WHERE rb_id = '"+k.getRbId()+"';");
				if(rs.getDouble("maengde") < 0){
					connector.doUpdate("ROLLBACK;");
					throw new DALException("Insuficient amount of raavarebatch with ID "+k.getRbId());
				}
			}
			connector.doUpdate("COMMIT;");
					
		} catch (SQLException e) {
			try {
				connector.doUpdate("ROLLBACK;");
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new DALException("Unable to rollback");
			}
			e.printStackTrace();
			throw new DALException("Unable to create produktbatch");
		}
	}

	@Override
	public void updateProduktBatch(ProduktBatchDTO pb) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("START TRANSACTION");
			connector.doUpdate("UPDATE produktbatch SET `status`='"+pb.getStatus()+"', recept_id='"+pb.getReceptId()+"' WHERE `pb_id`='"+pb.getPbId()+"';");
			
			// Put raavare back in stock
			ResultSet rs = connector.doQuery("SELECT * FROM produktbatchkomponent WHERE pb_id = '"+pb.getPbId()+"';");
			while(rs.next()){
				int rbId = rs.getInt("rb_id");
				double netto = rs.getDouble("netto");
				connector.doUpdate("UPDATE raavarebatch SET maengde = maengde + "+netto+" WHERE rb_id = '"+rbId+"';");
			}
			// Delete all the old batches
			connector.doUpdate("DELETE FROM produktbatch WHERE `pb_id`='"+pb.getPbId()+"';");
			// Add all the new batches
			for(ProduktBatchKompDTO k : pb.getList()){
				connector.doUpdate("INSERT INTO produktbatchkomponent(pb_id, rb_id, tara, netto, opr_id) "
						+ "VALUES ('"+pb.getPbId()+"', '"+k.getRbId()+"', '"+k.getTara()+"', '"+k.getNetto()+"', '"+k.getOprId()+"'),");
				// Update amount
				connector.doUpdate("UPDATE raavarebatch SET maengde = maengde -"+k.getNetto()+" WHERE rb_id = '"+k.getRbId()+"';");
				ResultSet rs1 = connector.doQuery("SELECT maengde FROM raavarebatch WHERE rb_id = '"+k.getRbId()+"';");
				if(rs1.getDouble("maengde") < 0){
					connector.doUpdate("ROLLBACK;");
					throw new DALException("Insuficient amount of raavarebatch with ID "+k.getRbId());
				}
			}
			connector.doUpdate("COMMIT;");
					
		} catch (SQLException e) {
			try {
				connector.doUpdate("ROLLBACK;");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

}
