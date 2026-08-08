package net.id107.flexfov.gui.advanced;

import net.id107.flexfov.ConfigManager;
import net.id107.flexfov.projection.Cylinder;
import net.id107.flexfov.projection.Projection;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class CylinderGui extends AdvancedGui {

	public CylinderGui(Screen parent) {
		super(parent);
		Projection.setProjection(new Cylinder());
	}
	
	@Override
	protected void init() {
		super.init();
		
		addDrawableChild(new SliderWidget(width / 2 - 180, height / 6 + 60, 360, 20,
				Text.literal("Horizontal FOV: " + Math.round(Projection.getProjection().getFovX())),
				Projection.getProjection().getFovX() / 360.0) {
			@Override
			protected void updateMessage() {
				this.setMessage(Text.literal("Horizontal FOV: " + Math.round(this.value * 360.0)));
			}

			@Override
			protected void applyValue() {
				Projection.fov = this.value * 360.0;
				ConfigManager.saveConfig();
			}
		});
		
		addDrawableChild(new SliderWidget(width / 2 - 180, height / 6 + 84, 180, 20,
				Text.literal("Vertical FOV: " + Math.round(Projection.getProjection().getFovY())),
				Projection.getProjection().getFovY() / 180.0) {
			@Override
			protected void updateMessage() {
				this.setMessage(Text.literal("Vertical FOV: " + Math.round(this.value * 180.0)));
			}

			@Override
			protected void applyValue() {
				Cylinder.fovy = this.value * 180.0;
				ConfigManager.saveConfig();
			}
		});
	}
}
