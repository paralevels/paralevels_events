package com.paralevels.events.domain.incoming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record MobileEventRequest(
    @NotBlank String type,
    @NotNull Integer id,
    @NotBlank String app,
    @NotBlank String user,
    @NotBlank String device,
    String requestId,
    String clientTimestamp,
    Map<String, Object> prop
) {}
