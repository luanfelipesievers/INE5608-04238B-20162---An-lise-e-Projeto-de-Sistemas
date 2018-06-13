/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.cliente.dao;

import br.com.moviecomander.cliente.entity.Reserva;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;


/**
 * @author Bruna
 */
public class ReservaDAO {

    protected EntityManager entityManager;

    public ReservaDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MovieComander");
        entityManager = factory.createEntityManager();

    }

    public Reserva findByCpfAndCodigo(String cpf, String codigo) {
        try {
            return (Reserva) entityManager
                    .createNamedQuery("Reserva.findByCpfAndCodigo")
                    .setParameter("cpf", cpf)
                    .setParameter("codigoReserva", codigo)
                    .getSingleResult();
        } catch (NoResultException noresult) {
            System.out.println("Não foi encontrado nenhuma reserva com os dados informadoa");
        } catch (Exception ex) {
            System.out.println("Erro ao buscar Reserva");
            System.out.println(ex);
        }
        return null;
    }


    public void updateReserva(Reserva reserva) {
        try {

            entityManager.getTransaction().begin();
            entityManager.merge(reserva);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            System.out.println("Erro ao atualizar o Reserva");

            System.out.println(ex);
        }
    }

    public void insertReserva(Reserva reserva) {
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(reserva);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            System.out.println("Erro ao inserir o Reserva");

            System.out.println(ex);
        }
    }
}
