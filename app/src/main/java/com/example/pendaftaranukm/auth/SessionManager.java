package com.example.pendaftaranukm.auth;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "UKMAppPref";
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String KEY_EMAIL = "userEmail";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveToken(String token, String email) {
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.putString(KEY_EMAIL, token);
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getUserEmail() {
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public void clearToken() {
        editor.remove(KEY_ACCESS_TOKEN);
        editor.apply();
    }
}
