package com.github.Mev10.config;

import com.github.Mev10.Tfcfreezer;
import com.github.Mev10.common.item.TfcfreezerFoodTraits;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TfcfreezerServerConfig {

    public final Map<TfcfreezerFoodTraits.Default, ForgeConfigSpec.DoubleValue> foodTraits;

    TfcfreezerServerConfig(ForgeConfigSpec.Builder innerBuilder) {
        Function<String, ForgeConfigSpec.Builder> builder =
                name -> innerBuilder.translation(Tfcfreezer.MOD_ID + ".config.server." + name);

        innerBuilder.push("general");

        foodTraits = new HashMap<>();
        Arrays.stream(TfcfreezerFoodTraits.Default.values()).forEach(trait ->
                foodTraits.put(trait, builder.apply("trait" + trait.getCapitalizedName() + "Modifier").comment("The modifier for the '" + trait.getCapitalizedName() + "' food trait. Values less than 1 extend food lifetime, values greater than one decrease it. A value of zero stops decay.")
                        .defineInRange("trait" + trait.getCapitalizedName() + "Modifier", trait.getMod(), 0, Double.MAX_VALUE))
        );
    }
}
