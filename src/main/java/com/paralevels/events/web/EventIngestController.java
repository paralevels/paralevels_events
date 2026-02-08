package com.paralevels.events.web;

import com.paralevels.events.domain.incoming.MobileEventRequest;
import com.paralevels.events.routing.EventHandlerRegistry;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class EventIngestController {

  private final EventHandlerRegistry registry;

  public EventIngestController(EventHandlerRegistry registry) {
    this.registry = registry;
  }

  @PostMapping("/event")
  public ResponseEntity<?> ingest(
      @Valid @RequestBody MobileEventRequest req,
      @RequestHeader(value = "X-Event-Type", required = false) String eventType,
      @RequestHeader(value = "X-App-Version", required = false) String appVersion
  ) {
    String type = org.springframework.util.StringUtils.hasText(eventType) ? eventType : req.type();

    if (!org.springframework.util.StringUtils.hasText(type)) {
      return ResponseEntity.badRequest().body(Map.of(
          "error", "missing_event_type",
          "message", "Provide event type in X-Event-Type header or in JSON body as 'type'."
      ));
    }

    registry.get(type.trim().toLowerCase()).handle(req, appVersion);
    return ResponseEntity.accepted().build();
  }
}
