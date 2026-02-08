package com.paralevels.events.handlers.leapp;

import com.paralevels.events.config.OracleSodaProperties;
import com.paralevels.events.domain.incoming.MobileEventRequest;
import com.paralevels.events.oracle.OracleSodaClient;
import com.paralevels.events.routing.EventHandler;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class LeAppEventHandler implements EventHandler {

  private final OracleSodaClient oracle;
  private final OracleSodaProperties props;

  public LeAppEventHandler(OracleSodaClient oracle, OracleSodaProperties props) {
    this.oracle = oracle;
    this.props = props;
  }

  @Override
  public String type() {
    return "le_app";
  }

  @Override
  public void handle(MobileEventRequest req, String appVersion) {
    var meta = LeAppEventCatalog.metaFor(req.id());
    var now = Instant.now().toString();

    String eventTimestamp = (req.client_timestamp() != null && !req.client_timestamp().isBlank())
        ? req.client_timestamp()
        : now;

    var enriched = new java.util.LinkedHashMap<String, Object>();
    enriched.put("schema_version", 1);
    enriched.put("type", type());
    enriched.put("event_id", req.id());
    enriched.put("event_name", meta.name());
    enriched.put("event_description", meta.description());
    enriched.put("event_weight", meta.weight());
    enriched.put("event_timestamp", eventTimestamp);
    enriched.put("ingest_timestamp", now);
    enriched.put("event_version", (appVersion != null ? appVersion : "unknown"));
    enriched.put("app", req.app());
    enriched.put("user", req.user());
    enriched.put("device", req.device());
    if (req.request_id() != null && !req.request_id().isBlank()) {
      enriched.put("request_id", req.request_id());
    }
    enriched.put("event_properties", (req.prop() != null ? req.prop() : Map.of()));

    var key = type().trim().toLowerCase();
    var target = props.types().get(key);
    if (target == null) throw new IllegalStateException("Missing oracle.soda.types." + key);

    oracle.insertEvent(target.url(), target.user(), target.pass(), enriched);
  }
}
