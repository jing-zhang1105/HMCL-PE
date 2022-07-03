package com.tungsten.hmclpe.launcher.mod.curse;

import com.google.gson.JsonParseException;
import com.google.gson.annotations.SerializedName;
import com.tungsten.hmclpe.utils.gson.tools.Validation;
import com.tungsten.hmclpe.utils.string.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurseManifestMinecraft implements Validation {
    @SerializedName("version")
    private final String gameVersion;
    @SerializedName("modLoaders")
    private final List<CurseManifestModLoader> modLoaders;

    public CurseManifestMinecraft() {
        gameVersion = "";
        modLoaders = Collections.emptyList();
    }

    public CurseManifestMinecraft(String str, List<CurseManifestModLoader> list) {
        gameVersion = str;
        modLoaders = new ArrayList(list);
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public List<CurseManifestModLoader> getModLoaders() {
        return Collections.unmodifiableList(modLoaders);
    }

    @Override
    public void validate() throws JsonParseException {
        if (StringUtils.isBlank(gameVersion)) {
            throw new JsonParseException("CurseForge Manifest.gameVersion cannot be blank.");
        }
    }
}
