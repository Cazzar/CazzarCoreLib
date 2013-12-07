/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
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
import java.util.logging.Level;

public final class McpMappings {
    private static McpMappings instance;
    private final Map<String, String> packageMap = Maps.newHashMap();
    private final Map<String, String> classMap = Maps.newHashMap();
    private final Map<FieldDescription, FieldDescription> fieldMap = Maps.newHashMap();
    private final Map<String, MethodDescription> methodMap = Maps.newHashMap();

    private McpMappings() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new LzmaInputStream(McpMappings.class.getResourceAsStream(CoreMod.getDeobfuscationFileName()))
            ));
            while (reader.ready()) {
                String[] fields = reader.readLine().split(" ");

                if ("PK:".equals(fields[0])) {
                    packageMap.put(fields[2], fields[1]);
                } else if ("CL:".equals(fields[0])) {
                    classMap.put(fields[2], fields[1]);
                } else if ("FD:".equals(fields[0])) {
                    String[] obfPart = fields[1].split("/");
                    String obfName = CommonUtil.arrayPopLast(obfPart);

                    String[] srgPart = fields[2].split("/");
                    String srgName = CommonUtil.arrayPopLast(srgPart);

                    fieldMap.put(new FieldDescription(CommonUtil.join("/", srgPart), srgName),
                            new FieldDescription(CommonUtil.join("/", obfPart), obfName));
                } else if ("MD:".equals(fields[0])) {
                    String[] obfParts = fields[1].split("/");
                    String[] srgParts = fields[3].split("/");

                    methodMap.put(srgParts[srgParts.length - 1],
                            new MethodDescription(obfParts[obfParts.length - 1], fields[2]));
                }
            }
        } catch (IOException e) {
            LogHelper.coreLog.log(Level.WARNING, e, "Unable to read obfuscation data successfully.");
        }
    }

    public static synchronized McpMappings instance() {
        if (instance == null) {
            instance = new McpMappings();
        }
        return instance;
    }

    public String getObfuscatedClassName(String name) {
        return classMap.get(name);
    }

    public MethodDescription getMethod(String srgMethod) {
        return methodMap.get(srgMethod);
    }

    public FieldDescription getField(String srgName) {
        String[] parts = srgName.split(".");
        String last = CommonUtil.arrayPopLast(parts);

        FieldDescription description = new FieldDescription(CommonUtil.join("/", parts), last);

        return fieldMap.get(description);
    }
}