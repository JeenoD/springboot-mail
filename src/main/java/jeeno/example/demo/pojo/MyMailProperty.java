package jeeno.example.demo.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author: Jeeno
 * @version: 0.0.1
 * @since: 2019/7/16 15:20
 */
@Component
@Data
@PropertySource("classpath:application.properties")
public class MyMailProperty {
    @Value("${spring.mail.username}")
    public String fromAddr;

}
