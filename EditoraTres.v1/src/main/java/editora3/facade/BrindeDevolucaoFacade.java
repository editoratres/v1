package editora3.facade;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.Brinde;
import editora3.entidades.BrindeDevolucao;
import editora3.entidades.BrindeDevolucaoIten;
import editora3.entidades.BrindeEntrada;
import editora3.entidades.BrindeEntradaItens;
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
			}
			
		}
		getEntityManager().remove(getEntityManager().merge(brindeEntrada));
		
	}
	 

}
