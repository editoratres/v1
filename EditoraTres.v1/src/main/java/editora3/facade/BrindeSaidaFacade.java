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
			}
			
		}
		getEntityManager().remove( getEntityManager().merge(brindeSaida));
		
	}
	 

}
