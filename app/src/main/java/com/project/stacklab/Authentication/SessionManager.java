package com.project.stacklab.Authentication;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;

public class SessionManager {

    SharedPreferences usersSession;
    SharedPreferences.Editor editor;
    Context context;

    public static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_UID = "uid";

    public static final String KEY_PHONE = "phone";

    public  static final String KEY_NAME = "name";

    public static final String IS_ADMIN = "isAdmin";

    public SessionManager(Context _context) {
        context = _context;
        usersSession = _context.getSharedPreferences("usersLoginSession", Context.MODE_PRIVATE);
        editor = usersSession.edit();

    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public void createLoginSession(String UID, String name, Boolean isAdmin) {
        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_UID, UID);

        editor.putBoolean(IS_ADMIN, isAdmin);

        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public HashMap<String, String> getUsersDetailsFromSessions() {
        HashMap<String, String> userData = new HashMap<String, String>();


        userData.put(KEY_UID, usersSession.getString(KEY_UID, null));
        userData.put(KEY_PHONE, usersSession.getString(KEY_PHONE, null));
        userData.put(KEY_NAME, usersSession.getString(KEY_NAME, null));


        return userData;
    }


    public SharedPreferences getUsersSession() {
        return usersSession;
    }

    public Boolean checkLogin() {
        if (usersSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }

    }
    public Boolean isAdmin() {
        return usersSession.getBoolean(IS_ADMIN, false);
    }

    public void logoutSession() {
        editor.clear();
        editor.commit();
    }


}
