package editora3.facade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import editora3.entidades.PontoDeVenda;
import editora3.entidades.Vendedor;
 

public class PontoDeVendaFacade extends AbstractFacade<PontoDeVenda> {

	public PontoDeVendaFacade() {
		super(PontoDeVenda.class);
		// TODO Auto-generated constructor stub
	}
	 
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public Integer totalContratosDoPontoDeVenda(Integer pontodevendabean) {
		Integer ret = 0;
		try {
			Query createQuery = getEntityManager()
					.createNativeQuery("select count(c.*) from Contrato c  where not c.inclusao is null"
									+ (pontodevendabean != null ? "  and c.pontodevendabean=:pontodevendabean" : ""));
			if (pontodevendabean != null) {
				createQuery.setParameter("pontodevendabean", pontodevendabean);
			}

			Object singleResult = createQuery.getSingleResult();
			if(singleResult!=null) {
				ret = Integer.valueOf(singleResult.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return ret;

	}
	
	public Integer totalSaidasBrindesParaPontoDeVenda(Integer pontodevendabean) {
		Integer ret = 0;
		try {
			Query createQuery = getEntityManager()
					.createNativeQuery("select count(c.*) from brinde_saida c  where c.datacancelamento is null "
									+ (pontodevendabean != null ? "  and c.pontodevendabean=:pontodevendabean" : ""));
			if (pontodevendabean != null) {
				createQuery.setParameter("pontodevendabean", pontodevendabean);
			}

			Object singleResult = createQuery.getSingleResult();
			if(singleResult!=null) {
				ret = Integer.valueOf(singleResult.toString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return ret;

	}

	
	public PontoDeVenda localizarPorNome(String nome) {
		PontoDeVenda c = null;
		
	 TypedQuery<PontoDeVenda> createQuery = getEntityManager().createQuery("from PontoDeVenda c where c.descricao=:nome",PontoDeVenda.class);
	 createQuery.setParameter("nome", nome);
	 List<PontoDeVenda> resultList = createQuery.getResultList();
	 if(resultList!=null && !resultList.isEmpty()) {
		 c = resultList.get(0);
	 }
		
		return c;
	}

	public List<PontoDeVenda> findAllPorEquipe(Integer codigoEquipe){
		List<PontoDeVenda>  ret= null;
		Query createNativeQuery = getEntityManager().createQuery("select p from PontoDeVenda p " + ( codigoEquipe==null ? "":  "where p.equipeBean.codigo=:codigoEquipe"), PontoDeVenda.class);
		if(codigoEquipe!=null) {
			createNativeQuery.setParameter("codigoEquipe", codigoEquipe);
		}
		 ret= (ArrayList<PontoDeVenda>) createNativeQuery.getResultList();
		return ret;
	}
	
	
}
