package editora3.facade;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;

import editora3.entidades.Contrato;
import editora3.entidades.Oferta;
import editora3.entidades.OfertaIten;

public class OfertaFacade extends AbstractFacade<Oferta> {

	public OfertaFacade() {
		super(Oferta.class);
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
	public void excluirOfertaItem(OfertaIten ofertaIten) {
		em.remove(em.merge(ofertaIten));
	}

	public OfertaIten localizarOfertaItemPorEdicao(String edicao, Integer codigooferta) {
		OfertaIten c = null;

		TypedQuery<OfertaIten> createQuery = getEntityManager().createQuery(
				"from OfertaIten oi where oi.edicao=:edicao and oi.oferta.codigo=:codigooferta", OfertaIten.class);
		createQuery.setParameter("edicao", edicao);
		createQuery.setParameter("codigooferta", codigooferta);
		List<OfertaIten> resultList = createQuery.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			c = resultList.get(0);
		}

		return c;
	}

	
	
	@Transactional
	public LazyObjetos<Oferta> findAllLazy(String status, Integer codigoequipe, FiltrosLazyDataModel dataModel) {
		LazyObjetos<Oferta> retLazy = new LazyObjetos();
		;
		try {

			StringBuilder sqlcomum = new StringBuilder();
			sqlcomum
			.append(" from oferta o, oferta_itens oi, oferta_Brindes ob, oferta_Equipe oe, produto p ")
			.append(" where o.codigo=oi.codigooferta and o.codigo=ob.codigooferta and o.codigo=oe.codigooferta and p.codigo=o.produtobean ")
			.append((!status.equalsIgnoreCase("todos") ? " and o.ativa=TRUE and oi.ativa=TRUE " : ""))
			.append(codigoequipe != null ? " and oe.equipeBean=:codigoequipe" : "");

			StringBuilder sql = new StringBuilder();

			sql.
			append("select distinct o.* ")
			.append(sqlcomum.toString());
					

			StringBuilder sqlContagem = new StringBuilder();
			sqlContagem
			.append("select count(distinct o.*) ")
			.append(sqlcomum.toString());
			
			List<ParametrosNativosQuery> parametrosNativosQuery = new ArrayList<ParametrosNativosQuery>();
			if (codigoequipe != null) {
				ParametrosNativosQuery p1 = new ParametrosNativosQuery();
				p1.setNome("codigoequipe");
				p1.setValor(codigoequipe);
				parametrosNativosQuery.add(p1);
			}

			QueryLazyGerador<Oferta> queryLazyGerador = new QueryLazyGerador<>(em, sql.toString(),
					sqlContagem.toString(), dataModel, parametrosNativosQuery, Oferta.class, "o");

			retLazy.setLista(queryLazyGerador.getTypedQueryResultado());

			if (retLazy.getLista() != null) {
				for (Iterator iterator = retLazy.getLista().iterator(); iterator.hasNext();) {
					Oferta oferta = (Oferta) iterator.next();
					oferta.getOfertaBrindes().size();
					oferta.getOfertaEquipe().size();
					oferta.getOfertaItens().size();
				}
			}

			retLazy.setTotalObjetos(queryLazyGerador.getTypedQueryTotalizador());

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return retLazy;
	}

	@Transactional
	public Oferta findEager(Integer codigoOferta) {
		Oferta ret = null;
		TypedQuery<Oferta> createQuery = getEntityManager()
				.createQuery("select DISTINCT  o from Oferta o" + " where o.codigo=:codigo ", Oferta.class);
		createQuery.setParameter("codigo", codigoOferta);
		ret = createQuery.getSingleResult();
		// ret= find(codigoOferta);
		ret.getOfertaBrindes().size();

		ret.getOfertaItens().size();

		ret.getOfertaEquipe().size();

		return ret;
	}

	public Oferta localizarPorNome(String nome) {
		Oferta c = null;

		TypedQuery<Oferta> createQuery = getEntityManager().createQuery("from Oferta c where c.descricao=:nome",
				Oferta.class);
		createQuery.setParameter("nome", nome);
		List<Oferta> resultList = createQuery.getResultList();
		if (resultList != null && !resultList.isEmpty()) {
			c = resultList.get(0);
		}

		return c;
	}
	public Integer totalContratosComAOferta(Integer ofertabean) {
		Integer ret = 0;
		try {
			Query createQuery = getEntityManager()
					.createNativeQuery("select count(c.*) from Contrato c , contrato_produto cp  where c.codigo=cp.codigocontrato and not c.inclusao is null"
									+ (ofertabean != null ? "  and cp.ofertabean=:ofertabean" : ""));
			if (ofertabean != null) {
				createQuery.setParameter("ofertabean", ofertabean);
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

}
