package uz.app.pc_market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("uz.app.pc_market.entity")
public class PcMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcMarketApplication.class, args);
    }

}
