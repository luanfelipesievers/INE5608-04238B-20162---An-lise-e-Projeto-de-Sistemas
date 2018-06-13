/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.administrativo.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Luan
 */
@Entity
@Table(name = "administrador")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a"),
        @NamedQuery(name = "Administrador.findById", query = "SELECT a FROM Administrador a WHERE a.id = :id"),
        @NamedQuery(name = "Administrador.findByLogin", query = "SELECT a FROM Administrador a WHERE a.login = :login"),
        @NamedQuery(name = "Administrador.findByLoginSenha", query = "SELECT a FROM Administrador a WHERE a.login = :login and a.senha = :senha"),
        @NamedQuery(name = "Administrador.findBySenha", query = "SELECT a FROM Administrador a WHERE a.senha = :senha")})
public class Administrador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "senha")
    private String senha;

    public Administrador() {
    }

    public Administrador(Integer id) {
        this.id = id;
    }

    public Administrador(Integer id, String login, String senha) {
        this.id = id;
        this.login = login;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrador)) {
            return false;
        }
        Administrador other = (Administrador) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.moviecomander.funcionario.entity.Administrador[ id=" + id + " ]";
    }

}
