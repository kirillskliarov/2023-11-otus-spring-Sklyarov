package ru.otus.hw.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import ru.otus.hw.domain.Water;

@MessagingGateway
public interface WaterFilter {

	@Gateway(requestChannel = "pipeWaterChannel", replyChannel = "drinkingWaterChannel")
	Water process(Water orderItem);
}
