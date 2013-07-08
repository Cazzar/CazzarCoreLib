package net.cazzar.corelib.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomRecord extends ItemRecord {
	
	private static HashMap<String, ItemCustomRecord>	records	= new HashMap<String, ItemCustomRecord>();
	
	public static Map<String, ItemCustomRecord> getRecords() {
		return records;
	}
	
	String		recordInfo;
	
	String[]	details;
	
	public ItemCustomRecord(int ID, String recordFile, String recordInfo,
			String... details) {
		super(ID, recordFile);
		
		this.recordInfo = recordInfo;
		this.details = details;
		setUnlocalizedName("record");
		// registerSong(recordFile);
		records.put(recordName, this);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack is, EntityPlayer par2EntityPlayer,
			List list, boolean par4) {
		list.add(getRecordTitle());
		for (final String s : details)
			list.add(s);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getRecordTitle() {
		return recordInfo;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister register) {
		itemIcon = register.registerIcon("cazzar:record_" + recordName);
	}
}
