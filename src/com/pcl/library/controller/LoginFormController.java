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

public class LoginFormController {
    public TextField txtEmail;
    public PasswordField txtPassword;
    public AnchorPane context;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
      String email = txtEmail.getText().toLowerCase();
      String password=txtPassword.getText().trim();

      for (User user:Database.usersTable){
          if(user.getEmail().equals(email)){
              if(user.getPassword().equals(password)){
                  setUi("Dashboard");
              }else {
                  new Alert(Alert.AlertType.ERROR,"Invalid Password!").show();
                  return;
              }
          }else {
              new Alert(Alert.AlertType.ERROR,"Invalid Email!").show();
          }

      }

    }

    public void signupOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }
    private void setUi(String location) throws IOException {
       Stage stage =(Stage) context.getScene().getWindow();
       stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));

    }
}
