//package com.galvanize.worldsbestvideos;
//
//import org.fluentlenium.adapter.junit.FluentTest;
//import org.fluentlenium.core.hook.wait.Wait;
//
//import org.junit.After;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.util.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.concurrent.TimeUnit;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.fluentlenium.core.filter.FilterConstructor.containingText;
//import static org.fluentlenium.core.filter.FilterConstructor.withText;
//
//import com.galvanize.worldsbestvideos.models.Person;
//import com.galvanize.worldsbestvideos.repositories.PersonRepository;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Wait
//public class PersonAT extends FluentTest {
//
//    @Override
//    public WebDriver newWebDriver() {
//        ChromeOptions opt = new ChromeOptions();
//
//        String chromeBin = System.getenv("GOOGLE_CHROME_BIN");
//        if (!StringUtils.isEmpty(chromeBin)) {
//            opt.addArguments("--headless");
//            opt.addArguments("--use-fake-ui-for-media-stream");
//            opt.addArguments("--use-fake-device-for-media-stream");
//            opt.addArguments("--disable-gpu");
//            opt.addArguments("--no-sandbox");
//
//            //System.setProperty("webdriver.chrome.driver", chromeBin);
//            opt.setBinary(chromeBin);
//        } else {
//            String homeDir = System.getenv("HOME");
//            System.setProperty("webdriver.chrome.driver", homeDir + "/bin/chromedriver");
//            //opt.setBinary(homeDir + "/bin/chromedriver");
//        }
//
//        WebDriver driver = new ChromeDriver(opt);
//        return driver;
//    }
//
//    @Value("${local.server.port}")
//    private String port;
//
//    @Autowired
//    PersonRepository videoRepository;
//
//    @Before
//    public void before() {
//        videoRepository.deleteAll();
//    }
//
//    @After
//    public void after() {
//        videoRepository.deleteAll();
//    }
//
//    @Test
//    public void testHomePage() {
//        goTo("http://localhost:" + this.port + "/");
//        await().until(() -> $("h1").present());
//        assertThat($("h1").text()).isEqualTo("World's Best Videos");
//    }
//
//    @Test
//    public void testAddVideo() {
//        goTo("http://localhost:" + this.port + "/");
//        await().until(() -> $("button").present());
//        assertThat($("button").text()).isEqualTo("Add Person");
//        $("button").click();
//
//        await().until(() -> $("input").present());
//        await().until(() -> $("button").present());
//        assertThat($("button").text()).isEqualTo("Add");
//
//        $("input").fill().with("Lethal Weapon");
//        $("input").submit();
//
//        await().until(() -> $("a").present());
//        assertThat($("a").text()).isEqualTo("Lethal Weapon");
//    }
//
//    @Test
//    public void testEditVideo() {
//        // Setup
//        Person video = new Person("The Towering Inferno");
//        videoRepository.save(video);
//
//        goTo("http://localhost:" + this.port + "/");
//        await().until(() -> $("a").present());
//        assertThat($("a").text()).isEqualTo("The Towering Inferno");
//        $("a").click();
//
//        await().until(() -> $("input").present());
//        $("input").fill().with("Lethal Weapon");
//        $("input").submit();
//
//        await().until(() -> $("a").present());
//        assertThat($("a").text()).isEqualTo("Lethal Weapon");
//    }
//
//    @Test
//    public void testDeleteVideo() {
//        // Setup
//        videoRepository.save(new Person("The Towering Inferno"));
//        videoRepository.save(new Person("Lethal Weapon"));
//
//        goTo("http://localhost:" + this.port + "/");
//        await().until(() -> $("a").present());
//        assertThat($("a").get(0).text()).isEqualTo("The Towering Inferno");
//        $("a").get(0).click();
//
//        await().until(() -> $("#delete-button").present());
//        $("#delete-button").click();
//
//        await().until(() -> $("a").present());
//        assertThat($("a")).hasSize(1);
//        assertThat($("a").text()).isEqualTo("Lethal Weapon");
//    }
//}
