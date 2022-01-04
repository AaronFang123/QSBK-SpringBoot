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
    private long loginExpireIn;

    @Value("${qsbk.token.expire-in}")
    private long tokenExpireIn;

    @Value("${qsbk.login.user_login_state_last}")
    private long userLoginStateLast;
}
