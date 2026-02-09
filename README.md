# paralevels_events (Paralevels Events Ingest Service)

This repository contains the **Paralevels Events Ingest Service**, a Spring Boot application that receives event data from mobile clients, enriches it server-side, and stores it in an Oracle Autonomous JSON Database (SODA collection).

The service is designed to be:
- lightweight
- extensible (multiple event families / collections)
- secure (backend not publicly exposed)
- production-friendly (reverse-proxied via Apache)

---

## High-Level Architecture

Mobile App
|
| HTTPS POST /api/event
|
Cloudflare
|
Apache (public entry point)
|
| localhost proxy
|
Spring Boot (port 8071, localhost only)
|
| HTTPS POST
|
Oracle Autonomous JSON DB (SODA)

Key properties:
- Spring Boot is **not** exposed publicly
- Apache reverse-proxies `/api/event` to the backend
- SELinux remains **enabled** (no blanket disabling)
- Event schemas are JSON-first and forward-compatible

---

## API Endpoint

### POST `/api/event`

This endpoint accepts event data from clients.

Event type can be supplied in **either**:
- HTTP header: `X-Event-Type`
- JSON body field: `"type"`

Header takes precedence if both are present.

---

## Incoming Event Model (Client → Server)

This is the **base event format** sent by mobile applications.

### Minimal example
```json
{
  "id": 1,
  "app": "app1234",
  "user": "user_789",
  "device": "iPhone 14",
  "prop": {
    "sid": 1,
    "cid": 1000,
    "eid": 1000001
  }
}
```

### Full example (recommended)
```json
{
  "type": "le_app",
  "id": 1,
  "app": "app1234",
  "user": "user_789",
  "device": "iPhone 14",
  "request_id": "550e8400-e29b-41d4-a716-446655440000",
  "client_timestamp": "2026-02-06T14:30:00Z",
  "prop": {
    "sid": 1,
    "cid": 1000,
    "eid": 1000001
  }
}
```

Field Description
type Event family identifier (e.g. le_app)
id Numeric event ID (interpreted by handler catalog)
app Application identifier
user Client-reported user identifier
device Client device descriptor
request_id Optional UUID for idempotency / tracing
client_timestamp Optional client-side event time (ISO-8601)
prop Arbitrary event-specific properties

Notes:

- type may be omitted from JSON if provided via X-Event-Type header
- prop is intentionally schema-flexible

---

## Enriched Event Model (Server → Database)

The backend enriches incoming events before persisting them to Oracle SODA.

### Example enriched document
```json
{
  "schema_version": 1,
  "type": "le_app",

  "event_id": 1,
  "event_name": "encounter_entered",
  "event_description": "User entered an encounter",
  "event_weight": 2,

  "event_timestamp": "2026-02-06T14:30:00Z",
  "ingest_timestamp": "2026-02-06T14:30:02Z",
  "event_version": "2.1.0",

  "app": "app1234",
  "user": "user_789",
  "device": "iPhone 14",
  "request_id": "550e8400-e29b-41d4-a716-446655440000",

  "event_properties": {
    "sid": 1,
    "cid": 1000,
    "eid": 1000001
  }
}
```

## Enrichment responsibilities

The server:

- resolves event_name, description, and weight from a catalog
- applies server-side timestamps
- normalizes structure across all event families
- routes events to the correct Oracle SODA collection by type


## Event Routing & Extensibility

Each event family is handled by its own EventHandler implementation.

To add a new event family:

- Create a new handler implementing EventHandler
- Define its catalog (ID → metadata mapping)
- Add a new SODA target under oracle.soda.types in application.yml

No controller changes are required.

---

## Configuration

### application.yml (example)

server:
  address: 127.0.0.1
  port: 8071

oracle:
  soda:
    types:
      le_app:
        url: https://.../soda/latest/le_app_events
        user: ORDS_USER
        pass: ORDS_PASSWORD

---

## Running Locally

./gradlew clean bootRun

---

## Test locally

curl -X POST http://localhost:8071/api/event \
  -H "Content-Type: application/json" \
  -H "X-Event-Type: le_app" \
  -d '{"id":1,"app":"local","user":"test","device":"dev"}'

---

## Production Deployment Notes

- Spring Boot runs on localhost only
- Apache reverse-proxies /api/event
- SELinux must allow Apache network connections: setsebool -P httpd_can_network_connect on
- Backend port must not be exposed via firewall

---

## Design Philosophy

- JSON-first, schema-evolving event storage
- Minimal client burden, maximal server control
- Clean separation between:
  - transport
  - enrichment
  - persistence
- Designed for long-term evolution without breaking clients

License

Internal / Private — Paralevels

---

## TODO:
- add a **schema version migration strategy** section,
- document **event catalogs** more formally,
- or write a short **“How to add a new event family”** developer guide.