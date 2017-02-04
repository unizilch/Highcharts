package cn.hcharts.svg;

import java.io.File;

import org.apache.batik.apps.rasterizer.DestinationType;
import org.apache.batik.apps.rasterizer.SVGConverter;
import org.apache.batik.apps.rasterizer.SVGConverterException;

/**
 * SVG ת���࣬ʵ�ֽ�SVG�ļ�ת��Ϊ����ͼƬ��ʽ�ļ�
 * @author Zhy
 * 
 * publish on Highcharts������  http://www.hcharts.cn 
 *
 */
public class MyConverter extends SVGConverter{
	
	/**
	 * ת������
	 * @param sources SVG�ļ�·��
	 * @param destination Ŀ���ļ�·��
	 * @param type ת�����ͣ��� image/png | image/jpeg | application/pdf |��image/svg+xml����ѡ
	 * @param width ����ͼƬ���
	 * @return Ŀ���ļ���
	 * @throws SVGConverterException
	 */
	public  String conver(String sources,String destination,String type,float width) throws SVGConverterException {
		
		SVGConverter converter = new MyConverter();
		
		// ���ø߶ȣ�Ĭ����400
		converter.setHeight(400);
		
		// ���ÿ�ȣ������ֵ
		converter.setWidth(width);
		
		// ����svgԴ�ļ�·������һ�����飬֧�ֶ���ļ�ͬʱת��
		String[] src = {sources};
		converter.setSources(src);
		

		// ����ͼƬ����
		converter.setQuality(MAXIMUM_QUALITY);
		
		// ��¼�ļ���׺
		String ext = "";
		
		// ���ߴ�����������õ������ͺ��ļ���׺
		if(type.equals("image/png")) {
			converter.setDestinationType(DestinationType.PNG);
			ext = "png";
		} else if(type.equals("image/jpeg")) {
			converter.setDestinationType(DestinationType.JPEG);
			ext = "jpg";
		} else if(type.equals("application/pdf")) {
			converter.setDestinationType(DestinationType.PDF);
			ext = "pdf";
		} else if (type.equals("image/svg+xml")) {
			converter.setDestinationType(DestinationType.TIFF);
			ext = "tif";
		} else {
			return null;
		}

		
		
		// ����Ŀ���ļ�·��
		converter.setDst(new File(destination+"\\chart."+ext));
		
		// ִ�е���
		converter.execute();
		return "chart."+ext;
	}
}
