package com.github.Mev10.common.blockentities;

import com.github.Mev10.Tfcfreezer;
import com.github.Mev10.common.capabilities.DelegateEnergyStorage;
import com.github.Mev10.common.capabilities.EnergyStorageCallback;
import com.github.Mev10.common.capabilities.InventoryConsumerEnergyStorage;
import com.github.Mev10.common.container.RefrigeratorContainer;
import com.github.Mev10.common.item.TfcfreezerFoodTraits;
import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.capabilities.InventoryItemHandler;
import net.dries007.tfc.common.capabilities.PartialItemHandler;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RefrigeratorBlockEntity extends ApplianceBlockEntity<RefrigeratorBlockEntity.RefrigeratorInventory> implements EnergyStorageCallback {
    public static final int SLOTS = 27;
    private static final Component NAME = Component.translatable(String.format("block.%s.refrigerator", Tfcfreezer.MOD_ID));

    private boolean prevRefrigerationState = false; // 记录前一次制冷状态

    public RefrigeratorBlockEntity(BlockPos pos, BlockState state) {
        super(TfcfreezerBlocksEntities.REFRIGERATOR_BLOCK.get(), pos, state, RefrigeratorInventory::new, NAME,true,20);

        // 初始设置提取规则
        updateExtractionRules();
    }

    // 添加：根据制冷状态更新物品提取规则
    private void updateExtractionRules() {
        if (canRefrigerate()) {
            // 制冷时禁止所有方向提取
            sidedInventory.on(new PartialItemHandler(inventory).extractAll(), d -> false);
        } else {
            // 非制冷时允许下方提取
            sidedInventory.on(new PartialItemHandler(inventory).extractAll(), d -> d == Direction.DOWN);
            sidedInventory.on(new PartialItemHandler(inventory).insertAll(), d -> d != Direction.DOWN);
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, RefrigeratorBlockEntity refrigerator)
    {
        refrigerator.checkForLastTickSync();
        if (level.getGameTime() % 2 == 0)
        {
            boolean currentState = refrigerator.canRefrigerate();

            if(refrigerator.canRefrigerate()){
                refrigerator.consumeEnergyForTicks(2);
                if(refrigerator.inventory.energyStorage.getEnergyStored() < refrigerator.energyTickConsumption){
                    refrigerator.setActivity(false);
                    refrigerator.removeRefrigeratorTraitFromInventory();
                }
            }
            else if(refrigerator.isTurnedOn() && !refrigerator.isActive()){
                if(refrigerator.inventory.energyStorage.getEnergyStored() >= refrigerator.energyTickConsumption){
                    refrigerator.setActivity(true);
                    refrigerator.applyRefrigeratorTraitToInventory();
                    refrigerator.consumeEnergyForTicks(2);
                }
            }

            // 检查状态是否变化，如果变化则更新提取规则
            if (refrigerator.prevRefrigerationState != currentState) {
                refrigerator.updateExtractionRules();
                refrigerator.prevRefrigerationState = currentState;
            }

            refrigerator.markForSync();
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int windowID, Inventory inv, Player player) {
        return RefrigeratorContainer.create(this, inv, windowID);
    }

    public boolean canRefrigerate()
    {
        return isTurnedOn() && isActive();
    }

    @Override
    public void toggleAppliance() {
        if(isTurnedOn()){
            turnOff();
        }else{
            turnOn();
        }
        markForSync();
    }

    @Override
    public void ejectInventory() {
        turnOff();
        super.ejectInventory();
    }

    @Override
    public void energyLevelChanged()
    {
    }

    @Override
    protected void turnOff() {
        super.turnOff();
        removeRefrigeratorTraitFromInventory();
    }

    @Override
    protected void turnOn() {
        super.turnOn();
        if(canRefrigerate()){
            applyRefrigeratorTraitToInventory();
        }
    }

    private void applyRefrigeratorTraitToInventory(){
        inventory.applyRefrigeratorTraitToInventory();
    }

    private void removeRefrigeratorTraitFromInventory(){
        inventory.removeRefrigeratorTraitFromInventory();
    }

    public static class RefrigeratorInventory extends InventoryItemHandler implements DelegateEnergyStorage, INBTSerializable<CompoundTag> {
        private static final int CAPACITY = 10000;
        private static final int MAX_TRANSFER = 100;

        private final RefrigeratorBlockEntity refrigerator;
        private final InventoryConsumerEnergyStorage energyStorage;

        public RefrigeratorInventory(InventoryBlockEntity<RefrigeratorInventory> entity)
        {
            super(entity, SLOTS);
            refrigerator = (RefrigeratorBlockEntity) entity;
            energyStorage = new InventoryConsumerEnergyStorage(CAPACITY, MAX_TRANSFER, (EnergyStorageCallback) entity);
        }

        @Deprecated
        @Override
        public void setStackInSlot(int slot, ItemStack stack)
        {
            if(refrigerator.canRefrigerate() && !FoodCapability.hasTrait(stack, TfcfreezerFoodTraits.REFRIGERATING)){
                super.setStackInSlot(slot, FoodCapability.applyTrait(stack.copy(), TfcfreezerFoodTraits.REFRIGERATING));
            }else{
                super.setStackInSlot(slot, stack.copy());
            }
        }

        @Override
        public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
            if(refrigerator.canRefrigerate() && !FoodCapability.hasTrait(stack, TfcfreezerFoodTraits.REFRIGERATING)){
                return super.insertItem(slot, FoodCapability.applyTrait(stack.copy(), TfcfreezerFoodTraits.REFRIGERATING), simulate);
            }else{
                return  super.insertItem(slot, stack.copy(), simulate);
            }
        }

        @NotNull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            // 制冷状态下禁止物品取出
            if (refrigerator.canRefrigerate()) {
                return ItemStack.EMPTY;
            }

            // 非制冷状态正常取出
            return FoodCapability.removeTrait(super.extractItem(slot, amount, simulate).copy(), TfcfreezerFoodTraits.REFRIGERATING);
        }

        public void applyRefrigeratorTraitToInventory(){
            for (int i = 0; i < SLOTS; i++)
            {
                super.setStackInSlot(i, FoodCapability.applyTrait(super.getStackInSlot(i).copy(), TfcfreezerFoodTraits.REFRIGERATING));
            }
        }

        public void removeRefrigeratorTraitFromInventory(){
            for (int i = 0; i < SLOTS; i++)
            {
                super.setStackInSlot(i, FoodCapability.removeTrait(super.getStackInSlot(i).copy(), TfcfreezerFoodTraits.REFRIGERATING));
            }
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return isFoodStack(stack) && super.isItemValid(slot, stack);
        }

        @Override
        public IEnergyStorage getEnergyStorage() {
            return energyStorage;
        }

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag compoundTag = super.serializeNBT();
            compoundTag.put("EnergyStorage", energyStorage.serializeNBT());
            return compoundTag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            super.deserializeNBT(nbt);
            energyStorage.deserializeNBT(nbt.get("EnergyStorage"));
        }

        public boolean isFoodStack(ItemStack stack){
            return !stack.isEmpty() && FoodCapability.get(stack) != null;
        }
    }
}
