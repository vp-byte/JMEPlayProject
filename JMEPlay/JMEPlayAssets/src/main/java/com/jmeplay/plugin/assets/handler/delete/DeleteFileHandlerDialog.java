/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.delete;

import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Dialog implementation to rename files
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class DeleteFileHandlerDialog {

    private final JMEPlayAssetsLocalization jmePlayAssetsLocalization;

    /**
     * Constructor to create delete confirm dialog
     *
     * @param jmePlayAssetsLocalization localisation for dialog
     */
    @Autowired
    public DeleteFileHandlerDialog(JMEPlayAssetsLocalization jmePlayAssetsLocalization) {
        this.jmePlayAssetsLocalization = jmePlayAssetsLocalization;
    }

    /**
     * Implementation to create dialog
     *
     * @return crated dialog
     */
    public Alert create() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_DELETE_CONFIRM_TITLE));
        alert.setHeaderText(null);
        alert.setContentText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_DELETE_CONFIRM_QUESTION));
        return alert;
    }

}
