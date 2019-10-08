package sample.Graph.Elements;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import sample.Graph.GraphActions.ChangeDirectionEdge;
import sample.Graph.GraphActions.ChangeWeightEdge;
import sample.Graph.GraphActionsController;
import sample.Graph.GraphGroup;


public abstract class Edge extends Group {
    public enum Direction {
        Both,
        FirstVertex,
        SecondVertex
    }

    protected static final double radiusCircle = 8, strokeWidthCircle = 2,
            strokeWidth = 2,
            arrowLength = 9, arrowRotateAngle = 0.4;
    protected static final Color lineColor = Color.web("BCBABE"),
                                 circleColor = Color.web("F1F1F2"),
                                 weightColor = Color.web("086070");

    protected GraphGroup graphGroup;
    protected GraphActionsController actionsController;

    protected Path arc = new Path(new MoveTo(), new ArcTo());
    protected Circle circle = new Circle(radiusCircle);
    protected Text weightText = new Text();
    protected DoubleProperty weight = new SimpleDoubleProperty(1.0);
    protected Property<Direction> direction = new SimpleObjectProperty<>();

    // constructors
    protected Edge(GraphGroup graphGroup) {
        this.graphGroup = graphGroup;
        actionsController = graphGroup.getActionsController();
    }

    // init
    protected void initArc() {
        arc.setStrokeWidth(strokeWidth);
        arc.setStroke(lineColor);
        ArcTo arcTo = (ArcTo)arc.getElements().get(1);
        arcTo.setLargeArcFlag(true);
    }

    protected void initCircle() {
        circle.setFill(circleColor);
        circle.setStrokeWidth(strokeWidthCircle);
        circle.setStroke(lineColor);
    }

    protected void initWeight() {
        weightText.setTextAlignment(TextAlignment.CENTER);
        weightText.setTextOrigin(VPos.CENTER);
        weightText.setText(Double.toString(weight.get()));
        weightText.setFill(weightColor);
        weightText.setStroke(weightColor);
        weightText.setStrokeWidth(0.3);
    }

    // 1. position
    abstract public void setPosition(double x, double y);

    // 2. weight
    public void setWeight(double weight, boolean createAction) {
        if (createAction)
            actionsController.addAction(new ChangeWeightEdge(this, this.weight.get(), weight));
        this.weight.set(weight);
    }

    public double getWeight() {
        return weight.get();
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    // 3. direction
    public void setDirection(Direction direction, boolean createAction) {
        if (direction.equals(this.direction.getValue()))
            return;
        if (createAction)
            actionsController.addAction(new ChangeDirectionEdge(this, this.direction.getValue(), direction));

        this.direction.setValue(direction);
    }

    public Direction getDirection() {
        return direction.getValue();
    }

    abstract public boolean isDirectionTo(Vertex vertex);

    public boolean equalsDirection(Direction direction) {
        return direction.equals(this.direction.getValue());
    }

    public Property<Direction> directionProperty() {
        return direction;
    }

    // update
    abstract public void update();

    // vertices
    abstract public Vertex getFirstVertex();

    abstract public Vertex getSecondVertex();

    abstract public void connect();

    abstract public void disconnect();



}
