package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeEstoqueEquipe;
import editora3.entidades.Contrato;
import editora3.entidades.Equipe;
import editora3.entidades.PontoDeVenda;

public class BrindeEstoqueFacade extends AbstractFacade<BrindeEstoqueEquipe> {
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
