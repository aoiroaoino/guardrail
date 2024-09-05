package core.issues

import dev.guardrail.Context
import dev.guardrail.generators.scala.ScalaGeneratorMappings.scalaInterpreter
import dev.guardrail.generators.scala.http4s.Http4sVersion
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import support.SwaggerSpecRunner
import scala.meta._

class IssueN extends AnyFunSuite with Matchers with SwaggerSpecRunner {
  val spec: String = """openapi: 3.0.2
                       |components:
                       |  schemas:
                       |    FooBarType:
                       |      type: string
                       |      enum:
                       |        - A
                       |        - B
                       |    foo_type:
                       |      type: array
                       |      nullable: false
                       |      items:
                       |        allOf:
                       |          - $ref: '#/components/schemas/FooBarType'
                       |""".stripMargin

  def testVersion(version: Http4sVersion): Unit =
    test(s"$version - Test correct escaping of numbers used as identifiers") {
      val actual = runSwaggerSpec(scalaInterpreter)(spec)(Context.empty, version.value)
      succeed
    }

  // testVersion(Http4sVersion.V0_22)
  testVersion(Http4sVersion.V0_23)
}
