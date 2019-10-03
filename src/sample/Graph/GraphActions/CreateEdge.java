package sample.Graph.GraphActions;

import sample.Graph.Elements.Edge;
import sample.Graph.GraphActionsController;
import sample.Graph.GraphGroup;

public class CreateEdge extends EdgeAction {
    public static void create(Edge edge, GraphGroup graphGroup) {
        GraphActionsController.addAction(new CreateEdge(edge, graphGroup));
    }

    private GraphGroup graphGroup;

    private CreateEdge(Edge edge, GraphGroup graphGroup) {
        super(edge);
        this.graphGroup = graphGroup;
    }

    @Override
    public void undo() {
        graphGroup.removeEdge(edge);
    }

    @Override
    public void redo() {
        graphGroup.addEdge(edge);
    }
}
