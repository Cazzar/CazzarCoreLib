package net.cazzar.corelib.asm;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

/**
 * The main access transformer for CCL
 */
public class CoreAccessTransformer extends AccessTransformer {
    public CoreAccessTransformer() throws IOException {
        super("cazzarcore_at.cfg");
    }
}
