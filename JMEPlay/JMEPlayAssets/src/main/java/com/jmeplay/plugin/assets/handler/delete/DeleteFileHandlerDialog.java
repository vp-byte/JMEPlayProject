/*
 * MIT-LICENSE Copyright (c) 2017 / 2018 VP-BYTE (http://www.vp-byte.de/) Vladimir Petrenko
 */
package com.jmeplay.plugin.assets.handler.delete;

import com.jmeplay.plugin.assets.JMEPlayAssetsLocalization;
import javafx.scene.control.Alert;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Dialog implementation to confirm file delete
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class DeleteFileHandlerDialog {

    @Getter
    private Alert dialog;

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
     * Implementation to create delete confirm dialog
     *
     * @return crated dialog
     */
    public Alert create() {
        dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_DELETE_CONFIRM_TITLE));
        dialog.setHeaderText(null);
        dialog.setContentText(jmePlayAssetsLocalization.value(JMEPlayAssetsLocalization.LOCALISATION_ASSETS_HANDLER_DELETE_CONFIRM_QUESTION));
        return dialog;
    }

}
