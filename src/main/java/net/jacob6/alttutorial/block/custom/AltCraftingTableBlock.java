package net.jacob6.alttutorial.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;

public class AltCraftingTableBlock extends CraftingTableBlock {

    public static final Component TITLE = Component.translatable("container.crafting");
    public static final BooleanProperty LIT = BooleanProperty.create("lit");


    public AltCraftingTableBlock(Properties properties) {
        super(properties.lightLevel(state -> state.getValue(AltCraftingTableBlock.LIT) ? 15 : 0));
    }
 
    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
    	if (worldIn.isClientSide) {
            worldIn.setBlock(pos, state.cycle(LIT), 3);
    		return InteractionResult.SUCCESS;
    	} else {
    		player.openMenu(state.getMenuProvider(worldIn, pos));
    		player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
    		return InteractionResult.CONSUME;
    	}
    }
}
