
package se.havero.sater.ejbhelloworld;

import java.util.List;
import javax.ejb.Remote;

@Remote
public interface LIbrarySessionRemote {
    
    public void addBook(String bookName);
    public List<String> getShelf();
    public String processText(String text);
    
}