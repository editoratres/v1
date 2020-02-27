package editora3.infra;

import java.io.Serializable;
import java.sql.Connection;

import javax.enterprise.context.SessionScoped;
 
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;

@SessionScoped
public class JPAUtil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "EditoraTres.v1")
    private EntityManager em;
	
	public Connection getConnection() {
	    Session session = em.unwrap(Session.class);
	    Connection connection = ((SessionImpl) session).connection();
	    return connection;
	}

}
