package net.cazzar.corelib.items;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.floor;

/**
 * A recipe helper class for FML
 * <p/>
 * <p/>
 * For the centre points reference:
 * <p/>
 * [1, 2, 3] <br/> [4, 5, 6] <br/> [7, 8, 9]
 */
@SuppressWarnings("UnusedDeclaration")
public class Recipe {
    char[][] recipe = new char[3][3];
    HashMap<Character, Object> characterMap = Maps.newHashMap();
    char currentChar = 'a';
    ItemStack produces;

    /**
     * Initialise the canvas with the specified Item
     *
     * @param item the item for the canvas
     */
    public Recipe(Item item) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                recipe[row][col] = currentChar;
            }
        }

        characterMap.put(currentChar, item);
        currentChar++;
    }

    /**
     * Initialise the canvas with the specified Block
     *
     * @param block the block for the canvas
     */
    public Recipe(Block block) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                recipe[row][col] = currentChar;
            }
        }

        characterMap.put(currentChar, block);
        currentChar++;
    }

    /**
     * Initialise the canvas with the specified Oredictionary item
     *
     * @param oreDict the item for the canvas
     */
    public Recipe(String oreDict) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                recipe[row][col] = currentChar;
            }
        }

        characterMap.put(currentChar, oreDict);
        currentChar++;
    }

    /**
     * Generate a cross at the centre point
     *
     * @param item the item to generate the cross at
     *
     * @return {@link this} for easy chaining
     */
    public Recipe cross(Item item) {
        return cross(5, item);
    }

    /**
     * Generate a cross at the specified point
     *
     * @param item   the item to generate the cross at
     * @param center the centre point for the cross
     *
     * @return {@link this} for easy chaining
     */
    public Recipe cross(int center, Item item) {
        int row = (int) floor((center - 1) / 3);
        int col = ((center - 1) % 3);

        int l = 3;
        int h = 3;

        for (int i = 0; i < l; i++)
            recipe[row][i] = currentChar;

        for (int i = 0; i < h; i++)
            recipe[i][col] = currentChar;


        characterMap.put(currentChar, item);
        return this;
    }

    /**
     * Generate a cross at the specified point
     *
     * @param block the block to generate the cross at
     *
     * @return {@link this} for easy chaining
     */
    public Recipe cross(Block block) {
        return cross(5, block);
    }

    /**
     * Generate a cross at the specified point
     *
     * @param block  the block to generate the cross at
     * @param center the centre point for the cross
     *
     * @return {@link this} for easy chaining
     */
    public Recipe cross(int center, Block block) {
        int row = (int) floor((center - 1) / 3);
        int col = ((center - 1) % 3);

        for (int i = 0; i < 3; i++)
            recipe[row][i] = currentChar;

        for (int i = 0; i < 3; i++)
            recipe[i][col] = currentChar;


        characterMap.put(currentChar, block);
        return this;
    }

    /**
     * Generate a cross at the specified point
     *
     * @param oreDict the OreDictionary item to generate the cross at
     *
     * @return {@link this} for easy chaining
     */
    public Recipe cross(String oreDict) {
        return cross(5, oreDict);
    }

    /**
     * Generate a cross at the specified point
     *
     * @param oreDict the OreDictionary item to generate the cross at
     * @param center  the centre point for the cross
     *
     * @return {@link this} for easy chaining
     */
    public Recipe cross(int center, String oreDict) {
        int row = (int) floor((center - 1) / 3);
        int col = ((center - 1) % 3);

        for (int i = 0; i < 3; i++)
            recipe[row][i] = currentChar;

        for (int i = 0; i < 3; i++)
            recipe[i][col] = currentChar;


        characterMap.put(currentChar, oreDict);
        return this;
    }

    /**
     * Remove any items at the position
     *
     * @param pos the position to remove
     *
     * @return {@link this} for easy chaining
     */
    public Recipe blank(int pos) {
        int row = (int) floor((pos - 1) / 3);
        int col = ((pos - 1) % 3);

        recipe[row][col] = ' ';
        if (!characterMap.containsKey(' '))
            characterMap.put(' ', null);

        return this;
    }

    public Recipe dot(int pos, Item item) {
        int row = (int) floor((pos - 1) / 3);
        int col = ((pos - 1) % 3);
        return this;
    }

    /**
     * Generate a line at the column/row
     *
     * @param mode the LineMode to generate
     * @param item the item to generate the line at
     * @param idx  the index to generate at it is in the range [0, 2]
     *
     * @return {@link this} for easy chaining
     */
    public Recipe line(LineMode mode, Item item, int idx) {
        switch (mode) {
            case HORIZONTAL:
                for (int i = 0; i < 3; i++) {
                    recipe[idx][i] = currentChar;
                }
                break;
            case VERTICAL:
                for (int i = 0; i < 3; i++) {
                    recipe[i][idx] = currentChar;
                }
        }
        characterMap.put(currentChar, item);
        currentChar++;
        return this;
    }

    /**
     * Generate a line at the column/row
     *
     * @param mode  the LineMode to generate
     * @param block the block to generate the line at
     * @param idx   the index to generate at it is in the range [0, 2]
     *
     * @return {@link this} for easy chaining
     */
    public Recipe line(LineMode mode, Block block, int idx) {
        switch (mode) {
            case HORIZONTAL:
                for (int i = 0; i < 3; i++) {
                    recipe[idx][i] = currentChar;
                }
                break;
            case VERTICAL:
                for (int i = 0; i < 3; i++) {
                    recipe[i][idx] = currentChar;
                }
        }
        characterMap.put(currentChar, block);
        currentChar++;
        return this;
    }

    /**
     * Generate a line at the column/row
     *
     * @param mode    the LineMode to generate
     * @param oreDict the OreDictionary item to generate the line at
     * @param idx     the index to generate at it is in the range [0, 2]
     *
     * @return {@link this} for easy chaining
     */
    public Recipe line(LineMode mode, String oreDict, int idx) {
        switch (mode) {
            case HORIZONTAL:
                for (int i = 0; i < 3; i++) {
                    recipe[idx][i] = currentChar;
                }
                break;
            case VERTICAL:
                for (int i = 0; i < 3; i++) {
                    recipe[i][idx] = currentChar;
                }
        }
        characterMap.put(currentChar, oreDict);
        currentChar++;
        return this;
    }

    /**
     * Set what the recipe produces
     *
     * @param result the result of the recipe
     *
     * @return {@link this} for easy chaining
     */
    public Recipe setProduces(ItemStack result) {
        produces = result;
        return this;
    }

    /**
     * Set what the recipe produces
     *
     * @param result the result of the recipe
     *
     * @return {@link this} for easy chaining
     */
    public Recipe produces(ItemStack result) {
        return setProduces(result);
    }

    /**
     * Set what the recipe produces
     *
     * @param item the result of the recipe
     *
     * @return {@link this} for easy chaining
     */
    public Recipe produces(Item item) {
        return produces(new ItemStack(item));
    }

    /**
     * Set what the recipe produces
     *
     * @param result the result of the recipe
     *
     * @return {@link this} for easy chaining
     */
    public Recipe produces(Block result) {
        return produces(new ItemStack(result));
    }

    /**
     * Register the item in FML
     */
    public void register() {
        ArrayList<Object> objs = new ArrayList<Object>();
        for (char[] row : recipe) {
            StringBuilder stringBuilder = new StringBuilder();
            for (char col : row) {
                stringBuilder.append(col);
            }
            objs.add(stringBuilder.toString());
        }

        for (Map.Entry<Character, Object> entry : characterMap.entrySet()) {
            if (recipeContains(entry.getKey())) {
                objs.add(entry.getKey());
                objs.add(entry.getValue());
            }
        }

        System.out.println(Arrays.toString(objs.toArray()));
        GameRegistry.addRecipe(new ShapedOreRecipe(produces, objs.toArray()));
    }

    private boolean recipeContains(char c) {
        for (char[] i : recipe)
            for (char ii : i)
                if (ii == c) return true;

        return false;
    }

    public enum LineMode {
        HORIZONTAL, VERTICAL
    }
}


/*
[ [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9] ]
 */