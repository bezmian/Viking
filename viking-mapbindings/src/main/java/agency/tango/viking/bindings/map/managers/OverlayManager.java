package agency.tango.viking.bindings.map.managers;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;

import agency.tango.viking.bindings.map.models.BindableOverlay;

public class OverlayManager<T extends BindableOverlay> extends MapEntityManagerBase<T>
    implements IMapEntityManager<T> {

  public OverlayManager(MapResolver mapResolver) {
    super(mapResolver);
  }

  @Override
  T addToMap(T item, GoogleMap googleMap) {
    item.setGroundOverlay(googleMap.addGroundOverlay(item.getGroundOverlayOptions()));
    return item;
  }

  @Override
  void removeFromMap(T entity, GoogleMap googleMap) {
    entity.getGroundOverlay().remove();
  }

  @Override
  void updateOnMap(T entity, T item, GoogleMap googleMap) {
    GroundOverlay groundOverlay = entity.getGroundOverlay();
    GroundOverlayOptions groundOverlayOptions = item.getGroundOverlayOptions();

    if (groundOverlayOptions.getBounds() != null) {
      groundOverlay.setPositionFromBounds(groundOverlayOptions.getBounds());
    } else {
      groundOverlay.setPosition(groundOverlayOptions.getLocation());
    }
    groundOverlay.setImage(groundOverlayOptions.getImage());
    groundOverlay.setBearing(groundOverlayOptions.getBearing());
    groundOverlay.setZIndex(groundOverlayOptions.getZIndex());
    groundOverlay.setTransparency(groundOverlayOptions.getTransparency());
    groundOverlay.setVisible(groundOverlayOptions.isVisible());
    groundOverlay.setClickable(groundOverlayOptions.isClickable());
  }

  @Nullable
  public T retrieveBindableOverlay(GroundOverlay groundOverlay) {
    for (T bindableOverlay : entities) {
      if (bindableOverlay.getGroundOverlay().equals(groundOverlay)) {
        return bindableOverlay;
      }
    }
    return null;
  }
}