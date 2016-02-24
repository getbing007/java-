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

	// 先建立Document对象：相对应的 这个版本的jar引入的是com.lowagie.text.Document
	Document document = new Document(PageSize.A4, 36.0F, 36.0F, 36.0F, 36.0F);

	public void getPDFdemo() throws DocumentException, IOException {
		// 这个导出用的是 iTextAsian.jar 和iText-2.1.3.jar 属于比较老的方法。 具体下在地址见：
		// 首先
		// 字体的定义：这里用的是自带的jar里面的字体
		// BaseFont bfChinese = BaseFont.createFont("STSong-Light",
		// "UniGB-UCS2-H", false);
		// 当然你也可以用你电脑里面带的字体库
		BaseFont bfChinese = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.TTC,1", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		// 定义字体 注意在最新的包里面 颜色是封装的
		Font fontChinese8 = new Font(bfChinese, 10.0F, 0, new Color(59, 54, 54));
		// 生成pdf的第一个步骤：
		// 保存本地指定路径
		saveLocal();
		document.open();
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		// PdfWriter writer = PdfWriter.getInstance(document, ba);
		document.open();
		// 获取此编译的文件路径
//		String path = this.getClass().getClassLoader().getResource("").getPath();
		// 获取根路径
//		String filePath = path.substring(1, path.length() - 15);
		// 获取图片路径 找到你需要往pdf上生成的图片
		// 这里根据自己的获取的路径写 只要找到图片位置就可以
		String picPath = "C:\\Users\\liubing17\\Desktop\\images\\";
		// 往PDF中添加段落
		Paragraph pHeader = new Paragraph();
		pHeader.add(new Paragraph("The word you want to input", new Font(bfChinese, 8.0F, 1)));
		// pHeader.add(new Paragraph("文字", 字体 可以自己写 也可以用fontChinese8 之前定义好的 );
		document.add(pHeader);// 在文档中加入你写的内容
		// 获取图片
		Image img2 = Image.getInstance(picPath + "testPDF.png");
		//设置图片大小
		img2.scaleAbsolute(100.0F, 100.0F);
		//设置图片在文档中的绝对位置30.0F定义图片距离左边距，600.0F定义图片距离底边距离
		img2.setAbsolutePosition(30.0F, 600.0F);
		// 将图片添加到文档中
		document.add(img2);
		// 关闭文档
		document.close();
	}

	public void saveLocal() throws IOException, DocumentException {
		// 直接生成PDF 制定生成到D盘test.pdf
		File file = new File("C:\\Users\\liubing17\\Desktop\\testPDF.pdf");
		file.createNewFile();
		PdfWriter.getInstance(document, new FileOutputStream(file));

	}
}