package main.java.com.booksaw.Engine2D;

/**
 * This is used to track a velocity or force
 * 
 * @author booksaw
 *
 */
public class Vector {
	/**
	 * Used to store both the components of the vector
	 */
	public double x, y;

	/**
	 * Used to make a 0 vector
	 */
	public Vector() {
		x = 0;
		y = 0;
	}

	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Used to apply a vector to this vector
	 * 
	 * @param v the vector to apply
	 */
	public void applyVector(Vector v) {
		this.x += v.x;
		this.y += v.y;
	}

	/**
	 * Used to apply a vector to this vector
	 * 
	 * @param ax the x component of the vector
	 * @param ay the y component of the vector
	 */
	public void applyVector(int ax, int ay) {
		this.x += ax;
		this.y += ay;
	}

	/**
	 * Used to apply a vector with a maximum to this vector
	 * 
	 * @param v           the vector to apply
	 * @param terminalX   the maximum value absolute of x (set to -1 to be infinite)
	 * @param terminalY   the maximum value absolute of y (set to -1 to be infinite)
	 * @param terminalMod the maximum speed of the vector (set to -1 to be infinite)
	 * @param resistive   if the force causing the change in velocity is a resistive
	 *                    force (if true, velocity will not cross 0 but instead stop
	 *                    at it)
	 */
	public void applyVector(Vector v, double terminalX, double terminalY, double terminalMod, boolean resistive) {
		double tempx = x;
		this.x += v.x;

		// checking x vector
		if (terminalX >= 0 && terminalX < Math.abs(x)) {
			x = (x > 0) ? terminalX : -terminalX;
		}

		double tempy = y;
		this.y += v.y;
		// checking y vector
		if (terminalY >= 0 && terminalY < Math.abs(y)) {
			y = (y > 0) ? terminalY : -terminalY;
		}

		// calculating modulus
		if (terminalMod > 0 && getMod() > terminalMod) {
			// calculating the angle
			double angle = getAngle();
			// using sin(angle) * terminalMod and cos(angle) * terminalMod, as that will
			// give the correct mod in the correct direction
			y = Math.sin(angle) * terminalMod;
			x = Math.cos(angle) * terminalMod;
			// mod will now be correct
		}

		if (resistive && ((tempx >= 0 && x <= 0) || (tempx <= 0 && x >= 0))) {
			x = 0;
		}

		if (resistive && ((tempy >= 0 && y <= 0) || (tempy <= 0 && y >= 0))) {
			y = 0;
		}

	}

	/**
	 * 
	 * @return the modulus of the vector (using pythag c^2 = x^2 + y^2)
	 */
	public double getMod() {
		return Math.sqrt((Math.pow(x, 2)) + Math.pow(y, 2));
	}

	/**
	 * 
	 * @return the angle from the horizontal the vector is travelling at
	 */
	public double getAngle() {
		return Math.atan(y / x);
	}

	/**
	 * Used to stop the object
	 */
	public void stop() {
		x = 0;
		y = 0;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
