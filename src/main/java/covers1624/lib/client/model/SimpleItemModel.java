package covers1624.lib.client.model;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;

/**
 * Created by covers1624 on 1/20/2016.
 */
@SuppressWarnings({ "deprecation", "ConstantConditions" })
public class SimpleItemModel extends ItemLayerModel{

	private ImmutableList<ResourceLocation> textures;

	public SimpleItemModel(ImmutableList<ResourceLocation> textures) {
		super(textures);
		this.textures = textures;
	}

	@Override
	public IFlexibleBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
		Optional<TRSRTransformation> transform = state.apply(Optional.<IModelPart>absent());
		for (int i = 0; i < textures.size(); i++) {
			builder.addAll(getQuadsForSprite(i, bakedTextureGetter.apply(textures.get(i)), format, transform));
		}
		TextureAtlasSprite particle = bakedTextureGetter.apply(textures.isEmpty() ? new ResourceLocation("missingno") : textures.get(0));
		ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = IPerspectiveAwareModel.MapWrapper.getTransforms(state);
		IFlexibleBakedModel model = new SimpleBakedItemModel(builder.build(), particle, format, transforms);
		if (transforms.isEmpty()){
			return model;
		}
		return new IPerspectiveAwareModel.MapWrapper(model, transforms);
	}
}
