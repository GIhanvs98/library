package com.pcl.library.controller;

import com.pcl.library.db.Database;
import com.pcl.library.model.User;
import com.pcl.library.util.security.PasswordManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginFormController {
    public TextField txtEmail;
    public PasswordField txtPassword;
    public AnchorPane context;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
      String email = txtEmail.getText().toLowerCase();
      String password=txtPassword.getText().trim();

       Optional<User> selectedUser = Database.usersTable.stream().filter
               (e->e.getEmail().equals(email)).findFirst();

       if (selectedUser.isPresent()){
           if (new PasswordManager().check(password,selectedUser.get().getPassword())){
               setUi("Dashboard");
           }else {
               new Alert(Alert.AlertType.ERROR,"Invalid Password!").show();
               return;
           }
       }else{
           new Alert(Alert.AlertType.ERROR,"user not found!").show();
           return;
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
