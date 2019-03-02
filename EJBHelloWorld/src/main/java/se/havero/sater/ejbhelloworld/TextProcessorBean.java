/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.havero.sater.ejbhelloworld;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;


/**
 * 
 */	
@Stateless
@SessionScoped
public class TextProcessorBean implements TextProcessorRemote {
    
    
    private final List<String> bookShelf;
    
    public  TextProcessorBean(){
        this.bookShelf = new ArrayList<>();
    }
    
    @Override
    public String processText(String text) {
        return text.toUpperCase();
    }

    @Override
    public void addBook(String bookName) {
       bookShelf.add(bookName);
    }

    @Override
    public List<String> getShelf() {
        return bookShelf;
    }
}
