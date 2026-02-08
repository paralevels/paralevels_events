package com.paralevels.events.handlers.leapp;

public record EventMeta(String name, String description, int weight) {}

public final class LeAppEventCatalog {
  public static EventMeta metaFor(int id) {
    return switch (id) {
      case 0 -> new EventMeta("app_launch", "App launched", 1);
      case 1 -> new EventMeta("encounter_entered", "User entered an encounter", 2);
      case 2 -> new EventMeta("encounter_scene_change", "Encounter scene changed", 1);
      case 3 -> new EventMeta("encounter_end", "Encounter ended", 2);
      case 20 -> new EventMeta("discovery_entered", "User entered discovery page", 1);
      case 21 -> new EventMeta("discovery_package_queued", "Discovery package queued", 2);
      case 22 -> new EventMeta("discovery_package_downloading", "Discovery package downloading", 1);
      case 23 -> new EventMeta("discovery_package_installing", "Discovery package installing", 1);
      case 24 -> new EventMeta("discovery_package_installed", "Discovery package installed", 3);
      default -> new EventMeta("unknown_event", "Unrecognized event id", 0);
    };
  }
}
