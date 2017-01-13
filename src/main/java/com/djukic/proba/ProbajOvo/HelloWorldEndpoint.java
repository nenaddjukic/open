package com.djukic.proba.ProbajOvo;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

@Path("/pm")
public class HelloWorldEndpoint {

	Logger LOG = LoggerFactory.getLogger(HelloWorldEndpoint.class);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JsonObject doGet() {
		JsonObject result = null;
		Map<String, Object> config = new HashMap<String, Object>();
		config.put("javax.json.stream.JsonGenerator.prettyPrinting", Boolean.valueOf(true));
		JsonBuilderFactory factory = Json.createBuilderFactory(config);
		if (checkPropertyFile()) {
			LOG.info("Found file");

			result = factory.createObjectBuilder()
					.add("counter",
							factory.createObjectBuilder().add("name", "DefaultCounter")
									.add("value", "DefaultCounterValue").add("threshholdCrossed", "N/A")
									.add("criticality", "MAJ"))
					.build();
		} else {
			LOG.info("Could not find file");
			result = factory.createObjectBuilder().build();
		}
		return result;
	}

	private boolean checkPropertyFile() {
		boolean result = false;
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("/etc/vmme.properties");
			prop.load(input);
			LOG.info("Finding property file: {}", prop);
			if (prop.containsKey("user_name")) {
				LOG.info("FOUND IT");
				result = true;
			}
		} catch (final IOException ex) {
			LOG.error("Found error in reading prop file: ", ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
