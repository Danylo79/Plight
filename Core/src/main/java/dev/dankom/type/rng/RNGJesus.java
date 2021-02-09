package dev.dankom.type.rng;

import dev.dankom.util.general.MathUtil;

import java.util.ArrayList;
import java.util.List;

public class RNGJesus {

    private List<String> pool = new ArrayList<>();

    public RNGJesus(RNGNode... nodes) {
        for (RNGNode n : nodes) {
            for (int i = 0; i < n.getChance(); i++) {
                pool.add(n.getName());
            }
        }
    }

    public String get() {
        return pool.get(MathUtil.randInt(1, pool.size()));
    }

    public static class RNGNode {
        private final String name;
        private final int chance;

        public RNGNode(String name, int chance) {
            this.name = name;
            this.chance = chance;
        }

        public String getName() {
            return name;
        }

        public int getChance() {
            return chance;
        }
    }
}
