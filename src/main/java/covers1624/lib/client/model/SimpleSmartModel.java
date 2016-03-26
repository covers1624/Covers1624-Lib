package covers1624.lib.client.model;

import covers1624.lib.api.texture.Icon;
import covers1624.lib.api.texture.provider.IBlockTextureProvider;
import covers1624.lib.util.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.util.vector.Vector3f;

import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by covers1624 on 1/20/2016.
 */
public class SimpleSmartModel implements IPerspectiveAwareModel {

    public static final Matrix4f thirdPersonTransform = ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(3.3F, 1, -0.3F), new Vector3f(0F, 0.1F, -0.15F), new Vector3f(0.35F, 0.35F, 0.35F)));
    private static FaceBakery faceBakery = new FaceBakery();

    private TextureAtlasSprite particleTexture;

    private Icon[] icons = new Icon[6];

    public SimpleSmartModel() {

    }

    public SimpleSmartModel(Icon[] icons) {
        this.icons = icons;
        particleTexture = (TextureAtlasSprite) icons[0].getSprite();
    }

    public void inventorySetup(IBlockState state) {
        if (state.getBlock() instanceof IBlockTextureProvider) {
            IBlockTextureProvider provider = (IBlockTextureProvider) state.getBlock();
            for (EnumFacing face : EnumFacing.VALUES) {
                icons[face.ordinal()] = provider.getIcon(state.getBlock().getMetaFromState(state), face);
            }
            particleTexture = (TextureAtlasSprite) icons[0].getSprite();
        }
    }

    //@Override
    public IBakedModel handleBlockState(IBlockState state) {
        LogHelper.info("Called handleBlockState.");
        if (state.getBlock() instanceof IBlockTextureProvider) {
            IBlockTextureProvider textureProvider = (IBlockTextureProvider) state.getBlock();
            for (EnumFacing face : EnumFacing.VALUES) {
                icons[face.ordinal()] = textureProvider.getIcon(state, face);
            }
            particleTexture = (TextureAtlasSprite) icons[0].getSprite();
            return new SimpleSmartModel(icons);
        }
        return null;
    }

    //@Override
    public List<BakedQuad> getFaceQuads(EnumFacing face) {
        return Collections.emptyList();
    }

    //@Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
        ArrayList<BakedQuad> list = new ArrayList<BakedQuad>();
        BlockFaceUV uv = new BlockFaceUV(new float[] { 0.0F, 0.0F, 16.0F, 16.0F }, 0);
        BlockPartFace face = new BlockPartFace(null, 0, "", uv);

        ModelRotation modelRot = ModelRotation.X0_Y0;
        list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 0.0F, 16.0F), face, (TextureAtlasSprite) icons[EnumFacing.DOWN.getIndex()].getSprite(), EnumFacing.DOWN, modelRot, null, true, true));//down
        list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 16.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, (TextureAtlasSprite) icons[EnumFacing.UP.getIndex()].getSprite(), EnumFacing.UP, modelRot, null, true, true));//up
        list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 0.0F), face, (TextureAtlasSprite) icons[EnumFacing.NORTH.getIndex()].getSprite(), EnumFacing.NORTH, modelRot, null, true, true));//north
        list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 16.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, (TextureAtlasSprite) icons[EnumFacing.SOUTH.getIndex()].getSprite(), EnumFacing.SOUTH, modelRot, null, true, true));//south
        list.add(faceBakery.makeBakedQuad(new Vector3f(16.0F, 0.0F, 0.0F), new Vector3f(16.0F, 16.0F, 16.0F), face, (TextureAtlasSprite) icons[EnumFacing.EAST.getIndex()].getSprite(), EnumFacing.EAST, modelRot, null, true, true));//east
        list.add(faceBakery.makeBakedQuad(new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 16.0F, 16.0F), face, (TextureAtlasSprite) icons[EnumFacing.WEST.getIndex()].getSprite(), EnumFacing.WEST, modelRot, null, true, true));//west

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

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || cameraTransformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND) {
            return Pair.of(IBakedModel.class.cast(this), ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(3.3F, 1, -0.3F), new Vector3f(0F, 0.1F, -0.15F), new Vector3f(0.35F, 0.35F, 0.35F))));
        }

        if (cameraTransformType == ItemCameraTransforms.TransformType.GUI) {
            return Pair.of(IBakedModel.class.cast(this), ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(3.5F, 1F, 2.65F), new Vector3f(0F, 0.0F, 0.0F), new Vector3f(0.63F, 0.63F, 0.63F))));
        }

        if (cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || cameraTransformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND) {
            return Pair.of(IBakedModel.class.cast(this), ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0F, 0, 0F), new Vector3f(0F, 0.0F, 0F), new Vector3f(0.35F, 0.35F, 0.35F))));
        }

        if (cameraTransformType == ItemCameraTransforms.TransformType.GROUND) {
            return Pair.of(IBakedModel.class.cast(this), ForgeHooksClient.getMatrix(new ItemTransformVec3f(new Vector3f(0F, 0, 0F), new Vector3f(0F, 0.0F, 0F), new Vector3f(0.25F, 0.25F, 0.25F))));
        }

        return Pair.of(IBakedModel.class.cast(this), null);
    }

    //@Override
    public VertexFormat getFormat() {
        return Attributes.DEFAULT_BAKED_FORMAT;
    }
}
