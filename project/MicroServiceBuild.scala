import sbt._
import uk.gov.hmrc.SbtAutoBuildPlugin
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin
import uk.gov.hmrc.versioning.SbtGitVersioning

object MicroServiceBuild extends Build with MicroService {

  override val appName = "mdtp-api-microservice"

  override lazy val plugins: Seq[Plugins] = Seq(
    SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin
  )

  override lazy val appDependencies: Seq[ModuleID] = AppDependencies()
}

private object AppDependencies {
  import play.core.PlayVersion

  private val microserviceBootstrapVersion = "2.0.0"
  private val playHealthVersion = "1.1.0"
  private val playConfigVersion = "2.0.0"
  private val hmrcTestVersion = "1.4.0"
  private val playReactivemongoVersion = "4.3.0"
  

  val compile = Seq(
    "uk.gov.hmrc" %% "play-reactivemongo" % playReactivemongoVersion,
    "uk.gov.hmrc" %% "microservice-bootstrap" % microserviceBootstrapVersion,
    "uk.gov.hmrc" %% "play-health" % playHealthVersion,
    "uk.gov.hmrc" %% "play-config" % playConfigVersion,
    "uk.gov.hmrc" %% "play-json-logger" % "1.0.0"
  )

  abstract class TestDependencies(scope: String) {
    lazy val test : Seq[ModuleID] = Seq(
      "uk.gov.hmrc" %% "hmrctest" % hmrcTestVersion % scope,
      "org.scalatest" %% "scalatest" % "2.2.4" % scope,
      "org.pegdown" % "pegdown" % "1.5.0" % scope,
      "com.typesafe.play" %% "play-test" % PlayVersion.current % scope
    )
  }

  object Test extends TestDependencies("test")
  object IntegrationTest extends TestDependencies("it")

  def apply() = compile ++ Test.test ++ IntegrationTest.test

}

