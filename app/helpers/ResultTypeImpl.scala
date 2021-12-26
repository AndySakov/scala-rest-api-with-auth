package helpers

object ResultTypeImpl extends Enumeration {
  type ResultType = Value
  val SUCCESS: ResultTypeImpl.Value = Value
  val FAILURE: ResultTypeImpl.Value = Value
  val ERROR: ResultTypeImpl.Value = Value
}
