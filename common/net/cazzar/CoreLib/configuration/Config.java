package net.cazzar.corelib.configuration;

import net.cazzar.corelib.configuration.annotations.*;
import net.minecraftforge.common.Configuration;

import java.lang.reflect.Field;

public final class Config {

    /**
     * Parse the passed class for annotated fields with the <i>@ConfigurationOption</i> or a class with
     * <i>@ConfigurationClass</i>
     *
     * @param instance the instance of the configurations option if it is non static
     * @param config
     */
    public static void parse(Object instance, Configuration config) {
        Class clazz = instance.getClass();
        if (clazz.isAnnotationPresent(ConfigurationClass.class)) {
            parseClass(instance, config);
            return;
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAccessible()) field.setAccessible(true);

            ConfigurationOption annotation = field
                    .getAnnotation(ConfigurationOption.class);
            if (annotation == null) {
                continue;
            }

            String category = annotation.category();
            String comment = annotation.comment();
            String key = annotation.key();

            try {
                parseField(field, instance, category, key, comment, config);
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

    private static void parseField(Field field, Object instance,
                                   String category, String key, String comment, Configuration config)
            throws IllegalArgumentException, IllegalAccessException {
        Class<?> type = field.getType();
        if (comment.isEmpty()) comment = null;

        if (type == boolean.class || type == Boolean.class) {
            Object def = field.get(instance);

            Object value;
            value = config.get(category, key, (Boolean) def, comment)
                    .getBoolean((Boolean) def);

            field.set(instance, value);
        } else if (type == double.class || type == Double.class) {
            Object def = field.get(instance);

            Object value;
            value = config.get(category, key, (Double) def, comment).getDouble(
                    (Double) def);

            field.set(instance, value);
        } else if (type == String.class) {
            Object def = field.get(instance);

            Object value;
            value = config.get(category, key, (String) def, comment)
                    .getString();

            field.set(instance, value);
        } else if (type == Integer.class || type == int.class) {
            if (field.isAnnotationPresent(BlockID.class)) {
                Object def = field.get(instance);

                Object value;
                value = config.getBlock(category, key, (Integer) def, comment)
                        .getInt();

                field.set(instance, value);
                return;
            }
            if (field.isAnnotationPresent(ItemID.class)) {
                Object def = field.get(instance);

                Object value;
                value = config.getItem(category, key, (Integer) def, comment)
                        .getInt();

                field.set(instance, value);
                return;
            }

            Object def = field.get(instance);

            Object value;
            value = config.get(category, key, (Integer) def, comment).getInt(
                    (Integer) def);

            field.set(instance, value);
        } else if (type == float.class || type == Float.class) {
            Object def = field.get(instance);
            String value;
            value = config.get(category, key, String.valueOf((Float) def),
                    comment).getString();

            Float actual = Float.valueOf((String) value);
            field.set(instance, actual);
        }
    }

    private static void parseClass(Object instance, Configuration config) {
        Class<? extends Object> clazz = instance.getClass();

        ConfigurationClass annotation = (ConfigurationClass) clazz
                .getAnnotation(ConfigurationClass.class);
        if (annotation == null) {
            return;
        }

        String category = (annotation.category().isEmpty()) ? clazz
                .getSimpleName() : annotation.category();

        if (clazz.isAnnotationPresent(ConfigurationComment.class)) {
            config.getCategory(category).setComment(
                    clazz.getAnnotation(ConfigurationComment.class).value());
        }

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAccessible()) field.setAccessible(true);
            ConfigurationComment commentAnnotation = field
                    .getAnnotation(ConfigurationComment.class);
            String comment = (commentAnnotation == null) ? ""
                    : commentAnnotation.value();
            String key = field.getName();

            try {
                parseField(field, instance, category, key, comment, config);
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
}
