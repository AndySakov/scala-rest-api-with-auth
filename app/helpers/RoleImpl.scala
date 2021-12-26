package helpers

object RoleImpl extends Enumeration {
  type Role = Value
  val NORMAL = Value("Normal")
  val SILVER = Value("Silver")
  val GOLD = Value("Gold")
  val DIAMOND = Value("Diamond")
}
