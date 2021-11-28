import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Model {
    private Controller controller;
    public String commandArea = "";
    public String hairStyle = "hair_short";
    public Paint hairColor = null;
    public String skinStyle = "skin_light";
    public String browsStyle = "brows_default";
    public double browsOffset = 0;
    public String eyesStyle = "eyes_default";
    public double eyeScale = 1;
    public String mouthStyle = "mouth_default";
    public Paint clothes0Color = null;
    public Paint clothes1Color = null;
    public Paint clothes2Color = null;
    public Paint clothes3Color = null;
    public Paint clothes4Color = null;
    public Paint backgroundColor = Color.WHITE;

    public void setColor(Paint color) {
        if (commandArea.equals("hair")) {
            hairColor = color;
        } else if (commandArea.equals("clothes0")) {
            clothes0Color = color;
        } else if (commandArea.equals("clothes1")) {
            clothes1Color = color;
        } else if (commandArea.equals("clothes2")) {
            clothes2Color = color;
        } else if (commandArea.equals("clothes3")) {
            clothes3Color = color;
        } else if (commandArea.equals("clothes4")) {
            clothes4Color = color;
        } else if (commandArea.equals("background")) {
            backgroundColor = color;
        }
        controller.viewUpdate();
    }

    public Paint getColor() {
        Paint returnColor = null;
        if (commandArea.equals("hair")) {
            returnColor = hairColor;
        } else if (commandArea.equals("clothes0")) {
            returnColor = clothes0Color;
        } else if (commandArea.equals("clothes1")) {
            returnColor = clothes1Color;
        } else if (commandArea.equals("clothes2")) {
            returnColor = clothes2Color;
        } else if (commandArea.equals("clothes3")) {
            returnColor = clothes3Color;
        } else if (commandArea.equals("clothes4")) {
            returnColor = clothes4Color;
        } else if (commandArea.equals("background")) {
            returnColor = backgroundColor;
        }
        return returnColor;
    }

    public Paint getClothesColor(String id) {
        Paint returnColor = null;
        if (id.equals("clothes0")) {
            returnColor = clothes0Color;
        } else if (id.equals("clothes1")) {
            returnColor = clothes1Color;
        } else if (id.equals("clothes2")) {
            returnColor = clothes2Color;
        } else if (id.equals("clothes3")) {
            returnColor = clothes3Color;
        } else if (id.equals("clothes4")) {
            returnColor = clothes4Color;
        }
        return returnColor;
    }

    public void setClothesColor(String id, Paint color) {
        if (id.equals("clothes0")) {
            clothes0Color = color;
        } else if (id.equals("clothes1")) {
            clothes1Color = color;
        } else if (id.equals("clothes2")) {
            clothes2Color = color;
        } else if (id.equals("clothes3")) {
            clothes3Color = color;
        } else if (id.equals("clothes4")) {
            clothes4Color = color;
        }
    }

    public void setScaleOffset(double num) {
        if (commandArea.equals("eyes")) {
            final double base = 0.9;
            eyeScale = num/100/0.5*(1-base) + base;
        } else if (commandArea.equals("brows")) {
            browsOffset = -((num - 50)/50 * 3);
        }
        controller.viewUpdate();
    }

    public double getScaleOffset() {
        double return_offset = 50;
        if (commandArea.equals("eyes")) {
            final double base = 0.9;
            return_offset = (eyeScale - base)/(1-base)*50;
        } else if (commandArea.equals("brows")) {
            return_offset = (-browsOffset/3*50+50);
        }
        return return_offset;
    }

    public void setControllerRef(Controller ref){
        this.controller = ref;
    }

    public void setHairStyle(String style) {
        hairStyle = style;
        controller.viewUpdate();
    }

    public void setSkinStyle(String style) {
        skinStyle = style;
        controller.viewUpdate();
    }

    public void setBrowsStyle(String style) {
        browsStyle= style;
        controller.viewUpdate();
    }

    public void setEyesStyle(String style) {
        eyesStyle = style;
        controller.viewUpdate();
    }

    public void setMouthStyle(String style) {
        mouthStyle = style;
        controller.viewUpdate();
    }

    public void setCommandArea(String commandArea) {
        this.commandArea = commandArea;
        controller.viewUpdate();
    }

    public String getParameters() {
        return
                "commandArea=" + commandArea + '\n' +
                "hairStyle=" + hairStyle + '\n' +
                "hairColor=" + hairColor + '\n' +
                "skinStyle=" + skinStyle + '\n' +
                "browsStyle=" + browsStyle + '\n' +
                "browsOffset=" + browsOffset + '\n' +
                "eyesStyle=" + eyesStyle + '\n' +
                "eyeScale=" + eyeScale + '\n' +
                "mouthStyle=" + mouthStyle + '\n' +
                "clothes0Color=" + clothes0Color + '\n' +
                "clothes1Color=" + clothes1Color + '\n' +
                "clothes2Color=" + clothes2Color + '\n' +
                "clothes3Color=" + clothes3Color + '\n' +
                "clothes4Color=" + clothes4Color + '\n' +
                "backgroundColor=" + backgroundColor
                ;
    }
}
