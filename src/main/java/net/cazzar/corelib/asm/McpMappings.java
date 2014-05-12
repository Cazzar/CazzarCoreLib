/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.cazzar.corelib.asm;

import LZMA.LzmaInputStream;
import com.google.common.collect.Maps;
import net.cazzar.corelib.CoreMod;
import net.cazzar.corelib.lib.LogHelper;
import net.cazzar.corelib.util.CommonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public final class McpMappings {
    private static McpMappings instance;
    private final Map<String, String> classMap = Maps.newHashMap();
    private final Map<FieldDescription, FieldDescription> fieldMap = Maps.newHashMap();
    private final Map<String, MethodDescription> methodMap = Maps.newHashMap();
    @SuppressWarnings("UnusedDeclaration")
    private final Map<FieldDescription, FieldDescription> deobfFieldMap = Maps.newHashMap();
    @SuppressWarnings("UnusedDeclaration")
    private final Map<String, MethodDescription> deobfMethodMap = Maps.newHashMap();


    private McpMappings() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new LzmaInputStream(McpMappings.class.getResourceAsStream(CoreMod.getDeobfuscationFileName()))
            ));
            while (reader.ready()) {
                String[] fields = reader.readLine().split(" ");

                if ("PK:".equals(fields[0])) {
                    Map<String, String> packageMap = Maps.newHashMap();
                    packageMap.put(fields[2], fields[1]);
                } else if ("CL:".equals(fields[0])) {
                    classMap.put(fields[2], fields[1]);
                } else if ("FD:".equals(fields[0])) {
                    String[] obfPart = fields[1].split("/");
                    String obfName = CommonUtil.arrayPopLast(obfPart);

                    String[] srgPart = fields[2].split("/");
                    String srgName = CommonUtil.arrayPopLast(srgPart);

                    fieldMap.put(new FieldDescription(CommonUtil.join("/", srgPart), srgName), new FieldDescription(CommonUtil.join("/", obfPart), obfName));
                } else if ("MD:".equals(fields[0])) {
                    String[] obfParts = fields[1].split("/");
                    String[] srgParts = fields[3].split("/");

                    methodMap.put(srgParts[srgParts.length - 1], new MethodDescription(obfParts[obfParts.length - 1], fields[2]));
                }
            }
             /*
            if (getClass().getResource("mcp-srg.srg") != null) {
                reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("mcp-srg.srg")));
                while (reader.ready()) {
                    String[] fields = reader.readLine().split(" ");

                    if ("FD:".equals(fields[0])) {
                        String[] srgPart = fields[1].split("/");
                        String srgName = CommonUtil.arrayPopLast(srgPart);

                        String[] obfPart = fields[2].split("/");
                        String obfName = CommonUtil.arrayPopLast(obfPart);

                        deobfFieldMap.put(new FieldDescription(CommonUtil.join("/", srgPart), srgName), new FieldDescription(CommonUtil.join("/", obfPart), obfName));
                    } else if ("MD:".equals(fields[0])) {
                        String[] srgParts = fields[1].split("/");
                        String[] obfParts = fields[3].split("/");

                        deobfMethodMap.put(srgParts[srgParts.length - 1], new MethodDescription(obfParts[obfParts.length - 1], fields[2]));
                    }
                }
            }
            */

        } catch (IOException e) {
//            LogHelper.coreLog.catching(e);
            LogHelper.coreLog.warn("Unable to read obfuscation data successfully.", e);
        }
    }

    /**
     * Get the instance of McpMappings
     *
     * @return the instance singleton
     */
    public static synchronized McpMappings instance() {
        if (instance == null) {
            instance = new McpMappings();
        }
        return instance;
    }

    /**
     * Get the actually obf name of the client
     *
     * @param name get the class name in the obf mappings
     *
     * @return the obf class name
     */
    @SuppressWarnings("UnusedDeclaration")
    public String getObfuscatedClassName(String name) {
        return classMap.get(name);
    }

    /**
     * Get a Method information of the passed SRG name
     *
     * @param srgMethod the srgName of the method
     *
     * @return the MethodDescription of the obfuscated method
     */
    public MethodDescription getMethod(String srgMethod) {
        return methodMap.get(srgMethod);
    }

    /**
     * Get the field information for the obfuscated name description
     *
     * @param srgName the SRG name
     *
     * @return the field description of the field
     */
    @SuppressWarnings("UnusedDeclaration")
    public FieldDescription getField(String srgName) {
        String[] parts = srgName.split(".");
        String last = CommonUtil.arrayPopLast(parts);

        FieldDescription description = new FieldDescription(CommonUtil.join("/", parts), last);

        return fieldMap.get(description);
    }
}