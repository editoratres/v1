package editora3.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import editora3.entidades.InfraModulo;
import editora3.entidades.Vendedor;

public class VendedorFacade extends AbstractFacade<Vendedor> implements Serializable{

	@PersistenceContext(unitName = "EditoraTres.v1")
	private EntityManager em;

	   
	public VendedorFacade() {
		super(Vendedor.class);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -9131126904904537819L;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public List<Vendedor> findAllPorEquipe(Integer codigoEquipe){
		List<Vendedor>  ret= null;
		Query createNativeQuery = getEntityManager().createQuery("select v from Vendedor v " + ( codigoEquipe==null ? "":  "where v.equipeBean.codigo=:codigoEquipe"), Vendedor.class);
		if(codigoEquipe!=null) {
			createNativeQuery.setParameter("codigoEquipe", codigoEquipe);
		}
		 ret= (ArrayList<Vendedor>) createNativeQuery.getResultList();
		return ret;
	}
	public Vendedor localizarCPF( String cpf) {
		Vendedor v = null;
		
		Query createNativeQuery = getEntityManager().createNativeQuery("select * from vendedor where cpf=:cpf", Vendedor.class);
		createNativeQuery.setParameter("cpf", cpf);
		 ArrayList<Vendedor> lista= (ArrayList<Vendedor>) createNativeQuery.getResultList();
		 if(lista!=null && !lista.isEmpty()) {
			 v = lista.get(0);
		 }
		
		return v;
		
	}

}
