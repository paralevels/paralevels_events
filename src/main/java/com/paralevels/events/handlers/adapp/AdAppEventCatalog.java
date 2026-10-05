package com.paralevels.events.handlers.adapp;

record EventMeta(String name, String description, int weight) {}

public final class AdAppEventCatalog {
  public static EventMeta metaFor(int id) {
    return switch (id) {
      case 0 -> new EventMeta("app_launched", "App launched", 1);
      case 1 -> new EventMeta("app_exit_pressed", "App exit pressed", 1);
      case 10 -> new EventMeta("scene_entered", "User entered a scene", 1);
      case 11 -> new EventMeta("previous_scene_pressed", "Previous scene pressed", 1);
      case 12 -> new EventMeta("next_scene_pressed", "Next scene pressed", 1);
      case 13 -> new EventMeta("scene_scroll_left_pressed", "Scene scroll left pressed", 1);
      case 14 -> new EventMeta("scene_scroll_right_pressed", "Scene scroll right pressed", 1);
      case 20 -> new EventMeta("subscription_entered", "User entered subscription page", 2);
      case 21 -> new EventMeta("subscription_submitted", "Subscription submitted", 3);
      case 22 -> new EventMeta("subscription_exited", "User exited subscription page", 2);
      case 1000 -> new EventMeta("healthcheck", "Health check", 1);
      default -> new EventMeta("unknown_event", "Unrecognized event id", 0);
    };
  }
}