package ru.amfitel.jagost.actors;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by estarcev on 06.02.2017.
 */
public class Table extends Actor{

	private float border = 0.05f;

	private float fullHeight = 1;
	private float fullWidth = 1;

	public Table(World world, float w, float h, float cp, float mp) {
		float cpl = (float)Math.sqrt(cp * cp / 2);
		fullHeight = h + border + border;
		fullWidth = w + border + border;

		float[] vert1 = {0, cpl, 0, (h / 2 + cpl - mp / 2), border, (h / 2 + cpl - mp / 2), border, cpl + border};
		buildBorder(world, vert1);

		float[] vert2 = {0, fullHeight / 2 + mp / 2, 0, fullHeight - cpl, border, fullHeight - cpl - border, border, fullHeight / 2 + mp / 2};
		buildBorder(world, vert2);

		float[] vert3 = {cpl, fullHeight, fullWidth - cpl, fullHeight, fullWidth - cpl - border, fullHeight - border, cpl + border, fullHeight - border};
		buildBorder(world, vert3);

		float[] vert4 = {fullWidth - border, fullHeight / 2 + mp / 2, fullWidth - border, fullHeight - cpl - border, fullWidth, fullHeight - cpl, fullWidth, fullHeight / 2 + mp / 2};
		buildBorder(world, vert4);

		float[] vert5 = {fullWidth - border, cpl+border, fullWidth-border, (h / 2 + cpl - mp / 2), fullWidth, (h / 2 + cpl - mp / 2), fullWidth, cpl};
		buildBorder(world, vert5);

		float[] vert6 = {cpl, 0, cpl+border, border, fullWidth - cpl - border, border, fullWidth - cpl, 0};
		buildBorder(world, vert6);
	}

	private void buildBorder(World world, float[] vertices) {
		BodyDef def = new BodyDef();
		Body grnd = world.createBody(def);

		ChainShape shape = new ChainShape();

		shape.createLoop(vertices);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1;
		fdef.restitution = 1f;
		fdef.friction = 0f;

		grnd.createFixture(fdef);

		shape.dispose();
	}

	public Table(World world) {
		this(world, 1.27f, 2.54f, 0.116f, 0.1285f); //шар 5,7cm (163gr)
	}

	public Table(World world, boolean debug) {
		float w = 1.27f;
		float h = 2.54f;

		fullHeight = h + border + border;
		fullWidth = w + border + border;

		float[] vert1 = {border, border, border, h +border, w+border,h+border, w+border, border};
		buildBorder(world, vert1);
	}

	public float getFullWidth() {
		return fullWidth;
	}

	public float getFullHeight() {
		return fullHeight;
	}
}
