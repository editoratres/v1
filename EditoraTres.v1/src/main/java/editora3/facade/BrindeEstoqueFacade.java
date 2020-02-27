package editora3.facade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeEstoqueEquipe;
import editora3.entidades.Contrato;
import editora3.entidades.Equipe;
import editora3.entidades.PontoDeVenda;

public class BrindeEstoqueFacade extends AbstractFacade<BrindeEstoqueEquipe>  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public List<BrindeEstoqueEquipe> RetornarEstoqueEquipe(Integer codigoBrinde, Integer codigoEquipe ) {
		return RetornarEstoqueEquipe(codigoBrinde,codigoEquipe,null);
	}
	public List<BrindeEstoqueEquipe> RetornarEstoqueEquipe(Integer codigoBrinde, Integer codigoEquipe , Integer pontoDeVendaBean) {
		Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean" +(pontoDeVendaBean!=null ?   " and b.pontoDeVendaBean.codigo=:pontoDeVendaBean " : ""),BrindeEstoqueEquipe.class);
		createNativeQuery.setParameter("brindeBean",codigoBrinde);
		createNativeQuery.setParameter("equipeBean",codigoEquipe);
		if(pontoDeVendaBean!=null) {
			createNativeQuery.setParameter("pontoDeVendaBean",pontoDeVendaBean);
		}
		
		return (List<BrindeEstoqueEquipe>) createNativeQuery.getResultList();
		 
		
	}
	
	public Double RetornarEstoqueEquipeGeral(Integer codigoEquipe) {
		Double ret=0d;
		Query createNativeQuery = getEntityManager().createQuery("select sum(b.quantidade) From BrindeEstoqueEquipe b " +(codigoEquipe!=null ?   " where  b.equipeBean.codigo=:equipeBean" : ""));
	 
		if(codigoEquipe!=null) {
			createNativeQuery.setParameter("equipeBean",codigoEquipe);
		}
		
		Number results = (Number)createNativeQuery.getSingleResult();
		if(results!=null ) {
			ret = Double.valueOf(results.toString());
		}
		
		
		return ret;
		 
		
	}
	public void MovimentarEstoqueBrindeEquipe(EntityManager em, Brinde brinde, Equipe equipe , PontoDeVenda pontoDeVenda, Double quantidade) {

		List<BrindeEstoqueEquipe> resultList = RetornarEstoqueEquipe(brinde.getCodigo(),equipe.getCodigo(),pontoDeVenda.getCodigo());

		if(resultList!=null) {
			BrindeEstoqueEquipe brindeEstoqueEquipe=null;
			if(resultList.isEmpty()) {
				brindeEstoqueEquipe  = new BrindeEstoqueEquipe();
				brindeEstoqueEquipe.setBrindeBean(brinde);
				brindeEstoqueEquipe.setEquipeBean(equipe);
				brindeEstoqueEquipe.setPontoDeVendaBean(pontoDeVenda);
				brindeEstoqueEquipe.setQuantidade( quantidade);
				em.persist(brindeEstoqueEquipe);
			}else {
				brindeEstoqueEquipe=resultList.get(0);
			 
				brindeEstoqueEquipe.setQuantidade(brindeEstoqueEquipe.getQuantidade() + quantidade  );
				em.merge(brindeEstoqueEquipe);
			}
		}
	}
	public BrindeEstoqueFacade() {
		super(BrindeEstoqueEquipe.class);
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "EditoraTres.v1")
	private EntityManager em;

	 
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	 
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
}
