package editora3.facade;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import javax.transaction.Transactional;

import editora3.entidades.Contrato;
import editora3.entidades.ContratoCancelamento;
import editora3.entidades.ContratoCancelamentoIten;
import editora3.entidades.ContratoEntrada;
import editora3.entidades.ContratoSaida;

public class ContratoCancelamentoFacade extends AbstractFacade<ContratoCancelamento> {

	public ContratoCancelamentoFacade() {
		super(ContratoCancelamento.class);
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
	public void excluirSaidaContrato(ContratoCancelamento contratoCancelamento) {
		contratoCancelamento = getEntityManager().merge(contratoCancelamento);
		for (int i = 0; i <contratoCancelamento.getContratoCancelamentoIten().size(); i++) {
			ContratoCancelamentoIten contratoCancelamentoIten = contratoCancelamento.getContratoCancelamentoIten().get(i);
			//getEntityManager().remove(contratoEntradaIten.getContratoBean());
			getEntityManager().remove(contratoCancelamentoIten);
			if(i% 20==0) {
				getEntityManager().flush();
			}
		}
		List<Object> listarEntradasDisponiveisNaFaixa = (List<Object>)listarEntradasDisponiveisNaFaixa(contratoCancelamento.getFaixainicial(), contratoCancelamento.getFaixafinal());
	     Integer quantidade=0;
		 for (Iterator iterator = listarEntradasDisponiveisNaFaixa.iterator(); iterator.hasNext();) {
			Object[] faixasDisponiveis = (Object[]) iterator.next();
			ContratoEntrada contratoEntrada = getEntityManager().find(ContratoEntrada.class, faixasDisponiveis[0],  LockModeType.PESSIMISTIC_READ);
			if(contratoEntrada!=null) {
				quantidade = (faixasDisponiveis[1]==null ? 0 : ((BigDecimal)faixasDisponiveis[1]).intValue() );
				if(quantidade!=null) {
					contratoEntrada.setCancelamentos((contratoEntrada.getCancelamentos()==null ? 0 :contratoEntrada.getCancelamentos())  - quantidade );
					getEntityManager().merge(contratoEntrada);
				}
			}
			
		}
		 
		getEntityManager().remove(contratoCancelamento);
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
		sql.append("select  ")
		.append("sum( " )
		.append("(ce.faixafinal-ce.faixainicial+1)- " )
		.append("		(  " )
		.append("		select   COALESCE( sum(upper(numrange(faixainicial,faixafinal,'[]') * numrange(ce.faixainicial,ce.faixafinal,'[]'))- ")
		.append("		 lower(numrange(faixainicial,faixafinal,'[]') * numrange(ce.faixainicial,ce.faixafinal,'[]')) +1)  ,0) ")
		.append("		from contrato_saida  ")
		.append("		where numrange(faixainicial,faixafinal,'[]') * numrange(ce.faixainicial,ce.faixafinal,'[]')<>'empty' ")
		.append("		)) disponivel ")
		.append("		 from contrato_entrada ce ")
		.append("		where numrange(ce.faixainicial,ce.faixafinal,'[]') * numrange(:faixainicial,:faixafinal,'[]')  <>'empty'");
						 
		 Query nativeQuery = getEntityManager().createNativeQuery(sql.toString());
		 nativeQuery.setParameter("faixainicial", faixainicial);
		 nativeQuery.setParameter("faixafinal", faixafinal);
		 Object singleResult = nativeQuery.getSingleResult();		
		 if(singleResult!=null) {
			 ret = Integer.parseInt(singleResult.toString());
		 }
		return ret;	
	}
	
	@Transactional
	public void criarCancelamento(ContratoCancelamento contratoCancelamento) {
		//List<ContratoEntradaIten> contratoEntradaIten = contratoEntrada.getContratoEntradaIten();
		for (int i = contratoCancelamento.getFaixainicial(); i  <= contratoCancelamento.getFaixafinal(); i++) {
			Contrato contrato = getEntityManager().find(Contrato.class, i);
			
			ContratoCancelamentoIten cancelamentoIten = new ContratoCancelamentoIten();
			cancelamentoIten.setContratoCancelamento(contratoCancelamento);
			cancelamentoIten.setContratoBean(contrato);
			
			contratoCancelamento.getContratoCancelamentoIten().add(cancelamentoIten);
			
		}
		 List<Object> listarEntradasDisponiveisNaFaixa = (List<Object>)listarEntradasDisponiveisNaFaixa(contratoCancelamento.getFaixainicial(), contratoCancelamento.getFaixafinal());
	     Integer quantidade=0;
		 for (Iterator iterator = listarEntradasDisponiveisNaFaixa.iterator(); iterator.hasNext();) {
			Object[] faixasDisponiveis = (Object[]) iterator.next();
			ContratoEntrada contratoEntrada = getEntityManager().find(ContratoEntrada.class, faixasDisponiveis[0],  LockModeType.PESSIMISTIC_READ);
			if(contratoEntrada!=null) {
				quantidade = (faixasDisponiveis[1]==null ? 0 : ((BigDecimal)faixasDisponiveis[1]).intValue() );
				if(quantidade!=null) {
					contratoEntrada.setCancelamentos((contratoEntrada.getCancelamentos()==null ? 0 :contratoEntrada.getCancelamentos())  + quantidade );
					getEntityManager().merge(contratoEntrada);
				}
			}
			
		}
		getEntityManager().persist(contratoCancelamento);
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
