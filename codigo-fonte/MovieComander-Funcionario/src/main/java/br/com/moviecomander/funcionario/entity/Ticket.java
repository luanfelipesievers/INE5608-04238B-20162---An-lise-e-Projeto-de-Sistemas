/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.funcionario.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Luan
 */
@Entity
@Table(name = "ticket")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t"),
        @NamedQuery(name = "Ticket.findById", query = "SELECT t FROM Ticket t WHERE t.id = :id"),
        @NamedQuery(name = "Ticket.findByDataSessao", query = "SELECT t FROM Ticket t WHERE t.dataSessao = :dataSessao"),
        @NamedQuery(name = "Ticket.findByValor", query = "SELECT t FROM Ticket t WHERE t.valor = :valor")})
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "dataSessao")
    private Date dataSessao;

    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;

    @JoinColumn(name = "idFuncionario", referencedColumnName = "id")
    @ManyToOne(optional = true)
    private Funcionario idFuncionario;

    @JoinColumn(name = "idSessao", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Sessao idSessao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTicket")
    private List<Reserva> reservaList;

    public Ticket() {
    }

    public Ticket(Integer id) {
        this.id = id;
    }

    public Ticket(Integer id, Date dataSessao, double valor) {
        this.id = id;
        this.dataSessao = dataSessao;
        this.valor = valor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataSessao() {
        return dataSessao;
    }

    public void setDataSessao(Date dataSessao) {
        this.dataSessao = dataSessao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Funcionario getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Funcionario idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public Sessao getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(Sessao idSessao) {
        this.idSessao = idSessao;
    }

    @XmlTransient
    public List<Reserva> getReservaList() {
        return reservaList;
    }

    public void setReservaList(List<Reserva> reservaList) {
        this.reservaList = reservaList;
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
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.moviecomander.funcionario.entity.Ticket[ id=" + id + " ]";
    }
    
}
