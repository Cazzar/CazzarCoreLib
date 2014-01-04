package net.cazzar.corelib.asm;

import cpw.mods.fml.common.asm.transformers.AccessTransformer;

import java.io.IOException;

/**
 * @Author: Cayde
 */
public class CoreAccessTransformer extends AccessTransformer{
    public CoreAccessTransformer() throws IOException {
        super("cazzarcore_at.cfg");
    }
}
