package helpers

object SessionTypeImpl extends Enumeration {
  type SessionType = Value
  val VALID: SessionTypeImpl.Value = Value("Valid")
  val NOT_FOUND: SessionTypeImpl.Value = Value("Not Found")
  val EXPIRED: SessionTypeImpl.Value = Value("Expired")
}
