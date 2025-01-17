package rezaei.mohammad.jalalidatepickerkmm

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jalalidatepickerkmm.composeapp.generated.resources.Res
import jalalidatepickerkmm.composeapp.generated.resources.round_event_24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import rezaei.mohammad.jalalidatepickerkmm.theme.PersianCalendarTheme
import rezaei.mohammad.jalalidatepickerkmm.theme.backgroundColor
import rezaei.mohammad.jalalidatepickerkmm.theme.selectedIconColor
import rezaei.mohammad.jalalidatepickerkmm.theme.textColor
import rezaei.mohammad.jalalidatepickerkmm.theme.textColorHighlight
import rezaei.mohammad.jalalidatepickerkmm.util.FormatHelper
import rezaei.mohammad.jalalidatepickerkmm.util.JalaliCalendar
import rezaei.mohammad.jalalidatepickerkmm.util.LeftToRightLayout
import rezaei.mohammad.jalalidatepickerkmm.util.PickerType
import rezaei.mohammad.jalalidatepickerkmm.util.RightToLeftLayout

@Composable
fun JalaliCalendarView(
    modifier: Modifier = Modifier,
    initialDate: JalaliCalendar? = null,
    onSelectDay: (JalaliCalendar) -> String?,
    onConfirm: (JalaliCalendar) -> Unit,
    onDismiss: () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.backgroundColor,
    selectedIconColor: Color = MaterialTheme.colorScheme.selectedIconColor,
    todayCircleColor: Color = MaterialTheme.colorScheme.selectedIconColor,
    textColorHighlight: Color = MaterialTheme.colorScheme.textColorHighlight,
    nextPreviousBtnColor: Color = MaterialTheme.colorScheme.textColor,
    headerDividerColor: Color = MaterialTheme.colorScheme.onBackground,
    headerTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    yearMonthTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    weekDaysTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
    dayNumberTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
    buttonsTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    eventTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
    eventIconRes: DrawableResource? = null
) {
    var iconSize: Dp by remember {
        mutableStateOf(43.dp)
    }

    var weekDaysLabelPadding: Dp by remember {
        mutableStateOf(18.dp)
    }
    var yearSelectorHeight: Dp by remember {
        mutableStateOf(280.dp)
    }

    var jalali by remember {
        mutableStateOf(initialDate ?: JalaliCalendar())
    }
    val today = JalaliCalendar()
    var selectedDate: JalaliCalendar? by remember {
        mutableStateOf(initialDate)
    }

    var pickerType: PickerType by remember {
        mutableStateOf(PickerType.Day)
    }

    /*val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            iconSize = 32.dp
            weekDaysLabelPadding = 9.dp
            yearSelectorHeight = 230.dp
        }

        else -> {}
    }*/


    LeftToRightLayout {
        Column(
            modifier = Modifier
                .requiredWidthIn(min = 250.dp)
                .navigationBarsPadding()
                .background(color = backgroundColor)
                .animateContentSize()
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var firstJomeh: Int
            firstJomeh = 7 - JalaliCalendar(jalali.year, jalali.month, 1).dayOfWeek
            if (JalaliCalendar(jalali.year, jalali.month, 1).dayOfWeek == 7)
                firstJomeh = 7

            var event: String? by remember { mutableStateOf(onSelectDay(jalali)) }

            RightToLeftLayout {
                Column {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = FormatHelper.toPersianNumber("${today.day} ${today.monthString} ${today.year}"),
                            style = headerTextStyle
                        )
                        Spacer(modifier = Modifier.weight(1F))
                        TextButton(
                            onClick = {
                                jalali = today
                                selectedDate = today
                                onSelectDay(today)
                            }
                        ) {
                            Text(
                                text = "تاریخ امروز",
                                style = headerTextStyle
                            )
                        }
                    }
                    Divider(color = headerDividerColor, thickness = 1.5.dp)
                }
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pickerType == PickerType.Day) {
                    IconButton(
                        onClick = {
                            jalali = if (jalali.month != 12) {
                                JalaliCalendar(jalali.year, jalali.month + 1, 1)
                            } else {
                                JalaliCalendar(jalali.year + 1, 1, 1)
                            }
                        },
                        modifier = Modifier.size(iconSize),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowLeft,
                            contentDescription = "",
                            tint = nextPreviousBtnColor
                        )
                    }
                }

                TextButton(
                    onClick = {
                        pickerType = if (pickerType != PickerType.Year)
                            PickerType.Year
                        else
                            PickerType.Day
                    },
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(
                        text = FormatHelper.toPersianNumber(jalali.year.toString()),
                        style = yearMonthTextStyle
                    )
                }

                TextButton(onClick = {
                    pickerType = if (pickerType != PickerType.Month)
                        PickerType.Month
                    else
                        PickerType.Day
                }) {
                    Text(
                        text = jalali.monthString,
                        style = yearMonthTextStyle
                    )
                }

                if (pickerType == PickerType.Day) {
                    IconButton(
                        onClick = {
                            jalali = if (jalali.month != 1) {
                                JalaliCalendar(jalali.year, jalali.month - 1, 1)
                            } else {
                                JalaliCalendar(jalali.year - 1, 12, 1)
                            }
                        },
                        modifier = Modifier.size(iconSize)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = "",
                            tint = nextPreviousBtnColor
                        )
                    }
                }

            }

            when (pickerType) {
                PickerType.Day -> {
                    RightToLeftLayout {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = weekDaysLabelPadding),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Text(
                                text = FormatHelper.toPersianNumber("شنبه"),
                                style = weekDaysTextStyle
                            )
                            Text(
                                text = FormatHelper.toPersianNumber("1شنبه"),
                                style = weekDaysTextStyle
                            )
                            Text(
                                text = FormatHelper.toPersianNumber("2شنبه"),
                                style = weekDaysTextStyle
                            )
                            Text(
                                text = FormatHelper.toPersianNumber("3شنبه"),
                                style = weekDaysTextStyle
                            )
                            Text(
                                text = FormatHelper.toPersianNumber("4شنبه"),
                                style = weekDaysTextStyle
                            )
                            Text(
                                text = FormatHelper.toPersianNumber("5شنبه"),
                                style = weekDaysTextStyle
                            )
                            Text(
                                text = "جمعه",
                                style = weekDaysTextStyle
                            )
                        }
                    }

                    var jomeh = firstJomeh
                    while (true) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            var day = jomeh
                            for (i in 7 downTo 1) {
                                if (day > 0 && day <= jalali.monthLength) {
                                    val selectDay = day

                                    OutlinedIconButton(
                                        onClick = {
                                            selectedDate =
                                                JalaliCalendar(jalali.year, jalali.month, selectDay)
                                            event = onSelectDay(selectedDate!!)
                                        },
                                        Modifier.size(iconSize),
                                        border = BorderStroke(
                                            width = 1.5.dp,
                                            color = if (day == today.day && jalali.year == today.year && jalali.month == today.month)
                                                todayCircleColor
                                            else
                                                Color.Transparent
                                        ),
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = if (selectedDate != null && day == selectedDate!!.day && jalali.year == selectedDate!!.year && jalali.month == selectedDate!!.month)
                                                selectedIconColor
                                            else
                                                Color.Transparent,
                                        )
                                    ) {

                                        Text(
                                            text = FormatHelper.toPersianNumber(day.toString()),
                                            style = dayNumberTextStyle,
                                            color = if (day == today.day && jalali.year == today.year && jalali.month == today.month) {
                                                textColorHighlight
                                            } else {
                                                dayNumberTextStyle.color
                                            }
                                        )
                                    }

                                } else {
                                    FilledIconButton(
                                        onClick = {},
                                        Modifier
                                            .size(iconSize)
                                            .alpha(0f),
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = Color.Transparent
                                        )
                                    ) {
                                        Text(
                                            text = day.toString(),
                                            style = dayNumberTextStyle
                                        )
                                    }
                                }
                                day--
                            }
                        }
                        if (jomeh >= jalali.monthLength)
                            break
                        jomeh += 7
                    }

                }

                PickerType.Month -> {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            style = yearMonthTextStyle,
                            text = "انتخاب ماه"
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 4, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "تیر",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    4,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 3, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "خرداد",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    3,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 2, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "اردیبهشت",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    2,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 1, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "فروردین",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    1,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 8, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "آبان",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    8,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 7, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "مهر",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    7,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 6, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "شهریور",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    6,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 5, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "مرداد",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    5,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 12, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "اسفند",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    12,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 11, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "بهمن",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    11,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 10, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "دی",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    10,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                        TextButton(onClick = {
                            jalali = JalaliCalendar(jalali.year, 9, 1)
                            pickerType = PickerType.Day
                        }) {
                            Text(
                                text = "آذر",
                                style = yearMonthTextStyle,
                                color = monthTextColorFun(
                                    9,
                                    jalali,
                                    textColorHighlight,
                                    yearMonthTextStyle.color
                                )
                            )
                        }
                    }
                }

                PickerType.Year -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            style = yearMonthTextStyle,
                            color = MaterialTheme.colorScheme.textColor,
                            text = "انتخاب سال"
                        )
                    }
                    val scrollState =
                        rememberLazyListState(initialFirstVisibleItemIndex = jalali.year - 2)
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(yearSelectorHeight)
                            .padding(horizontal = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        state = scrollState
                    ) {
                        items(3000) { index ->
                            Divider()
                            Box(modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    jalali = JalaliCalendar(index, jalali.month, 1)
                                    pickerType = PickerType.Day
                                }
                                .padding(vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = FormatHelper.toPersianNumber(index.toString()),
                                    style = yearMonthTextStyle,
                                    fontSize = 30.sp,
                                    color = yearTextColorFun(
                                        jalali.year,
                                        index,
                                        textColorHighlight,
                                        yearMonthTextStyle.color
                                    )
                                )
                            }
                        }
                    }
                }
            }


            if (pickerType == PickerType.Day) {
                event?.let {
                    RightToLeftLayout {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(
                                    eventIconRes ?: Res.drawable.round_event_24
                                ),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = it,
                                style = eventTextStyle,
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Row {
                        TextButton(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            onClick = { onDismiss() }) {
                            Text(
                                text = "لغو",
                                style = buttonsTextStyle,
                            )
                        }
                        TextButton(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            enabled = selectedDate != null,
                            onClick = {
                                onConfirm(selectedDate!!)
                            }) {
                            Text(
                                text = "تایید",
                                style = buttonsTextStyle,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun monthTextColorFun(
    currentMonth: Int,
    jalali: JalaliCalendar,
    textColorHighlight: Color,
    textColor: Color
): Color {
    return if (jalali.month == currentMonth) {
        textColorHighlight
    } else {
        textColor
    }
}

@Composable
fun yearTextColorFun(
    currentYear: Int,
    yearIndex: Int,
    textColorHighlight: Color,
    textColor: Color
): Color {
    return if (currentYear == yearIndex) {
        textColorHighlight
    } else {
        textColor
    }
}

@Preview
@Composable
fun JalaliDatePickerPrev() {
    PersianCalendarTheme {
        JalaliCalendarView(
            initialDate = JalaliCalendar().tomorrow,
            onDismiss = {},
            onSelectDay = {
                "Event for: $it"
            },
            onConfirm = {}
        )

    }
}