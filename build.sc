import mill._, scalalib._


object app extends ScalaModule {
  def scalaVersion = "2.13.4"

  def scalacOptions = Seq(
      "-deprecation",
      "-feature"
  )

  def ivyDeps = Agg(
    ivy"com.lihaoyi::cask:0.7.8",
    ivy"org.jsoup:jsoup:1.13.1",
  )

  object test extends Tests{
    def ivyDeps = Agg(
            //ivy"org.scalactic::scalactic:3.1.1",
            ivy"org.scalatest::scalatest:3.2.8",
            ivy"com.lihaoyi::requests::0.6.5",
        )
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}
