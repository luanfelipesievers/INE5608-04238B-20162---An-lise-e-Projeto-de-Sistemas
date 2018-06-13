package br.com.moviecomander.administrativo.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Guilherme on 06/12/2016.
 */
public class MenuInicialAdministrativoFXMLController {


    public void handleButtonCadastrarFilme(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CadastrarFilmeFXML.fxml"));
        loader.setRoot(null);
        loader.setController(null);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }

    public void handleButtonCadastrarSessao(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CadastrarSessaoFXML.fxml"));
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

    public void handleButtonLogout(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginAdministrativoFXML.fxml"));
        loader.setRoot(null);
        loader.setController(null);
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(scene);
        stage.show();
    }
}
