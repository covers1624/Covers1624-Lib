package covers1624.lib.client.model;

import covers1624.lib.api.texture.Icon;
import covers1624.lib.api.texture.provider.IBlockTextureProvider;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class BasicBlockModel implements IPerspectiveAwareModel {

	public static final Matrix4f thirdPersonTransform = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(3.3F, 1, -0.3F), new Vector3f(0F, 0.1F, -0.15F), new Vector3f(0.35F, 0.35F, 0.35F)));
	private static FaceBakery faceBakery = new FaceBakery();

	private TextureAtlasSprite particleTexture;
	private Icon[] icons = new Icon[6];

	public BasicBlockModel(Block block, int meta) {
		for (EnumFacing face : EnumFacing.VALUES) {
			icons[face.ordinal()] = ((IBlockTextureProvider) block).getIcon(meta, face);
		}
		particleTexture = (TextureAtlasSprite) icons[0].getSprite();

	}

	@Deprecated
	public BasicBlockModel(Icon[] icons, int particleIndex) {
		this.icons = icons;
		particleTexture = (TextureAtlasSprite) icons[particleIndex].getSprite();
	}

	@Override
	public List<BakedQuad> getFaceQuads(EnumFacing face) {
		return Collections.emptyList();
	}

	@Override
	public List<BakedQuad> getGeneralQuads() {
		ArrayList<BakedQuad> list = new ArrayList<BakedQuad>();
		BlockFaceUV uv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
		BlockPartFace face = new BlockPartFace(null, 0, "", uv);

		ModelRotation modelRot = ModelRotation.X0_Y0;
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, (TextureAtlasSprite) icons[0].getSprite(), EnumFacing.DOWN, modelRot, null, true, true));//down
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, (TextureAtlasSprite) icons[1].getSprite(), EnumFacing.UP, modelRot, null, true, true));//up
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, (TextureAtlasSprite) icons[2].getSprite(), EnumFacing.NORTH, modelRot, null, true, true));//north
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, (TextureAtlasSprite) icons[3].getSprite(), EnumFacing.SOUTH, modelRot, null, true, true));//south
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, (TextureAtlasSprite) icons[4].getSprite(), EnumFacing.WEST, modelRot, null, true, true));//west
		list.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, (TextureAtlasSprite) icons[5].getSprite(), EnumFacing.EAST, modelRot, null, true, true));//east

		return list;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return particleTexture;
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}

	//@Override
	public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON) {
			return Pair.of((IFlexibleBakedModel) this, thirdPersonTransform);
		}
		return Pair.of((IFlexibleBakedModel) this, null);
	}

	@Override
	public VertexFormat getFormat() {
		return Attributes.DEFAULT_BAKED_FORMAT;
	}
}
