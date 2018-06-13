/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.cliente.dao;

import br.com.moviecomander.cliente.entity.Ticket;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Bruna
 */
public class TicketDAO {
    protected EntityManager entityManager;

    public TicketDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MovieComander");
        entityManager = factory.createEntityManager();

    }

    public void insertTicket(Ticket ticket) {
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(ticket);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            System.out.println("Erro ao inserir o Ticket");

            System.out.println(ex);
        }
    }

    public void updateTicket(Ticket ticket) {
        try {

            entityManager.getTransaction().begin();
            entityManager.merge(ticket);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            System.out.println("Erro ao atualizar o Ticket");

            System.out.println(ex);
        }
    }
}
