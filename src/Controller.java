import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import sample.SVGLoader;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller {
    private Model model;
    private ArrayList<SVGPath> hairStyles = new ArrayList<SVGPath>();
    private ArrayList<ImageView> skinStyles = new ArrayList<ImageView>();
    private ArrayList<ImageView> browsStyles = new ArrayList<ImageView>();
    private ArrayList<ImageView> eyesStyles = new ArrayList<ImageView>();
    private ArrayList<ImageView> mouthStyles = new ArrayList<ImageView>();

    private SVGPath hair;
    private ImageView skin;
    private ImageView brows;
    private ImageView eyes;
    private ImageView nose;
    private ImageView mouth;
    private ArrayList<SVGPath> clothesCollection = new ArrayList<SVGPath>();
    private final Color EFFECT_COLOR = Color.DARKBLUE;

    //FXML elements
    //selection bar
    @FXML private FlowPane hairPane;
    @FXML private FlowPane skinPane;
    @FXML private FlowPane browsPane;
    @FXML private FlowPane eyesPane;
    @FXML private FlowPane mouthPane;
    //avatar area
    @FXML private StackPane mainPane;
    @FXML private Rectangle avatarBackground;
    //command bar
    @FXML private GridPane CommandPane;
    @FXML private ColorPicker colorPicker;
    @FXML private Slider slider;
    @FXML private Button saveButton;
    @FXML private Button fileButton;

    public Controller(Model model) {
        this.model = model;
    }

    //load preview
    private ImageView loadPreveiw(String type, String type_kind, int size) {
        Image image = new Image(type + "/" + type_kind + ".png");
        ImageView preview = new ImageView(image);
        preview.setFitWidth(size);
        preview.setPreserveRatio(true);
        return preview;
    }

    //default version of load preview, set size to 140, and add listener on clicking
    private ImageView loadPreveiw(String type, String type_kind) {
        ImageView preview = loadPreveiw(type, type_kind, 140);
        if (type.equals("skin")) {
            preview.setOnMouseClicked(mouseEvent -> { model.setSkinStyle(type_kind); });
        } else if (type.equals("mouth")) {
            preview.setOnMouseClicked(mouseEvent -> { model.setMouthStyle(type_kind); });
        } else if (type.equals("eyes")) {
            preview.setOnMouseClicked(mouseEvent -> { model.setEyesStyle(type_kind); });
        } else if ( type.equals("brows"))  {
            preview.setOnMouseClicked(mouseEvent -> { model.setBrowsStyle(type_kind); });
        } else if ( type.equals("hair")) {
            preview.setOnMouseClicked(mouseEvent -> { model.setHairStyle(type_kind); });
        }
        preview.setPickOnBounds(true);
        return preview;
    }

    //load preview and store avatar parts
    private ImageView loadStyle(String type, String type_kind) {
        ImageView view = loadPreveiw(type, type_kind, 200);
        view.setPickOnBounds(false);
        view.setId(type_kind);
        ImageView preview = loadPreveiw(type, type_kind);
        if (type.equals("skin")) {
            skinStyles.add(view);
        } else if (type.equals("mouth") ) {
            mouthStyles.add(view);
        } else if (type.equals("eyes")) {
            eyesStyles.add(view);
        } else if ( type.equals("brows")) {
            browsStyles.add(view);
        }
        return preview;
    }

    private void updateCommandBar() {
        if( model.commandArea.equals("")) {
            colorPicker.setVisible(false);
            slider.setVisible(false);
        } else if (model.commandArea.equals("eyes")) {
            colorPicker.setVisible(false);
            slider.setVisible(true);
        } else if (model.commandArea.equals("brows")) {
            colorPicker.setVisible(false);
            slider.setVisible(true);
        } else if (model.commandArea.contains("clothes")) {
            colorPicker.setVisible(true);
            slider.setVisible(false);
        } else if (model.commandArea.equals("hair")) {
            colorPicker.setVisible(true);
            slider.setVisible(false);
        } else if (model.commandArea.equals("background")) {
            colorPicker.setVisible(true);
            slider.setVisible(false);
        }
    }

    //update the whole view, usually called by model
    public void viewUpdate() {
        mainPane.getChildren().removeAll(skin, brows, eyes, mouth, hair, nose);
        final String hairType = model.hairStyle;
        final String skinType = model.skinStyle;
        final String browsType = model.browsStyle;
        final String eyesType = model.eyesStyle;
        final String mouthType = model.mouthStyle;
        for (SVGPath hair : hairStyles) {
            if (hair.getId().equals(hairType)) {
                this.hair = hair;
                break;
            }
        }
        for (ImageView skin: skinStyles) {
            if (skin.getId().equals(skinType)) {
                this.skin = skin;
                break;
            }
        }
        for (ImageView brows: browsStyles) {
            if (brows.getId().equals(browsType)) {
                this.brows = brows;
                break;
            }
        }
        for (ImageView eyes: eyesStyles) {
            if (eyes.getId().equals(eyesType)) {
                this.eyes = eyes;
                break;
            }
        }
        for (ImageView mouth: mouthStyles) {
            if (mouth.getId().equals(mouthType) ) {
                this.mouth = mouth;
                break;
            }
        }
        if (hair.getId().equals("hair_curly")  || hair.getId().equals("hair_long")) {
            hair.setTranslateY(4);
        } else {
            hair.setTranslateY(-19);
        }
        mainPane.getChildren().addAll(skin, brows, eyes, mouth, hair, nose);
        for (SVGPath clothes: clothesCollection) {
            mainPane.getChildren().remove(clothes);
            if(clothes.getId().equals("clothes0")) {
                clothes.setTranslateY(89);
            } else if (clothes.getId().equals("clothes1")) {
                clothes.setTranslateY(84);
            } else if (clothes.getId().equals("clothes2")) {
                clothes.setTranslateY(84);
            } else if (clothes.getId().equals("clothes3")) {
                clothes.setTranslateY(83);
                clothes.setTranslateX(-30);
            } else if (clothes.getId().equals("clothes4")) {
                clothes.setTranslateY(83);
                clothes.setTranslateX(30);
            }
            //add event handler
            clothes.setOnMouseEntered(mouseEvent -> {clothes.setEffect(new DropShadow(10, EFFECT_COLOR ));});
            clothes.setOnMouseExited(mouseEvent -> {clothes.setEffect(null);});
            clothes.setOnMouseClicked(mouseEvent -> {model.setCommandArea(clothes.getId()); mouseEvent.consume();});
            //change the color of the clothes
            final Paint clothesColor = model.getClothesColor(clothes.getId());
            if (clothesColor==null) {
                model.setClothesColor(clothes.getId(),clothes.getFill());
            } else {
                clothes.setFill(clothesColor);
            }
            mainPane.getChildren().add(clothes);
        }
        //set mainPane click event
        mainPane.setOnMouseClicked(mouseEvent -> {
            model.setCommandArea("");
        });

        //add avator part event handler
        eyes.setOnMouseEntered(mouseEvent -> {eyes.setEffect(new DropShadow(10, EFFECT_COLOR ));});
        eyes.setOnMouseExited(mouseEvent -> {eyes.setEffect(null);});
        eyes.setOnMouseClicked(mouseEvent -> {model.setCommandArea("eyes"); mouseEvent.consume();});
        hair.setOnMouseEntered(mouseEvent -> {hair.setEffect(new DropShadow(10, EFFECT_COLOR )); });
        hair.setOnMouseExited(mouseEvent -> {hair.setEffect(null); });
        hair.setOnMouseClicked(mouseEvent -> {model.setCommandArea("hair"); mouseEvent.consume();});
        brows.setOnMouseEntered(mouseEvent -> {brows.setEffect(new DropShadow(10, EFFECT_COLOR )); });
        brows.setOnMouseExited(mouseEvent -> {brows.setEffect(null); });
        brows.setOnMouseClicked(mouseEvent -> {model.setCommandArea("brows"); mouseEvent.consume();});
        avatarBackground.setOnMouseEntered(mouseEvent -> {avatarBackground.setEffect(new DropShadow(10, EFFECT_COLOR )); });
        avatarBackground.setOnMouseExited(mouseEvent -> {avatarBackground.setEffect(null); });
        avatarBackground.setOnMouseClicked(mouseEvent -> {model.setCommandArea("background"); mouseEvent.consume();});

        //change color of hair
        if (model.hairColor == null) {
            model.hairColor = hair.getFill();
        } else {
            hair.setFill(model.hairColor);
        }
        //change color of background
        avatarBackground.setFill(model.backgroundColor);
        //change scale of eyes
        eyes.setScaleX(model.eyeScale);
        eyes.setScaleY(model.eyeScale);
        //add offset to brows
        brows.setTranslateY(model.browsOffset);
        //update value in color picker
        colorPicker.setValue((Color)model.getColor());
        slider.setValue(model.getScaleOffset());
        //update command bar
        updateCommandBar();
    }

    public void initialize() {
        //load hair
        sample.SVGLoader svgLoader = new SVGLoader();
        SVGPath hairCurly = (SVGPath)(svgLoader.loadSVG("hair/hair_curly.svg").getChildren().get(0));
        hairCurly.setId("hair_curly");
        hairCurly.setPickOnBounds(false);
        ImageView hairCurlyPreview = loadPreveiw("hair", "hair_curly");
        SVGPath hairLong = (SVGPath)(svgLoader.loadSVG("hair/hair_long.svg").getChildren().get(0));
        hairLong.setId("hair_long");
        hairLong.setPickOnBounds(false);
        ImageView hairLongPreview = loadPreveiw("hair", "hair_long");
        SVGPath hairShort = (SVGPath)(svgLoader.loadSVG("hair/hair_short.svg").getChildren().get(0));
        hairShort.setId("hair_short");
        hairShort.setPickOnBounds(false);
        ImageView hairShortPreview = loadPreveiw("hair", "hair_short");
        SVGPath hairWavy = (SVGPath)(svgLoader.loadSVG("hair/hair_wavy.svg").getChildren().get(0));
        hairWavy.setId("hair_wavy");
        hairWavy.setPickOnBounds(false);
        ImageView hairWavyPreview = loadPreveiw("hair", "hair_wavy");
        //System.out.println(hairCurlySVG);
        hairStyles.add(hairCurly);
        hairStyles.add(hairLong);
        hairStyles.add(hairShort);
        hairStyles.add(hairWavy);
        hairPane.getChildren().addAll(hairCurlyPreview, hairLongPreview, hairShortPreview, hairWavyPreview);
        //load skins
        ImageView skinBrownPreview = loadStyle("skin", "skin_brown");
        ImageView skinLightPreview = loadStyle("skin",  "skin_light");
        ImageView skinLighterPreview = loadStyle("skin", "skin_lighter");
        skinPane.getChildren().addAll(skinBrownPreview, skinLightPreview, skinLighterPreview);
        //load brows
        ImageView browAngryPreview = loadStyle("brows", "brows_angry");
        ImageView browDefaultPreview = loadStyle("brows", "brows_default");
        ImageView browSadPreview = loadStyle("brows", "brows_sad");
        browsPane.getChildren().addAll(browAngryPreview, browDefaultPreview, browSadPreview);
        //load eyes
        ImageView eyesClosedPreview = loadStyle("eyes", "eyes_closed");
        ImageView eyesDefaultPreview = loadStyle("eyes", "eyes_default");
        ImageView eyesWidePreview = loadStyle("eyes", "eyes_wide");
        eyesPane.getChildren().addAll(eyesClosedPreview, eyesDefaultPreview, eyesWidePreview);
        //load mouth
        ImageView mouthDefaultPreview = loadStyle("mouth", "mouth_default");
        ImageView mouthSadPreview = loadStyle("mouth", "mouth_sad");
        ImageView mouthSeriousPreview = loadStyle("mouth", "mouth_serious");
        mouthPane.getChildren().addAll(mouthDefaultPreview, mouthSadPreview, mouthSeriousPreview);
        //load nose
        nose = loadPreveiw("", "nose_default");
        nose.setPickOnBounds(false);
        //load clothes
        ObservableList<Node> clothesNodes = svgLoader.loadSVG("clothes.svg").getChildren();
        for (int i = 0; i < clothesNodes.size(); i++) {
            SVGPath clothes = (SVGPath)clothesNodes.get(i);
            clothes.setPickOnBounds(false);
            clothes.setId("clothes"+Integer.toString(i));
            this.clothesCollection.add(clothes);
        }
        //create event handler for widgets in command area
        colorPicker.setOnAction(actionEvent -> {model.setColor(colorPicker.getValue());});
        slider.setOnMouseDragged(mouseEvent -> {model.setScaleOffset(slider.getValue());});
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Your Avatar");
        Stage stage = new Stage();
        saveButton.setOnMouseClicked(mouseEvent -> {
            try {
                File file = fileChooser.showSaveDialog(stage);
                file = new File(file.getAbsolutePath() + ".png");
                SnapshotParameters parameters = new SnapshotParameters();
                parameters.setFill(Color.TRANSPARENT);
                Image mainStackSnapshot = mainPane.snapshot(parameters, null);
                ImageIO.write(SwingFXUtils.fromFXImage(mainStackSnapshot, null), "png", file);
            } catch (Exception e) {
                System.out.println("fail to save you avator");
            }
        });
        fileButton.setOnMouseClicked(mouseEvent -> {
            try {
                File file = fileChooser.showSaveDialog(stage);
                file = new File(file.getAbsolutePath() + ".txt");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(model.getParameters());
                fileWriter.close();
            } catch (Exception e) {
                System.out.println("fail to save you avator");
            }
        });
        //update views
        viewUpdate();
    }

    // General ActionEvent Handler

}
