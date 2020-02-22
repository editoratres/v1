package editora3.facade;

import java.util.Comparator;
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

import org.hibernate.Hibernate;

import editora3.entidades.Assinante;
import editora3.entidades.Brinde;
import editora3.entidades.BrindeEstoqueEquipe;
import editora3.entidades.Contrato;
import editora3.entidades.ContratoBrinde;
import editora3.entidades.ContratoPagamento;
import editora3.entidades.Equipe;
import editora3.entidades.Relatorio;

public class ContratoFacade extends AbstractFacade<Contrato> {

	public ContratoFacade() {
		super(Contrato.class);
		// TODO Auto-generated constructor stub
	}
	@Inject
	private BrindeEstoqueFacade brindeEstoqueFacade;
	
	@PersistenceContext(unitName = "EditoraTres.v1")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}
	
	public List<Contrato> findAllLazy(){
		return findAllLazy(null);
	}
	
	@Transactional
	public List<Contrato> findAllLazy(Integer codigoequipe){
		List<Contrato> ret =null;
		try {
			TypedQuery<Contrato> createQuery = getEntityManager().createQuery(
					"select c from Contrato c  where not c.inclusao is null"+
							(codigoequipe!=null ?  "  and c.equipeBean.codigo=:codigoequipe": ""),
					Contrato.class);
			if(codigoequipe!=null) {
				createQuery.setParameter("codigoequipe", codigoequipe);
			}
		
			ret = createQuery.getResultList();
			
		 
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
		
		return ret;
		
	}
	
	@Transactional
	public List<Contrato> totalContratosEfetivados(Integer codigoequipe){
		List<Contrato> ret =null;
		try {
			TypedQuery<Contrato> createQuery = getEntityManager().createQuery(
					"select c from Contrato c  where not c.inclusao is null"+
							(codigoequipe!=null ?  "  and c.equipeBean.codigo=:codigoequipe": ""),
					Contrato.class);
			if(codigoequipe!=null) {
				createQuery.setParameter("codigoequipe", codigoequipe);
			}
		
			ret = createQuery.getResultList();
			
		 
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
		
		return ret;
		
	}
	
	@Transactional
	public void cancelarContrato(Contrato c) {
		
			c = getContratoEager(c.getCodigo());
			
			List<ContratoBrinde> contratoBrindes = c.getContratoBrindes();
			for (Iterator iterator = contratoBrindes.iterator(); iterator.hasNext();) {
				ContratoBrinde contratoBrinde = (ContratoBrinde) iterator.next();
				if(c.getEquipeBean()==null) {
					Brinde find = getEntityManager().find(Brinde.class, contratoBrinde.getBrindBean().getCodigo() , LockModeType.PESSIMISTIC_READ);
				
					if(find!=null ) {
						Double novaQT =
								 (find.getQuantidade()==null ? 0 : find.getQuantidade().intValue())
								+
								contratoBrinde.getQuantidade();
						find.setQuantidade(novaQT);
						getEntityManager().merge(find);
					}
				}else {
				
					getBrindeEstoqueFacade().MovimentarEstoqueBrindeEquipe(getEntityManager(),contratoBrinde.getBrindBean(),c.getEquipeBean(),c.getPontoDeVendaBean(),-contratoBrinde.getQuantidade());
								
				}
				
			}
			c.setInclusao(null);
			c.setRelatorioBean(null);
			c.setNascimento(null);
			c.setCartaobeneficio(null);
			c.setDatavenda(null);
			Assinante assinanteBean = c.getAssinanteBean();
			assinanteBean.setBairro("");
			assinanteBean.setBanco("");
			assinanteBean.setCep("");
			assinanteBean.setCidade("");
			assinanteBean.setCnpjcpf("");
			assinanteBean.setComplemento("");
			assinanteBean.setDescricao("");
			assinanteBean.setEmail("");
			assinanteBean.setEndereco("");
			assinanteBean.setEstado("");
			assinanteBean.setFone1("");
			assinanteBean.setFone2("");
			assinanteBean.setFone3("");
			
			c.setAssinanteBean(assinanteBean);
			//c.setCanalBean(null);
			c.setSubcanlBean(null);
			ContratoPagamento pagamentoBean = c.getPagamentoBean();
			pagamentoBean.setAutorizacao("");
			pagamentoBean.setBandeiraBean(null);
			pagamentoBean.setCartao("");
			pagamentoBean.setCodseguranca("");
			pagamentoBean.setCondposparcial(false);
			pagamentoBean.setCondpostotal(false);
			pagamentoBean.setData(null);
			pagamentoBean.setLote("");
			pagamentoBean.setMaquineta("");
			pagamentoBean.setNsu("");
			pagamentoBean.setTerminal("");
			pagamentoBean.setTitular("");
			pagamentoBean.setValidade("");
			pagamentoBean.setValor(0);
			
			c.setPagamentoBean(pagamentoBean);
			c.getContratoBrindes().clear();
			c.getContratoProdutos().clear();
			getEntityManager().merge(c);
		
	}
	
	@Transactional
	public void gravarContrato(Contrato c) {
		if(c.getInclusao()==null) {
			List<ContratoBrinde> contratoBrindes = c.getContratoBrindes();
			for (Iterator iterator = contratoBrindes.iterator(); iterator.hasNext();) {
				ContratoBrinde contratoBrinde = (ContratoBrinde) iterator.next();
				if(c.getEquipeBean()==null) {
					Brinde find = getEntityManager().find(Brinde.class, contratoBrinde.getBrindBean().getCodigo() , LockModeType.PESSIMISTIC_READ);
				
					if(find!=null ) {
						Double novaQT =
								 (find.getQuantidade()==null ? 0d : find.getQuantidade().intValue())
								-
								contratoBrinde.getQuantidade();
						find.setQuantidade(novaQT);
						getEntityManager().merge(find);
					}
				}else {
				
					getBrindeEstoqueFacade().MovimentarEstoqueBrindeEquipe(getEntityManager(),contratoBrinde.getBrindBean(),c.getEquipeBean(),c.getPontoDeVendaBean(),contratoBrinde.getQuantidade());
								
				}
				
			}
			c.setInclusao(new Date());
			getEntityManager().merge(c);
		}else {
			getEntityManager().merge(c);
		}
		
	}
	public List<BrindeEstoqueEquipe> RetornarEstoqueEquipe(Integer codigoBrinde, Integer codigoEquipe) {
		Query createNativeQuery = getEntityManager().createQuery("From BrindeEstoqueEquipe b where b.brindeBean.codigo=:brindeBean and b.equipeBean.codigo=:equipeBean",BrindeEstoqueEquipe.class);
		createNativeQuery.setParameter("brindeBean",codigoBrinde);
		createNativeQuery.setParameter("equipeBean",codigoEquipe);
		
		return (List<BrindeEstoqueEquipe>) createNativeQuery.getResultList();
		 
		
	}
	private void MovimentarEstoqueBrindeEquipe(Brinde brinde, Equipe equipe , String tipomov, Integer quantidade) {

		List<BrindeEstoqueEquipe> resultList = RetornarEstoqueEquipe(brinde.getCodigo(),equipe.getCodigo());

		if(resultList!=null) {
			BrindeEstoqueEquipe brindeEstoqueEquipe=null;
			if(resultList.isEmpty()) {
				brindeEstoqueEquipe  = new BrindeEstoqueEquipe();
				brindeEstoqueEquipe.setBrindeBean(brinde);
				brindeEstoqueEquipe.setEquipeBean(equipe);
				brindeEstoqueEquipe.setQuantidade(tipomov.equalsIgnoreCase("venda") ? -quantidade : quantidade);
				getEntityManager().persist(brindeEstoqueEquipe);
			}else {
				brindeEstoqueEquipe=resultList.get(0);
			 
				brindeEstoqueEquipe.setQuantidade(brindeEstoqueEquipe.getQuantidade() + (tipomov.equalsIgnoreCase("venda") ? -quantidade : quantidade) );
				getEntityManager().merge(brindeEstoqueEquipe);
			}
		}
	}
	public Integer totalContratosDisponiveis(Integer codigoequipe, Integer codigoContrato){
		Integer ret =null;
		try {
			Query createNativeQuery = getEntityManager().createNativeQuery(
					"select count(c.*) total from Contrato c  where c.inclusao is null and c.datacancelamento is null"+
							(codigoContrato!=null ?  "  and c.codigocontrato=:codigocontrato": "") +
							(codigoequipe!=null ?  "  and c.equipeBean=:codigoequipe": "")
					);
			if(codigoequipe!=null) {
				createNativeQuery.setParameter("codigoequipe", codigoequipe);
			}
			if(codigoContrato!=null) {
				createNativeQuery.setParameter("codigocontrato", codigoContrato);
			}
		
			 Object singleResult = createNativeQuery.getSingleResult();
			if(singleResult!=null) {
				ret = Integer.valueOf(singleResult.toString());
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
		
		return ret;
		
	}

	@Transactional
	public List<Contrato> contratosDisponiveis(Integer codigoequipe, Integer codigoContrato){
		List<Contrato> ret =null;
		try {
			TypedQuery<Contrato> createQuery = getEntityManager().createQuery(
					"select c from Contrato c  where c.inclusao is null and c.datacancelamento is null"+
							(codigoContrato!=null ?  "  and c.codigocontrato=:codigocontrato": "") +
							(codigoequipe!=null ?  "  and c.equipeBean.codigo=:codigoequipe": ""),
					Contrato.class);
			if(codigoequipe!=null) {
				createQuery.setParameter("codigoequipe", codigoequipe);
			}
			if(codigoContrato!=null) {
				createQuery.setParameter("codigocontrato", codigoContrato);
			}
		
			ret = createQuery.getResultList();
			
			ret.sort(new Comparator<Contrato>() {

				@Override
				public int compare(Contrato o1, Contrato o2) {
					// TODO Auto-generated method stub
					return o1.getCodigo().compareTo(o2.getCodigo());
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}	
		
		return ret;
		
	}
	
	public List ContratosEfetivadosUltimos6Meses(Integer codigoEquipe) {
		List ret= null;
		StringBuilder sql = new StringBuilder();
		sql
		.append("select extract(year from c.datavenda) ano , ")
		.append("extract(month from c.datavenda)  mes  ,")
		.append("sum(cp.valor)valores, count(distinct c.codigo) quantidade ")
		.append("from contrato c , contrato_pagamentos cp  ")
						.append("where  c.codigo=cp.codigocontrato and not c.inclusao is null  and c.datavenda >=now() - interval '6 month' ")
						.append(codigoEquipe!=null ? " and c.equipeBean=:equipeBean ": "")
								.append("group by extract(year from c.datavenda),extract(month from c.datavenda) ");
		
		
		Query createNativeQuery = getEntityManager().createNativeQuery(sql.toString());
		if(codigoEquipe!=null) {
			createNativeQuery.setParameter("equipeBean", codigoEquipe);
		}
	
		ret = createNativeQuery.getResultList();
		
		return ret;

	}
	
	@Transactional
	public Contrato getContratoEager(Integer codigo) {
		Contrato c = find(codigo);
		
		if(c!=null) {
			c.getContratoBrindes().size();
			c.getContratoProdutos().size();
		}
		
		
		return c;
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

	public BrindeEstoqueFacade getBrindeEstoqueFacade() {
		brindeEstoqueFacade.setEntityManager(getEntityManager());
		return brindeEstoqueFacade;
	}

	public void setBrindeEstoqueFacade(BrindeEstoqueFacade brindeEstoqueFacade) {
		this.brindeEstoqueFacade = brindeEstoqueFacade;
	}

}
