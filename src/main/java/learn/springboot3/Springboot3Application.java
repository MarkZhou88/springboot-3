package learn.springboot3;

import learn.springboot3.model.Post;
import learn.springboot3.service.JsonPlaceholderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;

@Slf4j
@SpringBootApplication
public class Springboot3Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot3Application.class, args);
    }

    @Bean
    JsonPlaceholderService jsonPlaceholderService() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .defaultHeader("SPRING-BOOT-VERSION", "3.1.0")
                .defaultHeader("SPRING-VERSION", "6")
                .exchangeStrategies(ExchangeStrategies.builder().codecs(c -> c.defaultCodecs().enableLoggingRequestDetails(true)).build())
                .build();
        // HttpServiceProxyFactory创建的WebClient在某些场景下可以提供更加灵活的配置方式，也为Spring WebFlux与不同的HTTP Server提供了扩展能力。
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient)).build();
        return factory.createClient(JsonPlaceholderService.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(JsonPlaceholderService jsonPlaceholderService) {
        return args -> {
            List<Post> posts = jsonPlaceholderService.loadPosts();
            log.info("请求返回数量：{}", posts.size());
        };
    }

}
