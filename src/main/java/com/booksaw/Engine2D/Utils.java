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

	/**
	 * Used to get stored boolean values from XML nodes
	 * 
	 * @param tag     the tag to get the value of
	 * @param element the element which includes the tag
	 * @return the boolean value of the node
	 */
	public static boolean getTagBoolean(String tag, Element element) {
		try {
			return Boolean.parseBoolean(getTagString(tag, element));
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Used to get stored double values from XML nodes
	 * 
	 * @param tag     the tag to get the value of
	 * @param element the element which includes the tag
	 * @return the double value of the node
	 */
	public static double getTagDouble(String tag, Element element) {
		try {
			return Double.parseDouble(getTagString(tag, element));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Used to get stored integer values from XML nodes
	 * 
	 * @param tag     the tag to get the value of
	 * @param element the element which includes the tag
	 * @return the integer value of the node
	 */
	public static int getTagInteger(String tag, Element element) {
		try {
			return Integer.parseInt(getTagString(tag, element));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Used to save a value to an XML file
	 * 
	 * @param reference the reference to the location to save the value (this is the
	 *                  path from the provided element)
	 * @param document  the document to save the value to
	 * @param element   the element of the document to save it to
	 * @param value     the value that the reference should be set to
	 */
	public static void saveValue(String reference, Document document, Element element, String value) {
		Element ele = document.createElement(reference);
		ele.appendChild(document.createTextNode(value));
		element.appendChild(ele);
	}
}
