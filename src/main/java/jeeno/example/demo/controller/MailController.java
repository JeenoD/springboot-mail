package jeeno.example.demo.controller;

import jeeno.example.demo.pojo.MyMailProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author: Jeeno
 * @version: 0.0.1
 * @since: 2019/7/16 15:14
 */
@RestController
@Slf4j
@RequestMapping("/mail")
public class MailController {

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private MyMailProperty mailProperty;

    @Resource
    private TemplateEngine templateEngine;

    /**
     * send a simple mail
     */
    @GetMapping("/send/simple/{toAddr}")
    public String simpleMail(@PathVariable("toAddr") String toAddr){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperty.fromAddr);
        message.setTo(toAddr);
        message.setSubject("simple mail");
        message.setText("its the content of the simple mail.");
        mailSender.send(message);
        return "Send simple mail success.";
    }

    /**
     * send a mail with attachment
     */
    @GetMapping("/send/attach/{toAddr}")
    public String attachMail(@PathVariable("toAddr") String toAddr) throws Exception{
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mailProperty.fromAddr);
        helper.setTo(toAddr);
        helper.setSubject("mail with attachment");
        helper.setText("it's a mail with attachment.");

        // TODO you have to replace to your own file path here.
        FileSystemResource file = new FileSystemResource(new File("Your own path:\\content.txt"));

        helper.addAttachment("attachment-1.txt", file);

        mailSender.send(mimeMessage);
        return "Send Attachment Mail success.";
    }

    /**
     * send a mail with html-template content
     */
    @GetMapping("/send/template/{toAddr}")
    public String templateMail(@PathVariable("toAddr") String toAddr) throws Exception{
        Context context = new Context();
        context.setVariable("username","Jeeno");
        // get the thymeleaf template
        String emailContent= templateEngine.process("mailContent", context);

        MimeMessage message=mailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message,true);
        helper.setFrom(mailProperty.fromAddr);
        helper.setTo(toAddr);
        helper.setSubject("html-template mail.");
        helper.setText(emailContent,true);
        mailSender.send(message);
        return "Send Html-Template mail success.";
    }

}
