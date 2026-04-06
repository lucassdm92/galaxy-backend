package com.galaxybck.event;

import com.galaxybck.model.dto.DeliveryResponse;

public record DeliveryAvailableEvent(String riderUsername, DeliveryResponse response) {}