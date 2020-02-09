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
import editora3.entidades.BrindeEntrada;
import editora3.entidades.BrindeEntradaItens;
import editora3.entidades.BrindeEstoqueEquipe;
import editora3.entidades.Canal;
 

public class BrindeDevolucaoFacade extends AbstractFacade<BrindeDevolucao> {

	public BrindeDevolucaoFacade() {
		super(BrindeDevolucao.class);
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
	public void CriarDevolucao(BrindeDevolucao brindeEntrada) {
		getEntityManager().persist(brindeEntrada);
		if(brindeEntrada.getBrindeDevolucaoItens()!=null && !brindeEntrada.getBrindeDevolucaoItens().isEmpty()) {
			List<BrindeDevolucaoIten> brindaEntradaItens = brindeEntrada.getBrindeDevolucaoItens();
			for (Iterator iterator = brindaEntradaItens.iterator(); iterator.hasNext();) {
				BrindeDevolucaoIten brindeEntradaItem = (BrindeDevolucaoIten) iterator.next();
				Brinde find = getEntityManager().find(Brinde.class, brindeEntradaItem.getBrindeBean().getCodigo(), LockModeType.PESSIMISTIC_READ);
				if(find!=null ) {
					Integer novaQT =
							 (find.getQuantidade()==null ? 0 : find.getQuantidade().intValue())
							+
							((Double) brindeEntradaItem.getQuantidade()).intValue();
					find.setQuantidade(novaQT);
					getEntityManager().merge(find);
					
					Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean",BrindeEstoqueEquipe.class);
					createNativeQuery.setParameter("brindeBean", find.getCodigo());
					createNativeQuery.setParameter("equipeBean",brindeEntrada.getEquipeBean().getCodigo());
					List<BrindeEstoqueEquipe> resultList =(List<BrindeEstoqueEquipe>) createNativeQuery.getResultList();
					if(resultList!=null) {
						BrindeEstoqueEquipe brindeEstoqueEquipe=null;
						if(resultList.isEmpty()) {
							brindeEstoqueEquipe  = new BrindeEstoqueEquipe();
							brindeEstoqueEquipe.setBrindeBean(find);
							brindeEstoqueEquipe.setEquipeBean(brindeEntrada.getEquipeBean());
							brindeEstoqueEquipe.setQuantidade(-brindeEntradaItem.getQuantidade());
							getEntityManager().persist(brindeEstoqueEquipe);
						}else {
							brindeEstoqueEquipe=resultList.get(0);
						 
							brindeEstoqueEquipe.setQuantidade(brindeEstoqueEquipe.getQuantidade() - brindeEntradaItem.getQuantidade() );
							getEntityManager().merge(brindeEstoqueEquipe);
						}
					}
					
				}
				
			}
		
		}
	}
	
	@Transactional
	public void cancelarDevolucaoBrinde(BrindeDevolucao brindeEntrada) {
		//brindeEntrada.setDataCancelamento(new Date());
		List<BrindeDevolucaoIten> brindaEntradaItens = brindeEntrada.getBrindeDevolucaoItens();
		for (Iterator iterator = brindaEntradaItens.iterator(); iterator.hasNext();) {
			BrindeDevolucaoIten brindeEntradaItem = (BrindeDevolucaoIten) iterator.next();
			Brinde find = getEntityManager().find(Brinde.class, brindeEntradaItem.getBrindeBean().getCodigo(), LockModeType.PESSIMISTIC_READ);
			if(find!=null ) {
				Integer novaQT =
						 (find.getQuantidade()==null ? 0 : find.getQuantidade().intValue())-  ((Double) brindeEntradaItem.getQuantidade()).intValue();
				find.setQuantidade(novaQT);
				getEntityManager().merge(find);
				
				Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean",BrindeEstoqueEquipe.class);
				createNativeQuery.setParameter("brindeBean", find.getCodigo());
				createNativeQuery.setParameter("equipeBean",brindeEntrada.getEquipeBean().getCodigo());
				List<BrindeEstoqueEquipe> resultList =(List<BrindeEstoqueEquipe>) createNativeQuery.getResultList();
				if(resultList!=null) {
					BrindeEstoqueEquipe brindeEstoqueEquipe=null;
					if(resultList.isEmpty()) {
						brindeEstoqueEquipe  = new BrindeEstoqueEquipe();
						brindeEstoqueEquipe.setBrindeBean(find);
						brindeEstoqueEquipe.setEquipeBean(brindeEntrada.getEquipeBean());
						brindeEstoqueEquipe.setQuantidade(+brindeEntradaItem.getQuantidade());
						getEntityManager().persist(brindeEstoqueEquipe);
					}else {
						brindeEstoqueEquipe=resultList.get(0);
					 
						brindeEstoqueEquipe.setQuantidade(brindeEstoqueEquipe.getQuantidade() + brindeEntradaItem.getQuantidade() );
						getEntityManager().merge(brindeEstoqueEquipe);
					}
				}
			}
			
		}
		getEntityManager().remove(getEntityManager().merge(brindeEntrada));
		
	}
	 

}
