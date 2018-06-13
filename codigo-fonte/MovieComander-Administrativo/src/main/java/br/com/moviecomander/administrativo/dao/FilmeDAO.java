/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.administrativo.dao;

import br.com.moviecomander.administrativo.entity.Filme;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

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
        } catch (NoResultException noresult) {
            System.out.println("Não foi encontrado nenhum Filme");
        } catch (Exception ex) {
            System.out.println("Erro ao buscar Filme");
            System.out.println(ex);
        }
        return null;
    }

    public Filme findFilmeByNome(String nome) {
        try {
            return (Filme) entityManager
                    .createNamedQuery("Filme.findByNome")
                    .setParameter("nome", nome)
                    .getSingleResult();
        } catch (NoResultException noresult) {
            System.out.println("Não foi encontrado nenhum filme com o nome:".concat(nome));
        } catch (Exception ex) {
            System.out.println("Erro ao buscar Filmes");
            System.out.println(ex);
        }
        return null;
    }

    public boolean insertFilme(Filme filme) {
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(filme); //em.merge(u); for updates
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Erro ao salvar o filme");

            System.out.println(ex);
            return false;
        }
    }

}
