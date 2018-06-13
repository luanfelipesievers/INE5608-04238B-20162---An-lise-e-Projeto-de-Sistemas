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
import java.util.Date;
import java.util.List;

/**
 * @author Luan
 */
@Entity
@Table(name = "sessao")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Sessao.findAll", query = "SELECT s FROM Sessao s"),
        @NamedQuery(name = "Sessao.findById", query = "SELECT s FROM Sessao s WHERE s.id = :id"),
        @NamedQuery(name = "Sessao.findByAtiva", query = "SELECT s FROM Sessao s WHERE s.ativa = :ativa"),
        @NamedQuery(name = "Sessao.findByHorarioFim", query = "SELECT s FROM Sessao s WHERE s.horarioFim = :horarioFim"),
        @NamedQuery(name = "Sessao.findByHorarioInicio", query = "SELECT s FROM Sessao s WHERE s.horarioInicio = :horarioInicio"),
        @NamedQuery(name = "Sessao.findByLinguagem", query = "SELECT s FROM Sessao s WHERE s.linguagem = :linguagem"),
        @NamedQuery(name = "Sessao.findByPoltronasLivres", query = "SELECT s FROM Sessao s WHERE s.poltronasLivres = :poltronasLivres"),
        @NamedQuery(name = "Sessao.findBySala", query = "SELECT s FROM Sessao s WHERE s.sala = :sala"),
        @NamedQuery(name = "Sessao.findByHorarioSala", query = "SELECT s FROM Sessao s WHERE s.sala = :sala and (s.horarioInicio = :horarioInicio or s.horarioFim = :horarioFim)")})
public class Sessao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ativa")
    private boolean ativa;
    @Basic(optional = false)
    @Column(name = "horarioFim")
    @Temporal(TemporalType.TIME)
    private Date horarioFim;
    @Basic(optional = false)
    @Column(name = "horarioInicio")
    @Temporal(TemporalType.TIME)
    private Date horarioInicio;
    @Basic(optional = false)
    @Column(name = "linguagem")
    private String linguagem;
    @Basic(optional = false)
    @Column(name = "poltronasLivres")
    private int poltronasLivres;
    @Basic(optional = false)
    @Column(name = "sala")
    private int sala;
    @JoinColumn(name = "idFilme", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Filme idFilme;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSessao")
    private List<Ticket> ticketList;

    public Sessao() {
    }

    public Sessao(Integer id) {
        this.id = id;
    }

    public Sessao(Integer id, boolean ativa, Date horarioFim, Date horarioInicio, String linguagem, int poltronasLivres, int sala) {
        this.id = id;
        this.ativa = ativa;
        this.horarioFim = horarioFim;
        this.horarioInicio = horarioInicio;
        this.linguagem = linguagem;
        this.poltronasLivres = poltronasLivres;
        this.sala = sala;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Date getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(Date horarioFim) {
        this.horarioFim = horarioFim;
    }

    public Date getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(Date horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public String getLinguagem() {
        return linguagem;
    }

    public void setLinguagem(String linguagem) {
        this.linguagem = linguagem;
    }

    public int getPoltronasLivres() {
        return poltronasLivres;
    }

    public void setPoltronasLivres(int poltronasLivres) {
        this.poltronasLivres = poltronasLivres;
    }

    public int getSala() {
        return sala;
    }

    public void setSala(int sala) {
        this.sala = sala;
    }

    public Filme getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Filme idFilme) {
        this.idFilme = idFilme;
    }

    @XmlTransient
    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
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
        if (!(object instanceof Sessao)) {
            return false;
        }
        Sessao other = (Sessao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.moviecomander.funcionario.entity.Sessao[ id=" + id + " ]";
    }

}
