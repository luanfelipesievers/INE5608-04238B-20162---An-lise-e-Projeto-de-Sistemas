package br.com.moviecomander.administrativo.dao;

import br.com.moviecomander.administrativo.entity.Sessao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;



/**
 * Created by Guilherme on 06/12/2016.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class SessaoDAO {

    protected EntityManager entityManager;

    public SessaoDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MovieComander");
        entityManager = factory.createEntityManager();

    }

    public boolean insertSessao(Sessao sessao) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(sessao);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception ex) {
            System.out.println("Erro ao salvar o Sessao");

            System.out.println(ex);
            return false;
        }
    }

    public List<Sessao> findByHorarioSala(Date horarioInicio, Date horarioFim) {
        try {
            return entityManager
                    .createNamedQuery("Sessao.findByHorarioSala")
                    .setParameter("horarioInicio", horarioInicio)
                    .setParameter("horarioFim", horarioFim)
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
