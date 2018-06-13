package br.com.moviecomander.administrativo.controller;

import br.com.moviecomander.administrativo.dao.FilmeDAO;
import br.com.moviecomander.administrativo.entity.Filme;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastrarFilmeFXMLController implements Initializable {

    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputDuracao;
    @FXML
    private TextField inputGenero;
    @FXML
    private TextArea inputSinopse;
    @FXML
    private ChoiceBox inputClassificacao;
    @FXML
    private TextField inputAno;
    @FXML
    private Text erro;

    @FXML
    private void handleButtonCadastrar(ActionEvent event) {
        //reseta a mensagem
        mostrarMensagem("");
        boolean todosCamposPreenchidos = isTodosCamposPreenchidos();
        if (todosCamposPreenchidos) {
            boolean naoExisteFilmeComMesmoNome = naoExisteFilmeComMesmoNome();
            if (naoExisteFilmeComMesmoNome) {
                Filme filmePopulado = getFilmePopulado();
                boolean filmeInserido = insereFilme(filmePopulado);
                if (filmeInserido) {
                    mostrarMensagem("Filme cadastrado com sucesso");
                    limpaCampos();
                } else {
                    mostrarMensagem("Ocorreu um erro ao tentar inserir o filme, tente novamente mais tarde");
                }
            } else {
                mostrarMensagem("Já existe filme cadastrado com este nome");
            }
        } else {
            mostrarMensagem("Por favor preencha todos os campos");
        }
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        limpaCampos();
        try {
            //Popula lista de classificaoes indicativas
            ObservableList<String> classificacoes = FXCollections.observableArrayList("Livre", "12", "14", "16", "18");
            inputClassificacao.setItems(classificacoes);
            // Força o campo a aceitar apenas numeros. e ter no máximo 4 digitos
            inputAno.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        inputAno.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                    if (newValue.length() > 4) {
                        inputAno.setText(newValue.substring(0, 4));
                    }
                }
            });
            // Força o campo a aceitar apenas numeros.
            inputDuracao.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (!newValue.matches("\\d*")) {
                        inputDuracao.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
            //Erro na versão do java com FX
        } catch (IllegalArgumentException ex) {
            return;
        }
    }

    private void mostrarMensagem(String mensagem) {
        erro.setText(mensagem);
    }

    private boolean isTodosCamposPreenchidos() {
        if (inputClassificacao.getValue() != null) {
            return !StringUtils.isNullOrEmpty(inputNome.getText())
                    && !StringUtils.isNullOrEmpty(inputDuracao.getText())
                    && !StringUtils.isNullOrEmpty(inputGenero.getText())
                    && !StringUtils.isNullOrEmpty(inputSinopse.getText())
                    && !StringUtils.isNullOrEmpty(inputAno.getText())
                    && !StringUtils.isNullOrEmpty(inputClassificacao.getValue().toString());
        }
        return false;

    }

    private boolean naoExisteFilmeComMesmoNome() {
        boolean naoExisteFilme = new FilmeDAO().findFilmeByNome(inputNome.getText()) == null;
        return naoExisteFilme;
    }

    private Filme getFilmePopulado() {
        Filme filme = new Filme();
        filme.setAno(Integer.valueOf(inputAno.getText()));
        filme.setClassificacao(inputClassificacao.getValue().toString());
        filme.setDuracao(Integer.valueOf(inputDuracao.getText()));
        filme.setGenero(inputGenero.getText());
        filme.setNome(inputNome.getText());
        filme.setSinopse(inputSinopse.getText());
        return filme;
    }

    private boolean insereFilme(Filme filmePopulado) {
        boolean filmeInserido = new FilmeDAO().insertFilme(filmePopulado);
        return filmeInserido;
    }

    private void limpaCampos() {
        inputNome.setText("");
        inputDuracao.setText("");
        inputGenero.setText("");
        inputSinopse.setText("");
        inputAno.setText("");
        inputClassificacao.setValue(null);
    }
}
