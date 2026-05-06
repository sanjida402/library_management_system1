import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class Book {
    int id;
    String title;
    String author;
    boolean isAvailable = true;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }
}
