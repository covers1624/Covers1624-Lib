package covers1624.lib.client.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import javax.vecmath.Matrix4f;

/**
 * Created by covers1624 on 1/20/2016.
 */
@SuppressWarnings("deprecation")
public class SimpleBakedItemModel extends ItemLayerModel.BakedModel implements IPerspectiveAwareModel{

	//private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
	private boolean isSword;

	public SimpleBakedItemModel(ImmutableList<BakedQuad> quads, TextureAtlasSprite particle, VertexFormat format, boolean isSword/*TODO better name.*/) {
		super(quads, particle, format);
		//this.transforms = transforms;
		this.isSword = isSword;
	}

	@Override
	public Pair<? extends IFlexibleBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
		if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON)
			return Pair.of(IFlexibleBakedModel.class.cast(this), isSword ? FIRST_PERSON_TEMP : FIRST_PERSON_FIX);

		if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON) {
			return Pair.of(IFlexibleBakedModel.class.cast(this), isSword ? THIRD_PERSON_TEMP : THIRD_PERSON_2D);
		}
		return Pair.of(IFlexibleBakedModel.class.cast(this), null);
	}

	public final Matrix4f THIRD_PERSON_2D = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(4.8F, 0, 0F), new Vector3f(0, .07F, -0.2F), new Vector3f(0.55F, 0.55F, 0.55F)));
	public final Matrix4f FIRST_PERSON_FIX = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0, 4, 0.5F), new Vector3f(-0.1F, 0.3F, 0.1F), new Vector3f(1.3F, 1.3F, 1.3F)));


	public final Matrix4f THIRD_PERSON_TEMP = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0F, 0F, 0F), new Vector3f(0F, 0F, 0F), new Vector3f(0F, 0F, 0F)));
	public final Matrix4f FIRST_PERSON_TEMP = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0F, 0F, 0F), new Vector3f(0F, 0F, 0F), new Vector3f(0F, 0F, 0F)));

	public final Matrix4f THIRD_PERSON_SWORD = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0F, 90F, -90F), new Vector3f(0, -0.0625F, 0F), new Vector3f(0.85F, 0.85F, 0.85F)));
	public final Matrix4f FIRST_PERSON_SWORD = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0, 4, 0.5F), new Vector3f(-0.1F, 0.3F, 0.1F), new Vector3f(1.7F, 1.7F, 1.7F)));
}
