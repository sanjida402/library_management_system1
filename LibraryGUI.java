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


        setVisible(true);
    }
    
    
    public static void main(String[] args) {
        new LibraryGUI();
    }
}
