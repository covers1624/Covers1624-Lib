package covers1624.lib.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

/**
 * Created by covers1624 on 1/15/2016.
 */
public class ChestGenHelper {

	public static void addToMineshaftCorridorLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToPyramidDesertChestLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToPyramidJungleChestLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToPyramidJungleDispenserLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToStrongholdCorridorLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToStrongholdLibraryLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToStrongholdCrossingLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToVillageBlacksmithLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToBonusChestLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}

	public static void addToDungenChestLoot(ItemStack stack, int minAmount, int maxAmount, int weight) {
		ChestGenHooks hook = ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST);
		hook.addItem(new WeightedRandomChestContent(stack, minAmount, maxAmount, weight));
	}
}
