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

package net.cazzar.corelib.configuration;

import net.cazzar.corelib.configuration.annotations.ConfigurationClass;
import net.cazzar.corelib.configuration.annotations.ConfigurationComment;
import net.cazzar.corelib.configuration.annotations.ConfigurationOption;
import net.cazzar.corelib.lib.LogHelper;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

@SuppressWarnings("UnusedDeclaration")
public class Config {
    static Marker marker = MarkerManager.getMarker("ConfigManager");
    public final Configuration config;
    public Object clazz;

    public Config(Configuration config) {
        this.config = config;
    }

    public Config(File configFile) {
        this(new Configuration(configFile));
    }

    static boolean isParseable(@NotNull Class<?> type) {
        if (type.isPrimitive() || type == String.class || type == Boolean.class || type == Double.class || type == Integer.class || type == Float.class)
            return true;
        if (type.isArray()) {
            Class<?> type1 = type.getComponentType();
            if (type1.isPrimitive() || type1 == String.class || type1 == Boolean.class || type1 == Double.class || type1 == Integer.class || type1 == Float.class)
                return true;
        }
        return false;
    }

    public Config setInstance(Object clazz) {
        this.clazz = clazz;
        return this;
    }

    public void build() throws IllegalAccessException {
        parseFieldsInClass(clazz);
    }

    private void parseFieldsInClass(@NotNull Object o) throws IllegalAccessException {
        LogHelper.coreLog.info(marker, "Parsing Object {}", o);
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            LogHelper.coreLog.debug(marker, "Parsing field {}.{} of type {}", field.getDeclaringClass().getName(), field.getName(), field.getType());

            if (field.getAnnotation(Config.Exclude.class) != null) {
                LogHelper.coreLog.debug(marker, "Skipping...");
                continue;
            }

            if (isParseable(field.getType())) parseField(field, o);
            else if (!field.isSynthetic()) parseFieldsInClass(field.get(o));
        }
    }

    public void parseField(@NotNull Field field, @NotNull Object instance) throws IllegalAccessException {
        ConfigurationComment configComment = field.getAnnotation(ConfigurationComment.class);
        String comment = "", category, key;
        if (configComment != null) comment = configComment.value();

        ConfigurationOption configurationOption = field.getAnnotation(ConfigurationOption.class);
        if (configurationOption != null) {
            category = configurationOption.category();
            key = configurationOption.key();
            if (key.isEmpty()) key = field.getName();
            if (comment.isEmpty() && (configurationOption.comment() != null || !configurationOption.comment().isEmpty()))
                comment = configurationOption.comment();
        } else {
            key = field.getName();
            category = field.getDeclaringClass().getSimpleName();
        }

        parseField(field, instance, category, key, comment);
    }

    /**
     * Parse the passed class for annotated fields with the <i>@ConfigurationOption</i> or a class with
     * <i>@ConfigurationClass</i>
     *
     * @param instance the instance of the configurations option if it is non static
     */
    private void parse(@NotNull Object instance) {
        Class clazz = instance.getClass();
        if (clazz.isAnnotationPresent(ConfigurationClass.class)) {
            parseClass(instance, config);
            return;
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAccessible()) field.setAccessible(true);

            ConfigurationOption annotation = field.getAnnotation(ConfigurationOption.class);
            if (annotation == null) {
                continue;
            }

            String category = annotation.category();
            String comment = annotation.comment();
            String key = annotation.key();

            try {
                parseField(field, instance, category, key, comment);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    private void parseField(@NotNull Field field, @NotNull Object instance, @NotNull String category, @NotNull String key, @Nullable String comment) throws IllegalArgumentException, IllegalAccessException {
        Class<?> type = field.getType();
        //null check or if it is empty
        if (comment == null || comment.isEmpty()) comment = null;

        if (type == boolean.class || type == Boolean.class) {
            //boolean or Boolean
            Object def = field.get(instance);
            Object value = config.get(category, key, (Boolean) def, comment).getBoolean((Boolean) def);

            field.set(instance, value);
        } else if (type.isArray() && (type.getComponentType() == boolean.class)) {
            //boolean[]
            boolean[] def = ((boolean[]) field.get(instance));
            boolean[] value = config.get(category, key, def, comment).getBooleanList();

            field.set(instance, value);
        } else if (type == double.class || type == Double.class) {
            //Double or double
            Object def = field.get(instance);
            Object value = config.get(category, key, (Double) def, comment).getDouble((Double) def);

            field.set(instance, value);
        } else if (type.isArray() && type.getComponentType() == double.class) {
            //double[]
            double[] def = ((double[]) field.get(instance));
            double[] value = config.get(category, key, def, comment).getDoubleList();

            field.set(instance, value);
        } else if (type == String.class) {
            //String
            Object def = field.get(instance);
            Object value = config.get(category, key, (String) def, comment).getString();

            field.set(instance, value);
        } else if (type.isArray() && type.getComponentType() == String.class) {
            //String[]
            String[] def = ((String[]) field.get(instance));
            String[] value = config.get(category, key, def, comment).getStringList();

            field.set(instance, value);
        } else if (type == Integer.class || type == int.class) {
            //Integer and int
            Object def = field.get(instance);
            Object value = config.get(category, key, (Integer) def, comment).getInt((Integer) def);

            field.set(instance, value);
        } else if (type.isArray() && type.getComponentType() == int.class) {
            //int[]
            int[] def = ((int[]) field.get(instance));
            int[] value = config.get(category, key, def, comment).getIntList();

            field.set(instance, value);
        } else if (type == float.class || type == Float.class) {
            //float and Float
            Object def = field.get(instance);
            String value = config.get(category, key, String.valueOf(def), comment).getString();

            Float actual = Float.valueOf(value);
            field.set(instance, actual);
        } else if (type.isArray() && type.getComponentType() == float.class) {
            //float[]
            float[] def = ((float[]) field.get(instance));
            String[] stringDef = new String[def.length];
            for (int i = 0; i < def.length; i++) stringDef[i] = String.valueOf(def[i]);

            String[] value = config.get(category, key, stringDef, comment).getStringList();
            float[] actual = new float[value.length];
            for (int i = 0; i < value.length; i++) {
                actual[i] = Float.valueOf(value[i]);
            }

            field.set(instance, actual);
        }
    }

    private void parseClass(@NotNull Object instance, @NotNull Configuration config) {
        Class<?> clazz = instance.getClass();

        @Nullable ConfigurationClass annotation = clazz.getAnnotation(ConfigurationClass.class);
        if (annotation == null) {
            return;
        }

        String category = (annotation.category().isEmpty()) ? clazz.getSimpleName() : annotation.category();

        if (clazz.isAnnotationPresent(ConfigurationComment.class)) {
            config.getCategory(category).setComment(clazz.getAnnotation(ConfigurationComment.class).value());
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAccessible()) field.setAccessible(true);
            ConfigurationComment commentAnnotation = field.getAnnotation(ConfigurationComment.class);
            String comment = (commentAnnotation == null) ? "" : commentAnnotation.value();
            String key = field.getName();

            try {
                parseField(field, instance, category, key, comment);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Exclude {
    }
}
