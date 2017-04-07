package com.ibm.cds.spark.samples.config

import org.apache.kafka.clients.CommonClientConfigs
import java.io.FileInputStream
import java.io.InputStream
import scala.collection.JavaConversions._
import org.apache.spark.SparkContext


/**
 * @author dtaieb
 */

class DemoConfig extends Serializable{  
  
  //Hold configuration key/value pairs
  var config = scala.collection.mutable.Map[String, String](
  )
  
  private def getKeyOrFail(key:String):String={
    config.get(key).getOrElse( {
      throw new IllegalStateException("Missing key: " + key)
    })
  }
  
  def cloneConfig():MessageHubConfig={
    val props = new MessageHubConfig
    config.foreach{ entry => props.setConfig(entry._1, entry._2)}
    props
  }
  
  def set_hadoop_config(sc:SparkContext){
    val prefix = "fs.swift.service." + getKeyOrFail("name") 
    val hconf = sc.hadoopConfiguration
    hconf.set(prefix + ".auth.url", getKeyOrFail("auth_url")+"/v2.0/tokens")
    hconf.set(prefix + ".auth.endpoint.prefix", "endpoints")
    hconf.set(prefix + ".tenant", getKeyOrFail("project_id"))
    hconf.set(prefix + ".username", getKeyOrFail("user_id"))
    hconf.set(prefix + ".password", getKeyOrFail("password"))
    hconf.setInt(prefix + ".http.port", 8080)
    hconf.set(prefix + ".region", getKeyOrFail("region"))
    hconf.setBoolean(prefix + ".public", true)
  }
  
  def initConfigKeys(){
    //Overridable by subclasses
  }
  
  //Give a chance to subclasses to init the keys
  initConfigKeys;
  
  {
    //Load config from property file if specified
    val configPath = System.getenv("DEMO_CONFIG_PATH");
    if ( configPath != null ){
      val props = new java.util.Properties
      var fis:InputStream = null
      try{
        fis = new FileInputStream(configPath)
        props.load(fis)
        for( key <- props.keysIterator ){
          setConfig( key, props.getProperty(key))
        }
      }catch{
        case e:Throwable => e.printStackTrace
      }finally{
        if ( fis != null ){
          fis.close
        }
      }    
    }
  }
  
  private[config] def registerConfigKey( key: String, default: String = null ) : (String,String) = {
    if ( default == null ){
      (key, Option(System.getProperty(key)).orNull )
    }
    (key, Option(System.getProperty(key)) getOrElse default )
  }
  
  def setConfig(key:String, value:String){
    config.put( key, value )
  }
  
  def getConfig(key:String):String={
    config.get(key).getOrElse("")
  }
  
  implicit def toImmutableMap(): Map[String,String]= {
    Map( config.toList: _* )
  }
}  
object DemoConfig extends DemoConfig

