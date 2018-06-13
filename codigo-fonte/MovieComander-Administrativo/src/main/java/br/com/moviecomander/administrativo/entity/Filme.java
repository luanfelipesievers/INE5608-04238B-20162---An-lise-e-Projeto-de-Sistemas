/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.administrativo.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Luan
 */
@Entity
@Table(name = "filme")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filme.findAll", query = "SELECT f FROM Filme f"),
    @NamedQuery(name = "Filme.findById", query = "SELECT f FROM Filme f WHERE f.id = :id"),
    @NamedQuery(name = "Filme.findByAno", query = "SELECT f FROM Filme f WHERE f.ano = :ano"),
    @NamedQuery(name = "Filme.findByClassificacao", query = "SELECT f FROM Filme f WHERE f.classificacao = :classificacao"),
    @NamedQuery(name = "Filme.findByDuracao", query = "SELECT f FROM Filme f WHERE f.duracao = :duracao"),
    @NamedQuery(name = "Filme.findByGenero", query = "SELECT f FROM Filme f WHERE f.genero = :genero"),
    @NamedQuery(name = "Filme.findByNome", query = "SELECT f FROM Filme f WHERE f.nome = :nome"),
    @NamedQuery(name = "Filme.findBySinopse", query = "SELECT f FROM Filme f WHERE f.sinopse = :sinopse")})
public class Filme implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ano")
    private int ano;
    @Basic(optional = false)
    @Column(name = "classificacao")
    private String classificacao;
    @Basic(optional = false)
    @Column(name = "duracao")
    private int duracao;
    @Basic(optional = false)
    @Column(name = "genero")
    private String genero;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "sinopse")
    private String sinopse;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFilme")
    private List<Sessao> sessaoList;

    public Filme() {
    }

    public Filme(Integer id) {
        this.id = id;
    }

    public Filme(Integer id, int ano, String classificacao, int duracao, String genero, String nome, String sinopse) {
        this.id = id;
        this.ano = ano;
        this.classificacao = classificacao;
        this.duracao = duracao;
        this.genero = genero;
        this.nome = nome;
        this.sinopse = sinopse;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    @XmlTransient
    public List<Sessao> getSessaoList() {
        return sessaoList;
    }

    public void setSessaoList(List<Sessao> sessaoList) {
        this.sessaoList = sessaoList;
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
        if (!(object instanceof Filme)) {
            return false;
        }
        Filme other = (Filme) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNome();
    }

}
