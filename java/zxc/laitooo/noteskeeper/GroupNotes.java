package zxc.laitooo.noteskeeper;

/**
 * Created by userr on 1/5/2019.
 */

public class GroupNotes {

    private int ID;
    private int Id_Group;
    private int Id_User;
    private String Content;
    private String Date;
    private String UserName;
    private String Profile;

    public GroupNotes(int ID, int id_Group, int id_User, String content, String date, String userName, String profile) {
        this.ID = ID;
        Id_Group = id_Group;
        Id_User = id_User;
        Content = content;
        Date = date;
        UserName = userName;
        Profile = profile;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getId_Group() {
        return Id_Group;
    }

    public void setId_Group(int id_Group) {
        Id_Group = id_Group;
    }

    public int getId_User() {
        return Id_User;
    }

    public void setId_User(int id_User) {
        Id_User = id_User;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }
}
