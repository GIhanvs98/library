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
    public Button btnSave;

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
        tblBook.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue !=null){
                setData((BookTm)newValue);
            }
        });
    }

    private void setData(BookTm newValue) {
        txtBId.setText(newValue.getBookID());
        txtBName.setText(newValue.getBookName());
        txtAuthor.setText(newValue.getBookAuthor());
        cmbCupboard.setValue(newValue.getCupboard());
        cmdSection.setValue(newValue.getSection());
        boolean isAvailible="Yes".equalsIgnoreCase(newValue.getAvailability());

        rBtnIsAvailableYes.setSelected(isAvailible);
        rBtnIsAvailableNo.setSelected(!isAvailible);


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
                    book.getAvailability() ? "Yes" : "No",
                    button
            );
            button.setOnAction(event -> {
              Alert alert=  new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this book?",
                        ButtonType.YES, ButtonType.NO
                );
              alert.showAndWait();
              if (alert.getResult()==ButtonType.YES){
                  Database.bookTable.remove(book);
                  new Alert(Alert.AlertType.INFORMATION,"Book deleted successfully").show();
                  setTableData();
                  setBookId();
              }
            });

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
