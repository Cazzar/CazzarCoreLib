package net.cazzar.corelib.items;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.registry.GameRegistry;
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

    public Recipe cross(Item item) {
        return cross(5, item);
    }

    private Recipe cross(int center, Item item) {
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

    public Recipe setProduces(ItemStack result) {
        produces = result;
        return this;
    }

    public void setRecipe() {
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
}


/*
[ [1, 2, 3],
  [4, 5, 6],
  [7, 8, 9] ]
 */