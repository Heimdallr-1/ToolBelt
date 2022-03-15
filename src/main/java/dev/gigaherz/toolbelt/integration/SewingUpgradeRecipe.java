package dev.gigaherz.toolbelt.integration;

import dev.gigaherz.sewingkit.api.SewingRecipe;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

import dev.gigaherz.sewingkit.api.SewingRecipe.Material;

public class SewingUpgradeRecipe extends SewingRecipe
{
    public SewingUpgradeRecipe(ResourceLocation id, String group, NonNullList<Material> materials, @Nullable Ingredient pattern, @Nullable Ingredient tool, ItemStack output)
    {
        super(id, group, materials, pattern, tool, output);
    }

    @Override
    public ItemStack assemble(IInventory inv)
    {
        ItemStack inputBelt = ItemStack.EMPTY;
        for (int i = 2; i < 6; i++)
        {
            if (inv.getItem(i).getItem() instanceof ToolBeltItem)
            {
                inputBelt = inv.getItem(i);
                break;
            }
        }
        ItemStack upgradedBelt = super.assemble(inv);
        if (inputBelt.getCount() > 0)
        {
            CompoundNBT inputTag = inputBelt.getTag();
            if (inputTag != null)
            {
                CompoundNBT tag = upgradedBelt.getOrCreateTag();
                upgradedBelt.setTag(inputTag.copy().merge(tag));
            }
        }
        return upgradedBelt;
    }

    public static class Serializer extends SewingRecipe.Serializer
    {
        @Override
        protected SewingRecipe createRecipe(ResourceLocation recipeId, String group, NonNullList<Material> materials, Ingredient pattern, Ingredient tool, ItemStack result)
        {
            return new SewingUpgradeRecipe(recipeId, group, materials, pattern, tool, result);
        }
    }
}
