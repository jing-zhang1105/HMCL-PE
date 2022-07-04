package com.tungsten.hmclpe.launcher.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.tungsten.hmclpe.R;
import com.tungsten.hmclpe.launcher.MainActivity;
import com.tungsten.hmclpe.launcher.list.install.DownloadTaskListBean;
import com.tungsten.hmclpe.launcher.mod.RemoteMod;
import com.tungsten.hmclpe.launcher.setting.game.PrivateGameSetting;
import com.tungsten.hmclpe.launcher.uis.game.download.right.resource.DownloadResourceUI;
import com.tungsten.hmclpe.launcher.uis.tools.BaseUI;
import com.tungsten.hmclpe.utils.file.FileUtils;
import com.tungsten.hmclpe.utils.gson.GsonUtils;

import java.io.File;
import java.util.ArrayList;

public class EditDownloadNameDialog extends Dialog implements View.OnClickListener {

    private DownloadResourceUI ui;
    private BaseUI baseUI;
    private RemoteMod.Version version;
    private boolean alert;
    private String dir;
    private String url;

    private EditText editText;
    private Button positive;
    private Button negative;

    private CallBacks callBacks;

    private DownloadDialog.CallBacks downloadCallBacks = new DownloadDialog.CallBacks() {
        @Override
        public void onDownloadSuccess(String path) {
            if (callBacks != null) {
                callBacks.onDownloadSuccess(path);
            }
        }

        @Override
        public void onDownloadError(ArrayList<DownloadTaskListBean> failedFile) {
            if (callBacks != null) {
                callBacks.onDownloadError(failedFile);
            }
        }
    };

    public interface CallBacks {
        void onDownloadSuccess(String path);

        void onDownloadError(ArrayList<DownloadTaskListBean> failedFile);
    }

    public EditDownloadNameDialog(@NonNull Context context, DownloadResourceUI ui, RemoteMod.Version version, boolean alert, String dir, CallBacks callBacks) {
        super(context);
        this.ui = ui;
        this.version = version;
        this.alert = alert;
        this.dir = dir;
        this.baseUI = (BaseUI) ui;
        this.callBacks = callBacks;
        setContentView(R.layout.dialog_edit_download_name);
        setCancelable(false);
        init();
    }

    public EditDownloadNameDialog(@NonNull Context context, BaseUI ui, String url, boolean alert, String dir, CallBacks callBacks) {
        super(context);
        this.baseUI = ui;
        this.url = url;
        this.alert = alert;
        this.dir = dir;
        this.callBacks = callBacks;
        setContentView(R.layout.dialog_edit_download_name);
        setCancelable(false);
        init();
    }

    private void init() {
        editText = findViewById(R.id.download_name);
        positive = findViewById(R.id.download);
        negative = findViewById(R.id.cancel);
        positive.setOnClickListener(this);
        negative.setOnClickListener(this);
        if (version != null) {
            editText.setText(version.getFile().getFilename());
        } else {
            editText.setText(url.split("/")[url.split("/").length - 1]);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == positive) {
            if (!editText.getText().toString().equals("") && !editText.getText().toString().contains("/")) {
                String name;
                String path;
                if (url == null) {
                    String currentVersion;
                    if (ui.resourceType == 0) {
                        currentVersion = ui.activity.uiManager.downloadUI.downloadUIManager.downloadModUI.gameVersion == null ? null : ui.activity.launcherSetting.gameFileDirectory + "/versions/" + ui.activity.uiManager.downloadUI.downloadUIManager.downloadModUI.gameVersion;
                    } else {
                        currentVersion = ui.activity.uiManager.downloadUI.downloadUIManager.downloadResourcePackUI.gameVersion == null ? null : ui.activity.launcherSetting.gameFileDirectory + "/versions/" + ui.activity.uiManager.downloadUI.downloadUIManager.downloadResourcePackUI.gameVersion;
                    }
                    PrivateGameSetting privateGameSetting;
                    String gameDir;
                    if (currentVersion != null) {
                        String settingPath = currentVersion + "/hmclpe.cfg";
                        if (new File(settingPath).exists() && GsonUtils.getPrivateGameSettingFromFile(settingPath) != null && (GsonUtils.getPrivateGameSettingFromFile(settingPath).forceEnable || GsonUtils.getPrivateGameSettingFromFile(settingPath).enable)) {
                            privateGameSetting = GsonUtils.getPrivateGameSettingFromFile(settingPath);
                        } else {
                            privateGameSetting = ui.activity.privateGameSetting;
                        }
                        if (privateGameSetting.gameDirSetting.type == 0) {
                            gameDir = ui.activity.launcherSetting.gameFileDirectory;
                        } else if (privateGameSetting.gameDirSetting.type == 1) {
                            gameDir = currentVersion;
                        } else {
                            gameDir = privateGameSetting.gameDirSetting.path;
                        }
                    } else {
                        privateGameSetting = ui.activity.privateGameSetting;
                        if (privateGameSetting.gameDirSetting.type == 0 || privateGameSetting.gameDirSetting.type == 1) {
                            gameDir = ui.activity.launcherSetting.gameFileDirectory;
                        } else {
                            gameDir = privateGameSetting.gameDirSetting.path;
                        }
                    }
                    FileUtils.createDirectory(gameDir + (ui.resourceType == 0 ? "/mods/" : "/resourcepacks/"));
                    name = version.getName();
                    url = version.getFile().getUrl();
                    path = dir == null ? (gameDir + (ui.resourceType == 0 ? "/mods/" : "/resourcepacks/") + editText.getText().toString()) : dir + "/" + editText.getText().toString();
                } else {
                    name = editText.getText().toString();
                    path = dir + "/" + name;
                }
                DownloadTaskListBean downloadTaskListBean = new DownloadTaskListBean(name, url, path, "");
                ArrayList<DownloadTaskListBean> list = new ArrayList<>();
                list.add(downloadTaskListBean);
                DownloadDialog dialog = new DownloadDialog(getContext(), baseUI.activity, list, alert, downloadCallBacks);
                dismiss();
                dialog.show();
            }
        }
        if (view == negative) {
            dismiss();
        }
    }
}
