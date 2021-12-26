package helpers

import helpers.ResultTypeImpl.ResultType

case class ResultSet[A](resultType: ResultType, result: Result[A])
