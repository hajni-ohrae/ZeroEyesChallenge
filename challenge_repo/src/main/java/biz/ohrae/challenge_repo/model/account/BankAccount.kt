package biz.ohrae.challenge_repo.model.account

data class BankAccount(
    val account_number: String,
    val bank: Bank,
    val created_date: String,
    val id: String,
    val identification_number: String?,
    val is_deleted: Int,
    val owner_name: String,
    val updated_date: String
)