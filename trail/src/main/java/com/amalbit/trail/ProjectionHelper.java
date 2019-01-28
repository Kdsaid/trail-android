package com.amalbit.trail;

import android.graphics.Point;
import com.amalbit.trail.RouteOverlayView.Route;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by amal.chandran on 04/11/17.
 */

class ProjectionHelper {

  private float x, y;

  private android.graphics.Point previousPoint;

  private boolean isRouteSet;

  private float previousZoomLevel = -1.0f;

  private boolean isZooming = false;

  private LatLng mLineChartCenterLatLng;

  public Point point;

  public ProjectionHelper() {
  }

  public void setCenterLatLng(LatLng lineChartCenterLatLng) {
    mLineChartCenterLatLng = lineChartCenterLatLng;
    isRouteSet = true;
  }

  void onCameraMove(Projection projection, CameraPosition cameraPosition, Route route) {
    if (previousZoomLevel != cameraPosition.zoom) {
      isZooming = true;
    }
    previousZoomLevel = cameraPosition.zoom;
    point = projection.toScreenLocation(mLineChartCenterLatLng);

    if (previousPoint != null) {
      x = previousPoint.x - point.x;
      y = previousPoint.y - point.y;
    }
    if (isRouteSet) {
      if (isZooming) {
        route.scalePathMatrix(cameraPosition.zoom);
        isZooming = false;
      }
      route.translatePathMatrix(-x, -y);
      previousPoint = point;
    }
  }
}
