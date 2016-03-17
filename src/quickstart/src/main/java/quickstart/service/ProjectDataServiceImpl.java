package quickstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import quickstart.model.ProjectData;

@Transactional
public class ProjectDataServiceImpl implements ProjectDataService {
    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    private EntityManager getEntityManager() {
        return em;
    }

    @SuppressWarnings("unchecked")
	public List<ProjectData> findAll() {
        Query query = getEntityManager().createQuery("select p FROM ProjectData p");
        return query.getResultList();
	}

	public void save(ProjectData projectData) {
		// TODO Auto-generated method stub

	}

	public void remove(int id) {
		// TODO Auto-generated method stub

	}

	public ProjectData find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
