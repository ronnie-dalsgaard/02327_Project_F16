package dal.dao_impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dal.DALException;
import dal.dao_interfaces.RaavareDAO;
import dal.dto.RaavareDTO;

public class MySQLRaavareDAO implements RaavareDAO {

	@Override
	public RaavareDTO getRaavare(int raavareId) throws DALException {
		Connector connector = new Connector();
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM raavare WHERE raavare_id = '"+raavareId+"';");
			if(!rs.next())
				throw new DALException("Råvaren med id " + raavareId + " findes ikke");
			return new RaavareDTO(raavareId, rs.getString("raavare_navn"), rs.getString("leverandoer"));
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}
	
	public RaavareDTO getFirstRaavare(String raavareNavn) throws DALException {
		Connector connector = new Connector();
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM raavare WHERE raavare_navn LIKE '"+raavareNavn+"';");
			if(!rs.next())
				throw new DALException("Råvaren med navn " + raavareNavn + " findes ikke");
			return new RaavareDTO(rs.getInt("raavare_id"), raavareNavn, rs.getString("leverandoer"));
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<RaavareDTO> getRaavareList() throws DALException {
		ArrayList<RaavareDTO> list = new ArrayList<RaavareDTO>();
		Connector connector = new Connector();
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM raavare;");
			while(rs.next())
				list.add(new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer")));
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return list;
	}

	@Override
	public void createRaavare(RaavareDTO raavare) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("INSERT INTO `raavare` (`raavare_id`, `raavare_navn`, `leverandoer`) "
					+ "VALUES ('"+raavare.getRaavareId()+"', '"+raavare.getRaavareNavn()+"', '"+raavare.getLeverandoer()+"');");
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public void updateRaavare(RaavareDTO raavare) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("UPDATE raavare SET `raavare_navn`='"+raavare.getRaavareNavn()+"', `leverandoer`='"+raavare.getLeverandoer()+
					"' WHERE `raavare_id`='"+raavare.getRaavareId()+"';");
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

}
