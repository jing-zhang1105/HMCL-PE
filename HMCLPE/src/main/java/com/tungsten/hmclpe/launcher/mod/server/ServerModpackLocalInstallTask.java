package com.tungsten.hmclpe.launcher.mod.server;

import android.os.AsyncTask;
import com.tungsten.hmclpe.launcher.mod.Modpack;
import java.io.File;

public class ServerModpackLocalInstallTask extends AsyncTask<Object, Integer, Exception> {
    @Override
    public Exception doInBackground(Object... objArr) {
        return null;
    }

    public ServerModpackLocalInstallTask(File file, Modpack modpack, ServerModpackManifest serverModpackManifest, String str) {}
}
