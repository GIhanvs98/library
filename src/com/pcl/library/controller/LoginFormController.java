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
import java.sql.*;
import java.util.Optional;

public class LoginFormController {
    public TextField txtEmail;
    public PasswordField txtPassword;
    public AnchorPane context;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
      String email = txtEmail.getText().toLowerCase();
      String password=txtPassword.getText().trim();

      try{
          User selectedUser=login(email);

          if (selectedUser!=null){
              if (new PasswordManager().check(password,selectedUser.getPassword())){
                  setUi("Dashboard");
              }else {
                  new Alert(Alert.AlertType.ERROR,"Invalid Password!").show();
                  return;
              }
          }else{
              new Alert(Alert.AlertType.ERROR,"user not found!").show();
              return;
          }
      }catch (ClassNotFoundException | SQLException e){
          new Alert(Alert.AlertType.WARNING,e.toString()).show();
      }

    }

    private User login(String email) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library","root","1234");
        String sql="SELECT * FROM user WHERE email=?";

        PreparedStatement statement= connection.prepareStatement(sql);
        statement.setString(1, email);
        ResultSet resultSet =statement.executeQuery();//insert,delete,update -->execute update  -- select-->executequery
        if (resultSet.next()){
            User user=new User(
                resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString("email"),
                    resultSet.getString(4)
            );

            return user;

        }
        return null;
    }

    public void signupOnAction(ActionEvent actionEvent) throws IOException {
        setUi("SignupForm");
    }
    private void setUi(String location) throws IOException {
       Stage stage =(Stage) context.getScene().getWindow();
       stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));

    }
}
