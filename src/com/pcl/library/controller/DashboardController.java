package com.pcl.library.controller;

import com.pcl.library.db.Database;
import com.pcl.library.model.Book;
import com.pcl.library.tm.BookTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.Observable;
import java.util.Optional;

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
        btnSave.setText("Update");
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

        try{
            String  lastBookId=getLastId();
            if(lastBookId!=null){
                String splitId[]=lastBookId.split("-");
                String lastCharAsString=splitId[1];
                int lastCharAsInt=Integer.parseInt(lastCharAsString);
                lastCharAsInt++;
                String genaratedBookId="B-"+lastCharAsInt;
                txtBId.setText(genaratedBookId);
            }else {
                txtBId.setText("B-1");
            }
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }

    }


    public void saveOnAction(ActionEvent actionEvent) {
        if (btnSave.getText().equalsIgnoreCase("save book")){
            Book book=new Book(
                    txtBId.getText(),
                    txtBName.getText(),
                    txtAuthor.getText(),
                    cmbCupboard.getValue().toString(),
                    cmdSection.getValue().toString(),
                    rBtnIsAvailableYes.isSelected()

            );
            try {
                boolean isSaved=  saveBook(book);
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION, "Book Saved").show();
                    setTableData();
                    setBookId();//pending
                    clear();

                }else {
                    new Alert(Alert.AlertType.WARNING,"something went wrong please try again").show();
                    return;
                }

            } catch (ClassNotFoundException | SQLException e) {
                System.out.println(e.getMessage());
            }

        }else{
           Optional<Book> selectedBook =Database.bookTable.stream().filter(e->e.getBookID().equals(txtBId.getText())).findFirst();
           if(!selectedBook.isPresent()){
               new Alert(Alert.AlertType.INFORMATION, "Book Not Found").show();

           }else {
               selectedBook.get().setBookName(txtBName.getText());
               selectedBook.get().setBookAuthor(txtAuthor.getText());
               selectedBook.get().setCupboard(cmbCupboard.getValue().toString());
               selectedBook.get().setSection(cmdSection.getValue().toString());
               selectedBook.get().setAvailability(rBtnIsAvailableYes.isSelected());
               new Alert(Alert.AlertType.INFORMATION, "Book Updated").show();
               setTableData();
               setTableData();
               btnSave.setText("Book Saved");
               clear();
           }
        }

    }

    private boolean saveBook(Book book) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library","root","1234");
        String sql="INSERT INTO book VALUES (?,?,?,?,?,?)";
       PreparedStatement statement =connection.prepareStatement(sql);
       statement.setString(1,book.getBookID());
       statement.setString(2,book.getBookName());
       statement.setString(3,book.getBookAuthor());
       statement.setString(4,book.getCupboard());
       statement.setString(5,book.getSection());
       statement.setBoolean(6,book.getAvailability());
       return statement.executeUpdate()>0;
    }

    private void clear(){

        txtBName.clear();
        txtAuthor.clear();
        cmdSection.setValue(null);
        cmbCupboard.setValue(null);
        rBtnIsAvailableYes.setSelected(false);
        rBtnIsAvailableNo.setSelected(true);
    }
    private String getLastId() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/library","root","1234");
        String sql="SELECT book_id FROM book ORDER BY CAST(SUBSTRING(book_id,3)AS UNSIGNED)DESC LIMIT 1";
       PreparedStatement statement= connection.prepareStatement(sql);
       ResultSet resultSet =statement.executeQuery();
       if(resultSet.next()){
           return resultSet.getString("book_id");
       }
       return null;
    }
}
