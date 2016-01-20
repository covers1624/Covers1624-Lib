package covers1624.lib.client.model;

import covers1624.lib.api.texture.Icon;
import covers1624.lib.api.texture.provider.IBlockTextureProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ISmartBlockModel;
import org.apache.commons.lang3.tuple.Pair;

import javax.vecmath.Matrix4f;
import java.util.Collections;
import java.util.List;

/**
 * Created by covers1624 on 1/20/2016.
 */
public class SimpleSmartModel implements ISmartBlockModel, IPerspectiveAwareModel {

	private Icon[] icons = new Icon[6];
	private BasicBlockModel model = null;

	//For inventory setup.
	public void setModel(BasicBlockModel model){
		this.model = model;
	}

	@Override
	public IBakedModel handleBlockState(IBlockState state) {
		if (state.getBlock() instanceof IBlockTextureProvider) {
			IBlockTextureProvider textureProvider = (IBlockTextureProvider) state.getBlock();
			for (EnumFacing face : EnumFacing.VALUES) {
				icons[face.ordinal()] = textureProvider.getIcon(state, face);
			}
			model = new BasicBlockModel(icons);
		}
		return model;
	}

	@Override
	public List<BakedQuad> getFaceQuads(EnumFacing face) {
		if (model != null) {
			return model.getFaceQuads(face);
		}
		return Collections.emptyList();
	}

	@Override
	public List<BakedQuad> getGeneralQuads() {
		if (model != null) {
			return model.getGeneralQuads();
		}
		return Collections.emptyList();
	}

	@Override
	public boolean isAmbientOcclusion() {
		return model != null && model.isAmbientOcclusion();
	}

	@Override
	public boolean isGui3d() {
		return model != null && model.isGui3d();
	}

	@Override
	public boolean isBuiltInRenderer() {
		return model != null && model.isBuiltInRenderer();
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		if (model != null) {
			return model.getParticleTexture();
		}
		return null;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		if (model != null) {
			return model.getItemCameraTransforms();
		}
		return ItemCameraTransforms.DEFAULT;
	}

	@Override
	public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		if (model != null) {
			return model.handlePerspective(cameraTransformType);
		}
		if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON) {
			return Pair.of((IFlexibleBakedModel) this, BasicBlockModel.thirdPersonTransform);
		}
		return Pair.of((IFlexibleBakedModel) this, null);
	}

	@Override
	public VertexFormat getFormat() {
		if (model != null) {
			return model.getFormat();
		}
		return Attributes.DEFAULT_BAKED_FORMAT;
	}
}
