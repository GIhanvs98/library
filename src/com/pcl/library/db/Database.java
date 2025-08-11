package com.pcl.library.db;

import com.pcl.library.model.Book;
import com.pcl.library.model.User;
import com.pcl.library.util.security.PasswordManager;

import java.util.ArrayList;

public class Database {
    public static ArrayList<User> usersTable=new ArrayList<>();
    static{
        usersTable.add(new User("gihan","viraj","email.com",new PasswordManager().encode("1234")));
    }

    public static ArrayList<Book> bookTable=new ArrayList<>();
}
