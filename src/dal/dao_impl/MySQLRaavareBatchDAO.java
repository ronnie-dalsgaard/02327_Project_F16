package dal.dao_impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dal.DALException;
import dal.dao_interfaces.RaavareBatchDAO;
import dal.dto.RaavareBatchDTO;

public class MySQLRaavareBatchDAO implements RaavareBatchDAO {

	@Override
	public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
		Connector connector = new Connector();
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM raavarebatch WHERE rb_id = '"+rbId+"';");
			if(!rs.next())
				throw new DALException("Råvarebatchen med id " + rbId + " findes ikke");
			return new RaavareBatchDTO(rbId, rs.getInt("raavare_id"), rs.getDouble("maengde"));
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		ArrayList<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		Connector connector = new Connector();
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM raavarebatch;");
			while(rs.next())
				list.add(new RaavareBatchDTO(rs.getInt("rb _id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
		ArrayList<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		Connector connector = new Connector();
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM raavarebatch WHERE raavare_id = '"+raavareId+"';");
			while(rs.next())
				list.add(new RaavareBatchDTO(rs.getInt("rb _id"), rs.getInt("raavare_id"), rs.getDouble("maengde")));
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("INSERT INTO raavarebatch(rb_id, raavare_id, maengde) "
					+ "VALUES ('"+raavarebatch.getRbId()+"', '"+raavarebatch.getRaavareId()+"', '"+raavarebatch.getMaengde()+"');");
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("UPDATE raavarebatch SET raavare_id='"+raavarebatch.getRaavareId()+"', maengde='"+raavarebatch.getMaengde()+"' "
					+ "WHERE rb_id='"+raavarebatch.getRbId()+"';");
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

}
