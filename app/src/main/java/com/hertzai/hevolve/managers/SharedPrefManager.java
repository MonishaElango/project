package com.hertzai.hevolve.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.hertzai.hevolve.constants.HevolveConstants;
public class SharedPrefManager {
    private static final Object sessionDataLock = new Object();
    private static SharedPrefManager sessionData = null;
    private static SharedPreferences sharedPreferences = null;

    private SharedPrefManager(SharedPreferences preferences) {
        sharedPreferences = preferences;
    }

    // Singleton, avoid to recreate each time
    public static SharedPrefManager getInstance(Context context) {
        synchronized (sessionDataLock) {
            if (sessionData == null) {
                sharedPreferences = context.getSharedPreferences(
                        HevolveConstants.PREFERENCE_FILE, Context.MODE_PRIVATE);
                sessionData = new SharedPrefManager(sharedPreferences);
            }
        }
        return sessionData;
    }

    public boolean getBooleanPreference(String key) {
        boolean value = sharedPreferences.getBoolean(key, false);
        return value;
    }

    public void removePreference(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public void setPreference(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setPreference(String pref, boolean trueOrFalse) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(pref, trueOrFalse);
        edit.commit();
    }

    public void setPreference(String key, long value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void setPreference(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void setPreference(String key, double value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, Double.doubleToRawLongBits(value));
        editor.commit();
    }

    public void clearPreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public String getPreferenceDefNull(String key) {
        String value = sharedPreferences.getString(key, null);
        return value;
    }

    public String getPreference(String key) {
        String value = sharedPreferences.getString(key, "");
        return value;
    }

    public String getPreference(String key, String defValue) {
        String value = sharedPreferences.getString(key, defValue);
        return value;
    }

    public long getLongPreference(String key) {
        long value = sharedPreferences.getLong(key, 0);
        return value;
    }

    public int getIntPreference(String key) {
        int value = sharedPreferences.getInt(key, 0);
        return value;
    }

    public float getFloatPreference(String key) {
        float value = sharedPreferences.getFloat(key, 0);
        return value;
    }

    public double getDoublePreference(String key, Double value) {
        // SharedPreferences.Editor editor = sharedPreferences.edit();
        return Double.longBitsToDouble(sharedPreferences.getLong(key, Double.doubleToRawLongBits(value)));
    }


    public boolean getContainsPreference(String key) {
        return sharedPreferences.contains(key);
    }

    public void removeAll() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }



    /*public void setJsonObjectPreference(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(value));
        editor.commit();
    }

    public Object getJsonObjectPreference(String key, Class klass) {
        String jsonString = sharedPreferences.getString(key, null);
        Gson gson = new Gson();
        return gson.fromJson(jsonString, klass);
    }*/
}
