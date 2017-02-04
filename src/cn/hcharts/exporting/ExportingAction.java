package cn.hcharts.exporting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import cn.hcharts.svg.MyConverter;


/**
 * SVG ת���࣬ʵ�ֽ�SVG�ļ�ת��Ϊ����ͼƬ��ʽ�ļ�
 * @author Zhy
 * 
 * publish on Highcharts������  http://www.hcharts.cn 
 *
 * �������裺
 * 	1������ҳ���ύ�Ĳ���   �� ���Խ�������ӡ������ȷ��ҳ���ύ�����Ĵ��벻�����룩
 * 	2����svg���뱣��Ϊ.svg�ļ� �������ļ�ʱע����룩
 * 	3��ִ��ת����������.svg�ļ�ת����Ŀ���ļ�
 * 	4����ȡĿ���ļ��������� ���������
 */
@SuppressWarnings("serial")
public class ExportingAction extends ActionSupport {
	/**
	 * ��һ������ȡҳ���ύ�Ĳ�����������Struts2������������
	 */
	
	
	// ���
	private float width;
	
	// ���ű��������ﲢû���õ��ò������API��http://www.hcharts.cn/api/index.php#exporting.scale
	private float scale;
	
	// ��������
	private String type;
	
	// �ļ���
	private String filename;
	
	// SVG���룬�ٷ�Ĭ�������ļ���ʽ�ϴ�����jsp/servlet ��request.getParameter(arg0)���޷���ȡ��ֵ�ģ�������������Struts2�������Ǵ������������ҽ���Highcharts������̳����ϸ˵��
	private String svg;
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public ExportingAction() {
		request = ServletActionContext.getRequest();
		response = ServletActionContext.getResponse();
	}
	
	// get set 
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getSvg() {
		return svg;
	}

	public void setSvg(String svg) {
		this.svg = svg;
	}


	// ��������
	public void  export() throws Exception{
		
		/**
		 * �ڶ�������svg���뱣��Ϊsvg�ļ�
		 */
		
		// ��ӡ��ȡ�Ĳ�����ȷ�����Ի�ȡֵ�����Ĳ������룬����������룬�뽫���Highchartsҳ��ı�������ΪUTF-8
		// System.out.println(type + "\n" + filename + "\n" +svg + "\n" + width+"\n"+scale); 
		
		// ��ȡ��Ŀ�ľ���·��
		@SuppressWarnings("deprecation")
		String WebRoot = request.getRealPath("")+"\\temp\\";
		
		// SVG��ʱ�ļ���
		String temp = WebRoot+System.currentTimeMillis()+(int)(Math.random()*1000)+".svg";

		// ��svg����д�뵽��ʱ�ļ��У��ļ���׺��.svg
		File svgTempFile = new File(temp);
		//д���ļ���ע���ļ�����
		OutputStreamWriter svgFileOsw = new OutputStreamWriter(new FileOutputStream(svgTempFile),"UTF-8");
		svgFileOsw.write(svg);
		svgFileOsw.flush();
		svgFileOsw.close();
		
		/**
		 * ������������ת������������Ŀ���ļ�
		 */
		MyConverter mc = new MyConverter();
		// ����ת������������Ŀ���ļ���
		String filename = mc.conver(temp,WebRoot, type, width);
		
		// ��ȡĿ���ļ�����ת����������
		File resultFile = new File(WebRoot+filename);
		FileInputStream resultFileFi = new FileInputStream(resultFile);
		long l = resultFile.length();
		int k = 0;
		byte abyte0[] = new byte[65000];
		
		/**
		 * ���Ĳ����������������
		 */
		
		// ��������
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) l);
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		while ((long) k < l) {
			int j;
			j = resultFileFi.read(abyte0, 0, 65000);
			k += j;
			response.getOutputStream().write(abyte0, 0, j);
		}
		resultFileFi.close();
		
		// ת���ɹ���ɾ����ʱ�ļ�
		svgTempFile.delete();
		resultFile.delete();		
	}

}
