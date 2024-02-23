package ora8.test.fel;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class Book{
    protected String author;
    protected String name;
    protected int pages;


    public Book() {
        author = "John Steinbeck";
        name = "Of Mice and Men";
        pages = 107;
    }


    public Book(String author, String name, int pages) {
        if(author.length() < 2 || name.length() < 4){
            throw new IllegalArgumentException();
        }

        this.author = author;
        this.name = name;
        this.pages = pages;
    }

    String getShortName(){
        return author.substring(0,2)+name.substring(0,4)+pages;
    }

    public int getPages(){
        return pages;
    }

    public String getName(){
        return name;
    }
}

class PrintedBook extends Book{
    enum Cover {Softcover, Hardcover};
    Cover cover;
    public PrintedBook(){
        super();
        cover = Cover.Hardcover;
        this.pages+=6;
    }    

    public PrintedBook(Cover cover, String author, String name, int pages){
        super(author, name, pages);
        this.cover = cover;
        this.pages+=6;
    }

}

class EBook extends Book{
    public EBook(){
        super();
    }    
}

class Article{
    private String title;
    private String body;
    private String conclusion;
    private ArrayList<Book> refs;
    PrintWriter pw;

    public Article(String title, String body, String conclusion){
        this.title = title;
        this.body = body;
        this.conclusion = conclusion;
        refs = new ArrayList<>();
    }

    public void addBookToReferences(Book book){
        refs.add(book);
    }

    private String createReference(Book book){
        return book.getName()+" 1 "+book.getPages();
    }

    public void print(String filename){
        try{
            File file = new File(filename);
            if(!file.exists()){
                file.createNewFile();
            }
            this.pw = new PrintWriter(file);
            pw.println(title);
            pw.println(body);
            pw.println(conclusion);
            pw.println("References:");
            for(Book elem : refs){
                pw.println(createReference(elem));
            }
            
        }catch(IOException exc){
            exc.printStackTrace();
        }finally{
            pw.flush();
            pw.close();
        }
    }
}

class Bag<T>{
    private HashMap<T, Integer> hm;

    public Bag(){
        hm = new HashMap<T, Integer>();
    }

    public void add(T element){
        if(!hm.containsKey(element)){
            hm.put(element, 1);
        }else{
            int num = hm.get(element)+1;
            hm.remove(element);
            hm.put(element, num);
        }
    }

    public int countOf(T element){
        if(!hm.containsKey(element)){
            return 0;
        }else{
            return hm.get(element);
        }
    }

    public void remove(T element) throws NotInBagException{
        if(hm.containsKey(element)){
            if(hm.get(element) == 1){
                hm.remove(element);
            }else{
                int num = hm.get(element)-1;
                hm.remove(element);
                hm.put(element, num);
            }
        }else{
            throw new NotInBagException();
        }
    }
}

class NotInBagException extends Exception{
    public NotInBagException(){
        super();
    }
}

class Main{
    public static void main (String [] args){
        Book book = new Book();
        System.out.println(book.getShortName());
        Article article = new Article("Alma","Alma csak hosszan","Alma vége");
        article.addBookToReferences(book);
        article.print("alma.txt");
    }
}