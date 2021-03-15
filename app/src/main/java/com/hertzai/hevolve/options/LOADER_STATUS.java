package com.hertzai.hevolve.options;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@IntDef({LOADER_STATUS.NONE, LOADER_STATUS.LOADING, LOADER_STATUS.SUCCESS, LOADER_STATUS.FAILED})
public @interface LOADER_STATUS {
    int NONE = 0;
    int LOADING = 1;
    int SUCCESS = 2;
    int FAILED = 3;
}
