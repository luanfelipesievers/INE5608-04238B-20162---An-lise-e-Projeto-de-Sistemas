package br.com.moviecomander.funcionario.dao;

import br.com.moviecomander.funcionario.entity.Filme;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by Guilherme on 05/12/2016.
 */
public class FilmeDAO {

    protected EntityManager entityManager;

    public FilmeDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MovieComander");
        entityManager = factory.createEntityManager();

    }

    public List<Filme> findAll() {
        try {
            return entityManager
                    .createNamedQuery("Filme.findAll")
                    .getResultList();
        }catch (NoResultException noresult) {
            System.out.println("Não foi encontrado nenhum Filme");
        } catch (Exception ex) {
            System.out.println("Erro ao buscar Filme");
            System.out.println(ex);
        }
        return null;
    }
}
