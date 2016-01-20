package covers1624.lib.client.model;

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
import java.util.HashMap;
import java.util.List;

/**
 * Created by covers1624 on 1/19/2016.
 */
@Deprecated
public class SimpleBlockModel implements IFlexibleBakedModel, IPerspectiveAwareModel {

	public static final Matrix4f thirdPersonnTransform = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(3.3F, 1, -0.3F), new Vector3f(0F, 0.1F, -0.15F), new Vector3f(0.35F, 0.35F, 0.35F)));
	private static FaceBakery faceBakery = new FaceBakery();

	private HashMap<EnumFacing, TextureAtlasSprite> faceSpriteMap = new HashMap<EnumFacing, TextureAtlasSprite>();

	public SimpleBlockModel(HashMap<EnumFacing, TextureAtlasSprite> faceSpriteMap) {
		this.faceSpriteMap = faceSpriteMap;
	}

	@Override
	public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON) {
			return Pair.of((IFlexibleBakedModel) this, thirdPersonnTransform);
		}
		return Pair.of((IFlexibleBakedModel) this, null);
	}

	@Override
	public VertexFormat getFormat() {
		return Attributes.DEFAULT_BAKED_FORMAT;
	}

	@Override
	public List<BakedQuad> getFaceQuads(EnumFacing p_177551_1_) {
		return Collections.emptyList();
	}

	@Override
	public List<BakedQuad> getGeneralQuads() {
		ArrayList<BakedQuad> list = new ArrayList<BakedQuad>();
		BlockFaceUV uv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
		BlockPartFace face = new BlockPartFace(null, 0, "", uv);

		ModelRotation modelRot = ModelRotation.X0_Y0;
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, faceSpriteMap.get(EnumFacing.DOWN), EnumFacing.DOWN, modelRot, null, true, true));//down
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, faceSpriteMap.get(EnumFacing.UP), EnumFacing.UP, modelRot, null, true, true));//up
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, faceSpriteMap.get(EnumFacing.NORTH), EnumFacing.NORTH, modelRot, null, true, true));//north
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, faceSpriteMap.get(EnumFacing.SOUTH), EnumFacing.SOUTH, modelRot, null, true, true));//south
		list.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, faceSpriteMap.get(EnumFacing.EAST), EnumFacing.EAST, modelRot, null, true, true));//east
		list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, faceSpriteMap.get(EnumFacing.WEST), EnumFacing.WEST, modelRot, null, true, true));//west

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
		return faceSpriteMap.get(EnumFacing.DOWN);
	}

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return ItemCameraTransforms.DEFAULT;
	}
}
