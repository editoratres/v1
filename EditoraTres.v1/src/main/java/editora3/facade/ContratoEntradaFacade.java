package editora3.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import editora3.entidades.Contrato;
import editora3.entidades.ContratoEntrada;
import editora3.entidades.ContratoEntradaIten;
import editora3.entidades.Produto;

public class ContratoEntradaFacade extends AbstractFacade<ContratoEntrada> {

	public ContratoEntradaFacade() {
		super(ContratoEntrada.class);
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
	public void excluirEntradaContrato(ContratoEntrada contratoEntrada) {
		 contratoEntrada = getEntityManager().merge(contratoEntrada);
		for (int i = 0; i <contratoEntrada.getContratoEntradaIten().size(); i++) {
			ContratoEntradaIten contratoEntradaIten = contratoEntrada.getContratoEntradaIten().get(i);
			//getEntityManager().remove(contratoEntradaIten.getContratoBean());
			getEntityManager().remove(contratoEntradaIten);
			if(i% 20==0) {
				getEntityManager().flush();
			}
		}
		 
		getEntityManager().remove(contratoEntrada);
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
	public Integer consultarDisponibilidadeDaFaixa(Integer faixainicial, Integer faixafinal) {
		Integer ret =0;
		
		
		 Query nativeQuery = getEntityManager().createNativeQuery("select sum(upper(numrange(faixainicial,faixafinal,'[]') * numrange(:faixainicial,:faixafinal,'[]'))- " + 
		 		" lower(numrange(faixainicial,faixafinal,'[]') * numrange(:faixainicial,:faixafinal,'[]')) +1 ) " + 
		 		"from contrato_saida " + 
		 		"where numrange(faixainicial,faixafinal,'[]') * numrange(:faixainicial,:faixafinal,'[]')<>'empty' " + 
		 		"");
		 nativeQuery.setParameter("faixainicial", faixainicial);
		 nativeQuery.setParameter("faixafinal", faixafinal);
		 Object singleResult = nativeQuery.getSingleResult();		
		 if(singleResult!=null) {
			 ret = Integer.parseInt(singleResult.toString());
		 }
		
		return ret;
	}
	@Transactional
	public void criarEntrada(ContratoEntrada contratoEntrada) {
		//List<ContratoEntradaIten> contratoEntradaIten = contratoEntrada.getContratoEntradaIten();
		for (int i = contratoEntrada.getFaixainicial(); i  <= contratoEntrada.getFaixafinal(); i++) {
			
			
			Contrato contrato = new Contrato();
			contrato.setDatageracao(contratoEntrada.getData());
			contrato.setCodigocontrato(i);
			getEntityManager().persist(contrato);
			
			ContratoEntradaIten entradaIten = new ContratoEntradaIten();
			entradaIten.setContratoEntrada(contratoEntrada);
			entradaIten.setContratoBean(contrato);
			
			contratoEntrada.getContratoEntradaIten().add(entradaIten);
			
		}
		getEntityManager().persist(contratoEntrada);
	}
	public List<ContratoEntrada> localizarPorFaixa(Integer faixainicial, Integer faixaFinal) {

		Query createNativeQuery = getEntityManager().createNativeQuery("select * from contrato_entrada where numrange(faixainicial,faixafinal,'[]')  * numrange(:faixainicial,:faixafinal,'[]')<>'empty'", ContratoEntrada.class);
		createNativeQuery.setParameter("faixainicial", faixainicial);
		createNativeQuery.setParameter("faixafinal", faixaFinal);
		return createNativeQuery.getResultList();
		  

	}

}
