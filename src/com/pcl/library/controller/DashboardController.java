package com.pcl.library.controller;

import com.pcl.library.db.Database;
import com.pcl.library.model.Book;
import com.pcl.library.tm.BookTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Observable;

public class DashboardController {
    public TextField txtBId;
    public TextField txtBName;
    public TextField txtAuthor;
    public ComboBox cmbCupboard;
    public ComboBox cmdSection;
    public RadioButton rBtnIsAvailableYes;
    public RadioButton rBtnIsAvailableNo;
    public TableView tblBook;
    public TableColumn colBId;
    public TableColumn colBName;
    public TableColumn colAuthor;
    public TableColumn colCupboard;
    public TableColumn colSection;
    public TableColumn colAvailability;
    public TableColumn colOption;

    public void initialize(){
        cmbCupboard.getItems().add("Cupboard-1");
        cmbCupboard.getItems().add("Cupboard-2");
        cmdSection.getItems().add("Section-A");
        cmdSection.getItems().add("Section-B");
        colBId.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colBName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        colCupboard.setCellValueFactory(new PropertyValueFactory<>("cupboard"));
        colSection.setCellValueFactory(new PropertyValueFactory<>("section"));
        colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("button"));
        setBookId();
        setTableData();
    }
    private void setTableData(){
        ObservableList<BookTm> bookList= FXCollections.observableArrayList();
        tblBook.setItems(bookList);
        for(Book book:Database.bookTable){
            Button button=new Button("Delete");
            BookTm bookTm=new BookTm(
                    book.getBookID(),
                    book.getBookName(),
                    book.getBookAuthor(),
                    book.getCupboard(),
                    book.getSection(),
                    book.getAvailability() ? "Yes" : "NO",
                    button
            );

            bookList.add(bookTm);
        }
    }

    private void setBookId() {
        if(!Database.bookTable.isEmpty()){
          Book lastBook= Database.bookTable.get(Database.bookTable.size()-1);
          String lastBookId=lastBook.getBookID();
          String splitId[]=lastBookId.split("-");
          String lastCharAsString=splitId[1];
          int lastCharAsInt=Integer.parseInt(lastCharAsString);
          lastCharAsInt++;
          String genaratedBookId="B-"+lastCharAsInt;
          txtBId.setText(genaratedBookId);

        }else{
            txtBId.setText("B-1");
        }
    }


    public void saveOnAction(ActionEvent actionEvent) {
      Book book=new Book(
            txtBId.getText(),
                txtBName.getText(),
                txtAuthor.getText(),
                cmbCupboard.getValue().toString(),
                cmdSection.getValue().toString(),
              rBtnIsAvailableYes.isSelected()


        );
        Database.bookTable.add(book);
        new Alert(Alert.AlertType.INFORMATION, "Book Saved").show();
        System.out.println(book.toString());
        setTableData();
        setBookId();
        clear();
    }
    private void clear(){

        txtBName.clear();
        txtAuthor.clear();
        cmdSection.setValue(null);
        cmbCupboard.setValue(null);
        rBtnIsAvailableYes.setSelected(false);
        rBtnIsAvailableNo.setSelected(true);
    }
}
