package covers1624.lib.client.model;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import covers1624.lib.api.texture.provider.IBlockTextureProvider;
import covers1624.lib.api.texture.provider.IFluidTextureProvider;
import covers1624.lib.api.texture.provider.IItemTextureProvider;
import covers1624.lib.api.texture.provider.ITextureProvider;
import covers1624.lib.client.icon.ItemIcon;
import covers1624.lib.client.registry.TextureRegistry;
import covers1624.lib.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ModelFluid;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by covers1624 on 1/19/2016.
 */
public class ModelGenerator {

	public static ArrayList<ITextureProvider> textureProviders = new ArrayList<ITextureProvider>();

	public static ArrayList<Block> blocks = new ArrayList<Block>();
	public static ArrayList<Item> items = new ArrayList<Item>();
	public static ArrayList<BlockFluidClassic> fluids = new ArrayList<BlockFluidClassic>();

	public static HashMap<BlockFluidClassic, TextureAtlasSprite> fluidSpriteMap = new HashMap<BlockFluidClassic, TextureAtlasSprite>();
	public static ArrayList<ItemIcon> itemIconList = new ArrayList<ItemIcon>();

	@SubscribeEvent
	public void textureStitchEvent(TextureStitchEvent.Pre event) {
		TextureMap textureMap = event.map;
		TextureRegistry textureRegistry = new TextureRegistry(event.map);
		clear();

		for (ITextureProvider provider : textureProviders) {
			provider.registerIcons(textureRegistry);
		}
		//Items
		for (Item item : items) {
			if (item instanceof IItemTextureProvider) {
				IItemTextureProvider itemTexture = (IItemTextureProvider) item;
				for (int i = 0; i < itemTexture.getMaxMeta(); i++) {
					ResourceLocation location = itemTexture.getTextureName(i);
					TextureAtlasSprite texture = textureMap.getTextureExtry(location.toString());
					if (texture == null) {
						texture = new ExtendedTextureAtlasSprite(location.toString());
						textureMap.setTextureEntry(location.toString(), texture);
					}
					ItemIcon icon = new ItemIcon(item, i, texture, location);
					itemIconList.add(icon);
				}
			}
		}
		//Fluids
		for (BlockFluidClassic fluid : fluids) {
			if (fluid instanceof IFluidTextureProvider) {
				IFluidTextureProvider fluidTexture = (IFluidTextureProvider) fluid;
				ResourceLocation location = fluidTexture.getTextureName();
				TextureAtlasSprite texture = textureMap.getTextureExtry(location.toString());
				if (texture == null) {
					texture = new ExtendedTextureAtlasSprite(location.toString());
					textureMap.setTextureEntry(location.toString(), texture);
				}
				fluidSpriteMap.put(fluid, texture);
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	//@SuppressWarnings({"deprecation" })
	public void modelBakeEvent(ModelBakeEvent event) {
		long start = System.currentTimeMillis();
		LogHelper.trace("Generating Models...");
		ItemModelMesher itemModelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();
		LogHelper.trace("Generating Block Models...");
		for (Block block : blocks) {
			LogHelper.trace("Generating Model for Object: " + Block.blockRegistry.getNameForObject(block));
			if (block instanceof IBlockTextureProvider) {
				IBlockTextureProvider textureProvider = (IBlockTextureProvider) block;
				ArrayList<ItemStack> tempStacks = new ArrayList<ItemStack>();
				block.getSubBlocks(Item.getItemFromBlock(block), block.getCreativeTabToDisplayOn(), tempStacks);
				for (int i = 0; i < tempStacks.size(); i++) {
					SimpleSmartModel blockModel = new SimpleSmartModel();
					blockModel.handleBlockState(block.getDefaultState());
					ModelResourceLocation modelLocation = getModelResourceLocation(block.getStateFromMeta(i));
					event.modelRegistry.putObject(modelLocation, blockModel);

					ModelResourceLocation inventoryModelLocation = getBlockInventoryResourceLocation(block);
					event.modelRegistry.putObject(inventoryModelLocation, blockModel);

					itemModelMesher.register(Item.getItemFromBlock(block), i, inventoryModelLocation);
					itemModelMesher.register(Item.getItemFromBlock(block), i, modelLocation);
				}
			} else {
				LogHelper.error("Block object %s is not an instance of IBlockTextureProvider. This object must have been injected using the wrong channel! \n Use: covers1624.client.registry.TextureRegistry.getProxy().<Register Method>", Block.blockRegistry.getNameForObject(block));
			}
		}
		LogHelper.info("Generating Item Models...");
		for (Item item : items) {
			if (item instanceof IItemTextureProvider) {
				LogHelper.trace("Generating Model for Object: " + Item.itemRegistry.getNameForObject(item));
				IItemTextureProvider textureProvider = (IItemTextureProvider) item;
				for (int i = 0; i < textureProvider.getMaxMeta(); i++) {
					TextureAtlasSprite texture = null;
					ItemIcon itemIcon = null;
					for (ItemIcon icon : itemIconList) {
						if (icon.getMeta() == i && icon.getItem() == item) {
							texture = icon.getSprite();
							itemIcon = icon;
							break;
						}
					}
					if (texture == null) {
						break;
					}
					ModelResourceLocation inventoryLocation;
					if (textureProvider.getMaxMeta() == 1) {
						inventoryLocation = getItemInventoryResourceLocation(item);
					} else {
						inventoryLocation = new ModelResourceLocation(textureProvider.getModID() + ":" + item.getUnlocalizedName(new ItemStack(item, 1, i)).substring(5), "inventory");
					}
					final TextureAtlasSprite finalSprite = texture;
					Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
						@Nullable
						@Override
						public TextureAtlasSprite apply(ResourceLocation input) {
							return finalSprite;
						}
					};
					ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();
					builder.add(itemIcon.getLocation());
					SimpleItemModel itemModel = new SimpleItemModel(builder.build());
					IBakedModel model = itemModel.bake(ItemLayerModel.instance.getDefaultState(), DefaultVertexFormats.ITEM, textureGetter);
					event.modelRegistry.putObject(inventoryLocation, model);
					itemModelMesher.register(item, i, inventoryLocation);
				}
			} else {
				LogHelper.error("Item object %s is not an instance of IITemTextureProvider. This object must have been injected using the wrong channel! \n Use: covers1624.client.registry.TextureRegistry.getProxy().<Register Method>", Item.itemRegistry.getNameForObject(item));
			}
		}
		LogHelper.info("Generating Fluid Models...");
		for (final BlockFluidClassic fluid : fluids) {
			LogHelper.trace("Generating Model for Object: " + Block.blockRegistry.getNameForObject(fluid));
			final ModelResourceLocation fluidLocation = new ModelResourceLocation(fluid.getFluid().getFlowing(), "fluid");
			Item fluidItem = Item.getItemFromBlock(fluid);
			ModelBakery.addVariantName(fluidItem);
			ModelLoader.setCustomMeshDefinition(fluidItem, new ItemMeshDefinition() {
				@Override
				public ModelResourceLocation getModelLocation(ItemStack stack) {
					return fluidLocation;
				}
			});
			ModelLoader.setCustomStateMapper(fluid, new StateMapperBase() {
				@Override
				protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
					return fluidLocation;
				}
			});

			for (int i = 0; i < 16; i++) {
				ModelResourceLocation flowStateLocation = new ModelResourceLocation(getBlockResourceLocation(fluid), "level=" + i);
				ModelFluid modelFluid = new ModelFluid(fluid.getFluid());
				Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
					@Nullable
					@Override
					public TextureAtlasSprite apply(ResourceLocation input) {
						return fluidSpriteMap.get(fluid);
					}
				};
				IFlexibleBakedModel bakedModel = modelFluid.bake(modelFluid.getDefaultState(), DefaultVertexFormats.BLOCK, textureGetter);
				event.modelRegistry.putObject(flowStateLocation, bakedModel);
			}
			ModelResourceLocation inventoryModelLocation = new ModelResourceLocation(getBlockResourceLocation(fluid), "inventory");
			ModelFluid modelFluid = new ModelFluid(fluid.getFluid());
			Function<ResourceLocation, TextureAtlasSprite> textureGetter = new Function<ResourceLocation, TextureAtlasSprite>() {
				@Nullable
				@Override
				public TextureAtlasSprite apply(ResourceLocation input) {
					return fluidSpriteMap.get(fluid);
				}
			};
			IFlexibleBakedModel bakedModel = modelFluid.bake(modelFluid.getDefaultState(), DefaultVertexFormats.ITEM, textureGetter);
			event.modelRegistry.putObject(inventoryModelLocation, bakedModel);
		}
		LogHelper.info("Finished Model Generation in %s Ms.", String.valueOf(System.currentTimeMillis() - start));
	}

	private static void clear() {
		//blockSpriteMap.clear();
		fluidSpriteMap.clear();

		//blockIconList.clear();
		itemIconList.clear();
	}

	@SideOnly(Side.CLIENT)
	public static ModelResourceLocation getModelResourceLocation(IBlockState state) {
		return new ModelResourceLocation(Block.blockRegistry.getNameForObject(state.getBlock()), (new DefaultStateMapper()).getPropertyString(state.getProperties()));
	}

	@SideOnly(Side.CLIENT)
	public static ModelResourceLocation getBlockInventoryResourceLocation(Block block) {
		return new ModelResourceLocation(Block.blockRegistry.getNameForObject(block), "inventory");
	}

	@SideOnly(Side.CLIENT)
	public static ModelResourceLocation getItemInventoryResourceLocation(Item block) {
		return new ModelResourceLocation(Item.itemRegistry.getNameForObject(block), "inventory");
	}

	@SideOnly(Side.CLIENT)
	public static ResourceLocation getBlockResourceLocation(Block block) {
		return Block.blockRegistry.getNameForObject(block);
	}

}