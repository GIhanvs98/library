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

       try{
           boolean isSaved= signup(new User(firstName,lastName,email,new PasswordManager().encode(password)));
           if(isSaved){
               new Alert(Alert.AlertType.INFORMATION,"Welcome!").show();
               setUi("LoginForm");
           }else{
               new Alert(Alert.AlertType.WARNING,"Something went wrong").show();
           }

       }catch (SQLException | ClassNotFoundException e){
           e.printStackTrace();
       }



    }

    private boolean signup(User user) throws ClassNotFoundException, SQLException {

           Class.forName("com.mysql.cj.jdbc.Driver");//load driver

       Connection connection =
               DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library","root","1234");//create connection

        String sql="INSERT INTO user VALUES (?,?,?,?)";//write query//static

       PreparedStatement statement= connection.prepareStatement(sql);//statement created
        statement.setString(1,user.getEmail());
        statement.setString(2,user.getFirstName());
        statement.setString(3,user.getLastName());
        statement.setString(4,user.getPassword());

       return statement.executeUpdate()>0;
       // return rowCount>0;
    /*    if (rowCount>0){
            return true;
        }
        return false;*/
    }

    private void setUi(String location) throws IOException {
       Stage stage=(Stage) context.getScene().getWindow();
       stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
       stage.show();
    }

    public void alreadyHaveAnAccountOnAction(ActionEvent actionEvent) {
    }


}
