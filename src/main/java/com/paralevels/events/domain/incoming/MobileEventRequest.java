package com.paralevels.events.domain.incoming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record MobileEventRequest(
    String type, // type must be present either in the header via X-Event-Type or in the JSON body type field
    @NotNull Integer id,
    @NotBlank String app,
    @NotBlank String user,
    @NotBlank String device,
    String requestId,
    String clientTimestamp,
    Map<String, Object> prop
) {}
