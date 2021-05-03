package org.thermofisher.magellan.chat

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

import scala.concurrent.duration._

class LoadSimulationMVC extends Simulation {

  val baseUrl = "http://xiao.thermofisher.com/"
  val testPath = "api/magellan/chat/agents?country=us&language=en&containerURI=/antibody/product/SERCA2-ATPase-Antibody-clone-2A7-A1-Monoclonal/MA3-919&version=f6163d4ecdc8c56f990c"
  //concurrent user
  val sim_users = 300

  val httpConf = http.baseURL(baseUrl)

  // define the resuest 30 times
  val helloRequest = repeat(30) {
    exec(http("Testing Spring MVC agents request")
      .get(testPath))
      // Simulate the user's thinking time, randomly 1 to 2 seconds
      .pause(1 second, 2 seconds)
  }

  val scn = scenario("High concurrency")
    .exec(helloRequest)

  // Configure the number of concurrent users evenly to the number specified by sim_users within 30 seconds
  setUp(scn.inject(rampUsers(sim_users).over(30 seconds)).protocols(httpConf))
}
