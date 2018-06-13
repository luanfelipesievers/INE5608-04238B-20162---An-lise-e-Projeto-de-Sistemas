/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.moviecomander.cliente.controller;


import br.com.moviecomander.cliente.dao.*;
import br.com.moviecomander.cliente.entity.*;
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

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class ReservarTicketFXMLController implements Initializable {


    //Globais
    private Reserva reserva;

    //Primeira Tela
    @FXML
    private TextField inputCPF;
    //Segunda Tela
    @FXML
    private ChoiceBox inputFilme;
    ;
    //Terceira Tela
    @FXML
    private ChoiceBox inputSessao;
    //Quarta Tela
    @FXML
    private RadioButton radioButtonMeia;
    @FXML
    private RadioButton radioButtonInteira;
    ;
    //Quinta Tela
    @FXML
    private TextField textFilme;
    @FXML
    private TextField textData;
    @FXML
    private TextField textHorario;
    ;
    @FXML
    private TextField textSala;
    @FXML
    private TextField textValor;
    @FXML
    private TextField textLinguagem;
    //Sexta Tela
    @FXML
    private Text textCodigoReserva;
    //Todas Telas
    @FXML
    private Text erro;

    @FXML
    private void handleButtonOk(ActionEvent event) {
        try {
            if (isCpfValido(inputCPF.getText())) {

                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = clienteDAO.findCliente(inputCPF.getText());
                if (cliente == null || !cliente.getBloqueado()) {
                    reserva = new Reserva();
                    if (cliente == null) {

                        reserva.setIdCliente(new Cliente(null, inputCPF.getText(), false));
                    } else {

                        reserva.setIdCliente(cliente);
                    }

                    FilmeDAO filmeDAO = new FilmeDAO();
                    List<Filme> filmesDisponiveis = filmeDAO.findFilmesDisponiveis();

                    exibirViewFilmes(filmesDisponiveis, event);


                } else {
                    mostrarMensagem("Usuário bloqueado. Você atingiu o número máximo de reservas não retiradas");
                }

            } else {
                mostrarMensagem("CPF Inválido!");
            }
        } catch (Exception ex) {
            System.out.println("Erro:: ");
            System.out.println(ex.getMessage());
        }
    }

    private void exibirViewFilmes(List<Filme> filmesDisponiveis, ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReservarTicket2FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

        //Popula lista de classificaoes indicativas
        ObservableList<Filme> data = FXCollections.observableArrayList(filmesDisponiveis);
        inputFilme.setItems(data);


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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReservarTicket3FXML.fxml"));
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
            reserva.setIdTicket(new Ticket());
            reserva.getIdTicket().setIdSessao((Sessao) inputSessao.getValue());
            reserva.getIdTicket().setDataSessao(new Date());
            exibirViewValores(event);
        } catch (Exception ex) {
            System.out.println("Erro:: ");
            System.out.println(ex.getMessage());
        }
    }

    private void exibirViewValores(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReservarTicket4FXML.fxml"));
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
                reserva.getIdTicket().setValor(20.00);
            } else {
                reserva.getIdTicket().setValor(10.00);
            }
            exibirViewDetalhesReserva(event);
        } catch (Exception ex) {
            System.out.println("Erro:: ");
            System.out.println(ex.getMessage());
        }
    }

    ;

    private void exibirViewDetalhesReserva(ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReservarTicket5FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

        textFilme.setText(reserva.getIdTicket().getIdSessao().getIdFilme().getNome());
        textData.setText(reserva.getIdTicket().getDataSessao().toString());
        textHorario.setText(reserva.getIdTicket().getIdSessao().getHorarioInicio().toString());
        textSala.setText(String.valueOf(reserva.getIdTicket().getIdSessao().getSala()));
        textValor.setText(String.valueOf(reserva.getIdTicket().getValor()));
        textLinguagem.setText(reserva.getIdTicket().getIdSessao().getLinguagem());

    }

    @FXML
    private void handleButtonConfirmar(ActionEvent event) {
        try {
            String codigoReserva = gerarCodigoReserva();

            TicketDAO ticketDAO = new TicketDAO();
            ticketDAO.insertTicket(reserva.getIdTicket());
            if (reserva.getIdCliente().getId() == null) {
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.insertCliente(reserva.getIdCliente());
            }

            reserva.setCodigoReserva(codigoReserva);

            ReservaDAO reservaDAO = new ReservaDAO();
            reservaDAO.insertReserva(reserva);
            diminuiPoltronaLivre();

            exibirCodigoReserva(codigoReserva, event);

        } catch (Exception ex) {
            System.out.println("Erro:: ");
            System.out.println(ex.getMessage());
        }
    }

    private void diminuiPoltronaLivre() {
        reserva.getIdTicket().getIdSessao().setPoltronasLivres(reserva.getIdTicket().getIdSessao().getPoltronasLivres() - 1);
        SessaoDAO sessaoDAO = new SessaoDAO();
        sessaoDAO.updateSessao(reserva.getIdTicket().getIdSessao());
    }

    private String gerarCodigoReserva() {
        Random random = new Random();
        Integer numero = random.nextInt(1000);
        return numero.toString();
    }

    private void exibirCodigoReserva(String codigoReserva, ActionEvent event) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ReservarTicket6FXML.fxml"));
        loader.setRoot(null);
        loader.setController(this);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();

        textCodigoReserva.setText(codigoReserva);

    }

    ;


    /*--------------------------------------------------------------------------*/

    @FXML
    private void handleButtonImprimir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleButtonCancelar(ActionEvent event) {
        System.exit(0);
    }


    private void mostrarMensagem(String mensagem) {
        erro.setText(mensagem);
    }

    /*----------------------*/
    private boolean isCpfValido(String cpf) {
        if (cpf == null || "".equals(cpf)) {
            return false;
        }
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
            d1 = d1 + (11 - nCount) * digitoCPF;

            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
            d2 = d2 + (12 - nCount) * digitoCPF;
        }
        ;

        //Primeiro resto da divisão por 11.
        resto = (d1 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2)
            digito1 = 0;
        else
            digito1 = 11 - resto;

        d2 += 2 * digito1;

        //Segundo resto da divisão por 11.
        resto = (d2 % 11);

        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2)
            digito2 = 0;
        else
            digito2 = 11 - resto;

        //Digito verificador do CPF que está sendo validado.
        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

        //Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
        return nDigVerific.equals(nDigResult);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
