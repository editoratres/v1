package editora3.facade;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import editora3.entidades.Equipe;
import editora3.entidades.Fornecedor;
import editora3.entidades.Vendedor;
 

public class FornecedorFacade  extends AbstractFacade<Fornecedor> implements Serializable{

	public FornecedorFacade() {
		super(Fornecedor.class);
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
	
	public Fornecedor localizarCPF( String cpf) {
		Fornecedor v = null;
		
		Query createNativeQuery = getEntityManager().createNativeQuery("select * from Fornecedor where cnpjcpf=:cpf", Fornecedor.class);
		createNativeQuery.setParameter("cpf", cpf);
		 ArrayList<Fornecedor> lista= (ArrayList<Fornecedor>) createNativeQuery.getResultList();
		 if(lista!=null && !lista.isEmpty()) {
			 v = lista.get(0);
		 }
		
		return v;
		
	}
	 
}
