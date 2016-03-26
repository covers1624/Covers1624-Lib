package covers1624.lib.api.texture;

/**
 * Created by covers1624 on 1/22/2016.
 */
public interface ITextureRegistry {

    Icon registerIcon(String location);

    Icon registerIcon(String prefix, String location);

}
