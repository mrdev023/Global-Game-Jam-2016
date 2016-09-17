package Items;

import Entity.*;

public class PowerItems extends Items{

	public PowerItems(double x, double y) {
		super(x, y, "res/textures/power.png");
	}

	public void action(Player player) {
		player.setPower(player.getPower() + 50.0f);
	}

}
