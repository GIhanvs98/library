package com.pcl.library.controller;

import com.pcl.library.db.Database;
import com.pcl.library.model.Book;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class DashboardController {
    public TextField txtBId;
    public TextField txtBName;
    public TextField txtAuthor;
    public TextField txtCupboard;
    public TextField txtSection;
    public TextField txtAvailability;

    public void saveOnAction(ActionEvent actionEvent) {
       Book book= new Book(
                txtBId.getText(),
                txtBName.getText(),
                txtAuthor.getText(),
                txtCupboard.getText(),
                txtSection.getText(),
                txtAvailability.getText()
                );
        Database.bookTable.add(book);
        new Alert(Alert.AlertType.INFORMATION,"New Book Added").show();
        System.out.println(book.toString());
    }
}
