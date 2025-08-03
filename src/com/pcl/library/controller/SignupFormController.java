package com.pcl.library.controller;

import com.pcl.library.db.Database;
import com.pcl.library.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupFormController {
    public TextField txtFirstName;
    public TextField txtLastName;
    public TextField txtEmail;
    public PasswordField txtPassword;
    public AnchorPane context;

    public void saveUserOnAction(ActionEvent actionEvent) throws IOException {
       String firstName= txtFirstName.getText();
       String lastName= txtLastName.getText();
       String email= txtEmail.getText().toLowerCase();
       String password= txtPassword.getText().trim();

        Database.usersTable.add(
                new User(firstName,lastName,email,password)
        );
        new Alert(Alert.AlertType.INFORMATION,"Welcome!").show();
        setUi("LoginForm");
        System.out.println(Database.usersTable);
    }

    private void setUi(String location) throws IOException {
       Stage stage=(Stage) context.getScene().getWindow();
       stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
       stage.show();
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) {
    }


}
