package com.example.demo;

import com.example.demo.configuration.XmlConfiguration;
import com.example.demo.dto.GpxModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.NotSupportedException;
import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XmlConfigurationTest {


	@Autowired
	XmlConfiguration xmlConfiguration;


	@Test
	public void testReadXml() {
		try {
			String resourceName = "sample.gpx";
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(resourceName).getFile());
			GpxModel gpx = xmlConfiguration.readXml(file.getAbsolutePath(), GpxModel.class);
			Assert.assertNotNull(gpx.getMetadata());
			Assert.assertNotNull(gpx.getTrk());
			Assert.assertNotNull(gpx.getWpt());
		} catch (IOException | NotSupportedException e) {
			e.printStackTrace();
		}
	}

}
