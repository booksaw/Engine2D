package main.java.com.booksaw.Engine2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * A class containing useful code snippets to avoid them being repeated across
 * the program (USE THESE INSTEAD OF MAKING CUSTOM VERSIONS, this means that if
 * any changes need to be made to these methods, other methods do not need
 * changing)
 * 
 * @author booksaw
 *
 */
public class Utils {

	/**
	 * Used to load an image file
	 * 
	 * @param file the file to load
	 * @return a buffered image version of the image
	 */
	public static BufferedImage loadTransparentImage(File file) {

		try {
			BufferedImage bufImg = ImageIO.read(file);
			BufferedImage convertedImg = new BufferedImage(bufImg.getWidth(), bufImg.getHeight(), 6);
			convertedImg.getGraphics().drawImage(bufImg, 0, 0, null);
			return convertedImg;
		} catch (IOException e) {
			// if there is an error while loading
			Logger.Log(LogType.ERROR, "Could not load image file (" + file.getPath() + ")");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Used to scale an image without causing blurring ie 1 pixel becomes 5
	 * 
	 * @param image the image to scale
	 * @param scale the scale to increase the size by
	 */
	public static BufferedImage scaleImage(BufferedImage image, int scale) {

		BufferedImage scaledImage = new BufferedImage(scale * image.getWidth(), scale * image.getHeight(), 6);

		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				int rgb = image.getRGB(i, j);
				for (int k = 0; k < scale; k++) {
					for (int l = 0; l < scale; l++) {
						scaledImage.setRGB(k + scale * i, l + scale * j, rgb);
					}
				}
			}
		}
		return scaledImage;
	}

	/**
	 * Used to get stored values from xml nodes
	 * 
	 * @param tag     the tag to get the value of
	 * @param element the element which includes the tag
	 * @return the value of the node
	 */
	public static String getTagString(String tag, Element element) {
		try {
			NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
			Node node = (Node) nodeList.item(0);
			return node.getNodeValue();
		} catch (NullPointerException e) {
			Logger.Log(LogType.ERROR, "Could not find tag " + tag + " in element " + element);
			return "";
		}
	}

	public static boolean getTagBoolean(String tag, Element element) {
		try {
			return Boolean.parseBoolean(getTagString(tag, element));
		} catch (Exception e) {
			return false;
		}
	}

	public static double getTagDouble(String tag, Element element) {
		try {
			return Double.parseDouble(getTagString(tag, element));
		} catch (Exception e) {
			return 0;
		}
	}

	public static int getTagInteger(String tag, Element element) {
		try {
			return Integer.parseInt(getTagString(tag, element));
		} catch (Exception e) {
			return 0;
		}
	}

	public static void saveValue(String reference, Document document, Element element, String value) {
		Element ele = document.createElement(reference);
		ele.appendChild(document.createTextNode(value));
		element.appendChild(ele);
	}
}
