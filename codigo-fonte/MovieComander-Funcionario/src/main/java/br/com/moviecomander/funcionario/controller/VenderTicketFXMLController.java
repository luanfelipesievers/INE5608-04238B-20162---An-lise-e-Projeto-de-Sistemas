package br.com.moviecomander.funcionario.controller;

import br.com.moviecomander.funcionario.dao.FilmeDAO;
import br.com.moviecomander.funcionario.dao.FuncionarioDAO;
import br.com.moviecomander.funcionario.dao.SessaoDAO;
import br.com.moviecomander.funcionario.dao.TicketDAO;
import br.com.moviecomander.funcionario.entity.Filme;
import br.com.moviecomander.funcionario.entity.Funcionario;
import br.com.moviecomander.funcionario.entity.Sessao;
import br.com.moviecomander.funcionario.entity.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Guilherme on 05/12/2016.
 */
public class VenderTicketFXMLController implements Initializable {

    @FXML
    private ChoiceBox inputFilme;

    @FXML
    private ChoiceBox inputSessao;

    @FXML
    private RadioButton radioButtonInteira;

    @FXML
    private TextField textFilme;
    @FXML
    private TextField textData;
    @FXML
    private TextField textHorario;
    @FXML
    private TextField textSala;
    @FXML
    private TextField textValor;
    @FXML
    private TextField textLinguagem;

    @FXML
    private ChoiceBox inputFuncionario;

    @FXML
    private Text erro;

    private Ticket ticket;


    private void fillChoiceBoxFilme() {
        ObservableList<Filme> data = FXCollections.observableArrayList(new FilmeDAO().findAll());
        inputFilme.setItems(data);
    }

    private void mostrarMensagem(String mensagem) {
        erro.setText(mensagem);
    }

    @FXML
    private void handleButtonBuscar(ActionEvent event) {
        try {
            SessaoDAO sessaoDAO = new SessaoDAO();
            List<Sessao> listaSessoes = sessaoDAO.buscarSessoesDisponiveisPorFilme(((Filme) inputFilme.getValue()).getNome());
            exibirViewSessoes(listaSessoes, event);
        } catch (Exception ex) {
            System.out.println("Erro:: ");
            System.out.println(ex.getMessage());
        }
    }

    private void exibirViewSessoes(List<Sessao> listaSessoes, ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VenderTicket2FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

        //Popula lista de classificaoes indicativas
        ObservableList<Sessao> data = FXCollections.observableArrayList(listaSessoes);
        inputSessao.setItems(data);
    }

    @FXML
    private void handleButtonSelecionarSessao(ActionEvent event) {
        try {
            ticket  = new Ticket();
            ticket.setIdSessao((Sessao) inputSessao.getValue());
            ticket.setDataSessao(new Date());
            exibirViewValores(event);
        } catch (Exception ex) {
            System.out.println("Erro:: ");
            System.out.println(ex.getMessage());
        }
    }

    private void exibirViewValores(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VenderTicket3FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void handleButtonSelecionarTipoEntrada(ActionEvent event) {
        try {
            if (radioButtonInteira.isSelected()) {
                ticket.setValor(20.00);
            } else {
                ticket.setValor(10.00);
            }
            exibirViewDetalhesIngresso(event);
        } catch (Exception ex) {
            System.out.println("Erro:: ");
            System.out.println(ex.getMessage());
        }
    }


    private void exibirViewDetalhesIngresso(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VenderTicket4FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

        textFilme.setText(ticket.getIdSessao().getIdFilme().getNome());
        textData.setText(ticket.getDataSessao().toString());
        textHorario.setText(ticket.getIdSessao().getHorarioInicio().toString());
        textSala.setText(String.valueOf(ticket.getIdSessao().getSala()));
        textValor.setText(String.valueOf(ticket.getValor()));
        textLinguagem.setText(ticket.getIdSessao().getLinguagem());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillChoiceBoxFilme();
    }

    @FXML
    private void handleButtonCancelar(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuInicialFuncionarioFXML.fxml"));
        loader.setRoot(null);
        loader.setController(null);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleButtonImprimir(ActionEvent event) throws IOException {
        diminuiPoltronaLivre();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/VenderTicket5FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

        ObservableList<Funcionario> data = FXCollections.observableArrayList(new FuncionarioDAO().findAllNotDemitido());
        inputFuncionario.setItems(data);
    }

    @FXML
    private void handleButtonRegistrar(ActionEvent event) throws IOException {
        ticket.setIdFuncionario((Funcionario) inputFuncionario.getValue());
        TicketDAO ticketDAO = new TicketDAO();
        ticketDAO.updateTicket(ticket);

        handleButtonCancelar(event);
    }

    private void diminuiPoltronaLivre() {
        ticket.getIdSessao().setPoltronasLivres(ticket.getIdSessao().getPoltronasLivres() - 1);
        SessaoDAO sessaoDAO = new SessaoDAO();
        sessaoDAO.updateSessao(ticket.getIdSessao());
    }
}
