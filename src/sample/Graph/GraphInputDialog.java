package sample.Graph;

import javafx.scene.control.TextInputDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphInputDialog extends TextInputDialog {

    public String getVertexName(String defaulName) {
        setTitle("Что тут обычно пишется?");
        setHeaderText("Введите имя вершины (от 1 до 10 непробельных символов)");
        setContentText("Имя:");
        getEditor().setText(defaulName);

        showAndWait();
        String res = getResult();
        if (res == null)
            return null;

        Pattern pattern = Pattern.compile("\\S{1,10}");
        Matcher matcher = pattern.matcher(res);
        return matcher.matches() ? res : null;
    }

    public Double getEdgeWeight(double defaultWeight) {
        setTitle("Так и не понял, что тут писать.");
        setHeaderText("Введите вес ребра");
        setContentText("Вес:");
        getEditor().setText(Double.toString(defaultWeight));

        showAndWait();
        String res = getResult();
        if (res == null)
            return null;

        try {
            return Double.parseDouble(res);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

}
