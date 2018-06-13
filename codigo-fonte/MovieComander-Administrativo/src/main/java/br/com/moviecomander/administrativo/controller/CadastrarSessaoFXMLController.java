package br.com.moviecomander.administrativo.controller;

import br.com.moviecomander.administrativo.dao.FilmeDAO;
import br.com.moviecomander.administrativo.dao.SessaoDAO;
import br.com.moviecomander.administrativo.entity.Filme;
import br.com.moviecomander.administrativo.entity.Sessao;
import com.mysql.jdbc.StringUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

/**
 * Created by Guilherme on 06/12/2016.
 */
public class CadastrarSessaoFXMLController implements Initializable {

    SimpleDateFormat dateFormat = new SimpleDateFormat("hhmm");
    @FXML
    private ChoiceBox inputFilme;
    @FXML
    private TextField inputHorarioInicio;
    @FXML
    private TextField inputHorarioFim;
    @FXML
    private Text erro;
    @FXML
    private ToggleGroup myToggleGroupSala;
    @FXML
    private ToggleGroup myToggleGroupLinguagem;

    @FXML
    private void handleButtonCadastrar(ActionEvent event) {
        //reseta a mensagem
        mostrarMensagem("");
        boolean todosCamposPreenchidos = isTodosCamposPreenchidos();
        if (todosCamposPreenchidos) {
            Sessao sessaoPopulada = getSessaoPopulada();
            boolean salaDisponivel = isSalaDisponivel(sessaoPopulada);
            if (salaDisponivel) {
                boolean sessaoInserida = insereSessao(sessaoPopulada);
                if (sessaoInserida) {
                    mostrarMensagem("Sessao cadastrada com sucesso");
                }
            } else {
                mostrarMensagem("A sala solicitada nao se encontra disponivel para horario solicitado");
            }
        } else {
            mostrarMensagem("Por favor, preencha todos os campos");
        }
    }

    private boolean insereSessao(Sessao sessaoPopulada) {
        boolean sessaoInserida = new SessaoDAO().insertSessao(sessaoPopulada);
        return sessaoInserida;
    }

    private boolean isSalaDisponivel(Sessao sessaoPopulada) {
        boolean naoExisteSessaoHorario = new SessaoDAO().findByHorarioSala(sessaoPopulada.getHorarioInicio(), sessaoPopulada.getHorarioFim()) == null;
        return naoExisteSessaoHorario;
    }

    private Sessao getSessaoPopulada() {
        Sessao sessao = new Sessao();
        sessao.setAtiva(true);
        try {
            sessao.setHorarioInicio(dateFormat.parse(inputHorarioInicio.getText()));
            sessao.setHorarioFim(dateFormat.parse(inputHorarioFim.getText()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sessao.setIdFilme((Filme) inputFilme.getValue());
        sessao.setSala(Integer.parseInt(((RadioButton) myToggleGroupSala.getSelectedToggle()).getText()));
        sessao.setLinguagem(((RadioButton) myToggleGroupLinguagem.getSelectedToggle()).getText());
        sessao.setPoltronasLivres(25);
        return sessao;
    }

    private void mostrarMensagem(String mensagem) {
        erro.setText(mensagem);
    }

    private boolean isTodosCamposPreenchidos() {
        if (inputFilme.getValue() != null) {
            return !StringUtils.isNullOrEmpty(inputHorarioInicio.getText())
                    && !StringUtils.isNullOrEmpty(inputHorarioFim.getText())
                    && myToggleGroupSala.selectedToggleProperty() != null
                    && myToggleGroupLinguagem.selectedToggleProperty() != null;
        }
        return false;

    }

    private void fillChoiceBoxFilme() {
        ObservableList<Filme> data = FXCollections.observableArrayList(new FilmeDAO().findAll());
        inputFilme.setItems(data);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillChoiceBoxFilme();
        // Força o campo a aceitar apenas numeros.
        inputHorarioInicio.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputHorarioInicio.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        inputHorarioFim.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    inputHorarioFim.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }

    @FXML
    private void handleButtonCancelar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuInicialAdministrativoFXML.fxml"));
        loader.setRoot(null);
        loader.setController(null);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
