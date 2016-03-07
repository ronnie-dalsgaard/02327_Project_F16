package test;

import java.util.ArrayList;

import dal.DALException;
import dal.dao_impl.MySQLOperatoerDAO;
import dal.dao_impl.MySQLRaavareDAO;
import dal.dao_impl.MySQLReceptDAO;
import dal.dto.OperatoerDTO;
import dal.dto.RaavareDTO;
import dal.dto.ReceptDTO;
import dal.dto.ReceptKompDTO;

public class Main {

	public static void main(String[] args) {
		Main main = new Main();
//		main.testOperatoerDAO();
		main.testRaavareDAO();
		main.testReceptDAO();
	}

	public void testOperatoerDAO() {
		MySQLOperatoerDAO dao = new MySQLOperatoerDAO();

		System.out.println("Operatoer nummer 3:");
		try {
			System.out.println(dao.getOperatoer(3));
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Indsaettelse af ny operatoer med opr_id =  4");
		OperatoerDTO oprDTO = new OperatoerDTO(4, "Don Juan", "DJ", "000000-0000", "iloveyou");
		try {
			dao.createOperatoer(oprDTO);
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Operatoer nummer 4:");
		try {
			System.out.println(dao.getOperatoer(4));
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Opdatering af initialer for operatoer nummer 4");
		oprDTO.setIni("DoJu");
		try {
			dao.updateOperatoer(oprDTO);
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Operatoer nummer 4:");
		try {
			System.out.println(dao.getOperatoer(4));
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Alle operatoerer:");
		try {
			System.out.println(dao.getOperatoerList());
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("Operatoer nummer 5:");
		try {
			System.out.println(dao.getOperatoer(5));
		} catch (DALException e) {
			System.out.println(e.getMessage());
		}
	}
	public void testRaavareDAO(){
		MySQLRaavareDAO dao = new MySQLRaavareDAO();
		try {
			for(RaavareDTO dto : dao.getRaavareList()){
				System.out.println(dto);
			}
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
	public void testReceptDAO(){
		MySQLReceptDAO dao = new MySQLReceptDAO();
		try {
			for(ReceptDTO dto : dao.getReceptList()){
				System.out.println(dto+"\n");
			}
		} catch (DALException e) {
			e.printStackTrace();
		}
		
		MySQLRaavareDAO d = new MySQLRaavareDAO();
		
		try {
			ArrayList<ReceptKompDTO> list = new ArrayList<ReceptKompDTO>();
			list.add(new ReceptKompDTO(4, d.getFirstRaavare("tomat"), 5.0, 5));
			list.add(new ReceptKompDTO(4, d.getFirstRaavare("ost"), 5.0, 5));
			list.add(new ReceptKompDTO(4, d.getFirstRaavare("skinke"), 5.0, 5));
			list.add(new ReceptKompDTO(4, d.getFirstRaavare("ananas"), 5.0, 5));
			
			ReceptDTO dto = new ReceptDTO(4, "Hawaii", list);
			dao.createRecept(dto);
		} catch (DALException e) {
			e.printStackTrace();
		}
	}
}
