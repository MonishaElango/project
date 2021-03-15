package com.hertzai.hevolve.options;

public class LoaderStatus {

    @LOADER_STATUS
    private int loader;

    public LoaderStatus(@LOADER_STATUS int loader) {
        this.loader = loader;
    }

    @LOADER_STATUS
    public int getLoader() {
        return loader;
    }

    public void setLoader(@LOADER_STATUS int loader) {
        this.loader = loader;
    }
}
