package main.java.de.xenodev.cbs.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemBuilder {
	private final ItemStack itemStack;

	private ItemMeta itemMeta;

	public ItemStack getItemStack() {
		return this.itemStack;
	}

	public ItemMeta getItemMeta() {
		return this.itemMeta;
	}

	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
		this.itemMeta = this.itemStack.getItemMeta();
	}

	public ItemBuilder(Material material) {
		this.itemStack = new ItemStack(material);
		this.itemMeta = this.itemStack.getItemMeta();
	}

	public ItemBuilder(Material material, int amount) {
		this.itemStack = new ItemStack(material, 1, (short) 0);
		this.itemMeta = this.itemStack.getItemMeta();
		setAmount(Integer.valueOf(amount));
	}

	public ItemBuilder(Material material, short itemData) {
		this.itemStack = new ItemStack(material, 1, itemData);
		this.itemMeta = this.itemStack.getItemMeta();
	}

	public ItemBuilder(Material material, int amount, short itemData) {
		this.itemStack = new ItemStack(material, 1, itemData);
		this.itemMeta = this.itemStack.getItemMeta();
	}

	public ItemBuilder setDisplayName(String displayName) {
		this.itemMeta.setDisplayName(displayName);
		return this;
	}

	public ItemBuilder setAmount(Integer amount) {
		this.itemStack.setAmount(amount.intValue());
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		this.itemMeta.setLore(Arrays.asList(lore));
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		this.itemMeta.setLore(lore);
		return this;
	}

	public ItemBuilder setEnchantment(Enchantment enchantment, Integer level) {
		this.itemMeta.addEnchant(enchantment, level.intValue(), true);
		return this;
	}

	public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
		this.itemStack.addUnsafeEnchantments(enchantments);
		return this;
	}

	public ItemBuilder setItemFlag(ItemFlag itemFlag) {
		this.itemMeta.addItemFlags(new ItemFlag[] { itemFlag });
		return this;
	}
	
	public ItemBuilder setDurability(Short amount) {
		this.itemStack.setDurability(amount);
		return this;
	}
	
	public ItemBuilder setColor(Color color) {
		LeatherArmorMeta meta = (LeatherArmorMeta) this.itemMeta;
		meta.setColor(color);
		return this;
	}

	public ItemBuilder setOwner(String name){
		SkullMeta meta = (SkullMeta) this.itemMeta;
		meta.setOwner(name);
		return this;
	}

	public ItemBuilder setValueOwner(String url) {
		ItemStack head = this.itemStack;
		if(url.isEmpty()) return this;


		SkullMeta headMeta = (SkullMeta) this.itemMeta;
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
		byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
		Field profileField = null;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		head.setItemMeta(headMeta);
		return this;
	}

	public ItemStack build() {
		this.itemStack.setItemMeta(this.itemMeta);
		return this.itemStack;
	}
}
