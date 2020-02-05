package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import editora3.entidades.Contrato;
import editora3.entidades.Relatorio;

public class ContratoFacade extends AbstractFacade<Contrato> {

	public ContratoFacade() {
		super(Contrato.class);
		// TODO Auto-generated constructor stub
	}

	@PersistenceContext(unitName = "EditoraTres.v1")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}

	public Contrato localizarPorCodigo(Integer codigocontrato) {
		Contrato c = null;

		TypedQuery<Contrato> createQuery = getEntityManager().createQuery("from Contrato c where c.codigocontrato=:codigocontrato",
				Contrato.class);
		createQuery.setParameter("codigocontrato", codigocontrato);
		List<Contrato> resultList = createQuery.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			c = resultList.get(0);
		}

		return c;
	}

}
