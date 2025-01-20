package com.foxinthebox.lichcraft;

import eu.midnightdust.lib.config.MidnightConfig;

public class LichcraftConfig extends MidnightConfig {

    public static final String LICHCRAFT = "lichcraft";

    @Comment(category = LICHCRAFT) public static Comment notice;

    @Entry(category = LICHCRAFT) public static int low_power_soul_value = 1;
    @Entry(category = LICHCRAFT) public static int mid_power_soul_value = 2;
    @Entry(category = LICHCRAFT) public static int high_power_soul_value = 100;
    @Comment(category = LICHCRAFT) public static Comment spacer1;
    @Entry(category = LICHCRAFT) public static int revive_cost = 1000;
    @Entry(category = LICHCRAFT) public static int max_phylactery_souls = 5000;
}
