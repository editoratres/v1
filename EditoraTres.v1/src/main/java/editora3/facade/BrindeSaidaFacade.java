package editora3.facade;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeDevolucao;
import editora3.entidades.BrindeDevolucaoIten;
import editora3.entidades.BrindeEstoqueEquipe;
import editora3.entidades.BrindeSaida;
import editora3.entidades.BrindeSaidaIten;
import editora3.entidades.PontoDeVenda;
 

public class BrindeSaidaFacade extends AbstractFacade<BrindeSaida> {

	@Inject
	private BrindeEstoqueFacade brindeEstoqueFacade;
	
	public BrindeSaidaFacade() {
		super(BrindeSaida.class);
		// TODO Auto-generated constructor stub
	}
	 
	
	
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	@Transactional
	public void CriarSaida(BrindeSaida brindeSaida) {
		getEntityManager().persist(brindeSaida);
		if(brindeSaida.getBrindeSaidaItens()!=null && !brindeSaida.getBrindeSaidaItens().isEmpty()) {
			List<BrindeSaidaIten> brindaEntradaItens = brindeSaida.getBrindeSaidaItens();
			for (Iterator iterator = brindaEntradaItens.iterator(); iterator.hasNext();) {
				BrindeSaidaIten brindeEntradaItem = (BrindeSaidaIten) iterator.next();
				Brinde find = getEntityManager().find(Brinde.class, brindeEntradaItem.getBrindeBean().getCodigo(), LockModeType.PESSIMISTIC_READ);
				if(find!=null ) {
					Double novaQT =
							 (find.getQuantidade()==null ? 0d : find.getQuantidade())
							-
							((Double) brindeEntradaItem.getQuantidade());
					find.setQuantidade(novaQT);
					getEntityManager().merge(find);
					
					
					/*Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean and b.pontoDeVendaBean.codigo=:pontoDeVendaBean",BrindeEstoqueEquipe.class);
					createNativeQuery.setParameter("brindeBean", find.getCodigo());
					createNativeQuery.setParameter("equipeBean",brindeSaida.getEquipeBean().getCodigo());
					createNativeQuery.setParameter("pontoDeVendaBean",brindeSaida.getPontoDeVendaBean().getCodigo());
					List<BrindeEstoqueEquipe> resultList =(List<BrindeEstoqueEquipe>) createNativeQuery.getResultList();
					if(resultList!=null) {
						BrindeEstoqueEquipe brindeEstoqueEquipe=null;
						if(resultList.isEmpty()) {
							brindeEstoqueEquipe  = new BrindeEstoqueEquipe();
							brindeEstoqueEquipe.setBrindeBean(find);
							brindeEstoqueEquipe.setEquipeBean(brindeSaida.getEquipeBean());
							brindeEstoqueEquipe.setQuantidade(brindeEntradaItem.getQuantidade());
							getEntityManager().persist(brindeEstoqueEquipe);
						}else {
							brindeEstoqueEquipe=resultList.get(0);
						 
							brindeEstoqueEquipe.setQuantidade(brindeEstoqueEquipe.getQuantidade() + brindeEntradaItem.getQuantidade() );
							getEntityManager().merge(brindeEstoqueEquipe);
						}
					}*/
				 
					getBrindeEstoqueFacade().MovimentarEstoqueBrindeEquipe(getEntityManager(),find,brindeSaida.getEquipeBean(),brindeSaida.getPontoDeVendaBean(),brindeEntradaItem.getQuantidade());
					
					
					
				}
				
			}
		
		}
	}
	
	/*private void MovimentarEstoque(Brinde find, BrindeSaida brindeSaida,  Double quantidade) {
		Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean and b.pontoDeVendaBean.codigo=:pontoDeVendaBean",BrindeEstoqueEquipe.class);
		createNativeQuery.setParameter("brindeBean", find.getCodigo());
		createNativeQuery.setParameter("equipeBean",brindeSaida.getEquipeBean().getCodigo());
		createNativeQuery.setParameter("pontoDeVendaBean",brindeSaida.getPontoDeVendaBean().getCodigo());
		List<BrindeEstoqueEquipe> resultList =(List<BrindeEstoqueEquipe>) createNativeQuery.getResultList();
		if(resultList!=null) {
			BrindeEstoqueEquipe brindeEstoqueEquipe=null;
			if(resultList.isEmpty()) {
				brindeEstoqueEquipe  = new BrindeEstoqueEquipe();
				brindeEstoqueEquipe.setBrindeBean(find);
				brindeEstoqueEquipe.setEquipeBean(brindeSaida.getEquipeBean());
				brindeEstoqueEquipe.setQuantidade(quantidade);
				brindeEstoqueEquipe.setPontoDeVendaBean(brindeSaida.getPontoDeVendaBean());
				getEntityManager().persist(brindeEstoqueEquipe);
			}else {
				brindeEstoqueEquipe=resultList.get(0);
				brindeEstoqueEquipe.setQuantidade(brindeEstoqueEquipe.getQuantidade()  + quantidade);
				getEntityManager().merge(brindeEstoqueEquipe);
			}
		}
		
	}*/
	
	@Transactional
	public void cancelarSaidaBrinde(BrindeSaida brindeSaida) {
		 
		
		List<BrindeSaidaIten> brindaSaidaItens = brindeSaida.getBrindeSaidaItens();
		for (Iterator iterator = brindaSaidaItens.iterator(); iterator.hasNext();) {
			BrindeSaidaIten brindeSaidaItem = (BrindeSaidaIten) iterator.next();
			Brinde find = getEntityManager().find(Brinde.class, brindeSaidaItem.getBrindeBean().getCodigo(), LockModeType.PESSIMISTIC_READ);
			if(find!=null ) {
				Double novaQT =
						 (find.getQuantidade()==null ? 0d : find.getQuantidade())+  ( brindeSaidaItem.getQuantidade());
				find.setQuantidade(novaQT);
				getEntityManager().merge(find);
				
				
			/*	Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean",BrindeEstoqueEquipe.class);
				createNativeQuery.setParameter("brindeBean", find.getCodigo());
				createNativeQuery.setParameter("equipeBean",brindeSaida.getEquipeBean().getCodigo());
				List<BrindeEstoqueEquipe> resultList =(List<BrindeEstoqueEquipe>) createNativeQuery.getResultList();
				if(resultList!=null) {
					BrindeEstoqueEquipe brindeEstoqueEquipe=null;
					if(resultList.isEmpty()) {
						brindeEstoqueEquipe  = new BrindeEstoqueEquipe();
						brindeEstoqueEquipe.setBrindeBean(find);
						brindeEstoqueEquipe.setEquipeBean(brindeSaida.getEquipeBean());
						brindeEstoqueEquipe.setQuantidade(brindeSaidaItem.getQuantidade());
						getEntityManager().persist(brindeEstoqueEquipe);
					}else {
						brindeEstoqueEquipe=resultList.get(0);
					 
						brindeEstoqueEquipe.setQuantidade(brindeEstoqueEquipe.getQuantidade() - brindeSaidaItem.getQuantidade() );
						getEntityManager().merge(brindeEstoqueEquipe);
					}
				}*/
				
			   getBrindeEstoqueFacade().MovimentarEstoqueBrindeEquipe(getEntityManager(),find,brindeSaida.getEquipeBean(),brindeSaida.getPontoDeVendaBean(),-brindeSaidaItem.getQuantidade());
				
			}
			
		}
		getEntityManager().remove( getEntityManager().merge(brindeSaida));
		
	}
	 
	public BrindeEstoqueFacade getBrindeEstoqueFacade() {
		brindeEstoqueFacade.setEntityManager(getEntityManager());
		return brindeEstoqueFacade;
	}
	public void setBrindeEstoqueFacade(BrindeEstoqueFacade brindeEstoqueFacade) {
		this.brindeEstoqueFacade = brindeEstoqueFacade;
	}
	 

}
