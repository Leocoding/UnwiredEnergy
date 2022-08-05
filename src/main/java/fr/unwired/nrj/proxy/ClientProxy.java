package fr.unwired.nrj.proxy;

import fr.unwired.nrj.util.Config;

public class ClientProxy extends CommonProxy {
	
	public void preInit() {
		super.preInit();
		Config.clientPreInit();
	}
	
	public void init() {
		super.init();
	}

	public void postInit() {
		super.postInit();
	}
	
}
