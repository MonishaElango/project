package com.hertzai.hevolve.views;
// AudioEmitter.java

import android.annotation.SuppressLint;
import android.media.AudioRecord;
import android.media.AudioRecord.Builder;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.protobuf.ByteString;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
//
//public final class AudioEmitterKt {
//    private static final String TAG = "Audio";
//}

@Metadata(
        mv = {1, 1, 16},
        bv = {1, 0, 3},
        k = 1,
        d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J8\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u000e\u001a\u00020\f2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\n0\u0010J\u0006\u0010\u0012\u001a\u00020\nR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.¢\u0006\u0002\n\u0000¨\u0006\u0013"},
        d2 = {"Lcom/google/cloud/examples/speechrecognition/AudioEmitter;", "", "()V", "mAudioExecutor", "Ljava/util/concurrent/ScheduledExecutorService;", "mAudioRecorder", "Landroid/media/AudioRecord;", "mBuffer", "", "start", "", "encoding", "", "channel", "sampleRate", "subscriber", "Lkotlin/Function1;", "Lcom/google/protobuf/ByteString;", "stop", "app"}
)
public final class AudioEmitter {
    private AudioRecord mAudioRecorder;
    private ScheduledExecutorService mAudioExecutor;
    private byte[] mBuffer;

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public final void start(int encoding, int channel, int sampleRate, @NotNull final Function1 subscriber) {
        Intrinsics.checkParameterIsNotNull(subscriber, "subscriber");
        this.mAudioExecutor = Executors.newSingleThreadScheduledExecutor();
        this.mAudioRecorder = (new Builder()).setAudioSource(1).setAudioFormat((new android.media.AudioFormat.Builder()).setEncoding(encoding).setSampleRate(sampleRate).setChannelMask(channel).build()).build();
        this.mBuffer = new byte[2 * AudioRecord.getMinBufferSize(sampleRate, channel, encoding)];
        StringBuilder var10001 = (new StringBuilder()).append("Recording audio with buffer size of: ");
        byte[] var10002 = this.mBuffer;
        if (var10002 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBuffer");
        }

        Log.d("Audio", var10001.append(var10002.length).append(" bytes").toString());
        AudioRecord var10000 = this.mAudioRecorder;
        if (var10000 == null) {
            Intrinsics.throwNpe();
        }

        var10000.startRecording();
        ScheduledExecutorService var5 = this.mAudioExecutor;
        if (var5 == null) {
            Intrinsics.throwNpe();
        }

        var5.scheduleAtFixedRate((Runnable)(new Runnable() {
            public final void run() {
                AudioRecord var10000 = AudioEmitter.this.mAudioRecorder;
                if (var10000 == null) {
                    Intrinsics.throwNpe();
                }

                int read = var10000.read(AudioEmitter.access$getMBuffer$p(AudioEmitter.this), 0, AudioEmitter.access$getMBuffer$p(AudioEmitter.this).length, 0);
                if (read > 0) {
                    Function1 var2 = subscriber;
                    ByteString var10001 = ByteString.copyFrom(AudioEmitter.access$getMBuffer$p(AudioEmitter.this), 0, read);
                    Intrinsics.checkExpressionValueIsNotNull(var10001, "ByteString.copyFrom(mBuffer, 0, read)");
                    var2.invoke(var10001);
                }

            }
        }), 0L, 10L, TimeUnit.MILLISECONDS);
    }

    // $FF: synthetic method
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void start$default(AudioEmitter var0, int var1, int var2, int var3, Function1 var4, int var5, Object var6) {
        if ((var5 & 1) != 0) {
            var1 = 2;
        }

        if ((var5 & 2) != 0) {
            var2 = 16;
        }

        if ((var5 & 4) != 0) {
            var3 = 16000;
        }

        var0.start(var1, var2, var3, var4);
    }

    public final void stop() {
        ScheduledExecutorService var10000 = this.mAudioExecutor;
        if (var10000 != null) {
            var10000.shutdown();
        }

        this.mAudioExecutor = (ScheduledExecutorService)null;
        AudioRecord var1 = this.mAudioRecorder;
        if (var1 != null) {
            var1.stop();
        }

        var1 = this.mAudioRecorder;
        if (var1 != null) {
            var1.release();
        }

        this.mAudioRecorder = (AudioRecord)null;
    }

    // $FF: synthetic method
    public static final void access$setMAudioRecorder$p(AudioEmitter $this, AudioRecord var1) {
        $this.mAudioRecorder = var1;
    }

    // $FF: synthetic method
    public static final byte[] access$getMBuffer$p(AudioEmitter $this) {
        byte[] var10000 = $this.mBuffer;
        if (var10000 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mBuffer");
        }

        return var10000;
    }

    // $FF: synthetic method
    public static final void access$setMBuffer$p(AudioEmitter $this, byte[] var1) {
        $this.mBuffer = var1;
    }
}

