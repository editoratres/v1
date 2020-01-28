package editora3.facade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import editora3.entidades.Equipe;
import editora3.entidades.Vendedor;
 

public class EquipeFacade  extends AbstractFacade<Equipe> implements Serializable{

	public EquipeFacade() {
		super(Equipe.class);
		// TODO Auto-generated constructor stub
	}

	 
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	private static final long serialVersionUID = -623836635616774886L;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	public List<Vendedor> localizarVendedoresEquipe(Integer idequipe){
		List<Vendedor> ret = null;
		Query createNativeQuery = getEntityManager().createQuery("from Vendedor v where v.equipeBean.codigo=:idequipe",
				Vendedor.class);
		createNativeQuery.setParameter("idequipe", idequipe);
		ret = (ArrayList<Vendedor>) createNativeQuery.getResultList();

		return ret;
	}
	
	public Equipe localizarCPF( String cpf) {
		Equipe v = null;
		
		Query createNativeQuery = getEntityManager().createNativeQuery("select * from equipe where cnpjcpf=:cpf", Equipe.class);
		createNativeQuery.setParameter("cpf", cpf);
		 ArrayList<Equipe> lista= (ArrayList<Equipe>) createNativeQuery.getResultList();
		 if(lista!=null && !lista.isEmpty()) {
			 v = lista.get(0);
		 }
		
		return v;
		
	}
	@Transactional
	public Equipe FindEager(Integer id) {
		 
		
		Equipe find = getEntityManager().find(Equipe.class, id);
	
		find.getVendedors().size();
		
		//find.getVendedors();
		
		return find;
	}
}
