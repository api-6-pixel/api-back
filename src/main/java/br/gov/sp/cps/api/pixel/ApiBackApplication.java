package br.gov.sp.cps.api.pixel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.gov.sp.cps.api.pixel.outbound.rest")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class ApiBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiBackApplication.class, args);
    }

}
