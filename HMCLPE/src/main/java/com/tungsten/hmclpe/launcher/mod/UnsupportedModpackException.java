package com.tungsten.hmclpe.launcher.mod;

public class UnsupportedModpackException extends Exception {
    public UnsupportedModpackException() {
    }

    public UnsupportedModpackException(String str) {
        super(str);
    }

    public UnsupportedModpackException(String str, Throwable th) {
        super(str, th);
    }

    public UnsupportedModpackException(Throwable th) {
        super(th);
    }
}
