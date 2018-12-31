package zxc.laitooo.noteskeeper;

import android.content.Context;

import java.util.List;

/**
 * Created by userr on 1/1/2019.
 */

public class Group {

    private int Id;
    private String Title;
    private int Admin_Id;
    private boolean IsAdmin;
    private String Link;

    public String getTitle() {
        return Title;
    }

    public int getAdmin_Id() {
        return Admin_Id;
    }

    public int getId() {
        return Id;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public String getLink() {
        return Link;
    }

    public Group(int id, String title, int admin_Id,String link,Context c){
        ManageUser m = new ManageUser(c);
        Id = id;
        Title = title;
        Admin_Id = admin_Id;
        Link = link;
        IsAdmin = (Admin_Id==m.GetUserId());
    }
}
