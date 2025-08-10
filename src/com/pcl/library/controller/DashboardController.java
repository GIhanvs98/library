package com.pcl.library.controller;

import com.pcl.library.db.Database;
import com.pcl.library.model.Book;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class DashboardController {
    public TextField txtBId;
    public TextField txtBName;
    public TextField txtAuthor;
    public ComboBox cmbCupboard;
    public ComboBox cmdSection;
    public RadioButton rBtnIsAvailableYes;
    public RadioButton rBtnIsAvailableNo;

    public void initialize(){
        cmbCupboard.getItems().add("Cupboard-1");
        cmbCupboard.getItems().add("Cupboard-2");
        cmdSection.getItems().add("Section-A");
        cmdSection.getItems().add("Section-B");
        setBookId();
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
        setBookId();
        clear();
    }
    private void clear(){
        txtBId.clear();
        txtBName.clear();
        txtAuthor.clear();
        cmdSection.setValue(null);
        cmbCupboard.setValue(null);
        rBtnIsAvailableYes.setSelected(false);
    }
}
