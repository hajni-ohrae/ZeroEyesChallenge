package biz.ohrae.challenge_repo.model.report

data class ReportListState(
    val reportList: List<ReportDetail>,
    var selectReport: String = "",
) {

}

data class ReportDetail(
    val code: String,
    val name: String,
    val description: String,
    val created_date: String,
    val updated_date: String,
)
