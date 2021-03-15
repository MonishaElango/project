// MainActivity.java
package com.hertzai.hevolve.views;

import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;
import java.io.InputStream;

import kotlin.Metadata;

//import kotlin.jvm.internal.DefaultConstructorMarker;

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 3,
        d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\n \u0002*\u0004\u0018\u00010\u00010\u0001H\n¢\u0006\u0002\b\u0003"},
        d2 = {"<anonymous>", "Lcom/google/auth/oauth2/GoogleCredentials;", "kotlin.jvm.PlatformType", "getCredentials"}
)
final class MainActivity$mSpeechClient$2$1$1 implements CredentialsProvider {
    // $FF: synthetic field
    final InputStream $it;

    // $FF: synthetic method
    // $FF: bridge method
//    public Credentials getCredentials() {
//        return (Credentials)this.getCredentials();
//    }

    public final GoogleCredentials getCredentials() {
        try {
            return GoogleCredentials.fromStream(this.$it);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    MainActivity$mSpeechClient$2$1$1(InputStream var1) {
        this.$it = var1;
    }
}


//public final class MainActivityKt {
//    private static final String TAG = "Speech";
//}
