package Items;

import Entity.*;

public class SpellItems extends Items{

	public SpellItems(double x, double y) {
		super(x, y, "res/textures/spell drop.png");
	}

	public void action(Player player) {
		player.setSpell(player.getSpell() + 50);
	}

}
