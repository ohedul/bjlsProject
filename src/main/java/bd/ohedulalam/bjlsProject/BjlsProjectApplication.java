package bd.ohedulalam.bjlsProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.convert.Jsr310Converters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackageClasses ={ BjlsProjectApplication.class,
        Jsr310Converters.class})
public class BjlsProjectApplication {
    @PostConstruct
    void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    }

	public static void main(String[] args) {
		SpringApplication.run(BjlsProjectApplication.class, args);
	}

}
