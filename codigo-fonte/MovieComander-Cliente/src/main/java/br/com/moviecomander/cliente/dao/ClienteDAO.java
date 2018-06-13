package br.com.moviecomander.cliente.dao;

import br.com.moviecomander.cliente.entity.Cliente;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 * Created by Luan on 27/11/2016.
 */
public class ClienteDAO {

    protected EntityManager entityManager;

    public ClienteDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MovieComander");
        entityManager = factory.createEntityManager();

    }

    public void insertCliente(Cliente cliente) {
        try {

            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } catch (Exception ex) {
            System.out.println("Erro ao inserir o cliente");

            System.out.println(ex);
        }
    }

    public Cliente findCliente(String cpf) {
        try {
            return (Cliente) entityManager
                    .createNamedQuery("Cliente.findByCpf")
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException noresult) {
            System.out.println("Não foi encontrado nenhum Cliente");
        } catch (Exception ex) {
            System.out.println("Erro ao buscar Cliente");
            System.out.println(ex);
        }
        return null;
    }
}
