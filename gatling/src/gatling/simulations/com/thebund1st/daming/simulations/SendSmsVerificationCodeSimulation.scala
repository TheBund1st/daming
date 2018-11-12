package com.thebund1st.daming.simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class SendSmsVerificationCodeSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("http://localhost:9091")

  private val scn = scenario("SendSmsVerificationCode")
    .exec(http("SendSmsVerificationCode_1")
      .post("/api/sms/verification/code"))

  setUp(
    scn.inject(
      constantUsersPerSec(20).during(15 seconds).randomized
    )
  ).protocols(httpProtocol)
}