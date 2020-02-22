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
import editora3.entidades.BrindeEntrada;
import editora3.entidades.BrindeEntradaItens;
import editora3.entidades.PontoDeVenda;
 

public class BrindeEntradaFacade extends AbstractFacade<BrindeEntrada> {

	public BrindeEntradaFacade() {
		super(BrindeEntrada.class);
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
	public void CriarEntrada(BrindeEntrada brindeEntrada) {
		getEntityManager().persist(brindeEntrada);
		if(brindeEntrada.getBrindaEntradaItens()!=null && !brindeEntrada.getBrindaEntradaItens().isEmpty()) {
			List<BrindeEntradaItens> brindaEntradaItens = brindeEntrada.getBrindaEntradaItens();
			for (Iterator iterator = brindaEntradaItens.iterator(); iterator.hasNext();) {
				BrindeEntradaItens brindeEntradaItem = (BrindeEntradaItens) iterator.next();
				Brinde find = getEntityManager().find(Brinde.class, brindeEntradaItem.getBrindeBean().getCodigo(), LockModeType.PESSIMISTIC_READ);
				if(find!=null ) {
					Double novaQT =((Double) brindeEntradaItem.getQuantidade()) 
							+ (find.getQuantidade()==null ? 0d : find.getQuantidade());
					find.setQuantidade(novaQT);
					getEntityManager().merge(find);
				}
				
			}
		
		}
	}
	
	@Transactional
	public void cancelarEntradaBrinde(BrindeEntrada brindeEntrada) {
		 
		
		List<BrindeEntradaItens> brindaEntradaItens = brindeEntrada.getBrindaEntradaItens();
		for (Iterator iterator = brindaEntradaItens.iterator(); iterator.hasNext();) {
			BrindeEntradaItens brindeEntradaItem = (BrindeEntradaItens) iterator.next();
			Brinde find = getEntityManager().find(Brinde.class, brindeEntradaItem.getBrindeBean().getCodigo(), LockModeType.PESSIMISTIC_READ);
			if(find!=null ) {
				Double novaQT =
						 (find.getQuantidade()==null ? 0d : find.getQuantidade())-  ((Double) brindeEntradaItem.getQuantidade());
				find.setQuantidade(novaQT);
				getEntityManager().merge(find);
			}
			
		}
		getEntityManager().remove(getEntityManager().merge(brindeEntrada));
		
	}
	public BrindeEntrada localizarPorNotaFiscal(String notafiscal) {
		BrindeEntrada c = null;
		
	 TypedQuery<BrindeEntrada> createQuery = getEntityManager().createQuery("from BrindeEntrada c where c.notafiscal=:notafiscal",BrindeEntrada.class);
	 createQuery.setParameter("notafiscal", notafiscal);
	 List<BrindeEntrada> resultList = createQuery.getResultList();
	 if(resultList!=null && !resultList.isEmpty()) {
		 c = resultList.get(0);
	 }
		
		return c;
	}

}
