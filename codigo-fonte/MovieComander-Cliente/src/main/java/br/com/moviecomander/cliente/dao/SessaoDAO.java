/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.cliente.dao;

import br.com.moviecomander.cliente.entity.Sessao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

public class SessaoDAO {

    protected EntityManager entityManager;

    public SessaoDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MovieComander");
        entityManager = factory.createEntityManager();

    }

    public void updateSessao(Sessao sessao) {
        try {

            entityManager.getTransaction().begin();
            entityManager.merge(sessao);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            System.out.println("Erro ao atualizar o sessao");

            System.out.println(ex);
        }
    }

    public List<Sessao> buscarSessoesDisponiveisPorFilme(String nomeFilme) {
        try {
            return entityManager
                    .createNamedQuery("Sessao.findByNomeFilme")
                    .setParameter("nome", nomeFilme)
                    .getResultList();

        } catch (NoResultException noresult) {
            System.out.println("Não foi encontrado nenhums sessao");
        } catch (Exception ex) {
            System.out.println("Erro ao buscar Sessao");
            System.out.println(ex);
        }
        return null;
    }

}
