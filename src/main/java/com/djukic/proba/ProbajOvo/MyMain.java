package com.djukic.proba.ProbajOvo;

import java.net.URL;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.undertow.WARArchive;

public class MyMain {

	public static void main(String[] args) throws Exception {

		ClassLoader cl = MyMain.class.getClassLoader();
		URL xmlConfig = cl.getResource("swarm-standalone.xml");
		assert xmlConfig != null : "Failed to load standalone.xml";

		Swarm swarm = new Swarm(false).withXmlConfig(xmlConfig);

		// container.fraction(new LoggingFraction());
		swarm.fraction(new DatasourcesFraction());
		// Start the container
		swarm.start();

		WARArchive appDeployment = ShrinkWrap.create(WARArchive.class);
		appDeployment.addClasses(HelloWorldEndpoint.class);
		appDeployment.addClasses(RestApplication.class);
		//appDeployment.addAsWebInfResource(cl.getResource("index.html"),"");
		// Deploy your app
		swarm.deploy(appDeployment);
	}
}
