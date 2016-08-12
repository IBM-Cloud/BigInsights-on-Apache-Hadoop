package net.christophersnow.etl.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.scheduling.annotation.Scheduled

import com.jayway.jsonpath.JsonPath
import groovy.json.JsonSlurper
import org.apache.hadoop.gateway.shell.Hadoop
import org.apache.hadoop.gateway.shell.hdfs.Hdfs
import org.apache.hadoop.gateway.shell.workflow.Workflow

import static java.util.concurrent.TimeUnit.SECONDS


@Controller
public class EtlController {

    def env = System.getenv()

    def gateway = env.gateway
    def username = env.username
    def password = env.password

    def jobDir = "/user/" + username + "/oozie_spark_bluemix_test"

    def hdfsWordCountJar = "${jobDir}/lib/OozieWorkflowSparkGroovyBluemixDeploy.jar"

    def configuration = """\
    <configuration>
        <property>
            <name>master</name>
            <value>yarn-master</value>
        </property>
        <property>
            <name>mode</name>
            <value>cluster</value>
        </property>
        <property>
            <name>queueName</name>
            <value>default</value>
        </property>
        <property>
            <name>user.name</name>
            <value>default</value>
        </property>
        <property>
            <name>nameNode</name>
            <value>default</value>
        </property>
        <property>
            <name>jobTracker</name>
            <value>default</value>
        </property>
        <property>
            <name>jobDir</name>
            <value>$jobDir</value>
        </property>
        <property>
            <name>inputDir</name>
            <value>$jobDir/input</value>
        </property>
        <property>
            <name>outputDir</name>
            <value>$jobDir/output</value>
        </property>
        <property>
            <name>hdfsWordCountJar</name>
            <value>$hdfsWordCountJar</value>
        </property>
        <property>
            <name>oozie.wf.application.path</name>
            <value>$jobDir</value>
        </property>
        <property>
            <name>oozie.use.system.libpath</name>
            <value>true</value>
        </property>
    </configuration>
    """

    @RequestMapping('/')
    public String greeting(
            Model model
            ) {

        def output
        def json
        def text

        def session = Hadoop.login( gateway, username, password )
    
        try {
            Hdfs.rm( session ).file( jobDir + '/output' ).recursive().now()
        } 
        catch (Exception e) {
            // ignore - output dir may not have existed which is ok
        }

        def jobId = Workflow.submit(session).text( configuration ).now().jobId

        def status = "RUNNING";
        def count = 0;
        while( status == "RUNNING" && count++ < 300 ) {
          sleep( 1000 )
          json = Workflow.status(session).jobId( jobId ).now().string
          status = JsonPath.read( json, "\$.status" )
        }

        if( status == "SUCCEEDED" ) {
          text = Hdfs.ls( session ).dir( jobDir + "/output" ).now().string
          json = (new JsonSlurper()).parseText( text )
          output = Hdfs.get( session ).from( jobDir + "/output/part-00000" ).now().string
        }

        session.shutdown()

        model.addAttribute('output', output)
        model.addAttribute('status', status)

        return 'greeting'
    }
}
