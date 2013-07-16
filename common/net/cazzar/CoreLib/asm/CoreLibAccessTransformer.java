package net.cazzar.corelib.asm;

import net.cazzar.corelib.lib.LogHelper;

import java.io.IOException;

/**
 * @Author: Cayde
 */
public class CoreLibAccessTransformer extends SrgAccessTransformer {

    public CoreLibAccessTransformer() throws IOException {
        super("cazzarcorelib_at.cfg");
        LogHelper.coreLog.info("Adding cazzarcorelib_at.cfg to the access transformers");
    }
}
