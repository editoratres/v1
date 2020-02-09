package editora3.facade;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import editora3.entidades.Canal;
 

public class BrindeSaidaFacade extends AbstractFacade<BrindeSaida> {

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
					Integer novaQT =
							 (find.getQuantidade()==null ? 0 : find.getQuantidade().intValue())
							-
							((Double) brindeEntradaItem.getQuantidade()).intValue();
					find.setQuantidade(novaQT);
					getEntityManager().merge(find);
					
					
					Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean",BrindeEstoqueEquipe.class);
					createNativeQuery.setParameter("brindeBean", find.getCodigo());
					createNativeQuery.setParameter("equipeBean",brindeSaida.getEquipeBean().getCodigo());
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
					}
					
				}
				
			}
		
		}
	}
	
	
	@Transactional
	public void cancelarSaidaBrinde(BrindeSaida brindeSaida) {
		 
		
		List<BrindeSaidaIten> brindaSaidaItens = brindeSaida.getBrindeSaidaItens();
		for (Iterator iterator = brindaSaidaItens.iterator(); iterator.hasNext();) {
			BrindeSaidaIten brindeSaidaItem = (BrindeSaidaIten) iterator.next();
			Brinde find = getEntityManager().find(Brinde.class, brindeSaidaItem.getBrindeBean().getCodigo(), LockModeType.PESSIMISTIC_READ);
			if(find!=null ) {
				Integer novaQT =
						 (find.getQuantidade()==null ? 0 : find.getQuantidade().intValue())+  ((Double) brindeSaidaItem.getQuantidade()).intValue();
				find.setQuantidade(novaQT);
				getEntityManager().merge(find);
				
				
				Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean",BrindeEstoqueEquipe.class);
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
				}
				
			}
			
		}
		getEntityManager().remove( getEntityManager().merge(brindeSaida));
		
	}
	 

}
