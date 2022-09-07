package com.blabla.dontruinyourlaundry.data

import com.blabla.dontruinyourlaundry.R

object ListOfCards {
    fun loadListOfCards(): MutableList<CardInfo> {
        return mutableListOf()
    }

    fun loadListOfAddedSymbols(): MutableList<SymbolForWashing> {
        val addSymbol = R.drawable.plus
        return mutableListOf(SymbolForWashing(addSymbol, "Добавить символы для стирки"))

    }


    fun loadListOfSymbolsForWashingCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wash, "Разрешена машинная стирка"),
            SymbolForWashing(
                R.drawable.wh_washing_not_allowed,
                "Машинная и ручная стирка запрещены. Разрешена химчстка. Обратитесь к специалистам, чтобы не испортить ткань"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_hand,
                "Стирать только руками при температуре воды до 40 °C. Не тереть, отжимать аккуратно, без перекручивания"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_hand_30deg,
                "Стирать только руками при температуре воды до 30 °C, не тереть, отжимать аккуратно, без перекручивания, обращаться с осторожностью"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_hand_40deg,
                "Стирать только руками при температуре воды до 40 °C, не тереть, отжимать аккуратно, без перекручивания, обращаться с осторожностью"
            ),

            SymbolForWashing(
                R.drawable.wh_washing_30deg,
                "Ручная или машинная стирка при температуре воды не выше 30°C. Требования к отжиму - минимальные обороты"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_40deg,
                "Ручная или машинная стирка при температуре воды не выше 40°C"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_50deg,
                "Ручная или машинная стирка при температуре воды не выше 50°C"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_60deg,
                "Ручная или машинная стирка при температуре воды не выше 60°"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_70deg,
                "Ручная или машинная стирка при температуре воды не выше 70°C"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_90deg,
                "Ручная или машинная стирка при температуре воды не выше 95°C"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_30deg_permanent_press,
                "Деликатная ручная или машинная стирка при температуре воды до 30 °C, нейтральные моющие средства, не подвергать сильной механической обработке, полоскание обычное, отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_30deg_extra_care,
                "Особо деликатная ручная или машинная стирка при температуре воды до 30°C, большой объем воды, минимальная механическая обработка, быстрое полоскание, не отжимать руками"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_40deg_permanent_press,
                "Деликатная ручная или машинная стирка при температуре воды не выше 40 °C, механические воздействия уменьшенные, умеренное полоскание при постепенном снижении температуры (в процессе остывания воды), отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_40deg_extra_care,
                "Особо деликатная стирка при температуре воды не выше 40°C, механические воздействия сильно уменьшенные, полоскание обычное, отжим ослабленный, не отжимать руками"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_50deg_permanent_press,
                "Деликатная ручная или машинная стирка при температуре до 50°C, механические воздействия уменьшенные, умеренное полоскание при постепенном снижении температуры (в процессе остывания воды), отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_60deg_permanent_press,
                "Деликатная ручная или машинная стирка при температуре воды до 60 °C, механические воздействия уменьшенные, умеренное полоскание при постепенном снижении температуры (в процессе остывания воды), отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_95deg_permanent_press,
                "Деликатная ручная или машинная стирка при температуре воды до 95 °C, механические воздействия уменьшенные, умеренное полоскание при постепенном снижении температуры (в процессе остывания воды), отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_30deg_alt,
                "Ручная или машинная стирка при температуре воды не выше 30°C. Требования к отжиму - минимальные обороты"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_40deg_alt,
                "Ручная или машинная стирка при температуре воды не выше 40°C"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_50deg_alt,
                "Ручная или машинная стирка при температуре воды не выше 50°C"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_60deg_alt,
                "Ручная или машинная стирка при температуре воды не выше 60°C"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_70deg_alt,
                "Ручная или машинная стирка при температуре воды не выше 70°C"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_95deg_alt,
                "Ручная или машинная стирка при температуре воды не выше 95°C"
            ),

            SymbolForWashing(
                R.drawable.wh_washing_30deg_permanent_press_alt,
                "Щадящая ручная или машинная стирка при температуре воды до 30°C, нейтральные моющие средства, не подвергать сильной механической обработке, полоскание обычное, отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_30deg_extra_care_alt,
                "Особо деликатная стирка при температуре воды до 30°C, большой объем воды, минимальная механическая обработка, быстрое полоскание, не отжимать руками"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_40deg_permanent_press_alt,
                "Деликатная ручная или машинная стирка при температуре воды до 40 °C, механические воздействия уменьшенные, умеренное полоскание при постепенном снижении температуры (в процессе остывания воды), отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_40deg_extra_care_alt,
                "Особо деликатная стирка при температуре воды до 40°C, механические воздействия сильно уменьшенные, полоскание обычное, отжим ослабленный, не отжимать руками"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_50deg_permanent_press_alt,
                "Деликатная ручная или машинная стирка при начальной температуре воды не выше 50 °C, механические воздействия уменьшенные, умеренное полоскание при постепенном снижении температуры (в процессе остывания воды), отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_60deg_permanent_press_alt,
                "Деликатная ручная или машинная стирка при температуре воды не более 60 °C, механические воздействия уменьшенные, умеренное полоскание при постепенном снижении температуры (в процессе остывания воды), отжим ослабленный"
            ),
            SymbolForWashing(
                R.drawable.wh_washing_95deg_permanent_press_alt,
                "Деликатная ручная или машинная стирка при температуре воды не выше 95 °C, механические воздействия уменьшенные, умеренное полоскание при постепенном снижении температуры (в процессе остывания воды), отжим ослабленный"
            )
        )
    }

    fun loadListOfSymbolsForBleachingCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(
                R.drawable.wh_bleaching,
                "Отбеливание разрешено любыми окисляющими отбеливателями"
            ),
            SymbolForWashing(
                R.drawable.wh_bleaching_non_chlorine,
                "Отбеливание разрешено только кислородсодержащими/нехлорными отбеливателями"
            ),
            SymbolForWashing(
                R.drawable.wh_bleaching_not_allowed,
                "Отбеливать изделие нельзя, не использовать отбеливающие и хлорсодержащие стиральные порошки"
            )
        )
    }

    fun loadListOfSymbolsForDryingCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(
                R.drawable.wh_drying_tumble,
                "Разрешено отжимать и сушить в стиральной машине или в сушилке, не требует особой осторожности"
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_low_heat,
                "Обычная барабанная сушка и отжим при низкой температуре — до 40°C"
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_medium_heat,
                "Обычная барабанная сушка и отжим при средней температуре — до 60°C"
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_high_heat,
                "Обычная барабанная сушка и отжим при высокой температуре — до 80°C"
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_no_heat,
                "Обычная барабанная сушка и отжим при нормальной температуре, без нагрева воздуха"
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_low_heat_permanent_press,
                "Щадящая сушка и отжим в сушильном барабане при низкой температуре — до 40°C"
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_low_heat_extra_care,
                "Особо деликатная сушка и отжим в сушильном барабане при низкой температуре — до 40°C"
            ),
            SymbolForWashing(
                R.drawable.wh_drying_tumble_medium_heat_permanent_press,
                "Щадящая сушка и отжим в сушильном барабане при средней температуре — до 60°C"
            ),
            SymbolForWashing(
                R.drawable.wh_drying_line_dry,
                "После отжима разрешена вертикальная сушка, сушить в подвешенном состоянии, отжимать можно"
            ),
            SymbolForWashing(R.drawable.wh_drying_dry_shade, "Изделие необходимо сушить в тени, вдали от прямых солнечных лучей"),
            SymbolForWashing(
                R.drawable.wh_drying_line_dry_shade,
                "Сушка изделия в вертикальном положении в тени и вдали от прямых солнечных лучей"
            ),
            SymbolForWashing(R.drawable.wh_drying_drip_dry, "Сушка без отжима в подвешенном (вертикальном) состоянии"),
            SymbolForWashing(
                R.drawable.wh_drying_drip_dry_shade,
                "Сушка без отжима в подвешенном (вертикальном) состоянии в тени, вдали от прямых солнечных лучей"
            ),
            SymbolForWashing(R.drawable.wh_drying_flat_dry, "Сушка насыщенного влагой изделия на горизонтальной поверхности в расправленном состоянии"),
            SymbolForWashing(R.drawable.wh_drying_flat_dry_shade, "Сушка насыщенного влагой изделия на горизонтальной поверхности в расправленном состоянии в тени"),
            SymbolForWashing(R.drawable.wh_drying_tumble_not_allowed, "Отжимать и сушить в стиральной машине нельзя")

        )
    }

    fun loadListOfSymbolsForIroningCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wh_ironing, "Для восстановления формы и внешнего вида изделия его разрешено гладить при любой температуре, с паром или без"),
            SymbolForWashing(R.drawable.wh_ironing_low, "Разрешено гладить при максимальной температуре 110°C (соответствует символу в виде одной точки на терморегуляторе утюга), допустимо для синтетики, нейлона, акрила, полиэстера, полиамида, ацетата; использовать тканевую прокладку, не пользоваться паром"),
            SymbolForWashing(R.drawable.wh_ironing_medium, "Разрешено гладить при максимальной температуре 150°C (соответствует символу в виде двух точек на терморегуляторе утюга), допустимо для шерсти и смешанных волокон с полиэстером и вискозой; использовать влажную ткань"),
            SymbolForWashing(R.drawable.wh_ironing_high, "Разрешено гладить при максимальной температуре 200°C (соответствует символу в виде трёх точек на терморегуляторе утюга), допустимо для льна и хлопка; можно слегка увлажнить изделие"),
            SymbolForWashing(R.drawable.wh_ironing_not_allowed, "Глажение с паром навредит одежде, рекомендовано регулярное глажение в указанном температурном режиме"),
            SymbolForWashing(R.drawable.wh_ironing_steam_not_allowed, "Изделие не должно подвергаться глажению, пропаривание и обработку паром не применять")
        )
    }

    fun loadListOfSymbolsForDryCleaningCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wh_drycleaning_a, "Сухая чистка с любым органическим растворителем"),
            SymbolForWashing(R.drawable.wh_drycleaning_p, "Обычная сухая чистка с использованием тетрахлорэтилена и всех растворителей, перечисленных для символа «F»"),
            SymbolForWashing(R.drawable.wh_drycleaning_p_2, "Деликатная химчистка при ограниченном добавлении воды с осторожным применением растворителей, указанных для символа «P», а также контроле за механическим воздействием и температуре сушки, чистка-самообслуживание запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_f, "Обычная сухая чистка с использованием только углеводорода, бензина и трифтортрихорметана"),
            SymbolForWashing(R.drawable.wh_drycleaning_f_2, "Деликатная химчистка при ограниченном добавлении воды с осторожным применением растворителей, указанных для символа «F», а также контроле за механическим воздействием и температуре сушки, чистка-самообслуживание запрещена"),
            SymbolForWashing(R.drawable.wh_drycleaning_w, "Обычный режим аквачистки и сушки в соответствии с IEC 456"),
            SymbolForWashing(R.drawable.wh_drycleaning_w_2, "Деликатный режим аквачистки с ограничениями по механическому режиму и режиму сушки с учётом особенностей изделия, относительная усадка — 50+5%"),
            SymbolForWashing(R.drawable.wh_drycleaning_w_3, "Очень деликатный режим аквачистки с ограничениями по механическому режиму и режиму сушки с учётом особенностей изделия, относительная усадка — 25+2,5%"),
            SymbolForWashing(R.drawable.wh_drycleaning_short_cycle, "Одежда может быть очищена в сокращенном цикле. Символ может использоваться в сочетании с буквой"),
            SymbolForWashing(R.drawable.wh_drycleaning_low_heat, "Одежда может быть очищена в умеренном температурном режиме. Символ может использоваться в сочетании с буквой"),
            SymbolForWashing(R.drawable.wh_drycleaning_reduced_moisture, "При чистке допустима пониженная влажность. Символ может использоваться в сочетании с буквой"),
            SymbolForWashing(R.drawable.wh_drycleaning_no_steam, "Запрещено использовать пар на завершающей стадии чистки. Символ может использоваться в сочетании с буквой"),
            SymbolForWashing(R.drawable.wh_drycleaning_not_allowed, "Изделие не должно подвергаться химической чистке, удаление пятен растворителем запрещено"),
            SymbolForWashing(R.drawable.wh_drycleaning_wetclean_not_allowed, "Мокрая чистка запрещена")
        )
    }

    fun loadListOfSymbolsForTwistingCategory(): List<SymbolForWashing> {
        return listOf(
            SymbolForWashing(R.drawable.wh_wringing_not_allowed, "Нельзя выкручивать изделие при отжиме")
        )
    }


    fun loadListOfSymbolGuide(): List<SymbolGuide> {
        return listOf(
            SymbolGuide("Стирка", loadListOfSymbolsForWashingCategory()),
            SymbolGuide("Отбеливание", loadListOfSymbolsForBleachingCategory()),
            SymbolGuide("Сушка", loadListOfSymbolsForDryingCategory()),
            SymbolGuide("Глажка", loadListOfSymbolsForIroningCategory()),
            SymbolGuide("Сухая чистка", loadListOfSymbolsForDryCleaningCategory()),
            SymbolGuide("Скручивание", loadListOfSymbolsForTwistingCategory())

        )


    }

    fun loadlistOfChoosedSymbols(): MutableList<SymbolForWashing> {
        return mutableListOf()
    }
}

