package testSendEmail;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class TestMakePDF {

	public static void main(String[] args) {
		TestMakePDF pdf = new TestMakePDF();
		try {
			pdf.getPDFdemo();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// �Ƚ���Document�������Ӧ�� ����汾��jar�������com.lowagie.text.Document
	Document document = new Document(PageSize.A4, 36.0F, 36.0F, 36.0F, 36.0F);

	public void getPDFdemo() throws DocumentException, IOException {
		// ��������õ��� iTextAsian.jar ��iText-2.1.3.jar ���ڱȽ��ϵķ����� �������ڵ�ַ����
		// ����
		// ����Ķ��壺�����õ����Դ���jar���������
		// BaseFont bfChinese = BaseFont.createFont("STSong-Light",
		// "UniGB-UCS2-H", false);
		// ��Ȼ��Ҳ�����������������������
		BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		// �������� ע�������µİ����� ��ɫ�Ƿ�װ��
		Font fontChinese8 = new Font(bfChinese, 10.0F, 0, new Color(59, 54, 54));
		// ����pdf�ĵ�һ�����裺
		// ���汾��ָ��·��
		saveLocal();
		document.open();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		// PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();
		// ��ȡ�˱�����ļ�·��
//		String path = this.getClass().getClassLoader().getResource("").getPath();
		// ��ȡ��·��
//		String filePath = path.substring(1, path.length() - 15);
		// ��ȡͼƬ·�� �ҵ�����Ҫ��pdf�����ɵ�ͼƬ
		// ��������Լ��Ļ�ȡ��·��д ֻҪ�ҵ�ͼƬλ�þͿ���
		String picPath = "C:\\Users\\liubing17\\Desktop\\images\\";
		// ��PDF����Ӷ���
		Paragraph pHeader = new Paragraph();
		pHeader.add(new Paragraph("The word you want to input", new Font(bfChinese, 8.0F, 1)));
		// pHeader.add(new Paragraph("����", ���� �����Լ�д Ҳ������fontChinese8 ֮ǰ����õ� );
		document.add(pHeader);// ���ĵ��м�����д������
		// ��ȡͼƬ
		Image img2 = Image.getInstance(picPath + "testPDF.png");
		//����ͼƬ��С
		img2.scaleAbsolute(100.0F, 100.0F);
		//����ͼƬ���ĵ��еľ���λ��30.0F����ͼƬ������߾࣬600.0F����ͼƬ����ױ߾���
		img2.setAbsolutePosition(30.0F, 600.0F);
		// ��ͼƬ��ӵ��ĵ���
		document.add(img2);
		// �ر��ĵ�
		document.close();
	}

	public void saveLocal() throws IOException, DocumentException {
		// ֱ������PDF �ƶ����ɵ�D��test.pdf
		File file = new File("C:\\Users\\liubing17\\Desktop\\testPDF.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));

	}
}