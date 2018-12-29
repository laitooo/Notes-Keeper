package zxc.laitooo.noteskeeper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by userr on 12/18/2018.
 */

public class ManageUser {

    private final String TAG = "LaitoooSan";
    private final String ID = "id";
    private final String USERNAME = "username";
    private final String EMAIL = "email";
    private Context c;

    public ManageUser(Context context){
        this.c = context;
    }

    public void SaveUser(int id, String username, String email){
        SharedPreferences preferences = c.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ID,id);
        editor.putString(USERNAME,username);
        editor.putString(EMAIL,email);
        editor.apply();
    }

    public void DeleteUser(){
        SharedPreferences preferences = c.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public int GetUserId(){
        SharedPreferences preferences = c.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        return preferences.getInt(ID,0);
    }

    public String GetUserName(){
        SharedPreferences preferences = c.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        return preferences.getString(USERNAME,null);
    }

    public String GetUserEmail(){
        SharedPreferences preferences = c.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        return preferences.getString(EMAIL,null);
    }

    public boolean isUserLogged(){
        SharedPreferences preferences = c.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        return preferences.getString(EMAIL,null) != null;
    }
}
