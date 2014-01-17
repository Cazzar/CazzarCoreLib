package net.cazzar.corelib.client.gui

import net.minecraft.item.ItemStack
import net.minecraft.inventory.{Slot, IInventory}

class RestrictedSlot(par1IInventory: IInventory, index: Int, xPosition: Int, yPosition: Int) extends Slot(par1IInventory, index, xPosition, yPosition){
  private[gui] var allowed: List[Class] = null


  /**
   * @param par1IInventory The inventory relating to the slot
   * @param index          the index of the slot
   * @param xPosition      the x position of the slot
   * @param yPosition      the position of the slot
   * @param allowed        the List that is allowed for the slot itself.
   */
  def this(par1IInventory: IInventory, index: Int, xPosition: Int, yPosition: Int, allowed: List[Class]) {
    this(par1IInventory, index, xPosition, yPosition)
    this.allowed = allowed
  }

  override def isItemValid(item: ItemStack): Boolean = {
    allowed.foreach((c) => if (item.getItem.getClass.isAssignableFrom(c)) return true)
    false
  }
}
