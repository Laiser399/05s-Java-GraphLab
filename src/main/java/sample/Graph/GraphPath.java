package sample.Graph;

import sample.Graph.Elements.Edge;
import sample.Graph.Elements.Element;
import sample.Graph.Elements.Vertex;

import java.util.ArrayList;
import java.util.List;

public class GraphPath {
    private List<Element> path = new ArrayList<>();

    public GraphPath(Vertex firstVertex) {
        path.add(firstVertex);
    }

    public GraphPath(GraphPath other) {
        path.addAll(other.path);
    }

    public void add(Edge edge, Vertex nextVertex) {
        path.add(edge);
        path.add(nextVertex);
    }

    public Vertex getLastVertex() {
        return (Vertex) path.get(path.size() - 1);
    }

    public List<Element> getPathCopy() {
        return new ArrayList<>(path);
    }

    public int getEdgeCount() {
        return (path.size() - 1) / 2;
    }

    public void setSelectedAsPath(boolean flag) {
        for (Element element : path)
            element.setSelectedAsPath(flag);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < path.size(); i += 2) {
            builder.append(((Vertex) path.get(i)).getName());
            if (i < path.size() - 1)
                builder.append(" -> ");
        }
        return builder.toString();
    }
}
