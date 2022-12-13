package biz.ohrae.challenge_repo.util.prefs

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.text.format.DateUtils
import androidx.compose.ui.graphics.Color
import biz.ohrae.challenge.ui.theme.DefaultWhite
import biz.ohrae.challenge_repo.model.detail.ChallengeData
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

object Utils {
    var sdf: SimpleDateFormat? = null
    var sdf2: SimpleDateFormat? = null
    var sdf3: SimpleDateFormat? = null
    var sdf4: SimpleDateFormat? = null
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
        if (phoneNumber.length >= 4) {
            when {
                phoneNumber.length in 4..6 -> {
                    result = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3)
                }
                phoneNumber.length in 7..10 -> {
                    result = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(
                        3,
                        6
                    ) + "-" + phoneNumber.substring(6)
                }
                phoneNumber.length >= 11 -> {
                    result = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(
                        3,
                        7
                    ) + "-" + phoneNumber.substring(7)
                }
            }
        } else {
            result = phoneNumber
        }
        return result
    }

    fun generateBirthDate(phoneNumber: String): String {
        var result = ""
        if (phoneNumber.length >= 5) {
            when {
                phoneNumber.length in 4..6 -> {
                    result = phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(4)
                }
                phoneNumber.length >= 7 -> {
                    result = phoneNumber.substring(0, 4) + "-" + phoneNumber.substring(
                        4,
                        6
                    ) + "-" + phoneNumber.substring(6)
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
            if (timeFormat.length == 7) {
                "0$timeFormat"
            } else {
                timeFormat
            }
        }
    }

    fun convertSecToString2(seconds: Int): String {
        val timeFormat = DateUtils.formatElapsedTime(seconds.toLong())
        return if (timeFormat.length == 7) {
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

    fun numberFormat(price: Int?): String {
        return if (price == null) {
            "0"
        } else {
            try {
                DecimalFormat("###,###").format(price)
            } catch (ignore: Exception) {
                price.toString()
            }
        }
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
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val date = inputFormat.parse(dateStr.replace("T", " "))
            val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertDate3(dateStr: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            val date = inputFormat.parse(dateStr)
            val outputFormat = SimpleDateFormat("yy.MM.dd\nHH:mm a", Locale.KOREA)
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
            var dateString = dateStr.replace("T", " ").replace("Z", "")
            dateString = dateString.substring(0, 19)
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
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

    fun convertDate8(dateStr: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            val date = inputFormat.parse(dateStr)
            val outputFormat = SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertDate9(dateStr: String, isFeed: Boolean = false): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            val date = inputFormat.parse(dateStr)
            val outputFormat: SimpleDateFormat =
                if (isFeed) {
                SimpleDateFormat("yyyy.MM.dd HH:mm a", Locale.US)
            } else {
                SimpleDateFormat("yyyy.MM.dd\nHH:mm a", Locale.US)
            }
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            ""
        }
    }

    fun convertDate10(dateStr: String): String {
        return try {
            val dateString = dateStr.replace("T", " ").replace("Z", "")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val date = inputFormat.parse(dateString)
            val outputFormat = SimpleDateFormat("yyyy.MM.dd E", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(date!!)
        } catch (ignore: Exception) {
            dateStr
        }
    }
    fun endDateCalculation(startDate: String, week: Int): String {
        return try {
            val cal = Calendar.getInstance()
            val df: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date: Date = df.parse(startDate)
            cal.time = date
            cal.add(Calendar.DATE, week * 7)
            val outputFormat = SimpleDateFormat("yyyy.MM.dd E요일", Locale.KOREA)
            outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")
            outputFormat.format(cal!!)
        } catch (ignore: Exception) {
            startDate
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
        if (sdf == null) {
            sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            sdf?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdf!!
    }

    fun sdf2(): SimpleDateFormat {
        if (sdf2 == null) {
            sdf2 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA)
            sdf2?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdf2!!
    }

    fun sdf3(): SimpleDateFormat {
        if (sdf3 == null) {
            sdf3 = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            sdf3?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdf3!!
    }

    fun sdf4(): SimpleDateFormat {
        if (sdf4 == null) {
            sdf4 = SimpleDateFormat("yyyy.MM.dd hh:mm a", Locale.KOREA)
            sdf4?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdf4!!
    }

    fun sdfDiffDay(): SimpleDateFormat? {
        if (sdfDiffDay == null) {
            sdfDiffDay = SimpleDateFormat("yy.MM.dd HH:mm", Locale.KOREA)
            sdfDiffDay?.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
        return sdfDiffDay!!
    }

    fun sdfSameDay(): SimpleDateFormat? {
        if (sdfSameDay == null) {
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
        return if (startIndex + itemCount >= listSize) {
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

    fun timeDaysToMinutes(timeDays: Float): Int {
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

    fun addDays(timeFormat: String, days: Int): String? {
        val calendarText = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date = calendarText.parse(timeFormat)

        if (date != null) {
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.add(Calendar.DAY_OF_MONTH, days)
            return calendarText.format(calendar.time)
        } else {
            return null
        }
    }

    fun getDifferenceDays(startDate: String, endDate: String): Int {
        val startTime = sdf3().parse(startDate)?.time ?: 0
        val endTime = sdf3().parse(endDate)?.time ?: 0

        val delay = endTime - startTime
        return TimeUnit.MILLISECONDS.toDays(delay).toInt()
    }

    fun getDefaultChallengeDate(): String {
        var date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_MONTH, 2)
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

    fun numberToString(text: String, maxNumber: Int = 500000): String {
        return if (text.isEmpty()) {
            text
        } else {
            var price = text.replace("[^\\d]".toRegex(), "")
            if (price.isEmpty()) {
                price
            } else {
                if (price.toInt() > maxNumber) {
                    price = maxNumber.toString()
                }
                numberFormat(price.toInt())
            }
        }
    }

    fun getRemainTimeDays(startDate: String): String {
        return try {
            val dateString = startDate.replace("T", " ").replace("Z", "")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.KOREA)

            val targetDate = LocalDate.parse(dateString, formatter)
            val today = LocalDate.now()

            val days = ChronoUnit.DAYS.between(today, targetDate)

            return if (days < 0L) {
                "D + ${days * -1}"
            } else if (days == 0L) {
                "오늘부터 시작"
            } else if (days == 1L) {
                "내일부터 시작"
            } else {
                "D - $days"
            }
        } catch (ignore: Exception) {
            ignore.printStackTrace()
            ""
        }
    }

    fun isAfter(dateStr: String): Boolean {
        val base = dateStr.replace("-", "").toInt()
        val today = sdf3().format(Date()).replace("-", "").toInt()
        return base > today
    }

    fun getDaysFromStart(startDate: String): Int {
        var result = 0
        return try {
            val dateString = startDate.replace("T", " ").replace("Z", "")
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
            val date = inputFormat.parse(dateString)
            var remain: Long = 0

            if (date != null) {
                remain = Date().time - date.time
                if (remain > 0) {
                    val days = ((((remain / 1000) / 60) / 60) / 24)
                    result = days.toInt()
                }
            }
            result
        } catch (ignore: Exception) {
            ignore.printStackTrace()
            result
        }
    }

    fun userChallengeBackground(type: String): Color {
        return when (type) {
            "ongoing" -> Color(0xfff3f8ff)
            "pending" -> Color(0xffebfaf1)
            "recruiting" -> Color(0xffebfaf1)
            "register_closed" -> Color(0xffdedede)
            "finished" -> Color(0xffdedede)
            "canceled" -> Color(0xffdedede)
            "rewards" -> Color(0xfff3f8ff)
            else -> {
                Color(0xfff3f8ff)
            }
        }
    }

    fun userChallengeTextColor(type: String): Color {
        return when (type) {
            "ongoing" -> Color(0xff4985f8)
            "pending" -> Color(0xff219653)
            "recruiting" -> Color(0xff219653)
            "register_closed" -> Color(0xff6c6c6c)
            "finished" -> Color(0xff6c6c6c)
            "canceled" -> Color(0xff6c6c6c)
            "rewards" -> Color(0xff4985f8)
            else -> {
                Color(0xff4985f8)
            }
        }
    }

    fun rewardBackground(type: String):Color {
        return when (type) {
            "earn" -> Color(0xfff3f8ff)
            "use" -> Color(0xfffafafa)
            "transfer" -> Color(0xffebfaf1)
            "expire" -> Color(0xffdedede)
            "refund" -> Color(0xfff3f8ff)
            "cancel" -> Color(0xfff3f8ff)
            else -> {
                Color(0xff4985f8)
            }
        }
    }

    fun rewardTextColor(type: String):Color {
        return when (type) {
            "earn" -> Color(0xff4985f8)
            "use" -> Color(0xffff5800)
            "transfer" -> Color(0xff219653)
            "expire" -> Color(0xff6c6c6c)
            "refund" -> Color(0xff4985f8)
            "cancel" -> Color(0xff4985f8)
            else -> {
                Color(0xff4985f8)
            }
        }
    }

    fun reward(type: String):String {
        return when (type) {
            "earn" -> "적립"
            "use" -> "사용"
            "transfer" -> "출금"
            "expire" -> "소멸"
            "refund" -> "환급"
            "cancel" -> "취소"
            else -> {
                ""
            }
        }
    }

    fun getAuthType(challengeData: ChallengeData): String {
        return if (challengeData.is_verification_photo == 1) {
            "사진 인증"
        } else if (challengeData.is_verification_checkin == 1) {
            "출석 인증"
        } else if (challengeData.is_verification_time == 1) {
            "이용 시간 인증"
        } else {
            ""
        }
    }

    fun getOpenType(challengeData: ChallengeData): String {
        return if (challengeData.min_deposit_amount == 0) {
            "무료"
        } else {
            "유료"
        }
    }

    fun getAuthTypeEnglish(challengeData: ChallengeData): String {
        return if (challengeData.is_verification_photo == 1) {
            "photo"
        } else (if (challengeData.is_verification_time == 1) {
            "staying_time"
        } else if (challengeData.is_verification_checkin == 1) {
            "checkin"
        } else {
            ""
        })
    }

    fun getAuthMethodText(challengeData: ChallengeData): String {
        return if (challengeData.is_verification_photo == 1) {
            "사진인증 (즉석 촬영으로만 인증 가능)"
        } else (if (challengeData.is_verification_checkin == 1) {
            "출석 인증 (입실 시 자동 인증)\n이용권 필요"
        } else if (challengeData.is_verification_time == 1) {
            "이용시간 인증 ( 입실~퇴실 시간으로 자동 인증)\n이용권 필요"
        } else {
            "알 수 없음"
        })
    }

    fun getAgeType(type: String): String {
        return when (type) {
            "minor" -> "18세 미만 전용"
            "adult" -> "18세 이상 전용"
            else -> {
                "제한없음"
            }
        }
    }

    fun getAuthButtonName(challengeData: ChallengeData, type: Boolean = false): String {
        return if(challengeData.inChallenge?.get(0)?.achievement_percent?.toDouble()!! >= 100.0f){
            "챌린지 달성 완료"
        } else if (challengeData.status == "finished") {
            "챌린지가 완료되었습니다"
        } else {
            if (challengeData.is_verification_photo == 1) {
                if (challengeData.isAuthed()) {
                    "오늘 인증완료"
                } else {
                    "인증하기"
                }
            } else if (challengeData.is_verification_time == 1) {
                if (type) {
                    "자동 인증 중"
                } else {
                    "내일 새벽 1시에 자동 인증됩니다"
                }
            } else if (challengeData.is_verification_checkin == 1) {
                if (challengeData.isAuthed()) {
                    "오늘 인증완료"
                } else {
                    if (type) {
                        "자동 인증 중"
                    } else {
                        "오늘 첫 입실시 자동 인증됩니다"
                    }
                }
            } else {
                if (challengeData.isAuthed()) {
                    "오늘 인증완료"
                } else {
                    "인증하기"
                }
            }
        }
    }

    fun getAuthBtnTextColor(challengeData: ChallengeData): Color {
        return if(challengeData.inChallenge?.get(0)?.achievement_percent?.toDouble()!! >= 100.0f){
            DefaultWhite
        }
        else if (challengeData.status == "finished") {
            Color(0xff6c6c6c)
        } else {
            if (challengeData.is_verification_photo == 1) {
                if (challengeData.isAuthed()) {
                    DefaultWhite
                } else {
                    DefaultWhite
                }
            } else if (challengeData.is_verification_time == 1) {
                if (challengeData.isAuthed()) {
                    DefaultWhite
                } else {
                    Color(0xff4985f8)
                }
            } else if (challengeData.is_verification_checkin == 1) {
                if (challengeData.isAuthed()) {
                    DefaultWhite
                } else {
                    Color(0xff4985f8)
                }
            } else {
                if (challengeData.isAuthed()) {
                    DefaultWhite
                } else {
                    DefaultWhite
                }
            }
        }
    }

    fun getAuthBtnColor(challengeData: ChallengeData): Color {
        return if(challengeData.inChallenge?.get(0)?.achievement_percent?.toDouble()!! >= 100.0f){
            Color(0xffc7c7c7)
        }
        else if (challengeData.status == "finished") {
            DefaultWhite
        } else {
            if (challengeData.is_verification_photo == 1) {
                if (challengeData.isAuthed()) {
                    Color(0xffc7c7c7)
                } else {
                    Color(0xff005bad)
                }
            } else if (challengeData.is_verification_time == 1) {
                if (challengeData.isAuthed()) {
                    Color(0xffc7c7c7)
                } else {
                    DefaultWhite
                }
            } else if (challengeData.is_verification_checkin == 1) {
                if (challengeData.isAuthed()) {
                    Color(0xffc7c7c7)
                } else {
                    DefaultWhite
                }
            } else {
                if (challengeData.isAuthed()) {
                    Color(0xffc7c7c7)
                } else {
                    Color(0xff005bad)
                }
            }
        }
    }

    fun getTimeDays(timeDays: String): String {
        val split = timeDays.split(":")
        return if (split.size >= 2) {
            "${split[0]}시간 ${split[1]}분"
        } else {
            timeDays
        }
    }
}