package dal.dao_impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.Connector;
import dal.DALException;
import dal.dao_interfaces.ReceptDAO;
import dal.dto.RaavareDTO;
import dal.dto.ReceptDTO;
import dal.dto.ReceptKompDTO;

public class MySQLReceptDAO implements ReceptDAO {

	@Override
	public ReceptDTO getRecept(int receptId) throws DALException {
		ArrayList<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
		Connector connector = new Connector();
		try {
			ResultSet rs = connector.doQuery("SELECT * FROM receptkomponent NATURAL JOIN raavare WHERE recept_id = '"+receptId+"';");
			while(rs.next()){
				RaavareDTO raavare = new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
				list.add(new ReceptKompDTO(receptId, raavare, rs.getDouble("nom_netto"), rs.getDouble("tolerance")));
			}
			rs = connector.doQuery("SELECT * FROM recept WHERE recept_id = '"+receptId+"';");
			if(!rs.next()) 
				throw new DALException("Recepten med id " + receptId + " findes ikke");
			return new ReceptDTO(receptId, rs.getString("recept_navn"), list);
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<ReceptDTO> getReceptList() throws DALException {
		ArrayList<ReceptDTO> receptList = new ArrayList<ReceptDTO>();
		Connector connector = new Connector();
		try {
			ResultSet rs1 = connector.doQuery("SELECT * FROM recept;");
			while(rs1.next()){
				int receptId = rs1.getInt("recept_id");
				String receptNavn = rs1.getString("recept_navn");

				ArrayList<ReceptKompDTO> kompList = new ArrayList<ReceptKompDTO>();
				ResultSet rs2 = connector.doQuery("SELECT * FROM receptkomponent NATURAL JOIN raavare WHERE recept_id = '"+receptId+"';");
				while(rs2.next()){
					RaavareDTO raavare = new RaavareDTO(rs2.getInt("raavare_id"), rs2.getString("raavare_navn"), rs2.getString("leverandoer"));
					kompList.add(new ReceptKompDTO(receptId, raavare, rs2.getDouble("nom_netto"), rs2.getDouble("tolerance")));
				}
				receptList.add(new ReceptDTO(receptId, receptNavn, kompList));
			}
		} catch (SQLException e) {
			throw new DALException(e);
		}
		return receptList;
	}

	@Override
	public void createRecept(ReceptDTO recept) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("START TRANSACTION");
			connector.doUpdate("INSERT INTO recept (`recept_id`, `recept_navn`) \n" +
				    "VALUES ('"+recept.getReceptId()+"', '"+recept.getReceptNavn()+"');");
			for(ReceptKompDTO k : recept.getList()){
				connector.doUpdate("INSERT INTO receptkomponent (`recept_id`, `raavare_id`, `nom_netto`, `tolerance`) " +
						"VALUES ('"+recept.getReceptId()+"', '"+k.getRaavare().getRaavareId()+"', " +
						"'"+k.getNomNetto()+"', '"+k.getTolerance()+"');");
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

	@Override
	public void updateRecept(ReceptDTO recept) throws DALException {
		Connector connector = new Connector();
		try {
			connector.doUpdate("START TRANSACTION");
			connector.doUpdate("UPDATE recept SET `recept_navn`='"+recept.getReceptNavn()+"' WHERE `recept_id`='"+recept.getReceptId()+"';");
			connector.doUpdate("DELETE FROM receptkomponent WHERE recept_id = "+recept.getReceptId()+";");
			for(ReceptKompDTO k : recept.getList()){
				connector.doUpdate("INSERT INTO receptkomponent (`recept_id`, `raavare_id`, `nom_netto`, `tolerance`) " +
						"VALUES ('"+recept.getReceptId()+"', '"+k.getRaavare().getRaavareId()+"', " +
						"'"+k.getNomNetto()+"', '"+k.getTolerance()+"');");
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
