package com.paralevels.events.routing;

import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EventHandlerRegistry {
  private final Map<String, EventHandler> handlersByType;

  public EventHandlerRegistry(List<EventHandler> handlers) {
    this.handlersByType = handlers.stream()
        .collect(Collectors.toUnmodifiableMap(
            h -> h.type().toLowerCase(),
            Function.identity()
        ));
  }

  public EventHandler get(String type) {
    var h = handlersByType.get(type.toLowerCase());
    if (h == null) throw new IllegalArgumentException("Unsupported event type: " + type);
    return h;
  }
}
