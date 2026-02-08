package com.paralevels.events.routing;

import com.paralevels.events.domain.incoming.MobileEventRequest;

public interface EventHandler {
  String type(); // e.g. "le_app", "analytics_v2", etc.
  void handle(MobileEventRequest req, String appVersion);
}
