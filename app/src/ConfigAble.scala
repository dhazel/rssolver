package app

import com.typesafe.config.{ConfigFactory, Config}

trait ConfigAble {
  def getConfig(): Config = {
    return ConfigFactory.load()
  }
}
