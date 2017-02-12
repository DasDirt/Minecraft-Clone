package de.theredcraft.cavegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;


public class CaveGame extends ApplicationAdapter {
	
	public final float field_of_view = 67;
	public final float camera_near = 1;
	public final float camera_far = 300;
	public final float camera_verlocity = 15;
	public final float camera_degress_per_pixel = 0.08f;
	public final float crosshair_size = 25;
	
	
	public Texture crosshair;
	
	
	public Environment envioment;
	public PerspectiveCamera camera;
	public SpriteBatch spride_batch;
	public ModelBatch model_batch;
	public FPSControll camera_controller;
	public Grid grid;
	
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		envioment = new Environment();
		envioment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f ));
		envioment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.5f));
		envioment.add(new DirectionalLight().set(0.2f, 0.2f, 0.2f, -1f, -0.8f, -0.5f));
		
		
		spride_batch = new SpriteBatch();
		model_batch = new ModelBatch();
		
		
		camera = new PerspectiveCamera(field_of_view, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(0, 10f, 10f);
		camera.near = camera_near;
		camera.far = camera_far;
		
		camera_controller = new FPSControll(camera){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(button == 0){
					grid.exitBoxByRayCast(camera.position, camera.direction, Block.Type.Dirtblock);
				}else if (button == 1){
					grid.exitBoxByRayCast(camera.position, camera.direction, null);
				}
				return super.touchDown(screenX, screenY, pointer, button);
			}
		};
		camera_controller.setDegreesPerPixel(camera_degress_per_pixel);
		camera_controller.setVelocity(camera_verlocity);
		
		
		crosshair = new Texture(Gdx.files.internal("interface/Crosshair.png"));
	
		grid = new Grid();
		
		
		
		Gdx.input.setInputProcessor(camera_controller);
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render () {
		camera_controller.update();
		Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		model_batch.begin(camera);
		grid.renderGrid(model_batch, envioment);
		model_batch.end();
		
		float crosshair_x = (Gdx.graphics.getWidth() - crosshair_size) / 2;
		float crosshair_y = (Gdx.graphics.getHeight() - crosshair_size) / 2;
		
		spride_batch.begin();
		spride_batch.draw(crosshair, crosshair_x, crosshair_y, crosshair_size, crosshair_size);
		spride_batch.end();
		
	}
	
	@Override
	public void dispose () {
		grid.dispose();
		crosshair.dispose();
		

	}
}
