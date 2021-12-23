package fun.aaronfang.qsbk.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class QsbkProps {

    @Value("${qsbk.login.enable-sms}")
    private boolean enableSms;

    @Value("${qsbk.login.expire-in}")
    private long expireIn;
}
