package editora3.facade;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.Contrato;
import editora3.entidades.ContratoCancelamento;
import editora3.entidades.ContratoEntrada;
import editora3.entidades.ContratoSaida;
import editora3.entidades.ContratoSaidaIten;
import editora3.entidades.Produto;

public class ContratoSaidaFacade extends AbstractFacade<ContratoSaida> {

	public ContratoSaidaFacade() {
		super(ContratoSaida.class);
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
	public void excluirSaidaContrato(ContratoSaida contratoSaida) {
		contratoSaida = getEntityManager().merge(contratoSaida);
		for (int i = contratoSaida.getFaixainicial(); i  <= contratoSaida.getFaixafinal(); i++) {
			 
			//getEntityManager().remove(contratoEntradaIten.getContratoBean());
			Query createQuery = getEntityManager().createNativeQuery("update contrato set equipeBean=null , vendedorbean=null, datasaida=null where codigocontrato=:ccodigocontrato", Contrato.class);
			
			createQuery.setParameter("ccodigocontrato", i);
			
			createQuery.executeUpdate();
			 
			 
			
			if(i% 20==0) {
				getEntityManager().flush();
			}
		}
		
		 
		getEntityManager().remove(contratoSaida);
	}
	public Integer consultarContratosEmUso(Integer faixainicial, Integer faixafinal) {
		Integer ret=0;
		 Query nativeQuery = getEntityManager().createNativeQuery("select count(*) from contrato c where c.codigocontrato >= :faixainicial and c.codigocontrato <= :faixafinal and not c.inclusao is null");
		 nativeQuery.setParameter("faixainicial", faixainicial);
		 nativeQuery.setParameter("faixafinal", faixafinal);
		 Object singleResult = nativeQuery.getSingleResult();		
		 if(singleResult!=null) {
			 ret = Integer.parseInt(singleResult.toString());
		 }
		return ret;		
	}
	
	public Integer verificarQuantidadeDeContratosDisponiveis(Integer faixainicial, Integer faixafinal) {
		Integer ret=0;
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("select count(*) totalcontratos from contrato c where c.codigocontrato>=:faixainicial and c.codigocontrato<=:faixafinal  and c.datasaida is null and c.datacancelamento is null"); 
		Query createNativeQuery = getEntityManager().createNativeQuery(sql.toString());
		createNativeQuery.setParameter("faixainicial", faixainicial);
		createNativeQuery.setParameter("faixafinal", faixafinal);
		 Object singleResult = createNativeQuery.getSingleResult();		
		 if(singleResult!=null) {
			 ret = Integer.parseInt(singleResult.toString());
		 } 
		return ret;	
	}
	
	@Transactional
	public void criarSaida(ContratoSaida contratoSaida) {
		//List<ContratoEntradaIten> contratoEntradaIten = contratoEntrada.getContratoEntradaIten();
		for (int i = contratoSaida.getFaixainicial(); i  <= contratoSaida.getFaixafinal(); i++) {
			
			
			Query createQuery = getEntityManager().createNativeQuery("update contrato set equipeBean=:equipebean "+ 
			(contratoSaida.getVendedorBean()==null ? "" : ", vendedorbean=:vendedorbean")
			+ ", datasaida=:datasaida where codigocontrato=:ccodigocontrato", Contrato.class);
			createQuery.setParameter("equipebean", contratoSaida.getEquipeBean().getCodigo());
			if(contratoSaida.getVendedorBean()!=null) {
				createQuery.setParameter("vendedorbean", contratoSaida.getVendedorBean().getCodigo());
			}
			createQuery.setParameter("datasaida", new Date());
			createQuery.setParameter("ccodigocontrato", i);
			
			createQuery.executeUpdate();
			
			
			
			/*ContratoSaidaIten saidaIten = new ContratoSaidaIten();
			saidaIten.setContratoSaida(contratoSaida);
			saidaIten.setContratoBean(contrato);
			
			contratoSaida.getContratoSaidaIten().add(saidaIten);*/
			
		}
		  
		getEntityManager().persist(contratoSaida);
	}
	public List listarEntradasDisponiveisNaFaixa(Integer faixainicial, Integer faixaFinal){
	
		StringBuilder sql = new StringBuilder();

		sql.append("select  ce.codigo, ")
		//.append("numrange(ce.faixainicial,ce.faixafinal,'[]') * numrange(:faixainicial,:faixainicial,'[]'), ")
		.append("upper(numrange(ce.faixainicial,ce.faixafinal,'[]') * numrange(:faixainicial,:faixafinal,'[]')) - lower(numrange(ce.faixainicial,ce.faixafinal,'[]') * numrange(:faixainicial,:faixafinal,'[]'))+1 quantidade ")
		.append("from contrato_entrada ce  ")
		.append("where numrange(ce.faixainicial,ce.faixafinal,'[]') * numrange(:faixainicial,:faixafinal,'[]')  <>'empty'; ");
		
		Query createNativeQuery = getEntityManager().createNativeQuery(sql.toString());
		createNativeQuery.setParameter("faixainicial", faixainicial);
		createNativeQuery.setParameter("faixafinal", faixaFinal);
		return createNativeQuery.getResultList();
	}
	
	public List<ContratoSaida> verificarFaixasDeSaida(Integer faixainicial, Integer faixaFinal) {

		Query createNativeQuery = getEntityManager().createNativeQuery("select * from contrato_saida where numrange(faixainicial,faixafinal,'[]')  * numrange(:faixainicial,:faixafinal,'[]')<>'empty'", ContratoSaida.class);
		createNativeQuery.setParameter("faixainicial", faixainicial);
		createNativeQuery.setParameter("faixafinal", faixaFinal);
		return createNativeQuery.getResultList();
		  

	}
	public List<ContratoCancelamento> verificarFaixasDeCancelamentos(Integer faixainicial, Integer faixaFinal) {

		Query createNativeQuery = getEntityManager().createNativeQuery("select * from contrato_cancelamento where numrange(faixainicial,faixafinal,'[]')  * numrange(:faixainicial,:faixafinal,'[]')<>'empty'", ContratoCancelamento.class);
		createNativeQuery.setParameter("faixainicial", faixainicial);
		createNativeQuery.setParameter("faixafinal", faixaFinal);
		return createNativeQuery.getResultList();
		  

	}
}
