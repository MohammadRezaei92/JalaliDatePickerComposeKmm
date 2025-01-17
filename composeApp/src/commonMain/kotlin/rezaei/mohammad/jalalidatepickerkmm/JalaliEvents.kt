package rezaei.mohammad.jalalidatepickerkmm

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlin.math.ceil

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JalaliEventView(
    modifier: Modifier = Modifier,
    onSelectDay: (JalaliCalendar) -> String?,
    backgroundColor: Color = MaterialTheme.colorScheme.backgroundColor,
    selectedIconColor: Color = MaterialTheme.colorScheme.selectedIconColor,
    todayCircleColor: Color = MaterialTheme.colorScheme.selectedIconColor,
    textColorHighlight: Color = MaterialTheme.colorScheme.textColorHighlight,
    nextPreviousBtnColor: Color = MaterialTheme.colorScheme.textColor,
    dividerColor: Color = MaterialTheme.colorScheme.onBackground,
    yearMonthTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    weekDaysTextStyle: TextStyle = MaterialTheme.typography.titleSmall,
    dayNumberTextStyle: TextStyle = MaterialTheme.typography.bodySmall,
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
        mutableStateOf(JalaliCalendar())
    }

    val today = JalaliCalendar()
    var selectedDate: JalaliCalendar? by remember {
        mutableStateOf(jalali)
    }

    var pickerType: PickerType by remember {
        mutableStateOf(PickerType.Day)
    }

/*    val configuration = LocalConfiguration.current
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
                .background(color = backgroundColor)
                .animateContentSize()
                .then(modifier),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    val firstDayOfWeek =
                        JalaliCalendar(jalali.year, jalali.month, 1).dayOfWeek.run {
                            if (this == 7)
                                1
                            else
                                this.plus(1)
                        }
                    val pageCount = ceil(jalali.monthLength.toDouble().div(7.0)).toInt().run {
                        if (firstDayOfWeek == 7)
                            this.plus(1)
                        else
                            this
                    }
                    val pagerState = rememberPagerState(pageCount = { pageCount })

                    var day = 1
                    val monthArray = Array(pageCount) { IntArray(7) }
                    for (column in 1..pageCount) {
                        for (row in 1..7) {
                            if (day <= jalali.monthLength) {
                                if (column == 1 && row < firstDayOfWeek) {
                                    monthArray[column.minus(1)][row.minus(1)] = 0
                                } else {
                                    monthArray[column.minus(1)][row.minus(1)] = day
                                    day++
                                }
                            }
                        }
                    }
                    var event: String? by remember { mutableStateOf(null) }

                    LaunchedEffect(key1 = jalali) {
                        pagerState.animateScrollToPage(0)
                    }

                    RightToLeftLayout {
                        HorizontalPager(
                            state = pagerState
                        ) { page ->
                            Column {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = weekDaysLabelPadding),
                                    horizontalArrangement = Arrangement.SpaceAround
                                ) {
                                    for (dayIndex in 1..7) {
                                        val selectedDay = monthArray[page][dayIndex.minus(1)]
                                        OutlinedIconButton(
                                            modifier = Modifier
                                                .weight(1f)
                                                .heightIn(min = 80.dp, max = 120.dp),
                                            onClick = {
                                                selectedDate =
                                                    JalaliCalendar(
                                                        jalali.year,
                                                        jalali.month,
                                                        selectedDay
                                                    )
                                                event = onSelectDay(selectedDate!!)
                                            },
                                            enabled = selectedDay != 0,
                                            border = BorderStroke(
                                                width = 1.5.dp,
                                                color = if (selectedDay == today.day && jalali.year == today.year && jalali.month == today.month)
                                                    todayCircleColor
                                                else
                                                    Color.Transparent
                                            ),
                                            colors = IconButtonDefaults.filledIconButtonColors(
                                                containerColor = if (selectedDate != null && selectedDay == selectedDate!!.day && jalali.year == selectedDate!!.year && jalali.month == selectedDate!!.month)
                                                    selectedIconColor
                                                else
                                                    Color.Transparent,
                                                disabledContainerColor = Color.Transparent,
                                                disabledContentColor = dayNumberTextStyle.color
                                            )
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                val dayName = when (dayIndex) {
                                                    7 -> "جمعه"
                                                    6 -> "5شنبه"
                                                    5 -> "4شنبه"
                                                    4 -> "3شنبه"
                                                    3 -> "2شنبه"
                                                    2 -> "1شنبه"
                                                    1 -> "شنبه"
                                                    else -> ""
                                                }
                                                RightToLeftLayout {
                                                    Text(
                                                        text = FormatHelper.toPersianNumber(dayName),
                                                        style = weekDaysTextStyle
                                                    )
                                                }
                                                Divider(
                                                    modifier = Modifier.padding(
                                                        vertical = 10.dp,
                                                        horizontal = 8.dp
                                                    ),
                                                    color = dividerColor
                                                )
                                                Text(
                                                    modifier = Modifier.alpha(if (selectedDay > 0 && selectedDay <= jalali.monthLength) 1F else 0F),
                                                    text = FormatHelper.toPersianNumber(selectedDay.toString()),
                                                    style = dayNumberTextStyle,
                                                    color = if (selectedDay == today.day && jalali.year == today.year && jalali.month == today.month) {
                                                        textColorHighlight
                                                    } else {
                                                        dayNumberTextStyle.color
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }

                                event?.let {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
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
                        }
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
        }
    }
}

@Preview
@Composable
fun JalaliEventViewPrev() {
    PersianCalendarTheme {
        JalaliEventView(
            onSelectDay = {
                "Returned event for ${it.year}/${it.month}/${it.day}"
            }
        )
    }
}