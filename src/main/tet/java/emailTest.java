import com.qianfeng.meeting.business.utils.MailUtils;
import org.junit.Test;

/**
 * @author Allen
 * @date 2020/10/12
 **/

public class emailTest {
    @Test
    public void testSendMail(){
        MailUtils.sendMail("1025226466@qq.com", "这是一封测试邮件", "千锋会议系统测试邮件");
    }
}
