package br.mackenzie;

import java.util.concurrent.ThreadLocalRandom;

public class RandomPicker {

    public static <T extends Enum<T>> T pickRandom(Class<T> enumClass) {
        T[] values = enumClass.getEnumConstants();

        int index = ThreadLocalRandom.current().nextInt(values.length);

        return values[index];
    }
}
