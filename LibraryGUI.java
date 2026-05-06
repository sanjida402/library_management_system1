import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

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


public class LibraryGUI extends JFrame implements ActionListener {

    JTextField bookIdField = new JTextField(5);
    JTextField titleField = new JTextField(10);
    JTextField authorField = new JTextField(10);

    JTextArea outputArea = new JTextArea(10, 40);

    Book[] books = new Book[900];
    int bookCount = 0;

    File file = new File("books.txt");

    public LibraryGUI() {
        setTitle("Library Management System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(new JLabel("Book ID"));
        inputPanel.add(bookIdField);

        inputPanel.add(new JLabel("Title"));
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Author"));
        inputPanel.add(authorField);

        add(inputPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        String[] buttons = {"AddBook", "EditBook", "DeleteBook", "Borrow", "Return", "Display"};

        for (int i = 0; i < buttons.length; i++) {
            JButton btn = new JButton(buttons[i]);

            if (buttons[i].equals("AddBook")) btn.setBackground(Color.GREEN);
            else if (buttons[i].equals("EditBook")) btn.setBackground(Color.ORANGE);
            else if (buttons[i].equals("DeleteBook")) btn.setBackground(Color.RED);
            else if (buttons[i].equals("Borrow")) btn.setBackground(Color.CYAN);
            else if (buttons[i].equals("Return")) btn.setBackground(Color.PINK);
            else if (buttons[i].equals("Display")) btn.setBackground(Color.LIGHT_GRAY);

            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.setOpaque(true);

            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        add(buttonPanel);

        outputArea.setEditable(false);
        add(new JScrollPane(outputArea));

        // load data
        loadFromFile();

        setVisible(true);
    }

    // file I/O operation write 
    void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < bookCount; i++) {
                bw.write(books[i].id + "," +
                         books[i].title + "," +
                         books[i].author + "," +
                         books[i].isAvailable);
                bw.newLine();
            }
        } catch (Exception e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }
    
    // file I/O operation read 
    void loadFromFile() {
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); 

                Book b = new Book(
                        Integer.parseInt(data[0]),
                        data[1],
                        data[2]
                );

                b.isAvailable = Boolean.parseBoolean(data[3]);

                books[bookCount++] = b;
            }

        } catch (Exception e) {
            System.out.println("Load error: " + e.getMessage());
        }
    }


    // find book
    Book findBook(int id) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].id == id) return books[i];
        }
        return null;
    }

    // button action
    public void actionPerformed(ActionEvent event) {

        String btn = event.getActionCommand();
        int bookId = 0;

        try {
            if (!bookIdField.getText().isEmpty())
                bookId = Integer.parseInt(bookIdField.getText());
        } catch (Exception e) {
            outputArea.setText("Invalid ID");
            return;
        }

        switch (btn) {

            case "AddBook":
                if (titleField.getText().isEmpty()) {
                    outputArea.setText("Title cannot be empty!");
                    return;
                }

                books[bookCount++] = new Book(
                        bookId,
                        titleField.getText(),
                        authorField.getText()
                );

                saveToFile(); 
                outputArea.setText("Book added successfully");
                break;

            case "EditBook": {
                Book b = findBook(bookId);
                if (b == null) {
                    outputArea.setText("Book not found");
                    break;
                }

                if (!titleField.getText().isEmpty())
                    b.title = titleField.getText();

                if (!authorField.getText().isEmpty())
                    b.author = authorField.getText();

                saveToFile(); 
                outputArea.setText("Book updated successfully");
                break;
            }

            case "DeleteBook":
                for (int i = 0; i < bookCount; i++) {
                    if (books[i].id == bookId) {
                        for (int j = i; j < bookCount - 1; j++) {
                            books[j] = books[j + 1];
                        }
                        bookCount--;

                        saveToFile(); 
                        outputArea.setText("Book deleted");
                        return;
                    }
                }
                outputArea.setText("Book not found");
                break;

            case "Borrow": {
                Book b = findBook(bookId);
                if (b == null) {
                    outputArea.setText("Book not found");
                    break;
                }

                if (b.isAvailable) {
                    b.isAvailable = false;
                    saveToFile();
                    outputArea.setText("Borrowed");
                } else {
                    outputArea.setText("Not available");
                }
                break;
            }

            case "Return": {
                Book b = findBook(bookId);
                if (b == null) {
                    outputArea.setText("Book not found");
                    break;
                }

                b.isAvailable = true;
                saveToFile(); 
                outputArea.setText("Returned");
                break;
            }

            case "Display":
                String result = "";

                for (int i = 0; i < bookCount; i++) {
                    result += "ID: " + books[i].id +
                              ", Title: " + books[i].title +
                              ", Author: " + books[i].author +
                              ", Status: " +
                              (books[i].isAvailable ? "Available" : "Borrowed") + "\n";
                }

                outputArea.setText(result.isEmpty() ? "No books!" : result);
                break;
        }
    }

    public static void main(String[] args) {
        new LibraryGUI();
    }
}