package biz.ohrae.challenge_repo.util.prefs

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.text.format.DateUtils
import androidx.compose.ui.graphics.Color
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

object Utils {
    var sdf: SimpleDateFormat? = null
    var sdf2: SimpleDateFormat? = null
    var sdfDiffDay: SimpleDateFormat? = null
    var sdfSameDay: SimpleDateFormat? = null

    fun getScreenWidth(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return dpWidth.toInt()
    }

    fun isLargeScreen(context: Context): Boolean {
        return getScreenWidth(context) > 800
    }

    fun generatePhoneNumber(phoneNumber: String): String {
        var result = ""
        if(phoneNumber.length >= 4) {
            when {
                phoneNumber.length in 4..6 -> {
                    result = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3)
                }
                phoneNumber.length in 7..10 -> {
                    result = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6)
                }
                phoneNumber.length >= 11 -> {
                    result = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" + phoneNumber.substring(7)
                }
            }
        } else {
            result = phoneNumber
        }
        return result
    }

    fun generateBirthDate(phoneNumber: String): String {
        var result = ""
        if(phoneNumber.length >= 5) {
            when {
                phoneNumber.length in 4..6 -> {
                    result = phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4)
                }
                phoneNumber.length >= 7 -> {
                    result = phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4, 6) + "-" + phoneNumber.substring(6)
                }
            }
        } else {
            result = phoneNumber
        }
        return result
    }

    fun convertSecToString(seconds: Int): String {
        return if (seconds < 60) {
            seconds.toString()
        } else {
            val timeFormat = DateUtils.formatElapsedTime(seconds.toLong())
            if(timeFormat.length == 7) {
                "0$timeFormat"
            } else {
                timeFormat
            }
        }
    }

    fun convertSecToString2(seconds: Int): String {
        val timeFormat = DateUtils.formatElapsedTime(seconds.toLong())
        return if(timeFormat.length == 7) {
            "0$timeFormat"
        } else {
            timeFormat
        }
    }

    fun makePhoneNumber(phoneNoStr: String): String? {
        val regEx = "(\\d{3})(\\d{3,4})(\\d{4})"
        if (!Pattern.matches(regEx, phoneNoStr)) {
            val regEx2 = "(\\d{4})(\\d{4})"
            return if (!Pattern.matches(regEx2, phoneNoStr)) {
                phoneNoStr
            } else phoneNoStr.replace(regEx2.toRegex(), "$1-$2")
        }
        return phoneNoStr.replace(regEx.toRegex(), "$1-$2-$3")
    }

    fun convertFloatTimeToString(timeDays: Float): String {
        var result = ""
        val hour = timeDays.toInt()
        val minute = (60.0 * (timeDays - hour)).toInt()
        if (hour > 0) {
            result += hour.toString() + "시간 "
        }
        if (minute > 0) {
            result += minute.toString() + "분"
        }
        return result.trim { it <= ' ' }
    }
    
    fun numberFormat(price: Int): String {
        return DecimalFormat("###,###").format(price)
    }

    fun convertDate(dateStr: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            val date = inputFormat.parse(dateStr)
            val outputFormat = SimpleDateFormat("yy.MM.dd", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertDate2(dateStr: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
            val date = inputFormat.parse(dateStr.replace("T", " "))
            val outputFormat = SimpleDateFormat("yy.MM.dd HH:mm", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertDate3(dateStr: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
            val date = inputFormat.parse(dateStr.replace("T", " "))
            val outputFormat = SimpleDateFormat("yy.MM.dd HH:mm", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertDate4(dateStr: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            val date = inputFormat.parse(dateStr.replace("T", " "))
            val outputFormat = SimpleDateFormat("HH:mm", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertDate5(dateStr: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA)
            val date = inputFormat.parse(dateStr.replace("T", " ").replace("Z", ""))
            val outputFormat = SimpleDateFormat("yy.MM.dd HH:mm", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertDate6(dateStr: String): String {
        return try {
            val dateString = dateStr.replace("T", " ").replace("Z", "")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.KOREA)
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("M월 d일(E)", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            dateStr
        }
    }

    fun convertDate7(dateStr: String): String {
        return try {
            val dateString = dateStr.replace("T", " ").replace("Z", "")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("yyyy.MM.dd E요일", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            dateStr
        }
    }

    fun minutesToString(min: Int): String {
        val days = min / (24 * 60)
        val hours = min % (24 * 60) / 60
        val minutes = min % (24 * 60) % 60
        var result = ""
        if (days > 0) {
            result += days.toString() + "일 "
        }
        if (hours > 0) {
            result += hours.toString() + "시간 "
        }
        if (minutes > 0) {
            result += minutes.toString() + "분 "
        }
        return result
    }

    fun minutesToHourString(min: Int): String {
        val hours = min / 60
        val minutes = min % 60
        var result = ""
        if (hours > 0) {
            result += hours.toString() + "시간 "
        }
        if (minutes > 0) {
            result += minutes.toString() + "분 "
        }
        return result
    }

    fun sdf(): SimpleDateFormat {
        if(sdf == null) {
            sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            sdf?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdf!!
    }

    fun sdf2(): SimpleDateFormat {
        if(sdf2 == null) {
            sdf2 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
            sdf2?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdf2!!
    }

    fun sdfDiffDay(): SimpleDateFormat? {
        if(sdfDiffDay == null) {
            sdfDiffDay = SimpleDateFormat("yy.MM.dd HH:mm", Locale.KOREA)
            sdfDiffDay?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdfDiffDay!!
    }

    fun sdfSameDay(): SimpleDateFormat? {
        if(sdfSameDay == null) {
            sdfSameDay = SimpleDateFormat("HH:mm", Locale.KOREA)
            sdfSameDay?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdfSameDay!!
    }

    fun convertMsToStringUTC(milliseconds: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = Date(milliseconds)
        return sdf.format(date)
    }

    fun convertMsToString(milliseconds: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        val date = Date(milliseconds)
        return sdf.format(date)
    }

    fun getEndIndexForPager(startIndex: Int, itemCount: Int, listSize: Int): Int {
        return if(startIndex + itemCount >= listSize) {
            listSize
        } else {
            startIndex + itemCount
        }
    }

    fun saveTempBitmap(bitmap: Bitmap) {
        if (isExternalStorageWritable()) {
            saveImage(bitmap)
        } else {
            //prompt the user or do something
            Timber.wtf("external storage disabled!!")
        }
    }

    fun timeDaysToMinutes(timeDays :Float): Int {
        val hour = timeDays.toInt()
        val minute = (60.0 * (timeDays - hour)).toInt()

        return hour * 60 + minute
    }

    fun timeDaysToAvailableTime(timeDays: Float): String {
        val hour = timeDays.toInt()
        val minute = (60.0 * (timeDays - hour)).toInt()

        return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}:00"
    }

    fun availableTileToMinutes(availableTime: String): Int {
        val split = availableTime.split(":").toTypedArray()
        val hour = split[0].toInt()
        val minute = split[1].toInt()
        val result = hour * 60 + minute

        return result
    }

    private fun saveImage(finalBitmap: Bitmap) {
        val root: String = Environment.getExternalStorageDirectory().toString()
        val myDir = File("$root/saved_images")
        myDir.mkdirs()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "Shut_$timeStamp.jpg"
        val file = File(myDir, fileName)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    /* Checks if external storage is available for read and write */
    private fun isExternalStorageWritable(): Boolean {
        val state: String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == state
    }

    fun isSameDay(sDate: Date?, eDate: Date?): Boolean {
        val calendar1 = Calendar.getInstance()
        calendar1.time = sDate
        val calendar2 = Calendar.getInstance()
        calendar2.time = eDate
        val sameYear = calendar1[Calendar.YEAR] == calendar2[Calendar.YEAR]
        val sameMonth = calendar1[Calendar.MONTH] == calendar2[Calendar.MONTH]
        val sameDay = calendar1[Calendar.DAY_OF_MONTH] == calendar2[Calendar.DAY_OF_MONTH]
        return sameDay && sameMonth && sameYear
    }

    fun addHourMinute(calendar: Calendar, timeDays: Float): Calendar? {
        val hour = timeDays.toInt()
        val minute = (60.0 * (timeDays - hour)).toInt()
        if (hour > 0) {
            calendar.add(Calendar.HOUR, hour)
        }
        if (minute > 0) {
            calendar.add(Calendar.MINUTE, minute)
        }
        return calendar
    }

    fun addDays(calendar: Calendar, days: Int): Calendar? {
        calendar.add(Calendar.DAY_OF_MONTH, days)
        return calendar
    }

    fun addWeeks(timeFormat: String, weeks: Int): String? {
        val calendarText = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date = calendarText.parse(timeFormat)

        if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.WEEK_OF_MONTH, weeks)
            return calendarText.format(calendar.time)
        } else {
            return null
        }
    }

    fun getDefaultChallengeDate(): String {
        var date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.WEEK_OF_MONTH, 1)
        date = calendar.time

        val calendarText = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        return calendarText.format(date)
    }

    fun hexToColor(hex: String?): Color? {
        return try {
            Color(android.graphics.Color.parseColor(hex))
        } catch (e: Exception) {
            null
        }
    }

    fun sliceTimeSecond(time: String, isDateSlice: Boolean): String? {
        val dateTimes = time.split(" ".toRegex()).toTypedArray()
        val times: Array<String> = if (dateTimes.size > 1 && isDateSlice) {
            dateTimes[1].split(":".toRegex()).toTypedArray()
        } else {
            time.split(":".toRegex()).toTypedArray()
        }
        return times[0] + ":" + times[1]
    }

    fun getRemainTimeDays(startDate: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            val date = inputFormat.parse(startDate)
            var remain: Long = 0
            var result = ""

            if (date != null) {
                remain = date.time - Date().time
                if (remain > 0) {
                    val days = ((((remain / 1000) / 60) / 60) / 24)

                    result = if (days <= 0L) {
                        "오늘부터 시작"
                    } else if (days == 1L) {
                        "내일부터 시작"
                    } else {
                        "D - $days"
                    }
                }
            }
            result
        } catch (ignore: Exception) {
            ignore.printStackTrace()
            ""
        }
    }
}