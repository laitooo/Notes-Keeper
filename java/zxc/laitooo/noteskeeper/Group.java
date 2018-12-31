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

    public Group(int id, String title, int admin_Id,Context c){
        ManageUser m = new ManageUser(c);
        Id = id;
        Title = title;
        Admin_Id = admin_Id;
        IsAdmin = (Admin_Id==m.GetUserId());
    }
}
