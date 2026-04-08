package com.paralevels.events.handlers.leapp;

record EventMeta(String name, String description, int weight) {}

public final class LeAppEventCatalog {
  public static EventMeta metaFor(int id) {
    return switch (id) {
      case 0 -> new EventMeta("app_launched", "App launched", 1);
      case 1 -> new EventMeta("encounter_entered", "User entered an encounter", 2);
      case 2 -> new EventMeta("encounter_scene_changed", "Encounter scene changed", 1);
      case 3 -> new EventMeta("encounter_ended", "Encounter ended", 2);
      case 20 -> new EventMeta("discovery_entered", "User entered discovery page", 1);
      case 21 -> new EventMeta("discovery_package_queued", "Discovery package queued", 2);
      case 22 -> new EventMeta("discovery_package_downloading", "Discovery package downloading", 1);
      case 23 -> new EventMeta("discovery_package_installing", "Discovery package installing", 1);
      case 24 -> new EventMeta("discovery_package_installed", "Discovery package installed", 3);
      case 1000 -> new EventMeta("healthcheck", "Health check", 1);
      default -> new EventMeta("unknown_event", "Unrecognized event id", 0);
    };
  }
}
