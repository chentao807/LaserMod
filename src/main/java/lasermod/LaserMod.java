package lasermod;

import lasermod.api.LaserRegistry;
import lasermod.laser.DamageLaser;
import lasermod.laser.DefaultLaser;
import lasermod.laser.FireLaser;
import lasermod.laser.InvisibleLaser;
import lasermod.laser.PullLaser;
import lasermod.laser.PushLaser;
import lasermod.lib.Reference;
import lasermod.network.NetworkManager;
import lasermod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author ProPercivalalb
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, dependencies = Reference.MOD_DEPENDENCIES)
public class LaserMod {

	@Instance(value = Reference.MOD_ID)
	public static LaserMod instance;
	
	@SidedProxy(clientSide = Reference.SP_CLIENT, serverSide = Reference.SP_SERVER)
    public static CommonProxy proxy;
	
	public static NetworkManager NETWORK_MANAGER;
	
	/** Laser Mod Creative tab **/
	public static CreativeTabs tabLaser = new CreativeTabs("tabLaser") {
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return ModItems.screwdriver;
		}
	};
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		ModBlocks.inti();
		ModItems.inti();
		ModEntities.inti();
		
		proxy.onPreLoad();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		NETWORK_MANAGER = new NetworkManager();
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
			
		proxy.registerHandlers();
	}
	
	@EventHandler
	public void modsLoaded(FMLPostInitializationEvent event) {
		//Registers all lasers
		LaserRegistry.registerLaser("default", new DefaultLaser());
		LaserRegistry.registerLaser("fire", new FireLaser());
		LaserRegistry.registerLaser("invisible", new InvisibleLaser());
		LaserRegistry.registerLaser("push", new PushLaser());
		LaserRegistry.registerLaser("pull", new PullLaser());
		LaserRegistry.registerLaser("damage", new DamageLaser());
				
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 0, LaserRegistry.getLaserFromId("fire"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 3, LaserRegistry.getLaserFromId("invisible"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 5, LaserRegistry.getLaserFromId("push"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 6, LaserRegistry.getLaserFromId("pull"));
		LaserRegistry.registerItemToLaser(ModItems.upgrades, 7, LaserRegistry.getLaserFromId("damage"));
	}
}
