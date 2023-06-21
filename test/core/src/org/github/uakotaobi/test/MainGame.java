package org.github.uakotaobi.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Random;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	double x = 0;
	double y = 0;

	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render() {
		Pixmap pixmap = null;
		try {
			pixmap = generateStarfieldTexture(100, 100, (int) x, (int) y, 0.02, 0);

			// ScreenUtils.clear(1, 0, 0, 1);
			batch.begin();
			batch.draw(img, 0, 0);
			batch.end();

			if (Gdx.input.isTouched()) {
				System.out.printf("Touched screen at (x=%d, y=%d)", Gdx.input.getX(), Gdx.input.getY());
			}

		} finally {
			pixmap.dispose();
		}
		x += 0.05;
		y += 0.01;
	}

	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}

	/**
	 * Procedurally generates a background image consisting of numerous
	 * pixelated stars of varying brightness. The generation is done in
	 * such a way that panning around the starfield by varying the
	 * (worldUpperLeftX, worldUpperLeftY) parameters will produce
	 * consistent stars in consistent locations.
	 *
	 * @param width           The width of the generated starfield bitmap.
	 * @param height          The height of the generated starfield bitmap.
	 * @param worldUpperLeftX The X coordinate of the upper left corner of
	 *                        the starfield in "world coordinates."
	 *                        <p>
	 *                        Importantly, generated star pixels at the
	 *                        same relative world coordinate will always
	 *                        look the same, whether that look is a black
	 *                        pixel or a brightened one.
	 * @param worldUpperLeftY The Y coordinate of the upper left corner of
	 *                        the starfield in "world coordinates."
	 * @param density         The density of the starfield, with 1.0 being
	 *                        the maximum and 0.0 representing an empty
	 *                        starfield.
	 * @param seed            Random number seed.
	 * @return The generated starfield pixmap, with the
	 *         same width and height that were passed into
	 *         us.
	 */
	private Pixmap generateStarfieldTexture(int width, int height, int worldUpperLeftX, int worldUpperLeftY,
			double density, int seed) {
		seed = 0;

		// The pixmap might be all-black already. Need to check.
		Pixmap pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setBlending(Blending.None);
		pixmap.setColor(Color.BLACK);
		pixmap.fill();

		final Random generator = new Random();
		final float min_hue = 45.0f,
			    max_hue = 170.0f,
			    min_saturation = 0.9f,
			    max_saturation = 1.0f,
			    min_value = 0.0f,
			    max_value = 1.0f;

		final int WORLD_WIDTH = (int) 1e08;
		for (int y = 0; y < height; ++y) {
			for (int x = 0; x < width; ++x) {
				// We assume the "world" to have a generous,
				// but fixed width here so that we can readily
				// convert (worldX, worldY) coordinates into
				// world offsets (offset = width * y + x.)
				//
				// The world offset for a given star will be
				// the same no matter where the "camera" is.
				final int worldX = (worldUpperLeftX + x);
				final int worldY = (worldUpperLeftY + y);
				int worldOffset = worldY * WORLD_WIDTH + worldX;

				generator.setSeed(seed + worldOffset);
				if (generator.nextDouble() < density) {
					// We will generate some sort of star
					// here.
					float u_h = generator.nextFloat(),
					      u_s = generator.nextFloat(),
					      u_v = generator.nextFloat();
					float h = min_hue + u_h * (max_hue - min_hue),
					      s = min_saturation + u_s * (max_saturation - min_saturation),
					      v = min_value + u_v * (max_value - min_value);

					Color starColor = new Color().fromHsv(h, s, v);
					pixmap.setColor(starColor);
					pixmap.drawRectangle(x, y, 1, 1);
				}
			}
		}

		return pixmap;
	}
}
