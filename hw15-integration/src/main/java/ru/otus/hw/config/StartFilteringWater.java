package ru.otus.hw.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import ru.otus.hw.services.PipeWaterService;

@Component
@RequiredArgsConstructor
public class StartFilteringWater implements InitializingBean  {

    private final PipeWaterService orderService;

    public void afterPropertiesSet() throws Exception {
        orderService.startGeneratePipeWater();
    }
}
