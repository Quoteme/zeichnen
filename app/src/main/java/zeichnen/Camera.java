package zeichnen;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Camera {
  private Double x            = 0.0;
  private Double y            = 0.0;
  private Double startPanX    = 0.0;
  private Double startPanY    = 0.0;
  private Double relativePanX = 0.0;
  private Double relativePanY = 0.0;
  private Double zoom         = 0.0; // this is stored exponentially;
  private Double zoomStep     = 0.1;
  private Canvas canvas;
  private GraphicsContext gc;

  Camera(Canvas canvas, GraphicsContext gc){
    this.canvas = canvas;
    this.gc = gc;
  }

  public Double getX(){
    // canvas.getWidth();
    // canvas.getHeight();
    return this.x
      - (this.relativePanX - this.startPanX)
      + (this.canvas.getWidth()/2*this.getZoom());
  }

  public Double getY(){
    return this.y
      - (this.relativePanY - this.startPanY)
      + (this.canvas.getWidth()/2*this.getZoom());
  }

  public void setX(Double x){
    this.x = x;
  }

  public void setY(Double y){
    this.y = y;
  }

  /**
   * start a panning of the camera
   * (call this when the mouse first clicks on the screen)
   *
   * @param cursorX x-position which the panning will be relative to
   * @param cursorY y-position which the panning will be relative to
   */
  public void startPan(Double cursorX, Double cursorY){
    this.startPanX = cursorX;
    this.startPanY = cursorY;
    System.out.println("--- panning started ---");
  }

  /**
   * pan the camera after having started a pan with `startpan`
   *
   * @param cursorX x-position of the cursor
   * @param cursorY y-position of the cursor
   */
  public void setPan(Double cursorX, Double cursorY){
    this.relativePanX = cursorX;
    this.relativePanY = cursorY;
  }

  /**
   * finish a panning of the camera
   */
  public void endPan(){
    System.out.println("!--- panning ended ---!");
    this.x            -= (this.relativePanX - this.startPanX);
    this.y            -= (this.relativePanY - this.startPanY);
    this.relativePanX = 0.0;
    this.relativePanY = 0.0;
    this.startPanX    = 0.0;
    this.startPanY    = 0.0;
  }

  public Double getZoom(){
    return Math.exp(this.zoom);
  }

  public void setZoom(Double zoom){
    this.zoom = Math.log(zoom);
  }

  public void zoomIn(){
    this.zoom += zoomStep;
  }

  public void zoomIn(Double speed){
    this.zoom += speed;
  }

  public void zoomOut(){
    this.zoom -= zoomStep;
  }

  public void zoomOut(Double speed){
    this.zoom -= speed;
  }
}
