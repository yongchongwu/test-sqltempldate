package com.mock;

import com.mock.config.DefaultProfileUtil;
import com.mock.config.ProfileConstants;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class TestSqltempldateApplication {

  private static final Logger log = LoggerFactory.getLogger(TestSqltempldateApplication.class);

  private final Environment env;

  public TestSqltempldateApplication(Environment env) {
    this.env = env;
  }

  @PostConstruct
  public void initApplication() {
    Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
    if (activeProfiles.contains(ProfileConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles
        .contains(ProfileConstants.SPRING_PROFILE_PRODUCTION)) {
      log.error("You have misconfigured your application! It should not run " +
          "with both the 'dev' and 'prod' profiles at the same time.");
    }
    if (activeProfiles.contains(ProfileConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles
        .contains(ProfileConstants.SPRING_PROFILE_CLOUD)) {
      log.error("You have misconfigured your application! It should not " +
          "run with both the 'dev' and 'cloud' profiles at the same time.");
    }
  }

  public static void main(String[] args) throws UnknownHostException {
    //SpringApplication.run(TestSqltempldateApplication.class, args);
    SpringApplication app = new SpringApplication(TestSqltempldateApplication.class);
    //app.setBannerMode(Banner.Mode.OFF);// 关闭启动Banner

    DefaultProfileUtil.addDefaultProfile(app);

    Environment env = app.run(args).getEnvironment();

    String protocol = "http";
    if (env.getProperty("server.ssl.key-store") != null) {
      protocol = "https";
    }
    log.info("\n----------------------------------------------------------\n\t" +
            "Application '{}' is running! Access URLs:\n\t" +
            "Local: \t\t{}://localhost:{}\n\t" +
            "External: \t{}://{}:{}\n\t" +
            "Profile(s): \t{}\n----------------------------------------------------------",
        env.getProperty("spring.application.name"),
        protocol,
        env.getProperty("server.port"),
        protocol,
        InetAddress.getLocalHost().getHostAddress(),
        env.getProperty("server.port"),
        env.getActiveProfiles());
  }
}
