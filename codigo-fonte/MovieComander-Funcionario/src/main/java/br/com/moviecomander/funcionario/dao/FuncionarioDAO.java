/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.funcionario.dao;

import br.com.moviecomander.funcionario.entity.Funcionario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.util.List;

/**
 *
 * @author Bruna
 */
public class FuncionarioDAO {


    protected EntityManager entityManager;

    public FuncionarioDAO() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("MovieComander");
        entityManager = factory.createEntityManager();

    }

    public List<Funcionario> findAllNotDemitido() {
        try {
            return entityManager
                    .createNamedQuery("Funcionario.findAllNotDemitido")
                    .getResultList();
        } catch (NoResultException noresult) {
            System.out.println("Não foi encontrado nenhum Funcionário");
        } catch (Exception ex) {
            System.out.println("Erro ao buscar Funcionário");
            System.out.println(ex);
        }
        return null;
    }
}
