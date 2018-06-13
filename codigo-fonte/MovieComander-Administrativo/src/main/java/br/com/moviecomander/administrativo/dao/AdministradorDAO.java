package br.com.moviecomander.administrativo.dao;

import br.com.moviecomander.administrativo.entity.Administrador;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 * Created by Guilherme on 06/12/2016.
 */
public class AdministradorDAO {

    protected EntityManager entityManager;

    public AdministradorDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MovieComander");
        entityManager = factory.createEntityManager();

    }

    public Administrador findByLoginSenha(String login, String senha) {
        try {
            return (Administrador) entityManager
                    .createNamedQuery("Administrador.findByLoginSenha")
                    .setParameter("login", login)
                    .setParameter("senha", senha)
                    .getSingleResult();
        } catch (NoResultException noresult) {
            System.out.println("Não foi encontrado nenhum login:".concat(login));
        } catch (Exception ex) {
            System.out.println("Erro ao buscar login");
            System.out.println(ex);
        }
        return null;

    }
}
