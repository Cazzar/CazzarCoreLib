package net.cazzar.corelib.asm;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;
import net.cazzar.corelib.lib.LogHelper;

import java.io.IOException;

/**
 * @Author: Cayde
 */
public class CoreLibAccessTransformer extends AccessTransformer {

    public CoreLibAccessTransformer() throws IOException {
        super("cazzarcorelib_at.cfg");
        LogHelper.corelog.info("Adding cazzarcorelib_at.cfg to the access transformers");
    }
}
