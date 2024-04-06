package ru.otus.hw.services;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import ru.otus.hw.domain.Admixture;
import ru.otus.hw.domain.AdmixtureType;
import ru.otus.hw.domain.Water;

@Service
@Slf4j
@AllArgsConstructor
public class PipeWaterServiceImpl implements PipeWaterService {

    private static final AdmixtureType[] ADMIXTURE_TYPES = {AdmixtureType.RUST, AdmixtureType.CHLORINE};

    private final WaterFilter waterFilter;

    @Override
    public void startGeneratePipeWater() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            int num = i + 1;
            pool.execute(() -> {
                Water item = generateWater();
                log.info("{}, New water chunk: {}", num, item.toString());
                Water water = waterFilter.process(item);
                log.info("{}, Drinking water: {}", num, water.toString());
            });
            delay();
        }
    }

    private static Water generateWater() {
        long volume = RandomUtils.nextLong(1, 5);
        List<Admixture> admixtures = Arrays.stream(ADMIXTURE_TYPES)
                .map(admixtureType -> new Admixture(
                        admixtureType,
                        RandomUtils.nextDouble(0.1, 0.5)
                ))
                .toList();

        return new Water(volume, admixtures);
    }


    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
