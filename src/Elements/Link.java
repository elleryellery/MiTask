package Elements;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class Link {
    private String link;
    private String title;

    public Link(){
        title = "Undefined";
        link = "google.com";
    }

    public Link(String title, String link){
        this.title = title;
        this.link = link;
    }

    public void copy(){
        StringSelection stringSelection = new StringSelection(link);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
