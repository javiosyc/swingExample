package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;

public class Utils {
	public static String getExtension(String name) {
		int pointIndex = name.lastIndexOf(".");
		if (pointIndex == -1) {
			return null;
		}

		if (pointIndex == name.length() - 1) {
			return null;
		}

		return name.substring(pointIndex + 1, name.length());
	}

	public static ImageIcon createIcon(String path) {
		URL url = System.class.getResource(path);

		if (url == null) {
			System.err.println("Unable to laod image:" + url);
			return null;
		}

		ImageIcon icon = new ImageIcon(url);
		return icon;
	}
	public static Font createFont(String path) {
		URL url = System.class.getResource(path);

		if (url == null) {
			System.err.println("Unable to laod font:" + url);
			return null;
		}

		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,url.openStream());
		} catch (FontFormatException e) {
			System.out.println("Bad format in font file: "+path);
		} catch (IOException e) {
			System.out.println("Unable to read font file: "+path);
		}
		
		return font;
	}
}
