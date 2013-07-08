/**
 * 
 */
package net.cazzar.corelib;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

/**
 * @author Cayde
 *
 */
@IFMLLoadingPlugin.MCVersion("1.6.1")
public class CoreMod implements IFMLLoadingPlugin, IFMLCallHook {
	@Override
	public String[] getLibraryRequestClass() {
		return null;
	}

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {
                "net.cazzar.corelib.asm.CoreLibAccessTransformer"
        };
	}

	@Override
	public String getModContainerClass() {
		return "net.cazzar.corelib.ModContainer";
	}

	@Override
	public String getSetupClass() {
		return this.getClass().getCanonicalName();
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

    @Override
    public Void call() throws Exception {
        return null;
    }
}
