package net.id107.flexfov.gui.advanced;

import net.id107.flexfov.ConfigManager;
import net.id107.flexfov.projection.Panini;
import net.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class PaniniGui extends AdvancedGui {
	
	public PaniniGui(Screen parent) {
		super(parent);
		Projection.setProjection(new Panini());
	}
	
	@Override
	protected void init() {
		super.init();
		
		addDrawableChild(new SliderWidget(width / 2 - 180, height / 6 + 60, 360, 20,
				Text.literal("FOV: " + (int)Projection.getProjection().getFovX()),
				Projection.getProjection().getFovX() / 360.0) {
			@Override
			protected void updateMessage() {
				this.setMessage(Text.literal("FOV: " + (int)(this.value * 360.0)));
			}

			@Override
			protected void applyValue() {
				Projection.fov = this.value * 360.0;
				ConfigManager.saveConfig();
			}
		});
	}
}
