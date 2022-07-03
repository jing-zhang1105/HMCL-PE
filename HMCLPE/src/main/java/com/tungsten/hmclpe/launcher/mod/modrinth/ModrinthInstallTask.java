package com.tungsten.hmclpe.launcher.mod.modrinth;

import android.os.AsyncTask;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import java.io.File;

public class ModrinthInstallTask extends AsyncTask<Object, Integer, Exception> {
    @Override
    public Exception doInBackground(Object... objArr) {
        return null;
    }

    public ModrinthInstallTask(File file, Modpack modpack, ModrinthManifest modrinthManifest, String str) {}
}
