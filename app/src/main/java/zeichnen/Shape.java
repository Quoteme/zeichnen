package zeichnen;

import java.util.Arrays;
import java.util.LinkedList;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shape {
  LinkedList<Double[]> points;
  Double opacity;
  Color color;

  Shape(Color color, Double[]... points) {
    this.color = color;
    this.opacity = 1.0;
    this.points = new LinkedList<> (Arrays.asList(points));
  }

  public void draw(Canvas canvas, GraphicsContext gc, Camera camera) {
    if (this.points.size()==0) {
      return;
    }
    gc.setLineWidth(camera.getZoom());
    gc.setStroke(this.color);
    gc.beginPath();
    Double width = canvas.getWidth();
    Double height = canvas.getHeight();
    gc.moveTo(
      camera.getZoom() * points.getFirst()[0]-camera.getX(),
      camera.getZoom() * points.getFirst()[1]-camera.getY());
    for (Double[] point : this.points) {
      gc.lineTo(
        camera.getZoom() * point[0]-camera.getX(),
        camera.getZoom() * point[1]-camera.getY());
    }
    gc.stroke();
    // gc.strokeLine(
    //   lastInput[0],
    //   lastInput[1],
    //   event.getX(),
    //   event.getY()
    // );
  }

  public Double[] getPenultimate(){
    return this.points.size()>=2
      ? this.points.get(this.points.size()-2)
      : null;
  }

  public Double[] getLast(){
    return this.points.size()>=1
      ? this.points.getLast()
      : null;
  }

  /**
   * Draw only the latest addition of this shape
   *
   * @param canvas the canvas which to draw on
   * @param gc drawing context with respect to the canvas
   */
  public void drawLast(Canvas canvas, GraphicsContext gc, Camera camera) {
    gc.setLineWidth(camera.getZoom());
    gc.setStroke(this.color);
    Double[] prev = this.getPenultimate();
    Double[] last = this.getLast();
    if (prev != null && last != null)
      gc.strokeLine(
        camera.getZoom() * prev[0]-camera.getX(),
        camera.getZoom() * prev[1]-camera.getY(),
        camera.getZoom() * last[0]-camera.getX(),
        camera.getZoom() * last[1]-camera.getY()
        );
  }

  /**
   * Add a new point, but only under the condition that
   * it is far enough away from the last point added
   *
   * @param x x-Position of new point to add
   * @param y y-Position of new point to add
   * @param threshold Optional: minimum distance from last added point needed to add this new point
   */
  public void addPoint(Double x, Double y) {
    this.addPoint(x, y, 2.0);
  }
  public void addPoint(Double x, Double y, Double threshold) {
    Double[] last = this.points.getLast();
    if (Math.hypot(x-last[0], y-last[1])>threshold) {
      this.points.add(new Double[] {x,y});
    }
  }

  public Double getOpacity(){
    return this.opacity;
  }

  public void setOpacity(Double opacity){
    this.opacity = opacity;
  }

  public Color getColor(){
    return this.color;
  }

  public void setColor(Color color){
    this.color = color;
  }
}
