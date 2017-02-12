package de.theredcraft.cavegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class DirtBlock extends Block{

	public DirtBlock() {
		super(new Texture(Gdx.files.internal("texture/Dirt.png")), Type.Dirtblock);
	}

}
