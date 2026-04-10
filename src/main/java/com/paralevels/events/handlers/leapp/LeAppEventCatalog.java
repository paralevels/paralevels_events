package com.paralevels.events.handlers.leapp;

record EventMeta(String name, String description, int weight) {}

public final class LeAppEventCatalog {
  public static EventMeta metaFor(int id) {
    return switch (id) {
      case 0 -> new EventMeta("app_launched", "App launched", 1);
      case 1 -> new EventMeta("encounter_entered", "User entered an encounter", 2);
      case 2 -> new EventMeta("encounter_scene_changed", "Encounter scene changed", 1);
      case 3 -> new EventMeta("encounter_ended", "Encounter ended", 2);
      case 4 -> new EventMeta("encounter_scene_choice_made", "Encounter scene choice made", 1);
      case 10 -> new EventMeta("encounter_package_queued", "Encounter package queued", 2);
      case 11 -> new EventMeta("encounter_package_downloading", "Encounter package downloading", 1);
      case 12 -> new EventMeta("encounter_package_installing", "Encounter package installing", 1);
      case 13 -> new EventMeta("encounter_package_installed", "Encounter package installed", 2);
      case 20 -> new EventMeta("discovery_entered", "User entered discovery page", 1);
      case 21 -> new EventMeta("discovery_package_queued", "Discovery package queued", 2);
      case 22 -> new EventMeta("discovery_package_downloading", "Discovery package downloading", 1);
      case 23 -> new EventMeta("discovery_package_installing", "Discovery package installing", 1);
      case 24 -> new EventMeta("discovery_package_installed", "Discovery package installed", 3);
      case 30 -> new EventMeta("payment_entered", "User entered payment page", 1);
      case 31 -> new EventMeta("payment_package_toggled_true", "Payment package toggled true", 1);
      case 32 -> new EventMeta("payment_package_toggled_false", "Payment package toggled false", 1);
      case 33 -> new EventMeta("payment_purchase_button_pressed", "Payment purchase button pressed", 2);
      case 34 -> new EventMeta("payment_purchase_response_success", "Payment purchase response success", 3);
      case 35 -> new EventMeta("payment_purchase_response_failure", "Payment purchase response failure", 3);
      case 1000 -> new EventMeta("healthcheck", "Health check", 1);
      default -> new EventMeta("unknown_event", "Unrecognized event id", 0);
    };
  }
}
