package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import sample.Graph.GraphGroup;


//rb_createVertex
//rb_createEdge
//rb_delete
//rb_move

public class Controller {

    private GraphGroup graphGroup;
    @FXML private AnchorPane anchorPane;

    void init() {
        graphGroup = new GraphGroup();
        anchorPane.getChildren().add(graphGroup);
        AnchorPane.setLeftAnchor(graphGroup.getClip(), 0.0);
    }

    @FXML private void onAction(ActionEvent event) {
        switch (((RadioButton)event.getSource()).getId()) {
            case "rb_createVertex":
                graphGroup.setCurrentAction(GraphGroup.Action.CreateVertex);
                break;
            case "rb_createEdge":
                graphGroup.setCurrentAction(GraphGroup.Action.CreateEdge);
                break;
            case "rb_delete":
                graphGroup.setCurrentAction(GraphGroup.Action.Delete);
                break;
            case "rb_move":
                graphGroup.setCurrentAction(GraphGroup.Action.Move);
                break;
        }
    }


}
