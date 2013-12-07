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
 * @Author: Cayde
 */
@SuppressWarnings("UnusedDeclaration")
public class Recipe {
    char[][] recipe = new char[3][3];
    HashMap<Character, Object> characterMap = Maps.newHashMap();
    char currentChar = 'a';
    ItemStack produces;

    public Recipe(Item item) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                recipe[row][col] = currentChar;
            }
        }

        characterMap.put(currentChar, item);
        currentChar++;
    }

    public Recipe(Block block) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                recipe[row][col] = currentChar;
            }
        }

        characterMap.put(currentChar, block);
        currentChar++;
    }

    public Recipe(String oreDict) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                recipe[row][col] = currentChar;
            }
        }

        characterMap.put(currentChar, oreDict);
        currentChar++;
    }

    public Recipe cross(Item item) {
        return cross(5, item);
    }

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

    public Recipe cross(Block block) {
        return cross(5, block);
    }

    public Recipe cross(int center, Block block) {
        int row = (int) floor((center - 1) / 3);
        int col = ((center - 1) % 3);

        int l = 3;
        int h = 3;

        for (int i = 0; i < l; i++)
            recipe[row][i] = currentChar;

        for (int i = 0; i < h; i++)
            recipe[i][col] = currentChar;


        characterMap.put(currentChar, block);
        return this;
    }

    public Recipe cross(String oreDict) {
        return cross(5, oreDict);
    }

    public Recipe cross(int center, String oreDict) {
        int row = (int) floor((center - 1) / 3);
        int col = ((center - 1) % 3);

        int l = 3;
        int h = 3;

        for (int i = 0; i < l; i++)
            recipe[row][i] = currentChar;

        for (int i = 0; i < h; i++)
            recipe[i][col] = currentChar;


        characterMap.put(currentChar, oreDict);
        return this;
    }

    public Recipe blank(int pos) {
        int row = (int) floor((pos - 1) / 3);
        int col = ((pos - 1) % 3);

        recipe[row][col] = ' ';
        characterMap.put(' ', null);

        return this;
    }

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

    public Recipe setProduces(ItemStack result) {
        produces = result;
        return this;
    }

    public Recipe produces(ItemStack result) {
        return setProduces(result);
    }

    public Recipe produces(Item item) {
        return produces(new ItemStack(item));
    }

    public Recipe produces(Block result) {
        return produces(new ItemStack(result));
    }

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
            objs.add(entry.getKey());
            objs.add(entry.getValue());
        }

        System.out.println(Arrays.toString(objs.toArray()));
        GameRegistry.addRecipe(new ShapedOreRecipe(produces, objs.toArray()));
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