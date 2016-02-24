package testSendEmail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendEmail {
	private MimeMessage message;
	private Session session;
	private Transport transport;

	private String mailHost = "";
	private String sender_username = "";
	private String sender_password = "";

	private Properties properties = new Properties();

	/*
	 * ��ʼ������
	 */
	public SendEmail(boolean debug) {
		InputStream in = SendEmail.class.getResourceAsStream("/MailServer.properties");
		try {
			properties.load(in);
			this.mailHost = properties.getProperty("mail.smtp.host");
			this.sender_username = properties.getProperty("mail.sender.username");
			this.sender_password = properties.getProperty("mail.sender.password");
		} catch (IOException e) {
			e.printStackTrace();
		}

		session = Session.getInstance(properties);
		session.setDebug(debug);// �������е�����Ϣ
		message = new MimeMessage(session);
	}

	/**
	 * �����ʼ�
	 * 
	 * @param subject
	 *            �ʼ�����
	 * @param sendHtml
	 *            �ʼ�����
	 * @param receiveUser
	 *            �ռ��˵�ַ
	 * @param attachment
	 *            ����
	 */
	public void doSendHtmlEmail(String subject, String sendHtml, String receiveUser, File attachment) {
		try {
			// ������
			InternetAddress from = new InternetAddress(sender_username);
			message.setFrom(from);

			// �ռ���
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);

			// �ʼ�����
			message.setSubject(subject);

			// ��multipart����������ʼ��ĸ����������ݣ������ı����ݺ͸���
			Multipart multipart = new MimeMultipart();

			// ����ʼ�����
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);

			// ��Ӹ���������
			if (attachment != null) {
				BodyPart attachmentBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachment);
				attachmentBodyPart.setDataHandler(new DataHandler(source));

				// ���������Ľ���ļ�������ķ�������ʵ��MimeUtility.encodeWord�Ϳ��Ժܷ���ĸ㶨
				// �������Ҫ��ͨ�������Base64�����ת�����Ա�֤������ĸ����������ڷ���ʱ����������
				// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
				// messageBodyPart.setFileName("=?GBK?B?" +
				// enc.encode(attachment.getName().getBytes()) + "?=");

				// MimeUtility.encodeWord���Ա����ļ�������
				attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
				multipart.addBodyPart(attachmentBodyPart);
			}

			// ��multipart����ŵ�message��
			message.setContent(multipart);
			// �����ʼ�
			message.saveChanges();

			transport = session.getTransport("smtp");
			// smtp��֤���������������ʼ��������û�������
			transport.connect(mailHost, sender_username, sender_password);
			// ����
			transport.sendMessage(message, message.getAllRecipients());

			System.out.println("send success!");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		SendEmail se = new SendEmail(true);
		File affix = new File("C:\\Users\\liubing17\\Desktop\\note_for_lenovo.txt");
		se.doSendHtmlEmail("�ʼ�����", "�ʼ�����", "liubing17@lenovo.com", affix);//
	}
}
