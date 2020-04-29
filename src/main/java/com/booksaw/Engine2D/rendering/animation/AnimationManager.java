package main.java.com.booksaw.Engine2D.rendering.animation;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.List;

import main.java.com.booksaw.Engine2D.logging.LogType;
import main.java.com.booksaw.Engine2D.logging.Logger;

/**
 * This class is used to store all animations for an image (can just be a single
 * static image)
 * 
 * @author booksaw
 *
 */
public class AnimationManager {

	/**
	 * The reference name of the active animation
	 */
	private String activeAnimation;
	/**
	 * Used to reference which frame the animation is on
	 */
	private int frameNumber = 0;

	/**
	 * Used if the animation frame should be updated
	 */
	private boolean updateAnimation = true;

	/**
	 * Used to track if the specific animation should be updated, so if a static
	 * animation is then switched to an active animation, updateAnimation will be
	 * remembered
	 */
	private boolean updateSpecificAnimation = true;

	private HashMap<String, Animation> animations;

	/**
	 * Used when only a single animation is being used
	 * 
	 * @param animation the animation to be used
	 */
	public AnimationManager(Animation animation) {
		animations = new HashMap<>();
		animations.put(animation.reference, animation);
		setActiveAnimation(animation.reference);
	}

	/**
	 * Used to add multiple animations to the manager
	 * 
	 * @param animationList the list of animations
	 * @param active        the initially active animation reference
	 */
	public AnimationManager(List<Animation> animationList, String active) {

		animations = new HashMap<>();

		for (Animation animation : animationList) {
			animations.put(animation.reference, animation);
		}

		// setting the active animation
		setActiveAnimation(active);

	}

	/**
	 * Used to paint the animation
	 * 
	 * @param graphics the graphics to paint it on
	 * @param x        the x location of the paint
	 * @param y        the y location of the paint
	 * @param width    the width of the resultant image
	 * @param height   the height of the resultant image
	 */
	public void paint(Graphics graphics, int x, int y, int width, int height) {
		Animation animation = getActiveAnimation();
		// not doing null checks as will slow down rendering process, and an error
		// message has already been provided to the user if the selected animation is
		// null
		graphics.drawImage(animation.image, x, y - height, x + width, y, 0, (frameNumber * animation.frameHeight),
				animation.image.getWidth(), (frameNumber * animation.frameHeight) + animation.frameHeight, null);
	}

	/**
	 * Used to track how long the animation has been on that specific animation
	 */
	private int ticksOnAnimation = 0;

	/**
	 * Used once per tick to ensure the animation is updated correctly and not tied
	 * to the frame rate
	 * 
	 * @param the number of ticks since last update
	 */
	public void update(int time) {

		if (!updateSpecificAnimation) {
			return;
		}

		ticksOnAnimation += time;

		Animation animation = getActiveAnimation();
		// using loop, because may have skipped multiple frames
		while (ticksOnAnimation >= animation.timeGap) {

			frameNumber++;
			// reducing ticks on animation
			ticksOnAnimation -= animation.timeGap;

			// checking if it needs to loop
			if (frameNumber == animation.frameCount) {
				frameNumber = 0;
			}

		}
	}

	/**
	 * Used to set the animation which is currently rendering
	 * 
	 * @param reference
	 */
	public void setActiveAnimation(String reference) {
		Animation animation = animations.get(reference);

		// null check
		if (animation == null) {
			// the animation does not exist
			Logger.Log(LogType.ERROR, "The Animation with the reference " + reference + " does not exist");
			// ensuring something is selected
			if (activeAnimation == null || activeAnimation == "") {
				// trying to find a fall back animation
				animation = animations.entrySet().iterator().next().getValue();
				// checking if the fall back animation exists
				if (animation == null) {
					Logger.Log(LogType.ERROR, "No fall-back animation found, errors expected");
				} else {
					// if the animation does exist setting it to be active
					setActiveAnimation(animation.reference);
				}
			}

			// returning to stop it breaking
			return;

		}

		// animation exists
		// checking if it is a static animation
		if (animation.frameCount == 1 || animation.timeGap < 1) {
			// animation is static, disabling updating
			updateSpecificAnimation = false;
		} else {
			// setting the specific animation update to the general update
			updateSpecificAnimation = updateAnimation;
		}

		// resetting the frame number and switching animation
		frameNumber = 0;
		ticksOnAnimation = 0;
		activeAnimation = reference;

		// checking if update needs changing
		updateSpecificAnimation = (animations.get(activeAnimation).frameCount == 1
				|| animations.get(activeAnimation).timeGap < 1) ? false : updateAnimation;
	}

	/**
	 * Used to temporarily pause the animation without losing previous
	 * updateAnimation state
	 */
	public void pause() {
		updateSpecificAnimation = false;
	}

	public void resume() {
		updateSpecificAnimation = (animations.get(activeAnimation).frameCount == 1
				|| animations.get(activeAnimation).timeGap < 1) ? false : updateAnimation;
	}

	/**
	 * Used to add an animation to the list of possible animations
	 * 
	 * @param animation the animation to add
	 */
	public void addAnimation(Animation animation) {
		animations.put(animation.reference, animation);
	}

	/**
	 * Used to remove an animation
	 * 
	 * @param reference the animation to remove
	 */
	public void removeAnimation(String reference) {
		// checking if it is the active animation
		if (reference.equals(activeAnimation)) {
			Logger.Log(LogType.WARNING,
					"Program tried to remvoe animation while it is active, replace active animation before removing. Animation reference: "
							+ reference);
			return;
		}

		animations.remove(reference);
	}

	// basic getters and setters
	public void setFrameNumber(int frameNumber) {
		this.frameNumber = frameNumber;
		ticksOnAnimation = 0;
	}

	public boolean isUpdatingAnimation() {
		return updateAnimation;
	}

	public void setUpdatingAnimation(boolean updateAnimation) {
		this.updateAnimation = updateAnimation;

		// updating the specific animation if appropriate
		updateSpecificAnimation = (animations.get(activeAnimation).frameCount == 1
				|| animations.get(activeAnimation).timeGap < 1) ? false : updateAnimation;
	}

	public HashMap<String, Animation> getAnimations() {
		return animations;
	}

	public void setAnimations(HashMap<String, Animation> animations) {
		this.animations = animations;
	}

	public String getActiveAnimationReference() {
		return activeAnimation;
	}

	public Animation getActiveAnimation() {
		return animations.get(activeAnimation);
	}

	public int getFrameNumber() {
		return frameNumber;
	}

}
