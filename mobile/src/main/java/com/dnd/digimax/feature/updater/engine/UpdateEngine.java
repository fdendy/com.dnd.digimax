package com.dnd.digimax.feature.updater.engine;

import com.dnd.digimax.feature.updater.installer.ApkInstaller;
import com.dnd.digimax.feature.updater.installer.AssetInstaller;
import com.dnd.digimax.feature.updater.installer.PluginInstaller;
import com.dnd.digimax.feature.updater.model.UpdatePackage;
import com.dnd.digimax.feature.updater.model.UpdateResult;
import com.dnd.digimax.feature.updater.policy.UpdatePolicy;
import com.dnd.digimax.feature.updater.rollback.RollbackManager;
import com.dnd.digimax.feature.updater.validator.ChecksumValidator;
import com.dnd.digimax.feature.updater.validator.SignatureValidator;
import com.dnd.digimax.feature.updater.validator.VersionValidator;

public class UpdateEngine {

    private final UpdatePolicy policy;

    private final VersionValidator versionValidator;

    private final ChecksumValidator checksumValidator;

    private final SignatureValidator signatureValidator;

    private final ApkInstaller apkInstaller;

    private final AssetInstaller assetInstaller;

    private final PluginInstaller pluginInstaller;

    private final RollbackManager rollbackManager;

    public UpdateEngine() {

        policy =
                new UpdatePolicy();

        versionValidator =
                new VersionValidator();

        checksumValidator =
                new ChecksumValidator();

        signatureValidator =
                new SignatureValidator();

        apkInstaller =
                new ApkInstaller();

        assetInstaller =
                new AssetInstaller();

        pluginInstaller =
                new PluginInstaller();

        rollbackManager =
                new RollbackManager();
    }

    public UpdateResult installApk(
            String currentVersion,
            UpdatePackage updatePackage) {

        if (!versionValidator.isUpgrade(
                currentVersion,
                updatePackage.getVersion())) {

            return new UpdateResult(
                    false,
                    currentVersion,
                    "No update required"
            );
        }

        rollbackManager.save(
                currentVersion
        );

        boolean success =
                apkInstaller.install(
                        updatePackage
                );

        return new UpdateResult(
                success,
                updatePackage.getVersion(),
                success
                        ? "APK Installed"
                        : "APK Failed"
        );
    }

    public UpdateResult installPlugin(
            UpdatePackage updatePackage) {

        boolean success =
                pluginInstaller.install(
                        updatePackage
                );

        return new UpdateResult(
                success,
                updatePackage.getVersion(),
                "Plugin Update"
        );
    }

    public UpdateResult installAsset(
            UpdatePackage updatePackage) {

        boolean success =
                assetInstaller.install(
                        updatePackage
                );

        return new UpdateResult(
                success,
                updatePackage.getVersion(),
                "Asset Update"
        );
    }

    public RollbackManager getRollbackManager() {
        return rollbackManager;
    }

    public UpdatePolicy getPolicy() {

        return policy;
    }
}