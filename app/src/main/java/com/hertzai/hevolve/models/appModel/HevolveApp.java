package com.hertzai.hevolve.models.appModel;

import android.app.Application;
import android.content.Context;

import com.hertzai.hevolve.api.AskMeChatApi;
import com.hertzai.hevolve.constants.HevolveConstants;
import com.hertzai.hevolve.gson.AskMeAssis;
import com.hertzai.hevolve.gson.Revision_Response_Message;
import com.hertzai.hevolve.managers.SharedPrefManager;
import com.hertzai.hevolve.models.gson.User;

public class HevolveApp extends Application {

    public static boolean DEBUG = true;
    public static boolean FIRST_CHECK = true;
    private static String authToken;
    private static String email;
    private static User user;
    private static AskMeAssis speakBook;
    private static Revision_Response_Message revision_response_message;


    public static String getAuthToken(Context context) {
        if (authToken == null) {
            authToken = SharedPrefManager.getInstance(context).getPreferenceDefNull(HevolveConstants.LOGGED_IN_AUTH_TOKEN);
        }
        return authToken;
    }

    public static String getEmail(Context context) {
        if (email == null) {
            email = SharedPrefManager.getInstance(context).getPreferenceDefNull(HevolveConstants.LOGGED_IN_EMAIL);
        }
        return email;
    }

    public static User getUser(Context context) {
        if (user == null) {
            user = User.parse(SharedPrefManager.getInstance(context).getPreference(HevolveConstants.LOGGED_IN_USER_DATA));
        }
        return user;
    }

    public static void setUser(Context context, User user) {
        HevolveApp.user = user;
        SharedPrefManager.getInstance(context).setPreference(HevolveConstants.LOGGED_IN_USER_DATA, User.toJson(user));
    }

    public static void setSpeakBook(Context context, AskMeAssis speakBook) {
        HevolveApp.speakBook = speakBook;
        SharedPrefManager.getInstance(context).setPreference("Speak to Book", AskMeAssis.toJson(speakBook));
    }

    public static void setRevision(Context context, Revision_Response_Message revision_response_message) {
        HevolveApp.revision_response_message = revision_response_message;
        SharedPrefManager.getInstance(context).setPreference("Revise with pal", Revision_Response_Message.toJson(revision_response_message));
    }


    public static void clearAll() {
        authToken = null;
        email = null;
        // user = null;
    }


}
