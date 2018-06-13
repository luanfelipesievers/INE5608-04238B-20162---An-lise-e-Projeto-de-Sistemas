package br.com.moviecomander.administrativo.controller;

import br.com.moviecomander.administrativo.dao.AdministradorDAO;
import br.com.moviecomander.administrativo.entity.Administrador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Guilherme on 06/12/2016.
 */
public class LoginAdministrativoFXMLController {
    @FXML
    private TextField inputUsuario;

    @FXML
    private TextField inputSenha;

    @FXML
    private Text erro;


    public void handleButtonLogin(ActionEvent actionEvent) throws IOException {
        //reseta a mensagem
        mostrarMensagem("");
        boolean todosCamposPreenchidos = isTodosCamposPreenchidos();
        if (todosCamposPreenchidos) {
            boolean usuarioValidado = validarUsuario();
            if (usuarioValidado) {
                irParaMenuAdministrador(actionEvent);
            } else {
                mostrarMensagem("Dados nao localizados");
            }
        }else{
            mostrarMensagem("Por favor preencha todos os campos");
        }
    }

    private boolean validarUsuario () {
        Administrador admin = new AdministradorDAO().findByLoginSenha(inputUsuario.getText(), inputSenha.getText());
        if (admin != null) {
            return true;
        }
        return false;
    }

    private void mostrarMensagem(String mensagem) {
        erro.setText(mensagem);
    }

    private boolean isTodosCamposPreenchidos() {
        if (inputUsuario.getText().trim().hashCode() != 0 && inputSenha.getText().trim().hashCode() != 0) {
            return true;
        }
        return false;
    }

    private void irParaMenuAdministrador(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuInicialAdministrativoFXML.fxml"));
        loader.setRoot(null);
        loader.setController(null);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    public void handleButtonFechar(ActionEvent actionEvent) {
        System.exit(0);
    }
}
