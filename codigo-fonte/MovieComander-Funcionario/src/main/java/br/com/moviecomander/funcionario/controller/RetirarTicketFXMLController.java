/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.funcionario.controller;


import br.com.moviecomander.funcionario.dao.FuncionarioDAO;
import br.com.moviecomander.funcionario.dao.ReservaDAO;
import br.com.moviecomander.funcionario.dao.TicketDAO;
import br.com.moviecomander.funcionario.entity.Funcionario;
import br.com.moviecomander.funcionario.entity.Reserva;
import com.mysql.jdbc.StringUtils;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RetirarTicketFXMLController implements Initializable {

    //Primeira Tela
    @FXML
    private TextField inputCPF;
    @FXML
    private TextField inputCodigo;


    //Segunda Tela
    @FXML
    private TextField textCodigo;
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

    //Terceira Tela
    @FXML
    private ChoiceBox inputFuncionario;

    //Todas Telas
    @FXML
    private Text erro;


    private Reserva reserva;

    @FXML
    private void handleButtonBuscar(ActionEvent event) {
        try {
            mostrarMensagem("");
            boolean preenchido = isTodosCamposPreenchidos();
            if (preenchido) {
                ReservaDAO reservaDao = new ReservaDAO();
                reserva = reservaDao.findByCpfAndCodigo(inputCPF.getText(), inputCodigo.getText());
                if (reserva != null) {
                    exibirViewDadosReserva(reserva, event);
                } else {
                    mostrarMensagem("Reserva não localizada");
                }
            } else {
                mostrarMensagem("Por favor, preencha todos os campos");
            }
        } catch (Exception ex) {
            System.out.println("Erro");
            System.out.println(ex);
        }

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
    private void handleButtonImprimir(ActionEvent event) {
        try {
            exibirViewRetiradaFuncionario(event);
            fillChoiceBoxFuncionario();
        } catch (Exception ex) {
            System.out.println("Erro");
            System.out.println(ex);
        }
    }

    @FXML
    private void handleButtonSelecionarFuncionario(ActionEvent event) {
        try {
            Funcionario funcionarioSelecionado = (Funcionario) inputFuncionario.getValue();
            reserva.getIdTicket().setIdFuncionario(funcionarioSelecionado);
            TicketDAO ticketDAO = new TicketDAO();
            ticketDAO.updateTicket(reserva.getIdTicket());
            reserva.setRetirado(true);
            ReservaDAO reservaDAO = new ReservaDAO();
            reservaDAO.updateReserva(reserva);
            mostrarMensagem("Ticket gerado com sucesso");
            //Pause for 4 seconds
//            Thread.sleep(10000);
            handleButtonCancelar(event);
        } catch (Exception ex) {
            System.out.println("Erro");
            System.out.println(ex);
        }
    }

    private void exibirViewDadosReserva(Reserva reserva, ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RetirarTicket2FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

        textCodigo.setText(reserva.getCodigoReserva());
        textFilme.setText(reserva.getIdTicket().getIdSessao().getIdFilme().getNome());
        textData.setText(reserva.getIdTicket().getDataSessao().toString());
        textSala.setText(String.valueOf(reserva.getIdTicket().getIdSessao().getSala()));
        textHorario.setText(String.valueOf(reserva.getIdTicket().getIdSessao().getHorarioInicio()));
        textValor.setText(String.valueOf(reserva.getIdTicket().getValor()));


    }

    private void mostrarMensagem(String mensagem) {
        erro.setText(mensagem);
    }

    private boolean isTodosCamposPreenchidos() {
        return !StringUtils.isNullOrEmpty(inputCodigo.getText())
                && !StringUtils.isNullOrEmpty(inputCPF.getText());
    }

    private void exibirViewRetiradaFuncionario(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RetirarTicket3FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

    }

    private void fillChoiceBoxFuncionario() {
        //Popula lista de classificaoes indicativas
        ObservableList<Funcionario> data = FXCollections.observableArrayList(new FuncionarioDAO().findAllNotDemitido());
        inputFuncionario.setItems(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
