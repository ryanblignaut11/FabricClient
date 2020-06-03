package theSilverEcho.tweaks.cosmetic;

import net.minecraft.util.math.Vec3d;

public interface Rotator
{

	Vec3d getRotation();
	void setRotation(Vec3d rotation);
	void addRotation(Vec3d rotation);
	void addRotation(double x, double y, double z);
}
