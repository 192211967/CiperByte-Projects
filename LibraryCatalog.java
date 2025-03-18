import java.util.ArrayList;
import java.util.Scanner;
class Book
{
    private String title;
    private String author;

    public Book(String title, String author)
    {
        this.title = title;
        this.author = author;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    @Override
    public String toString()
    {
        return "Title: " + title + ", Author: " + author;
    }
}

public class LibraryCatalog
{
    private ArrayList<Book> books;

    public LibraryCatalog() {
        books = new ArrayList<>();
    }

    public void addBook(String title, String author)
    {
        if (title.isEmpty() || author.isEmpty())
        {
            System.out.println("Title and author cannot be empty. Book not added.");
            return;
        }
        books.add(new Book(title, author));
        System.out.println("Book added successfully!");
    }

    public void searchByTitle(String title)
    {
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title))
            {
                System.out.println(book);
                found = true;
            }
        }
        if (!found)
        {
            System.out.println("No book found with title: " + title);
        }
    }

    public void searchByAuthor(String author)
    {
        boolean found = false;
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author))
            {
                System.out.println(book);
                found = true;
            }
        }
        if (!found)
        {
            System.out.println("No book found by author: " + author);
        }
    }

    public void listAllBooks()
    {
        if (books.isEmpty())
        {
            System.out.println("No books available in the library.");
        } else {
            for (Book book : books)
            {
                System.out.println(book);
            }
        }
    }

    public void removeBook(String title)
    {
        boolean removed = books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
        if (removed)
        {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("No book found with title: " + title);
        }
    }

    public void updateBook(String title, String newTitle, String newAuthor)
    {
        boolean updated = false;
        for (Book book : books)
        {
            if (book.getTitle().equalsIgnoreCase(title))
            {
                if (!newTitle.isEmpty())
                {
                    book.setTitle(newTitle);
                }
                if (!newAuthor.isEmpty())
                {
                    book.setAuthor(newAuthor);
                }
                updated = true;
                System.out.println("Book updated successfully!");
                break;
            }
        }
        if (!updated)
        {
            System.out.println("No book found with title: " + title);
        }
    }

    public static void main(String[] args)
    {
        LibraryCatalog catalog = new LibraryCatalog();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running)
        {
            System.out.println("\nLibrary Catalog System");
            System.out.println("1. Add a Book");
            System.out.println("2. Search by Title");
            System.out.println("3. Search by Author");
            System.out.println("4. List All Books");
            System.out.println("5. Remove a Book");
            System.out.println("6. Update Book Details");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice)
            {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    catalog.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter title to search: ");
                    String searchTitle = scanner.nextLine();
                    catalog.searchByTitle(searchTitle);
                    break;
                case 3:
                    System.out.print("Enter author to search: ");
                    String searchAuthor = scanner.nextLine();
                    catalog.searchByAuthor(searchAuthor);
                    break;
                case 4:
                    catalog.listAllBooks();
                    break;
                case 5:
                    System.out.print("Enter title of the book to remove: ");
                    String removeTitle = scanner.nextLine();
                    catalog.removeBook(removeTitle);
                    break;
                case 6:
                    System.out.print("Enter title of the book to update: ");
                    String updateTitle = scanner.nextLine();
                    System.out.print("Enter new title (leave blank to keep current): ");
                    String newTitle = scanner.nextLine();
                    System.out.print("Enter new author (leave blank to keep current): ");
                    String newAuthor = scanner.nextLine();
                    catalog.updateBook(updateTitle, newTitle, newAuthor);
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
}