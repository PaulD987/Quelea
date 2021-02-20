/*
 * This file is part of Quelea, free projection software for churches.
 *
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.quelea.windows.options;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.preferencesfx.model.Category;
import com.dlsc.preferencesfx.model.Setting;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.quelea.data.displayable.TextAlignment;
import org.quelea.services.languages.LabelGrabber;
import org.quelea.services.languages.LanguageFile;
import org.quelea.services.languages.LanguageFileManager;
import org.quelea.services.utils.QueleaProperties;
import org.quelea.services.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import static org.quelea.services.utils.QueleaPropertyKeys.*;
import static org.quelea.windows.options.PreferencesDialog.getColorPicker;

/**
 * The panel that shows the stage view options.
 *
 * @author Arvid
 */
public class OptionsNDIPanel {
    private HashMap<Field, ObservableValue> bindings;
    private ObservableList<String> lineAlignmentList;
    private ObjectProperty<String> alignmentSelectionProperty;

    private ObservableList<String> fontsList;
    private ObjectProperty<String> fontSelectionProperty;
    private ObservableList<String> screenDimensionsList;
    private ObjectProperty<String> screenDimensionsProperty;
    /**
     * Create the NDI options panel.
     * @param bindings HashMap of bindings to setup after the dialog has been created
     */
    OptionsNDIPanel(HashMap<Field, ObservableValue> bindings) {
        this.bindings = bindings;
        ArrayList<String> textAlignment = new ArrayList<>();
        for (TextAlignment alignment : TextAlignment.values()) {
            textAlignment.add(alignment.toFriendlyString());
        }
        lineAlignmentList = FXCollections.observableArrayList(textAlignment);
        alignmentSelectionProperty = new SimpleObjectProperty<>(QueleaProperties.get().getStageTextAlignment());

        Set<String> screenDimensions = new TreeSet<String>();

        screenDimensions.add("1920x1080");
        screenDimensions.add("1280x720");

        screenDimensionsList = FXCollections.observableArrayList(screenDimensions);
        screenDimensionsProperty = new SimpleObjectProperty<>(QueleaProperties.get().getNDIDimensions());
    }

    public Category getNDITab() {
        return Category.of(LabelGrabber.INSTANCE.getLabel("ndi.options.output.heading"), new ImageView(new Image("file:icons/ndisettingsicon.png")),
                Setting.of(LabelGrabber.INSTANCE.getLabel("ndi.enable.output"), new SimpleBooleanProperty(QueleaProperties.get().getEnableNDIOutput())).customKey(enableNDIOutputKey),
                Setting.of(LabelGrabber.INSTANCE.getLabel("ndi.screen.dimensions.label"), screenDimensionsList, screenDimensionsProperty).customKey(ndiDimensionsKey)
        );
    }


}
