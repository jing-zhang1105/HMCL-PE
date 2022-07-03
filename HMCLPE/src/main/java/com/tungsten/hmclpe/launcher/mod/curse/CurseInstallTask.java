package com.tungsten.hmclpe.launcher.mod.curse;

import android.os.AsyncTask;

import com.tungsten.hmclpe.launcher.mod.Modpack;

import java.io.File;

public class CurseInstallTask extends AsyncTask<Object, Integer, Exception> {
    @Override
    public Exception doInBackground(Object... objArr) {
        return null;
    }

    public CurseInstallTask(File file, Modpack modpack, CurseManifest curseManifest, String str) {}
}