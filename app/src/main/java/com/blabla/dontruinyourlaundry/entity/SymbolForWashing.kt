package com.blabla.dontruinyourlaundry.data

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blabla.dontruinyourlaundry.R
import com.google.android.material.internal.ParcelableSparseArray
import kotlinx.parcelize.Parcelize

@Parcelize
data class SymbolForWashing(
    @DrawableRes val pictureId: Int,
    val meaningOfSymbol: String,
    var selected: Boolean = false
) : Parcelable


@Parcelize
class Symbols : ArrayList<SymbolForWashing>(), Parcelable

enum class SymbolForWashingDBO(val text: String) {
    WASH("Разрешена машинная стирка"),
    WASHNOTALLOWED("Машинная и ручная стирка запрещены. Разрешена химчистка"),
    WASHHAND("Ручная стирка при температуре воды до 40 °C. Не тереть, отжимать аккуратно, без перекручивания"),
    WASHHAND30("Ручная стирка при температуре воды до 30 °C, не тереть, отжимать деликатно"),
    WASHHAND40("Ручная стирка при температуре воды до 40 °C, не тереть, отжимать деликатно"),
    WASH30("Ручная или машинная стирка при температуре воды не выше 30°C. Не подвергать сильному отжиму"),
    WASH40("Ручная или машинная стирка при температуре воды не выше 40°C"),
    WASH50("Ручная или машинная стирка при температуре воды не выше 50°C"),
    WASH60("Ручная или машинная стирка при температуре воды не выше 60°"),
    WASH70("Ручная или машинная стирка при температуре воды не выше 70°C"),
    WASH90("Ручная или машинная стирка при температуре воды не выше 95°C"),
    WASH30CARE("Деликатная ручная или машинная стирка при температуре воды до 30 °C, с использованием программ деликатной стирки (полоскание при низкой температуре и короткое время отжима)"),
    WASH30EXTRACARE("Особо деликатная ручная или машинная стирка при температуре воды до 30°C. Не отжимать руками"),
    WASH40CARE("Деликатная ручная или машинная стирка при температуре воды не выше 40°C"),
    WASH40EXTRACARE("Особо деликатная стирка при температуре воды не выше 40°C"),
    WASH50CARE("Деликатная ручная или машинная стирка при температуре до 50°C"),
    WASH60CARE("Деликатная ручная или машинная стирка при температуре воды до 60 °C"),
    WASH90CARE("Деликатная ручная или машинная стирка при температуре воды до 95 °C"),
    WASH30DOT("Ручная или машинная стирка при температуре воды не выше 30°C. Не подвергать сильному отжиму"),
    WASH40DOT("Ручная или машинная стирка при температуре воды не выше 40°C"),
    WASH50DOT("Ручная или машинная стирка при температуре воды не выше 50°C"),
    WASH60DOT("Ручная или машинная стирка при температуре воды не выше 60°C"),
    WASH70DOT("Ручная или машинная стирка при температуре воды не выше 70°C"),
    WASH90DOT("Ручная или машинная стирка при температуре воды не выше 95°C"),
    WASH30DOTCARE("Деликатная ручная или машинная стирка при температуре воды до 30°C. Не подвергать сильной механической обработке, отжим ослабленный"),
    WASH30DOTEXTRACARE("Особо деликатная стирка при температуре воды до 30°C. Не отжимать руками"),
    WASH40DOTCARE("Деликатная ручная или машинная стирка при температуре воды до 40 °C"),
    WASH40DOTEXTRACARE("Особо деликатная стирка при температуре воды до 40°C. Не отжимать руками"),
    WASH50DOTCARE("Деликатная ручная или машинная стирка при начальной температуре воды не выше 50°C"),
    WASH60DOTCARE("Деликатная ручная или машинная стирка при температуре воды не более 60°C"),
    WASH90DOTCARE("Деликатная ручная или машинная стирка при температуре воды не выше 95°C"),

    BLEACH("Отбеливание разрешено любыми окисляющими средствами"),
    BLEACHNONCHLORINE("Отбеливание разрешено только кислородсодержащими отбеливателями, в составе которых отсутствует хлор"),
    BLEACHNOTALLOWED("Отбеливание запрещено. Стиральные порошки не должны содержать хлор"),

    DRY("Разрешено отжимать и сушить в стиральной машине или в сушилке"),
    DRY40("Сушка и отжим при низкой температуре не выше 40°C"),
    DRY60("Сушка и отжим при средней температуре не выше 60°C"),
    DRY80("Сушка и отжим при высокой температуре не выше 80°C"),
    DRYNOHEAT("Обычная барабанная сушка и отжим при нормальной температуре, без нагрева воздуха"),
    DRY40CARE("Деликатная сушка и отжим в сушильном барабане при температуре не выше 40°C"),
    DRY40EXTRACARE("Особо деликатная сушка и отжим в сушильном барабане при температуре не выше 40°C"),
    DRY60CARE("Деликатная сушка и отжим при температуре не выше 60°C"),
    DRYLINE("После отжима разрешена сушка в подвешенном(вертикальном) состоянии"),
    DRYSHADE("Сушка в тени, вдали от прямых солнечных лучей"),
    DRYLINESHADE("Сушка в подвешенном (вертикальном) состоянии в тени и вдали от прямых солнечных лучей"),
    DRYDRIP("Сушка без отжима в подвешенном (вертикальном) состоянии"),
    DRYDRIPSHADE("Сушка без отжима в подвешенном (вертикальном) состоянии в тени, вдали от прямых солнечных лучей"),
    DRYFLAT("Сушка на горизонтальной поверхности в расправленном состоянии"),
    DRYFLATSHADE("Сушка на горизонтальной поверхности в расправленном состоянии в тени"),
    DRYNOTALLOWED("Запрещено отжимать и сушить в машинке"),

    IRON("Для восстановления формы и внешнего вида изделия его разрешено гладить при любой температуре, с паром или без"),
    IRON110("Разрешено гладить при максимальной температуре 110°C (соответствует символу в виде одной точки на терморегуляторе утюга), допустимо для синтетики, нейлона, акрила, полиэстера, полиамида, ацетата; использовать тканевую прокладку, не пользоваться паром"),
    IRON150("Разрешено гладить при максимальной температуре 150°C (соответствует символу в виде двух точек на терморегуляторе утюга), допустимо для шерсти и смешанных волокон с полиэстером и вискозой; использовать влажную ткань"),
    IRON200("Разрешено гладить при максимальной температуре 200°C (соответствует символу в виде трёх точек на терморегуляторе утюга), допустимо для льна и хлопка; можно слегка увлажнить изделие"),
    IRONNOTALLOWED("Глажение с паром навредит одежде, рекомендовано регулярное глажение в указанном температурном режиме"),
    IRONSTEAMNOTALLOWED("Изделие не должно подвергаться глажению, пропаривание и обработку паром не применять"),

    DRYCLEANA("Сухая чистка с любым органическим растворителем"),
    DRYCLEANP("Обычная сухая чистка с использованием тетрахлорэтилена и всех растворителей, перечисленных для символа «F»"),
    DRYCLEANP2("Деликатная химчистка при ограниченном добавлении воды с осторожным применением растворителей, указанных для символа «P», а также контроле за механическим воздействием и температуре сушки, чистка-самообслуживание запрещена"),
    DRYCLEANF("Обычная сухая чистка с использованием только углеводорода, бензина и трифтортрихорметана"),
    DRYCLEANF2("Деликатная химчистка при ограниченном добавлении воды с осторожным применением растворителей, указанных для символа «F», а также контроле за механическим воздействием и температуре сушки, чистка-самообслуживание запрещена"),
    DRYCLEANW("Обычный режим аквачистки и сушки в соответствии с IEC 456"),
    DRYCLEANW2("Деликатный режим аквачистки с ограничениями по механическому режиму и режиму сушки с учётом особенностей изделия, относительная усадка — 50+5%"),
    DRYCLEANW3("Очень деликатный режим аквачистки с ограничениями по механическому режиму и режиму сушки с учётом особенностей изделия, относительная усадка — 25+2,5%"),
    DRYCLEANSHORT("Одежда может быть очищена в сокращенном цикле. Символ может использоваться в сочетании с буквой"),
    DRYCLEANLOWHEAT("Одежда может быть очищена в умеренном температурном режиме. Символ может использоваться в сочетании с буквой"),
    DRYCLEANLOWMOISTURE("При чистке допустима пониженная влажность. Символ может использоваться в сочетании с буквой"),
    DRYCLEANNOSTEAM("Запрещено использовать пар на завершающей стадии чистки. Символ может использоваться в сочетании с буквой"),
    DRYCLEANNOTALLOWED("Изделие не должно подвергаться химической чистке, удаление пятен растворителем запрещено"),
    DRYCLEANWETNOTALLOWED("Мокрая чистка запрещена"),
    WRINGING("Нельзя выкручивать изделие при отжиме");

    fun toSymbolForWashing(): SymbolForWashing {
        val resourceId = when (this) {
            WASH -> R.drawable.wash
            WASHNOTALLOWED -> R.drawable.wh_washing_not_allowed
            WASHHAND -> R.drawable.wh_washing_hand
            WASHHAND30 -> R.drawable.wh_washing_hand_30deg
            WASHHAND40 -> R.drawable.wh_washing_hand_40deg
            WASH30 -> R.drawable.wh_washing_30deg
            WASH40 -> R.drawable.wh_washing_40deg
            WASH50 -> R.drawable.wh_washing_50deg
            WASH60 -> R.drawable.wh_washing_60deg
            WASH70 -> R.drawable.wh_washing_70deg
            WASH90 -> R.drawable.wh_washing_90deg
            WASH30CARE -> R.drawable.wh_washing_30deg_permanent_press
            WASH30EXTRACARE -> R.drawable.wh_washing_30deg_extra_care
            WASH40CARE -> R.drawable.wh_washing_40deg_permanent_press
            WASH40EXTRACARE -> R.drawable.wh_washing_40deg_extra_care
            WASH50CARE -> R.drawable.wh_washing_50deg_permanent_press
            WASH60CARE -> R.drawable.wh_washing_60deg_permanent_press
            WASH90CARE -> R.drawable.wh_washing_95deg_permanent_press
            WASH30DOT -> R.drawable.wh_washing_30deg_alt
            WASH40DOT -> R.drawable.wh_washing_40deg_alt
            WASH50DOT -> R.drawable.wh_washing_50deg_alt
            WASH60DOT -> R.drawable.wh_washing_60deg_alt
            WASH70DOT -> R.drawable.wh_washing_70deg_alt
            WASH90DOT -> R.drawable.wh_washing_95deg_alt
            WASH30DOTCARE -> R.drawable.wh_washing_30deg_permanent_press_alt
            WASH30DOTEXTRACARE -> R.drawable.wh_washing_30deg_extra_care_alt
            WASH40DOTCARE -> R.drawable.wh_washing_40deg_permanent_press_alt
            WASH40DOTEXTRACARE -> R.drawable.wh_washing_40deg_extra_care_alt
            WASH50DOTCARE -> R.drawable.wh_washing_50deg_permanent_press_alt
            WASH60DOTCARE -> R.drawable.wh_washing_60deg_permanent_press_alt
            WASH90DOTCARE -> R.drawable.wh_washing_95deg_permanent_press_alt
            BLEACH -> R.drawable.wh_bleaching
            BLEACHNONCHLORINE -> R.drawable.wh_bleaching_non_chlorine
            BLEACHNOTALLOWED -> R.drawable.wh_bleaching_not_allowed
            DRY -> R.drawable.wh_drying_tumble
            DRY40 -> R.drawable.wh_drying_tumble_low_heat
            DRY60 -> R.drawable.wh_drying_tumble_medium_heat
            DRY80 -> R.drawable.wh_drying_tumble_high_heat
            DRYNOHEAT -> R.drawable.wh_drying_tumble_no_heat
            DRY40CARE -> R.drawable.wh_drying_tumble_low_heat_permanent_press
            DRY40EXTRACARE -> R.drawable.wh_drying_tumble_low_heat_extra_care
            DRY60CARE -> R.drawable.wh_drying_tumble_medium_heat_permanent_press
            DRYLINE -> R.drawable.wh_drying_line_dry
            DRYSHADE -> R.drawable.wh_drying_dry_shade
            DRYLINESHADE -> R.drawable.wh_drying_line_dry_shade
            DRYDRIP -> R.drawable.wh_drying_drip_dry
            DRYDRIPSHADE -> R.drawable.wh_drying_drip_dry_shade
            DRYFLAT -> R.drawable.wh_drying_flat_dry
            DRYFLATSHADE -> R.drawable.wh_drying_flat_dry_shade
            DRYNOTALLOWED -> R.drawable.wh_drying_tumble_not_allowed
            IRON -> R.drawable.wh_ironing
            IRON110 -> R.drawable.wh_ironing_low
            IRON150 -> R.drawable.wh_ironing_medium
            IRON200 -> R.drawable.wh_ironing_high
            IRONNOTALLOWED -> R.drawable.wh_ironing_not_allowed
            IRONSTEAMNOTALLOWED -> R.drawable.wh_ironing_steam_not_allowed
            DRYCLEANA -> R.drawable.wh_drycleaning_a
            DRYCLEANP -> R.drawable.wh_drycleaning_p
            DRYCLEANP2 -> R.drawable.wh_drycleaning_p_2
            DRYCLEANF -> R.drawable.wh_drycleaning_f
            DRYCLEANF2 -> R.drawable.wh_drycleaning_f_2
            DRYCLEANW -> R.drawable.wh_drycleaning_w
            DRYCLEANW2 -> R.drawable.wh_drycleaning_w_2
            DRYCLEANW3 -> R.drawable.wh_drycleaning_w_3
            DRYCLEANSHORT -> R.drawable.wh_drycleaning_short_cycle
            DRYCLEANLOWHEAT -> R.drawable.wh_drycleaning_low_heat
            DRYCLEANLOWMOISTURE -> R.drawable.wh_drycleaning_reduced_moisture
            DRYCLEANNOSTEAM -> R.drawable.wh_drycleaning_no_steam
            DRYCLEANNOTALLOWED -> R.drawable.wh_washing_95deg_permanent_press_alt
            DRYCLEANWETNOTALLOWED -> R.drawable.wh_drycleaning_wetclean_not_allowed
            WRINGING -> R.drawable.wh_wringing_not_allowed
        }
        return SymbolForWashing(
            pictureId = resourceId,
            meaningOfSymbol = this.text
        )
    }
}



