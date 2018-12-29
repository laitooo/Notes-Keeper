package zxc.laitooo.noteskeeper;

/**
 * Created by Zizo on 12/22/2018.
 */

public class Note {
    private String Title;
    private String Content;
    private String Date;
    private int ID;

    public Note (int id,String title,String content,String date){
        ID = id;
        Title = title;
        Content = content;
        Date = date;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
