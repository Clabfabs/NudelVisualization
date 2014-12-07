package com.example.nudelvisualization.client;

import java.io.File;
import java.util.HashMap;

import com.example.nudelvisualization.shared.TripleHashMap;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side for the RPC service.
 */
@RemoteServiceRelativePath("saveConfig")
public interface saveConfig extends RemoteService {
	void getConfigAsFile(Configuration config);
	Configuration setConfigFromFile();
}
