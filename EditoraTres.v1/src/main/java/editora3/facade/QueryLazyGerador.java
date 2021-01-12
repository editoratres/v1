package editora3.facade;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.annotations.QueryHints;
import org.primefaces.model.SortOrder;

 

public class QueryLazyGerador<T> {

	
	public QueryLazyGerador(EntityManager em, String sqlLista, String sqlContagem,  FiltrosLazyDataModel dataModel ,List<ParametrosNativosQuery> parametrosNativosQuery,Class<T> tClass, String Alias) {
		super();
		this.cl = tClass;
		this.Alias=Alias;
		this.setSqlLista(sqlLista);
		this.sqlContagem=sqlContagem;
		this.setEm(em);
		//this.typeQuery = typeQuery;
		this.dataModel = dataModel;
		this.parametrosNativosQuery=parametrosNativosQuery;
	}
	private String Alias;
	private List<ParametrosNativosQuery> parametrosNativosQuery;
	private Class<T> cl;
	private EntityManager em;
	private String sqlLista;
	private TypedQuery<T> typeQuery;
	private FiltrosLazyDataModel dataModel;
	private String sqlContagem;
	
	 
	public TypedQuery<T> getTypeQuery() {
		return typeQuery;
	}
	public void setTypeQuery(TypedQuery<T> typeQuery) {
		this.typeQuery = typeQuery;
	}
	public FiltrosLazyDataModel getDataModel() {
		return dataModel;
	}
	public void setDataModel(FiltrosLazyDataModel dataModel) {
		this.dataModel = dataModel;
	}
	
	public List<T> getTypedQueryResultado(){
		Query ret = null;
		
		StringBuilder sql = aplicarParametrosNaQuery(getSqlLista(),false);

		ret = getEm().createNativeQuery(sql.toString(), cl);

		ret = setarParametrosQuery(ret);
		//ret.setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
		

		if (dataModel != null) {
			ret.setFirstResult(dataModel.getFirst());
			ret.setMaxResults(dataModel.getPageSize());

		}

		return ret.getResultList();
	}
	private TypedQuery<T> setarParametrosQuery(TypedQuery<T> ret) {
		if (dataModel != null) {
			Map<String, Object> filters = dataModel.getFilters();
			if (filters != null && !filters.isEmpty()) {
				Set<String> keySet = filters.keySet();
				for (String key : keySet) {
					if(filters.get(key).toString().trim().length()>0) {
						 
							ret.setParameter(key.replace(".", "_").toLowerCase(), filters.get(key) instanceof Number ? filters.get(key) : filters.get(key).toString().toLowerCase()+"%" );
						 
					}
				}
			}
			
		}
		if(parametrosNativosQuery!=null) {
			for (Iterator iterator = parametrosNativosQuery.iterator(); iterator.hasNext();) {
				ParametrosNativosQuery parametro = (ParametrosNativosQuery) iterator.next();
				ret.setParameter(parametro.getNome(), parametro.getValor());
				
			}
		}
		return ret;
	}
	private Query setarParametrosQuery(Query query) {
		if (dataModel != null) {
			Map<String, Object> filters = dataModel.getFilters();
			if (filters != null && !filters.isEmpty()) {
				Set<String> keySet = filters.keySet();
				for (String key : keySet) {
					if(filters.get(key).toString().trim().length()>0) {
						 
						query.setParameter(key.replace(".", "_").toLowerCase(), filters.get(key) instanceof Number ? filters.get(key) : filters.get(key).toString().toLowerCase()+"%" );
						 
					}
				}
			}
		}
		if(parametrosNativosQuery!=null) {
			for (Iterator iterator = parametrosNativosQuery.iterator(); iterator.hasNext();) {
				ParametrosNativosQuery parametro = (ParametrosNativosQuery) iterator.next();
				query.setParameter(parametro.getNome(), parametro.getValor());
				
			}
		}
		return query;
	}
//	private StringBuilder aplicarParametrosNaQuery(String sqlQuery, boolean ignorarOrderBy) {
//		StringBuilder sql = new StringBuilder();
//		sql.append(sqlQuery);
//		if (dataModel != null) {
//			Map<String, Object> filters = dataModel.getFilters();
//			if (filters != null && !filters.isEmpty()) {
//				Set<String> keySet = filters.keySet();
//				for (String key : keySet) {
//					if(filters.get(key).toString().trim().length()>0) {
//						 
//					 
//						   sql.append(" and cast(").append(Alias).append(".").append(key).append(" as varchar) like ").append(":").append(key.replace(".", "_"));
//						 
//					}
//				}
//			}
//			if(!ignorarOrderBy && dataModel.getSortField()!=null) {
//				sql.append(" order by ").append(Alias).append(".").append(dataModel.getSortField()).append(dataModel.getSortOrder()!=SortOrder.ASCENDING ? " desc ": ""); 
//			}
//		}
//		return sql;
//	}
//	
	private StringBuilder aplicarParametrosNaQuery(String sqlQuery, boolean ignorarOrderBy) {
		StringBuilder sql = new StringBuilder();
		sql.append(sqlQuery);
		if (dataModel != null) {
			Map<String, Object> filters = dataModel.getFilters();
			if (filters != null && !filters.isEmpty()) {
				Set<String> keySet = filters.keySet();
				 String campoModel ="";
				for (String key : keySet) {
					if(filters.get(key).toString().trim().length()>0) {
						 
						   campoModel = dataModel.getMapeamentoCampoViewModel().get(key);	  
						
						   sql.append(" and lower(cast(").append(campoModel).append(" as varchar)) like ").append(":").append(key.toLowerCase().replace(".", "_"));
						 
					}
				}
			}
			if(!ignorarOrderBy && dataModel.getSortField()!=null) {
				 String filtroCampoModel =dataModel.getMapeamentoCampoViewModel().get(dataModel.getSortField());
				sql.append(" order by ").append(filtroCampoModel).append(dataModel.getSortOrder()!=SortOrder.ASCENDING ? " desc ": ""); 
			}
		}
		return sql;
	}
	public Integer getTypedQueryTotalizador(){
		Integer ret=0;
	
		StringBuilder sql = aplicarParametrosNaQuery(getSqlContagem(),true); 
		
		Query query =  getEm().createNativeQuery(sql.toString());
		
		query = setarParametrosQuery(query);
		Object singleResult = query.getSingleResult();
		if(singleResult!=null) {
			ret = Long.valueOf(singleResult.toString()).intValue();
		}

		
		
		return ret;
	}
	public EntityManager getEm() {
		return em;
	}
	public void setEm(EntityManager em) {
		this.em = em;
	}
	public String getSqlContagem() {
		return sqlContagem;
	}
	public void setSqlContagem(String sqlContagem) {
		this.sqlContagem = sqlContagem;
	}
	public String getSqlLista() {
		return sqlLista;
	}
	public void setSqlLista(String sqlLista) {
		this.sqlLista = sqlLista;
	}
	
	
	
	
}
