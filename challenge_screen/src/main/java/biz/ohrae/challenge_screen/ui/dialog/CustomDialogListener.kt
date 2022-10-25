package biz.ohrae.challenge_screen.ui.dialog

interface CustomDialogListener {
    fun clickPositive()
    fun clickNegative()
}

interface FilterDialogListener {
    fun clickPositive(
        periodType: String = "",
        perWeek: String = "",
        period: String = "",
        isAdultOnly: String = ""
    )

    fun clickNegative()
    fun clickInitialization()
    fun selectVerificationPeriodType(item: String)
    fun selectPeriod(item: String)
    fun selectIsAdultOnly(item: String)
}

interface CalendarDialogListener {
    fun clickPositive()
    fun clickNegative()
    fun clickDay(day: String)
}
interface ReportDialogListener {
    fun clickPositive()
    fun clickNegative()
    fun clickItem(item: String)
}
