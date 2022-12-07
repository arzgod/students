package darkStudios.servicios;

import darkStudios.entities.Estudiantes;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped

public class EstudiantesService {
    @Inject
    EntityManager em;

    public List<Estudiantes> getAll() {
        List<Estudiantes> games = em.createNamedQuery("Estudiantes.findAll", Estudiantes.class)
                .getResultList();
        return games != null ? games : new ArrayList<>();
    }
    public Estudiantes findById(String carnet) {
        return em.find(Estudiantes.class, carnet);
    }
    @Transactional
    public void update(Estudiantes estudiante) {
        em.merge(estudiante);
    }
    @Transactional
    public void create(Estudiantes estudiante) {
        em.persist(estudiante);
    }
    @Transactional
    public void delete(Estudiantes estudiante) {
        if (!em.contains(estudiante)) {
            estudiante = em.merge(estudiante);
        }
        em.remove(estudiante);
    }
}
