package Andreea.Bican;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OAuthApplication.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
@WebAppConfiguration
public class OAuthApplicationTests {

	@Test
	public void contextLoads() {
	}

}